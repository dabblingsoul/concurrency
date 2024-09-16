package test.concurrent.race_condition;

/**
 * 2. Incorrect use of ThreadLocal for per-request context:
 *
 * This code fails to clean up the ThreadLocal after processing each request, potentially
 * leading to memory leaks and incorrect context in thread pools.
 * Always clean up ThreadLocals after use.
 */
public class IncorrectThreadLocalRequestContext {
    private static ThreadLocal<UserContext> userContext = new ThreadLocal<>();

    public static void setUserContext(UserContext context) {
        userContext.set(context);
    }

    public static UserContext getUserContext() {
        return userContext.get();
    }

    public static void processRequest(Request request) {
        setUserContext(new UserContext(request.getUserId()));
        // Process the request
        // The ThreadLocal is never cleared!
    }

    static class UserContext {
        private String userId;
        UserContext(String userId) { this.userId = userId; }
    }

    static class Request {
        String getUserId() { return "user1"; }
    }
}