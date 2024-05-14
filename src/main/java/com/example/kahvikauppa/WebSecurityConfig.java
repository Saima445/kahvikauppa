package com.example.kahvikauppa;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .csrf(csrf -> csrf.disable()) // Poistaa CSRF-suojaus käytöstä // mahdollistetaan
                                                              // h2-konsolin käyttö
                                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable())) // Poistaa
                                                                                                                  // kehysvalinnat
                                                                                                                  // käytöstä
                                                                                                                  // mahdollistetaan
                                                                                                                  // h2-konsolin
                                                                                                                  // käyttö
                                .authorizeHttpRequests((requests) -> requests
                                                .requestMatchers("/admin")
                                                .authenticated() // Vain admin-sivu vaatii kirjautumisen
                                                .requestMatchers("/h2-console/**").permitAll()
                                                .anyRequest().permitAll()) // muille sivuille vapaa pääsy
                                .formLogin((form) -> form
                                                .loginPage("/login")
                                                .permitAll()
                                                .defaultSuccessUrl("/admin", true)) // Ohjataan admin sivulle

                                .logout((logout) -> logout.permitAll());

                return http.build();
        }

        @Bean
        public UserDetailsService userDetailsService() {
                UserDetails user = User.withDefaultPasswordEncoder()
                                .username("pääkäyttäjä")
                                .password("jeejee")
                                .roles("USER")
                                .build();
                System.out.println(user.getPassword());
                return new InMemoryUserDetailsManager(user);
        }
        // @Bean
        // public UserDetailsService userDetailsService() {
        // PasswordEncoder encoder = //salasanan kryptauskoodi
        // PasswordEncoderFactories.createDelegatingPasswordEncoder(); //salasanan
        // kryptaukseen käytettävän PasswordEncoder-olio
        // UserDetails user = org.springframework.security.core.userdetails.User
        // .withUsername("user") //UserDetails-olion instanssi käyttäjälle
        // .password(encoder.encode("password")) // salasana kryptataan käyttäen aiemmin
        // luotua PasswordEncoder-oliota
        // .roles("USER")
        // .build(); //rakentaa UserDetails-olion ja palauttaa sen käyttäjäksi.

        // return new InMemoryUserDetailsManager(user); //luo uuden
        // InMemoryUserDetailsManager-olion ja antaa sille käyttäjätiedot
        // }
}
