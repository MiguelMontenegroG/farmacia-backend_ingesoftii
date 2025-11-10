package com.farmacia;

import com.farmacia.security.SecurityConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SecurityConfigTest {

    private SecurityConfig securityConfig;

    @BeforeEach
    void setUp() {
        securityConfig = new SecurityConfig();
    }

    @Test
    void testSecurityConfigCreation() {
        // Simple test to verify that SecurityConfig can be instantiated
        assertNotNull(securityConfig, "SecurityConfig debe poder instanciarse");
    }
}