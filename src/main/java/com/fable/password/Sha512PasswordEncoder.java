package com.fable.password;

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
    public boolean supports(CharSequence rawPassword) {
        String id = this.extractId(rawPassword.toString());
        return this.idForEncode.equals(id);
    }
}
