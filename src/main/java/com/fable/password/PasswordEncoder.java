package com.fable.password;

/**
 * Service interface for encoding passwords.
 *
 *  The preferred implementation is {@code BCryptPasswordEncoder}.
 *
 * @author Arfat Chaus on 5/23/22
 * @version 1.0
 */
public interface PasswordEncoder {

    String DEFAULT_ID_PREFIX = "{";
    String DEFAULT_ID_SUFFIX = "}";

    /**
     * Encode the raw password. Generally, a good encoding algorithm applies a SHA-1 or
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
     * Returns true if it supports the prefix.
     */
    boolean supports(CharSequence rawPassword);


    /**
     * Returns an instance of DelegatingPasswordEncoder which encodes passwords using
     * the random {@link PasswordEncoder} using delegation.
     *
     * @see DelegatingPasswordEncoder
     */
    static PasswordEncoder delegatingPasswordEncoder() {
        return new DelegatingPasswordEncoder();
    }

    default String extractId(String prefixEncodedPassword) {
        if (prefixEncodedPassword == null) {
            return null;
        } else {
            int start = prefixEncodedPassword.indexOf(DEFAULT_ID_PREFIX);
            if (start != 0) {
                return null;
            } else {
                int end = prefixEncodedPassword.indexOf(DEFAULT_ID_SUFFIX, start);
                return end < 0 ? null : prefixEncodedPassword.substring(start + DEFAULT_ID_PREFIX.length(), end);
            }
        }
    }

    static PasswordEncoder withBCrypt(){
        return new BCryptPasswordEncoder();
    }

    static PasswordEncoder withSHA512(){
        return new Sha512PasswordEncoder();
    }
}
