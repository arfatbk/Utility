package com.fable.password;

/**
 * @author Arfat Chaus on 5/30/22
 */
public final class PasswordEncoders {

    private PasswordEncoders() {
    }

    /**
     * Returns an instance of DelegatingPasswordEncoder which encodes passwords using
     * the random {@link PasswordEncoder} using delegation.
     *
     * @see DefaultDelegatingPasswordEncoder
     */
    public static PasswordEncoder delegatingPasswordEncoder() {
        return new DefaultDelegatingPasswordEncoder();
    }

    /**
     * Returns an instance of BCryptPasswordEncoder which encodes passwords using
     * the {@link BCryptPasswordEncoder}.
     * <b>This use is not recommended!</b>
     *
     * @see DefaultDelegatingPasswordEncoder
     */
    public static PasswordEncoder withBCrypt() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Returns an instance of BCryptPasswordEncoder which encodes passwords using
     * the {@link Sha512PasswordEncoder}.
     * <b>This use is not recommended!</b>
     *
     * @see DefaultDelegatingPasswordEncoder
     */
    public static PasswordEncoder withSHA512() {
        return new Sha512PasswordEncoder();
    }
}
