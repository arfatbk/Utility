package com.fable.password;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * For matching older passwords only. Use of the {@link PasswordEncoder} is not recommended.
 * recommended one is {@link PasswordEncoder#delegatingPasswordEncoder()}.
 *
 * @author Arfat Chaus on 5/23/22
 * @version 1.0
 */
final class LegacyPasswordEncoder implements PasswordEncoder {

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

    /**
     * Below method tells {@link PasswordEncoder#delegatingPasswordEncoder()} to use this class to match
     * only if :
     *
     * <li> It fails to find other {@link PasswordEncoder}</li>
     * <li> rawPassword does not have {@link PasswordEncoder#DEFAULT_ID_PREFIX}
     * and {@link PasswordEncoder#DEFAULT_ID_SUFFIX}</li>
     * @param rawPassword
     * @return true if this class can match the password
     */
    @Override
    public boolean supports(CharSequence rawPassword) {
        if(rawPassword == null) {
            return false;
        }
        int start = rawPassword.toString().indexOf(PasswordEncoder.DEFAULT_ID_PREFIX);
        int end = rawPassword.toString().indexOf(PasswordEncoder.DEFAULT_ID_SUFFIX);

        return start == -1 || end == -1;
    }
}
