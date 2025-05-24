package kp.resource.server.b.configuration;

import graphql.language.StringValue;
import graphql.schema.*;
import graphql.schema.idl.SchemaDirectiveWiring;
import graphql.schema.idl.SchemaDirectiveWiringEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.util.Optional;

/**
 * Implements the {@link SchemaDirectiveWiring} interface to provide
 * authorization directive wiring for GraphQL fields.
 */
@Component
public class AuthorisationDirectiveWiring implements SchemaDirectiveWiring {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

    /**
     * {@inheritDoc}
     */
    @Override
    public GraphQLFieldDefinition onField(
            SchemaDirectiveWiringEnvironment<GraphQLFieldDefinition> environment) {

        final Optional<GrantedAuthority> grantedAuthorityOpt = extractGrantedAuthority(environment);
        if (grantedAuthorityOpt.isEmpty()) {
            return environment.getFieldDefinition();
        }
        final DataFetcher<?> originalDataFetcher = environment.getFieldDataFetcher();
        final GraphQLFieldDefinition graphQLFieldDefinition = environment.setFieldDataFetcher(
                buildAuthorisingDataFetcher(originalDataFetcher, grantedAuthorityOpt.get(),
                        environment.getElement().getName()));
        if (logger.isDebugEnabled()) {
            logger.debug("onField(): authority[{}]", grantedAuthorityOpt.get().getAuthority());
        }
        return graphQLFieldDefinition;
    }

    /**
     * Extracts the authorization role from the applied GraphQL directive.
     *
     * @param environment the schema directive wiring environment
     * @return an optional containing the granted authority if present, or an empty optional
     */
    private Optional<GrantedAuthority> extractGrantedAuthority(
            SchemaDirectiveWiringEnvironment<GraphQLFieldDefinition> environment) {

        return Optional.ofNullable(environment.getAppliedDirective("auth"))
                .map(directive -> directive.getArgument("role"))
                .map(GraphQLAppliedDirectiveArgument::getArgumentValue)
                .map(InputValueWithState::getValue)
                .filter(StringValue.class::isInstance)
                .map(StringValue.class::cast)
                .map(StringValue::getValue)
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role));
    }

    /**
     * Builds a data fetcher that checks authorization roles before invoking the original data fetcher.
     *
     * @param originalDataFetcher the original data fetcher
     * @param grantedAuthority    the required granted authority
     * @param elementName         the name of the GraphQL element
     * @return a data fetcher that enforces authorization
     */
    private DataFetcher<Object> buildAuthorisingDataFetcher(DataFetcher<?> originalDataFetcher,
                                                            GrantedAuthority grantedAuthority,
                                                            String elementName) {

        return dataFetchingEnvironment -> {
            final boolean roleFlag = Optional.ofNullable(dataFetchingEnvironment)
                    .map(DataFetchingEnvironment::getGraphQlContext)
                    .map(context -> context
                            .get("org.springframework.security.core.context.SecurityContext"))
                    .filter(SecurityContext.class::isInstance)
                    .map(SecurityContext.class::cast)
                    .map(SecurityContext::getAuthentication)
                    .map(Authentication::getAuthorities)
                    .map(authorities -> authorities.contains(grantedAuthority))
                    .orElse(false);
            if (!roleFlag) {
                final String message = "Unauthorized to access this resource, required authority[%s], element name[%s]"
                        .formatted(grantedAuthority.getAuthority(), elementName);
                logger.error("onField():\n\t {}", message);
                throw new AccessDeniedException(message);
            }
            return originalDataFetcher.get(dataFetchingEnvironment);
        };
    }
}