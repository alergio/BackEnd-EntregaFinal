package com.dh.dental.clinic.security.auth;

import com.dh.dental.clinic.entity.Token;
import com.dh.dental.clinic.repository.impl.ITokenRepository;
import com.dh.dental.clinic.security.configuration.JwtService;
import com.dh.dental.clinic.entity.RoleType;
import com.dh.dental.clinic.entity.User;
import com.dh.dental.clinic.repository.impl.IRoleRepository;
import com.dh.dental.clinic.repository.impl.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final ITokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public AuthenticationResponse register(RegisterRequest registerRequest) throws Exception {

        // probablemente el register puedo hacer que no devuelva token, solo registre

        var roleUser = roleRepository.findByName(RoleType.ROLE_USER)
                .orElseThrow(() -> new Exception("FAIL ROLE"));

        var user = User.builder()
                .firstname(registerRequest.getFirstname())
                .lastname(registerRequest.getLastname())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .roles(Collections.singleton(roleUser))
                .build();

        userRepository.save(user);

        var jwt = jwtService.generateToken(user);

        saveUserToken(jwt, user);

        return AuthenticationResponse
                .builder()
                .jwt(jwt)
                .build();
    }


    public AuthenticationResponse logIn(AuthenticationRequest authenticationRequest){

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()
                )
        );

        var user = userRepository.findByEmail(authenticationRequest.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found. (AM)"));

        var jwt = jwtService.generateToken(user);

        blockAllTokenByUser(user);
        saveUserToken(jwt, user);

        return AuthenticationResponse.builder()
                .jwt(jwt)
                .build();
    }

    private void blockAllTokenByUser(User user) {
        List<Token> validTokenListByUser = tokenRepository.findAllTokenByUser(user.getId());
        if(!validTokenListByUser.isEmpty()){
            validTokenListByUser.forEach(token->{
                token.setLoggedOut(true);
            });
        }
    }

    private void saveUserToken(String jwt, User user){

        // guardamos el token generado
        Token token = new Token();
        token.setToken(jwt);
        token.setLoggedOut(false);
        token.setUser(user);

        tokenRepository.save(token);

    }

    // aca puedo agregar un refreshToken
    // https://youtu.be/EjrlN_OQVDQ min: 59:50

}
