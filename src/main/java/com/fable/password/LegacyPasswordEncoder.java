package com.fable.password;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.function.Function;

/**
 * For matching older passwords only. Use of the {@link LegacyPasswordEncoder} is not recommended.
 * recommended one is {@link PasswordEncoders#delegatingPasswordEncoder()}.
 *
 * @author Arfat Chaus on 5/23/22
 * @version 1.0
 */
final class LegacyPasswordEncoder implements DelegatingPasswordEncoder {

    /**
     * Override below method as per your existing password encoding logic.
     * @param rawPassword password to be encoded
     * @return encoded password
     */
    @Deprecated
    @Override
    public String encode(CharSequence rawPassword) {
        throw new UnsupportedOperationException("LegacyPasswordEncoder is not supported");
    }

    /**
     * Override below method as per your existing password encoding logic.
     * @param rawPassword the raw password to encode and match
     * @param encodedPassword the encoded password from storage to compare with
     * @return
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        md.update(rawPassword.toString().getBytes());
        byte[] digest = md.digest();
        return new String(digest).equals(encodedPassword);
    }

    @Override
    public boolean shouldUpgrade() {
        return true;
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword, Function<String, Boolean> passwordUpgrade) {
        boolean matches = matches(rawPassword, encodedPassword);
        if (shouldUpgrade()) {
            if (matches) {
                String encoded = PasswordEncoders.delegatingPasswordEncoder().encode(rawPassword);
                passwordUpgrade.apply(encoded);
            }
        }
        return matches;
    }

    /**
     * Below method tells {@link PasswordEncoders#delegatingPasswordEncoder()} to use this class to match
     * only if :
     *
     * <li> It fails to find other {@link PasswordEncoder}</li>
     * <li> rawPassword does not have {@link DelegatingPasswordEncoder#DEFAULT_ID_PREFIX}
     * and {@link DelegatingPasswordEncoder#DEFAULT_ID_SUFFIX}</li>
     * @param rawPassword
     * @return true if this class can match the password
     */
    @Override
    public boolean supports(CharSequence rawPassword) {
        if(rawPassword == null) {
            return false;
        }
        int start = rawPassword.toString().indexOf(this.DEFAULT_ID_PREFIX);
        int end = rawPassword.toString().indexOf(this.DEFAULT_ID_SUFFIX);

        return start == -1 || end == -1;
    }
}
