package com.arfat.password;

import java.util.function.Function;

/**
 * @author Arfat Chaus on 5/23/22
 */
final class Sha512PasswordEncoder implements DelegatingPasswordEncoder {

    private final String idForEncode = "sha512";

    //TODO: implement
    @Override
    public String encode(CharSequence rawPassword) {
        return null;
    }

    //TODO: implement
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return false;
    }

    @Override
    public boolean shouldUpgrade() {
        return false;
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

    @Override
    public boolean supports(CharSequence rawPassword) {
        String id = this.extractId(rawPassword.toString());
        return this.idForEncode.equals(id);
    }
}
