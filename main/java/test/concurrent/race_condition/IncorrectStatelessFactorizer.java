package test.concurrent.race_condition;

import javax.servlet.*;
import java.math.BigInteger;

/**
 * 1. Incorrect use of StatelessFactorizer:
 *
 * This seemingly stateless factorizer actually introduces shared mutable
 * state (lastNumber and lastFactors), making it thread-unsafe.
 * Remove the caching mechanism to make it truly stateless.
 */
public class IncorrectStatelessFactorizer implements Servlet {

    private long lastNumber;
    private BigInteger[] lastFactors;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    public void service(ServletRequest req, ServletResponse resp) {
        BigInteger i = extractFromRequest(req);
        if (i.equals(BigInteger.valueOf(lastNumber))) {
            encodeIntoResponse(resp, lastFactors);
        } else {
            BigInteger[] factors = factor(i);
            lastNumber = i.longValue();
            lastFactors = factors;
            encodeIntoResponse(resp, factors);
        }
    }
    // Other methods omitted for brevity

    private BigInteger extractFromRequest(ServletRequest req) {
        return new BigInteger("7");  // Dummy implementation
    }

    private BigInteger[] factor(BigInteger i) {
        return new BigInteger[]{i};  // Dummy implementation
    }

    private void encodeIntoResponse(ServletResponse resp, BigInteger[] factors) {
        // Dummy implementation
    }

    @Override
    public String getServletInfo() {
        return "";
    }

    @Override
    public void destroy() {

    }

}