package com.ada.challenge;

import com.ada.challenge.scoring.ScoreCalculator;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;

import static org.junit.platform.engine.discovery.ClassNameFilter.includeClassNamePatterns;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectPackage;

/**
 * Main runner for the test suite that calculates and displays scores
 */
public class TestSuiteRunner {
    
    public static void main(String[] args) {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("🚀 EXECUTANDO TESTES DO DESAFIO TÉCNICO ADA - QUARKUS REST API");
        System.out.println("=".repeat(80) + "\n");
        
        // Create score calculator
        ScoreCalculator scoreCalculator = new ScoreCalculator();
        
        // Build test discovery request
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
            .selectors(selectPackage("com.ada.challenge.tests"))
            .filters(includeClassNamePatterns(".*Tests"))
            .build();
        
        // Create launcher and register listener
        Launcher launcher = LauncherFactory.create();
        launcher.registerTestExecutionListeners(scoreCalculator);
        
        // Execute tests
        launcher.execute(request);
        
        // Print score report
        scoreCalculator.printReport();
    }
}

// Made with Bob
