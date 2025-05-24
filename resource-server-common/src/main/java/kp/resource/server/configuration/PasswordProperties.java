package kp.resource.server.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Configuration properties for custom passwords defined in the application.yml file.
 */
@Component
@ConfigurationProperties(prefix = "custom.passwords")
public class PasswordProperties {
    private String passwordOfficial;
    private String passwordRestricted;
    private String passwordConfidential;
    private String passwordSecret;

    /**
     * Gets the password for the official user.
     *
     * @return the official user's password.
     */
    public String getPasswordOfficial() {
        return passwordOfficial;
    }

    /**
     * Sets the password for the official user.
     *
     * @param passwordOfficial the official user's password.
     */
    @SuppressWarnings("unused")
    public void setPasswordOfficial(String passwordOfficial) {
        this.passwordOfficial = passwordOfficial;
    }

    /**
     * Gets the password for the restricted user.
     *
     * @return the restricted user's password.
     */
    public String getPasswordRestricted() {
        return passwordRestricted;
    }

    /**
     * Sets the password for the restricted user.
     *
     * @param passwordRestricted the restricted user's password.
     */
    @SuppressWarnings("unused")
    public void setPasswordRestricted(String passwordRestricted) {
        this.passwordRestricted = passwordRestricted;
    }

    /**
     * Gets the password for the confidential user.
     *
     * @return the confidential user's password.
     */
    public String getPasswordConfidential() {
        return passwordConfidential;
    }

    /**
     * Sets the password for the confidential user.
     *
     * @param passwordConfidential the confidential user's password.
     */
    @SuppressWarnings("unused")
    public void setPasswordConfidential(String passwordConfidential) {
        this.passwordConfidential = passwordConfidential;
    }

    /**
     * Gets the password for the secret user.
     *
     * @return the secret user's password.
     */
    public String getPasswordSecret() {
        return passwordSecret;
    }

    /**
     * Sets the password for the secret user.
     *
     * @param passwordSecret the secret user's password.
     */
    @SuppressWarnings("unused")
    public void setPasswordSecret(String passwordSecret) {
        this.passwordSecret = passwordSecret;
    }
}