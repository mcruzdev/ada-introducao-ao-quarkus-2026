package com.ada.challenge.scoring;

import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.TestPlan;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Calculates and reports test scores based on test results
 */
public class ScoreCalculator implements TestExecutionListener {
    
    // Static fields to share data across all instances
    private static int totalMandatoryPoints = 0;
    private static int earnedMandatoryPoints = 0;
    private static int totalOptionalPoints = 0;
    private static int earnedOptionalPoints = 0;
    
    private static final Map<String, CategoryScore> categoryScores = new LinkedHashMap<>();
    private static final List<TestResult> testResults = new ArrayList<>();
    private static boolean reportPrinted = false;
    
    private static class CategoryScore {
        String name;
        int totalPoints = 0;
        int earnedPoints = 0;
        int totalTests = 0;
        int passedTests = 0;
        
        CategoryScore(String name) {
            this.name = name;
        }
    }
    
    private static class TestResult {
        String testName;
        String description;
        int points;
        boolean passed;
        boolean mandatory;
        String category;
        
        TestResult(String testName, String description, int points, boolean passed, boolean mandatory, String category) {
            this.testName = testName;
            this.description = description;
            this.points = points;
            this.passed = passed;
            this.mandatory = mandatory;
            this.category = category;
        }
    }
    
    @Override
    public void testPlanExecutionFinished(TestPlan testPlan) {
        // Print report when all tests are finished (only once)
        synchronized (ScoreCalculator.class) {
            if (!reportPrinted) {
                printReport();
                reportPrinted = true;
            }
        }
    }
    
    @Override
    public void executionFinished(TestIdentifier testIdentifier, TestExecutionResult testExecutionResult) {
        if (testIdentifier.isTest()) {
            String sourceStr = "";
            String methodName = "";
            TestScore score = null;
            
            try {
                sourceStr = testIdentifier.getSource()
                    .map(source -> source.toString())
                    .orElse("");
                
                // Parse MethodSource format: MethodSource [className = 'com.ada.challenge.tests.CRUDOperationsTests', methodName = 'testCreateCourse', methodParameterTypes = '']
                String className = null;
                if (sourceStr.contains("className = '")) {
                    int start = sourceStr.indexOf("className = '") + 13;
                    int end = sourceStr.indexOf("'", start);
                    className = sourceStr.substring(start, end);
                }
                
                if (sourceStr.contains("methodName = '")) {
                    int start = sourceStr.indexOf("methodName = '") + 14;
                    int end = sourceStr.indexOf("'", start);
                    methodName = sourceStr.substring(start, end);
                }
                
                if (className != null && !methodName.isEmpty()) {
                    Class<?> testClass = Class.forName(className);
                    Method method = findMethod(testClass, methodName);
                    
                    if (method != null && method.isAnnotationPresent(TestScore.class)) {
                        score = method.getAnnotation(TestScore.class);
                    }
                }
            } catch (Exception e) {
                // Log error but continue processing
                System.err.println("⚠️  Warning: Error processing test " + methodName + ": " + e.getMessage());
            }
            
            // Process score even if there were errors, as long as we found the annotation
            if (score != null) {
                try {
                    boolean passed = testExecutionResult.getStatus() == TestExecutionResult.Status.SUCCESSFUL;
                    
                    String category = score.category().isEmpty() ? "General" : score.category();
                    CategoryScore catScore = categoryScores.computeIfAbsent(category, CategoryScore::new);
                    
                    catScore.totalTests++;
                    catScore.totalPoints += score.points();
                    
                    if (score.mandatory()) {
                        totalMandatoryPoints += score.points();
                        if (passed) {
                            earnedMandatoryPoints += score.points();
                            catScore.earnedPoints += score.points();
                            catScore.passedTests++;
                        }
                    } else {
                        totalOptionalPoints += score.points();
                        if (passed) {
                            earnedOptionalPoints += score.points();
                            catScore.earnedPoints += score.points();
                            catScore.passedTests++;
                        }
                    }
                    
                    testResults.add(new TestResult(
                        methodName,
                        score.description(),
                        score.points(),
                        passed,
                        score.mandatory(),
                        category
                    ));
                } catch (Exception e) {
                    // Log error in score processing but ensure total points are still counted
                    System.err.println("⚠️  Warning: Error calculating score for test " + methodName + ": " + e.getMessage());
                }
            }
        }
    }
    
    private Method findMethod(Class<?> clazz, String methodName) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
        return null;
    }
    
    public void printReport() {
        StringBuilder report = new StringBuilder();
        
        // Build report content
        report.append("\n").append("=".repeat(80)).append("\n");
        report.append("📊 RELATÓRIO DE PONTUAÇÃO - DESAFIO TÉCNICO ADA\n");
        report.append("=".repeat(80)).append("\n");
        
        // Summary by category
        report.append("\n📦 PONTUAÇÃO POR CATEGORIA:\n");
        report.append("-".repeat(80)).append("\n");
        for (CategoryScore cat : categoryScores.values()) {
            double percentage = cat.totalPoints > 0 ? (cat.earnedPoints * 100.0 / cat.totalPoints) : 0;
            report.append(String.format("%-30s: %3d/%3d pontos (%.1f%%) - %d/%d testes passaram%n",
                cat.name, cat.earnedPoints, cat.totalPoints, percentage, cat.passedTests, cat.totalTests));
        }
        
        // Mandatory points
        report.append("\n").append("=".repeat(80)).append("\n");
        report.append("🔴 PONTOS OBRIGATÓRIOS:\n");
        report.append("-".repeat(80)).append("\n");
        double mandatoryPercentage = totalMandatoryPoints > 0 ? (earnedMandatoryPoints * 100.0 / totalMandatoryPoints) : 0;
        report.append(String.format("Total: %d/%d pontos (%.1f%%)%n", earnedMandatoryPoints, totalMandatoryPoints, mandatoryPercentage));
        
        // Optional points
        report.append("\n🟢 PONTOS OPCIONAIS (PLUS):\n");
        report.append("-".repeat(80)).append("\n");
        double optionalPercentage = totalOptionalPoints > 0 ? (earnedOptionalPoints * 100.0 / totalOptionalPoints) : 0;
        report.append(String.format("Total: %d/%d pontos (%.1f%%)%n", earnedOptionalPoints, totalOptionalPoints, optionalPercentage));
        
        // Grand total
        report.append("\n").append("=".repeat(80)).append("\n");
        report.append("🎯 PONTUAÇÃO FINAL:\n");
        report.append("-".repeat(80)).append("\n");
        int totalEarned = earnedMandatoryPoints + earnedOptionalPoints;
        int totalPossible = totalMandatoryPoints + totalOptionalPoints;
        report.append(String.format("TOTAL: %d/%d pontos%n", totalEarned, totalPossible));
        report.append(String.format("Obrigatórios: %d/100 pontos (%.1f%%)%n", earnedMandatoryPoints, mandatoryPercentage));
        report.append(String.format("Opcionais: %d/%d pontos extras%n", earnedOptionalPoints, totalOptionalPoints));
        
        // Detailed results
        report.append("\n").append("=".repeat(80)).append("\n");
        report.append("📋 DETALHAMENTO DOS TESTES:\n");
        report.append("-".repeat(80)).append("\n");
        
        Map<String, List<TestResult>> resultsByCategory = new LinkedHashMap<>();
        for (TestResult result : testResults) {
            resultsByCategory.computeIfAbsent(result.category, k -> new ArrayList<>()).add(result);
        }
        
        for (Map.Entry<String, List<TestResult>> entry : resultsByCategory.entrySet()) {
            report.append("\n").append(entry.getKey()).append(":\n");
            for (TestResult result : entry.getValue()) {
                String status = result.passed ? "✅ PASS" : "❌ FAIL";
                String type = result.mandatory ? "🔴" : "🟢";
                report.append(String.format("  %s %s [%d pts] %s - %s%n",
                    status, type, result.points, result.testName, result.description));
            }
        }
        
        report.append("\n").append("=".repeat(80)).append("\n");
        report.append("Legenda: 🔴 Obrigatório | 🟢 Opcional (Plus)\n");
        report.append("=".repeat(80)).append("\n\n");
        
        // Print to console
        System.out.print(report.toString());
        
        // Save to file
        saveReportToFile(report.toString(), totalEarned, totalPossible);
    }
    
    private void saveReportToFile(String reportContent, int totalEarned, int totalPossible) {
        try {
            // Create reports directory if it doesn't exist
            Path reportsDir = Paths.get("test-reports");
            if (!Files.exists(reportsDir)) {
                Files.createDirectories(reportsDir);
            }
            
            // Generate filename with timestamp
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
            String filename = String.format("test-reports/score-report_%s_%d-of-%d.txt", timestamp, totalEarned, totalPossible);
            
            // Write report to file
            try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
                writer.print(reportContent);
            }
            
            System.out.println("📄 Relatório salvo em: " + filename);
            
            // Also save a "latest" version for easy access
            String latestFilename = "test-reports/score-report_latest.txt";
            try (PrintWriter writer = new PrintWriter(new FileWriter(latestFilename))) {
                writer.print(reportContent);
            }
            System.out.println("📄 Última versão salva em: " + latestFilename);
            
        } catch (IOException e) {
            System.err.println("⚠️  Aviso: Não foi possível salvar o relatório em arquivo: " + e.getMessage());
        }
    }
}

// Made with Bob
