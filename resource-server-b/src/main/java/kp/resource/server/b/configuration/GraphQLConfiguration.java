package kp.resource.server.b.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

/**
 * GraphQL configuration class.
 */
@Configuration
public class GraphQLConfiguration {

    /**
     * The runtime wiring configurer bean.
     *
     * @param authorisationDirectiveWiring the {@link AuthorisationDirectiveWiring}
     * @return {@link RuntimeWiringConfigurer}
     */
    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer(
            AuthorisationDirectiveWiring authorisationDirectiveWiring) {
        return builder -> builder.directiveWiring(authorisationDirectiveWiring);
    }
}