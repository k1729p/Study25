package kp.resource.server.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;

import static kp.resource.server.Constants.*;

/**
 * Security configuration class for managing authentication and authorization.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    /**
     * The custom password properties loaded from the application.yml file.
     */
    private final PasswordProperties passwordProperties;

    /**
     * Constructor to initialize the security configuration with custom password properties.
     *
     * @param passwordProperties the password properties loaded from the application.yml file.
     */
    public SecurityConfiguration(PasswordProperties passwordProperties) {
        this.passwordProperties = passwordProperties;
    }

    /**
     * The security filter chain bean.
     *
     * @param httpSecurity the {@link HttpSecurity}
     * @return {@link DefaultSecurityFilterChain}
     * @throws Exception the {@link Exception}
     */
    @Bean
    @SuppressWarnings("java:S4502")
    // switch off Sonarqube rule 'Disabling CSRF protections is security-sensitive'
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    /**
     * Configures an in-memory user details manager with predefined users and passwords.
     *
     * @return the user details manager containing the predefined users.
     */
    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {

        final PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        final UserDetails officialUser = User.withUsername(LEVEL_OFFICIAL_USER)
                .password(passwordEncoder.encode(passwordProperties.getPasswordOfficial()))
                .roles(LEVEL_OFFICIAL)
                .build();
        final UserDetails restrictedUser = User.withUsername(LEVEL_RESTRICTED_USER)
                .password(passwordEncoder.encode(passwordProperties.getPasswordRestricted()))
                .roles(LEVEL_OFFICIAL, LEVEL_RESTRICTED)
                .build();
        final UserDetails confidentialUser = User.withUsername(LEVEL_CONFIDENTIAL_USER)
                .password(passwordEncoder.encode(passwordProperties.getPasswordConfidential()))
                .roles(LEVEL_OFFICIAL, LEVEL_RESTRICTED, LEVEL_CONFIDENTIAL)
                .build();
        final UserDetails secretUser = User.withUsername(LEVEL_SECRET_USER)
                .password(passwordEncoder.encode(passwordProperties.getPasswordSecret()))
                .roles(LEVEL_OFFICIAL, LEVEL_RESTRICTED, LEVEL_CONFIDENTIAL, LEVEL_SECRET)
                .build();
        return new InMemoryUserDetailsManager(officialUser, restrictedUser, confidentialUser, secretUser);
    }

}
