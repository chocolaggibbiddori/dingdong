package side.dingdong.api.common.util;

import org.springframework.http.HttpMethod;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher.Builder;

public abstract class CommonPathPatternRequestMatchers {

    private static final Builder DEFAULT_BUILDER = PathPatternRequestMatcher.withDefaults().basePath("/api/v1");

    private CommonPathPatternRequestMatchers() {
        throw new AssertionError("Don't instantiate");
    }

    public static Builder getDefaultBuilder() {
        return DEFAULT_BUILDER;
    }

    public static PathPatternRequestMatcher get(String path) {
        return getDefaultBuilder().matcher(path);
    }

    public static PathPatternRequestMatcher get(HttpMethod httpMethod, String path) {
        return getDefaultBuilder().matcher(httpMethod, path);
    }
}
