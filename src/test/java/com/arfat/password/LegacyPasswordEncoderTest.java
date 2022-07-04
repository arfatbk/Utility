package com.arfat.password;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Arfat Chaus on 5/23/22
 */
@ExtendWith(MockitoExtension.class)
class LegacyPasswordEncoderTest {

    private static final String VALID_LEGACY_HASH = "$2a$10$ydyJiK50sNYQYIS.yX1Q4ubqLbhvMk3a.jaF4yAQq4EJFFWPh0Uxi";

    @Mock
    PasswordUpgrade passwordUpgrade;

    @Test
    @DisplayName("should return supports true for passwords without any prefix")
    public void shouldReturnSupportsTrueForPasswordsWithoutAnyPrefix(){
        DelegatingPasswordEncoder passwordEncoder = (DelegatingPasswordEncoder) PasswordEncoders.delegatingPasswordEncoder();
        assertTrue(passwordEncoder.supports("rawPasswordWithoutAnyPrefix-==##3345oy83y98343"));
    }


    @Test
    @DisplayName("should match the hash for provided password using legacy encoder")
    public void shouldMatchTheHashForProvidedPassword() throws NoSuchAlgorithmException {
        PasswordEncoder passwordEncoder = PasswordEncoders.delegatingPasswordEncoder();
        String hash = generateLegacyHash("password");
        assertTrue(passwordEncoder.matches("password", hash), "password should match hash");
    }

    @Test
    @DisplayName("should return false for invalid password hash")
    public void shouldReturnFalseForInvalidPasswordHash() {
        PasswordEncoder passwordEncoder = PasswordEncoders.delegatingPasswordEncoder();
        String hash = passwordEncoder.encode("wrong");
        assertFalse(passwordEncoder.matches("password", hash), "supports should be false");
    }

    @Test
    @DisplayName("should return supports true for legacy encoder")
    public void shouldReturnSupportsTrueForCustomEncoder() {
        DelegatingPasswordEncoder passwordEncoder = (DelegatingPasswordEncoder) PasswordEncoders.delegatingPasswordEncoder();
        assertTrue(passwordEncoder.supports(VALID_LEGACY_HASH), "supports should be true");
    }

    @Test
    @DisplayName("should throw UnsupportedOperationException for encoding")
    @SuppressWarnings("deprecation")
    public void shouldThrowUnsupportedOperationExceptionForEncoding() {
        LegacyPasswordEncoder passwordEncoder = new LegacyPasswordEncoder();
        assertThrows(UnsupportedOperationException.class, () -> {
                    passwordEncoder.encode("password");
                },
                "should throw UnsupportedOperationException for encoding");
    }

    @Test
    @DisplayName("should return true for shouldUpgrade()")
    @SuppressWarnings("deprecation")
    public void shouldReturnTrueForShouldUpgrade() {
        LegacyPasswordEncoder passwordEncoder = new LegacyPasswordEncoder();
        assertTrue(passwordEncoder.shouldUpgrade(),"should return true for shouldUpgrade()");
    }

    @Test
    @DisplayName("should upgrade password using given Function")
    @SuppressWarnings("deprecation")
    public void shouldUpgradePasswordUsingGivenFunction() throws NoSuchAlgorithmException {
        PasswordEncoder passwordEncoder = PasswordEncoders.delegatingPasswordEncoder();
        String hash = generateLegacyHash("password");

        when(passwordUpgrade.apply(any())).thenReturn(true);

        assertTrue(passwordEncoder.matches("password", hash, passwordUpgrade),"should return true for shouldUpgrade()");
        verify(passwordUpgrade).apply(any());
    }


    @Test
    @DisplayName("should upgrade password using given Lambda")
    @SuppressWarnings("deprecation")
    public void shouldUpgradePasswordUsingGivenLambda() throws NoSuchAlgorithmException {
        PasswordEncoder passwordEncoder = PasswordEncoders.delegatingPasswordEncoder();
        String hash = generateLegacyHash("password");
        assertTrue(passwordEncoder.matches("password",hash, encodedPassword -> {
            //Update new encoded password to Storage
            return true;
        }),"should return true for shouldUpgrade()");
    }


    private String generateLegacyHash(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        return new String(digest);
    }

    class PasswordUpgrade implements Function<String,Boolean> {

        @Override
        public Boolean apply(String encodedPassword) {
            //Update encodedPassword to Storage

            //Return 'true' -> Update operation succeeded
            //Return 'false' -> Update operation failed
            //this will return will not stop Password encoder from matching password,
            // It's just here to mark failure in storing new password
            return false;
        }
    }
}