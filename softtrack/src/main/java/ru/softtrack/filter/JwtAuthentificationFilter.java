package ru.softtrack.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.softtrack.service.CookieService;
import ru.softtrack.service.CustomUserDetailsService;
import ru.softtrack.service.JwtService;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthentificationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;
    private final CookieService cookieService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        String token = cookieService.getSessionTokenFromRequest(request);

        if (token != null && jwtService.validateToken(token)) {
            String login = jwtService.extractUserId(token);

            UserDetails userDetails = userDetailsService.loadUserByUsername(login);

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails,null,userDetails.getAuthorities()
            );

            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        chain.doFilter(request,response);
        return;
    }

}
