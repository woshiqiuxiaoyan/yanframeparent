package filter;


import lombok.NoArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import java.io.IOException;

//@NoArgsConstructor
public class CustomterCharacterFilter implements Filter {

    private String encoding;

    private boolean forceRequestEncoding = false;

    private boolean forceResponseEncoding = false;

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public boolean isForceRequestEncoding() {
        return forceRequestEncoding;
    }

    public void setForceRequestEncoding(boolean forceRequestEncoding) {
        this.forceRequestEncoding = forceRequestEncoding;
    }

    public boolean isForceResponseEncoding() {
        return forceResponseEncoding;
    }

    public void setForceResponseEncoding(boolean forceResponseEncoding) {
        this.forceResponseEncoding = forceResponseEncoding;
    }

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String encoding = getEncoding();

        LoggerFactory.getLogger("CustomSystemLog").info("编码过滤request："+encoding);

        if (encoding != null) {
            if (isForceRequestEncoding() || request.getCharacterEncoding() == null) {
                request.setCharacterEncoding(encoding);

            }
            if (isForceResponseEncoding()) {
                response.setCharacterEncoding(encoding);
            }
        }
        filterChain.doFilter(request, response);
    }

    public void destroy() {

    }
}
