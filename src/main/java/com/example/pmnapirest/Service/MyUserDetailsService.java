package com.example.pmnapirest.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class MyUserDetailsService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    // Pour cet exemple, nous utilisons une source de données statique.
    // Dans une application réelle, cela devrait être remplacé par une base de données ou une autre source de données.
    private Map<String, String> users = new HashMap<>();

    public MyUserDetailsService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        // Juste pour le test, ajoutez quelques utilisateurs.
        // Dans la pratique, vous récupérerez les informations de l'utilisateur depuis une base de données.
        users.put("user", passwordEncoder.encode("user")); // Nom d'utilisateur: user, Mot de passe: password
        users.put("admin", passwordEncoder.encode("admin"));   // Nom d'utilisateur: admin, Mot de passe: admin
    }

   /* @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if ("user".equals(username)) {
            return User.withUsername("user")
                    .password(passwordEncoder.encode("user"))
                    .build();
        }
        throw new UsernameNotFoundException("User not found with username: " + username);
    }*/

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (!users.containsKey(username)){
            throw new UsernameNotFoundException("User not found: "+ username);
        }
        log.info("LoadUserByUsername");
        // Créer un objet UserDetails.
        // Dans une application réelle, vous devrez également charger les rôles/autorisations de l'utilisateur.
        return new User(username, users.get(username), new ArrayList<>());
    }

}
