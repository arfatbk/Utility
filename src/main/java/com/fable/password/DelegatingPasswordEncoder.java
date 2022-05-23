package com.fable.password;


import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author Arfat Chaus on 5/23/22
 * version 1.0
 */
final class DelegatingPasswordEncoder implements PasswordEncoder {

    List<PasswordEncoder> encoders = Arrays.asList(
            new BCryptPasswordEncoder(),
            new LegacyPasswordEncoder()
//            new Sha512PasswordEncoder()
    );

    @Override
    public String encode(CharSequence rawPassword) {
        PasswordEncoder delegate = pickARandomPasswordEncoder();
        return delegate.encode(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        PasswordEncoder delegate = pickSupportingPasswordEncoder(encodedPassword);
        return delegate.matches(rawPassword, encodedPassword);
    }


    @Override
    public boolean supports(CharSequence rawPassword) {
        return null != pickSupportingPasswordEncoder(rawPassword.toString());
    }

    private PasswordEncoder pickARandomPasswordEncoder() {
        //Skip the Legacy Password encoder for creating a new hash.
        List<PasswordEncoder> encoders = this.encoders.stream()
                .filter(e -> !LegacyPasswordEncoder.class.isAssignableFrom(e.getClass()))
                .collect(Collectors.toList());
        Random r = new Random();
        return encoders.get(r.nextInt(encoders.size()));
    }

    private PasswordEncoder pickSupportingPasswordEncoder(String encodedPassword) {
        return this.encoders.stream()
                .filter(e -> e.supports(encodedPassword))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No password encoder supporting the encoded password"));
    }
}
