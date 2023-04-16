package semester3.project.sanomed.configuration.security.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import semester3.project.sanomed.Generated;
import semester3.project.sanomed.business.AccessTokenDecoder;
import semester3.project.sanomed.business.exceptions.InvalidAccessTokenException;
import semester3.project.sanomed.domain.AccessToken;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Generated
@Component
public class AuthenticationRequestFilter extends OncePerRequestFilter {
    private final static String SPRING_SECURITY_ROLE_PREFIX = "ROLE_";

    @Autowired
    private AccessTokenDecoder accessTokenDecoder;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String requestTokenHeader = request.getHeader("Authorization");
        if (requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        String accessToken = requestTokenHeader.substring(7);

        try {
            AccessToken accessTokenDTO = accessTokenDecoder.decode(accessToken);
            setupSpringSecurityContext(accessTokenDTO);
            chain.doFilter(request, response);
        } catch (InvalidAccessTokenException e) {
            logger.error("Error validating access token", e);
            sendAuthenticationError(response);
        }
    }

    private void sendAuthenticationError(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.flushBuffer();
    }

    private void setupSpringSecurityContext(AccessToken accessToken) {
        UserDetails userDetails = new User(accessToken.getSubject(), "",
                accessToken.getRoles()
                        .stream()
                        .map(role -> new SimpleGrantedAuthority(SPRING_SECURITY_ROLE_PREFIX + role))
                        .toList());

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        usernamePasswordAuthenticationToken.setDetails(accessToken);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }

}
