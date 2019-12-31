package config.security.singlechain;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author cj
 * @date 2019/12/31
 */
public class AFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("-----debug: \"AFilter\" = " + "AFilter");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
