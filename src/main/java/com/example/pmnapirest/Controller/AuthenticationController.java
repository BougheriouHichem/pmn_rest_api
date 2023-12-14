package com.example.pmnapirest.Controller;

import com.example.pmnapirest.Dto.AuthentificationDTO;
import com.example.pmnapirest.Entity.AuthenticationRequest;
import com.example.pmnapirest.Entity.AuthenticationResponse;
import com.example.pmnapirest.Util.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/authenticate")
    public Map<String, String > createAuthenticationToken(@RequestBody AuthentificationDTO authentificationDTO) throws Exception{
        log.info("Success");
        final Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authentificationDTO.username(),authentificationDTO.password()));

        log.info(authentificationDTO.username());
        log.info(authentificationDTO.password());

        log.info(passwordEncoder.encode("user"));


        /*if (authenticate.isAuthenticated()){
            return this.jwtTokenUtil.generate(authentificationDTO.username());
        }*/
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authentificationDTO.username());

        log.info(userDetails.getUsername());
        final String jwt = jwtTokenUtil.generateToken(userDetails.getUsername());
        return Map.of("Token", jwt);



/*
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
        );
        final Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authentificationDTO.username(), authentificationDTO.password())
        );
        log.info("Success");



        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        log.info(userDetails.getUsername());
        final String jwt = jwtTokenUtil.generateToken(userDetails.getUsername());

        return new AuthenticationResponse(jwt);

 */
        //return null;
    }

}
