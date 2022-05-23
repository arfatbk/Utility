package com.fable.password;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Arfat Chaus on 5/23/22
 */
class LegacyPasswordEncoderTest {

    private static final String VALID_LEGACY_HASH = "$2a$10$ydyJiK50sNYQYIS.yX1Q4ubqLbhvMk3a.jaF4yAQq4EJFFWPh0Uxi";

    @Test
    @DisplayName("should return supports true for passwords without any prefix")
    public void shouldReturnSupportsTrueForPasswordsWithoutAnyPrefix(){
        PasswordEncoder passwordEncoder = PasswordEncoder.delegatingPasswordEncoder();
        assertTrue(passwordEncoder.supports("rawPasswordWithoutAnyPrefix-==##3345oy83y98343"));
    }


    @Test
    @DisplayName("should match the hash for provided password using legacy encoder")
    public void shouldMatchTheHashForProvidedPassword() throws NoSuchAlgorithmException {
        PasswordEncoder passwordEncoder = PasswordEncoder.delegatingPasswordEncoder();
        String hash = generateLegacyHash("password");
        assertTrue(passwordEncoder.matches("password", hash), "password should match hash");
    }

    private String generateLegacyHash(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        return new String(digest);
    }

    @Test
    @DisplayName("should return false for invalid password hash")
    public void shouldReturnFalseForInvalidPasswordHash() {
        PasswordEncoder passwordEncoder = PasswordEncoder.delegatingPasswordEncoder();
        String hash = passwordEncoder.encode("wrong");
        assertFalse(passwordEncoder.matches("password", hash), "supports should be false");
    }

    @Test
    @DisplayName("should return supports true for legacy encoder")
    public void shouldReturnSupportsTrueForCustomEncoder() {
        PasswordEncoder passwordEncoder = PasswordEncoder.delegatingPasswordEncoder();
        assertTrue(passwordEncoder.supports(VALID_LEGACY_HASH), "supports should be true");
    }

}