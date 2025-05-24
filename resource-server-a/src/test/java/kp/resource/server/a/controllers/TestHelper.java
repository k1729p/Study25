package kp.resource.server.a.controllers;

import org.springframework.graphql.test.tester.WebGraphQlTester;

import java.util.HashMap;
import java.util.Map;

import static kp.resource.server.Constants.*;
import static kp.resource.server.a.controllers.TestConstants.*;

/**
 * The helper class for tests.
 */
class TestHelper {
    /**
     * Loads the testers map.
     *
     * @param webGraphQlTester the {@link WebGraphQlTester}
     * @return the testers map
     */
    static Map<String, WebGraphQlTester> createTestersMap(WebGraphQlTester webGraphQlTester) {

        final Map<String, WebGraphQlTester> testersMap = new HashMap<>();
        testersMap.put("With Classification Level Official", webGraphQlTester.mutate()
                .headers(headers -> headers.setBasicAuth(LEVEL_OFFICIAL_USER, PASSWORD_OFFICIAL))
                .build());
        testersMap.put("With Classification Level Restricted", webGraphQlTester.mutate()
                .headers(headers -> headers.setBasicAuth(LEVEL_RESTRICTED_USER, PASSWORD_RESTRICTED))
                .build());
        testersMap.put("With Classification Level Confidential", webGraphQlTester.mutate()
                .headers(headers -> headers.setBasicAuth(LEVEL_CONFIDENTIAL_USER, PASSWORD_CONFIDENTIAL))
                .build());
        testersMap.put("With Classification Level Secret", webGraphQlTester.mutate()
                .headers(headers -> headers.setBasicAuth(LEVEL_SECRET_USER, PASSWORD_SECRET))
                .build());
        testersMap.put("Without Authorization", webGraphQlTester);
        testersMap.put("With Invalid Credentials", webGraphQlTester.mutate()
                .headers(headers -> headers.setBasicAuth(LEVEL_RESTRICTED_USER, "The Invalid Password"))
                .build());
        return testersMap;
    }

    /**
     * Private constructor to prevent instantiation.
     */
    private TestHelper() {
        throw new IllegalStateException("Utility class");
    }
}
