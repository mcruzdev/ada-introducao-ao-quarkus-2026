package com.ada.challenge.scoring;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to mark test methods with their point values
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TestScore {
    /**
     * Points awarded if test passes
     */
    int points();
    
    /**
     * Weight of the test (for reference)
     */
    double weight();
    
    /**
     * Description of what is being tested
     */
    String description();
    
    /**
     * Whether this is a mandatory requirement or optional (plus)
     */
    boolean mandatory() default true;
    
    /**
     * Category of the test
     */
    String category() default "";
}

// Made with Bob
