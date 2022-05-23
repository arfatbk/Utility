package com.fable.password;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Arfat Chaus on 5/23/22
 */
class PasswordEncoderTest {

    private static final String VALID_BCRYPT_HASH = "{bcrypt}$2a$10$ydyJiK50sNYQYIS.yX1Q4ubqLbhvMk3a.jaF4yAQq4EJFFWPh0Uxi";

    @Test
    @DisplayName("should return a delegating password instance")
    public void shouldReturnADelegatingPasswordInstance() {
        PasswordEncoder passwordEncoder = PasswordEncoder.delegatingPasswordEncoder();
        assertNotNull(passwordEncoder, "passwordEncoder should not be null");
    }

    @Test
    @DisplayName("should return hash for provided password")
    public void shouldReturnHashForProvidedPassword() {
        PasswordEncoder passwordEncoder = PasswordEncoder.delegatingPasswordEncoder();
        String hash = passwordEncoder.encode("password");
        assertNotNull(hash, "hash should not be null");
        assertNotEquals("password", hash, "hash should not be equal to password");
    }

    @Test
    @DisplayName("should match the hash for provided password")
    public void shouldMatchTheHashForProvidedPassword() {
        PasswordEncoder passwordEncoder = PasswordEncoder.delegatingPasswordEncoder();
        String hash = passwordEncoder.encode("password");
        assertTrue(passwordEncoder.matches("password", hash), "password should match hash");
    }

    @Test
    @DisplayName("should return false for invalid password hash")
    public void shouldReturnFalseForInvalidPasswordHash() {
        PasswordEncoder passwordEncoder = PasswordEncoder.delegatingPasswordEncoder();
        String hash = passwordEncoder.encode("wrong");
        assertFalse(passwordEncoder.matches("password", hash), "supports should be false");
    }

    @Test
    @DisplayName("should return supports true for bcrypt encoder")
    public void shouldReturnSupportsTrueForCustomEncoder() {
        PasswordEncoder passwordEncoder = PasswordEncoder.delegatingPasswordEncoder();
        assertTrue(passwordEncoder.supports(VALID_BCRYPT_HASH), "supports should be true");
    }

    @Test
    @DisplayName("should throw exception for invalid password hash")
    public void shouldReturnSupportsFalseForInvalidPasswordHash() {
        PasswordEncoder passwordEncoder = PasswordEncoder.delegatingPasswordEncoder();
        assertThrows(IllegalArgumentException.class,
                () -> passwordEncoder.supports("{invalid}sdgfhjk"),
                "Should throw exception for invalid password hash");
    }

}