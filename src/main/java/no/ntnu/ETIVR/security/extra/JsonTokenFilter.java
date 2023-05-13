package no.ntnu.ETIVR.security.extra;

import no.ntnu.ETIVR.security.SecurityService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Represents a filter that checks if the incoming requests has an authentication token.
 */
@Component
public class JsonTokenFilter extends OncePerRequestFilter {

    private UserDetailsService detailsService;

    @Value("${jwt.secret}")
    private String secret;

    /**
     * Makes an instance of the json token filter.
     * @param securityService the security service.
     */
    public JsonTokenFilter(SecurityService securityService){
        checkIfObjectIsNull(securityService, "security service");
        this.detailsService = securityService;
    }

    /**
     * !TODO documentation on this
     * @param request
     * @param response
     * @param chain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        try {
            RequestHeaderToken requestHeaderToken = new RequestHeaderToken(request.getHeader("Authorization"), secret);
            setAuthentication(requestHeaderToken, request);
        }catch (IllegalArgumentException exception) {

        }finally {
            chain.doFilter(request, response);
        }
    }

    /**
     * Sets the authentication of the user.
     * @param requestHeaderToken the request header token.
     * @param request the http request serverlet itself.
     */
    private void setAuthentication(RequestHeaderToken requestHeaderToken, HttpServletRequest request){
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = detailsService.loadUserByUsername(requestHeaderToken.getUsername());
            if (requestHeaderToken.checkIfUsernameMatches(userDetails) && !requestHeaderToken.isTokenExpired()) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
    }

    /**
     * Checks if an object is null.
     *
     * @param object the object you want to check.
     * @param error  the error message the exception should have.
     * @throws IllegalArgumentException gets thrown if the object is null.
     */
    private void checkIfObjectIsNull(Object object, String error) {
        if (object == null) {
            throw new IllegalArgumentException("The " + error + " cannot be null.");
        }
    }

}
