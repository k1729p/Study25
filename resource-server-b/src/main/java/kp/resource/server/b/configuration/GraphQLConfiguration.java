package kp.resource.server.b.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

/**
 * Configures GraphQL runtime wiring for the application.
 */
@Configuration
public class GraphQLConfiguration {

    /**
     * Registers the directive wiring used for GraphQL authorization.
     *
     * @param authorisationDirectiveWiring the {@link AuthorisationDirectiveWiring} instance
     * @return a {@link RuntimeWiringConfigurer} bean that applies the provided directive wiring
     */
    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer(
            AuthorisationDirectiveWiring authorisationDirectiveWiring) {
        return builder -> builder.directiveWiring(authorisationDirectiveWiring);
    }
}