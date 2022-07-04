package com.arfat.password;

import java.util.function.Function;

/**
 * Service interface for encoding passwords.
 *  The preferred implementation is {@code BCryptPasswordEncoder}.
 *
 * @author Arfat Chaus on 5/23/22
 * @version 1.0
 */
public interface PasswordEncoder {

    /**
     * Encode the raw password. Generally, a good encoding algorithm applies an SHA-1 or
     * greater hash combined with an 8-byte or greater randomly generated salt.
     */
    String encode(CharSequence rawPassword);

    /**
     * Verify the encoded password obtained from storage matches the submitted raw
     * password after it too is encoded. Returns true if the passwords match, false if
     * they do not. The stored password itself is never decoded.
     * @param rawPassword the raw password to encode and match
     * @param encodedPassword the encoded password from storage to compare with
     * @return true if the raw password, after encoding, matches the encoded password from
     * storage
     */
    boolean matches(CharSequence rawPassword, String encodedPassword);

    /**
     *  @return true if the passwordEncoder is legacy and should be decommissioned
     *  User should supply a {@link java.util.function.Supplier} in case the 'legacy password' has to be upgraded
     */
    boolean shouldUpgrade();

    /**
     * Verify the encoded password obtained from storage matches the submitted raw
     * password after it too is encoded. Returns true if the passwords match, false if
     * they do not. The stored password itself is never decoded.
     * @param rawPassword the raw password to encode and match
     * @param encodedPassword the encoded password from storage to compare with
     * @return true if the raw password, after encoding, matches the encoded password from
     * storage
     */
    boolean matches(CharSequence rawPassword, String encodedPassword, Function<String, Boolean> passwordUpgrade);
}
