package com.arfat.password;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Arfat Chaus on 6/21/22
 */
class BCryptPasswordEncoderTest {

    @Test
    @DisplayName("should return false for shouldUpgrade()")
    public void shouldReturnFalseForShouldUpgrade() {
        PasswordEncoder passwordEncoder = PasswordEncoders.withBCrypt();
        assertFalse(passwordEncoder.shouldUpgrade(),"should return false for shouldUpgrade()");
    }
}