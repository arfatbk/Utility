package com.arfat.password;

/**
 * @author Arfat Chaus on 5/30/22
 */
interface DelegatingPasswordEncoder extends PasswordEncoder {
    String DEFAULT_ID_PREFIX = "{";
    String DEFAULT_ID_SUFFIX = "}";


    /**
     * Returns true if it supports the prefix.
     */
    boolean supports(CharSequence rawPassword);

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
}
