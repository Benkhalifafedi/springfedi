package tn.esprit.twin.twin2demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                // ❌ on désactive CSRF pour simplifier les appels REST
                .csrf(csrf -> csrf.disable())
                // ✅ on autorise toutes les requêtes sans authentification
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                )
                // on garde httpBasic mais il ne sera pas exigé vu qu'on a permitAll
                .httpBasic(Customizer.withDefaults())
                // on désactive la page de login HTML par défaut
                .formLogin(form -> form.disable());

        return http.build();
    }
}
