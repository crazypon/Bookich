package com.ilnur.bookich.filters;

import com.ilnur.bookich.constants.ApplicationConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


public class JWTTokenValidatorFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        Environment env = getEnvironment();
        String jwt = request.getHeader(ApplicationConstants.JWT_HEADER);
        String secret = env.getProperty(ApplicationConstants.JWT_SECRET_KEY,
                ApplicationConstants.JWT_DEFAULT_SECRET_KEY);
        SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

        if (jwt != null && jwt.startsWith("Bearer ")) {
            jwt = jwt.substring(7); // Remove "Bearer "

            try {
                // Parse the token
                Claims claims = Jwts.parser()
                        .verifyWith(secretKey)
                        .build()
                        .parseSignedClaims(jwt)
                        .getPayload();

                // Extract data
                String username = String.valueOf(claims.get("username")); // Or claims.getSubject() if you updated the login logic
                String authorities = String.valueOf(claims.get("authorities"));

                // Create Authentication object
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        AuthorityUtils.commaSeparatedStringToAuthorityList(authorities)
                );

                // Set Context
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (Exception e) {
                // Ideally, log this error.
                // Do NOT throw an exception here if you want the "AuthenticationEntryPoint"
                // to handle the 401 later, OR throw BadCredentialsException if you have
                // logic to catch it.
                throw new BadCredentialsException("Invalid Token received!");
            }
        }

        // Continue filter chain
        filterChain.doFilter(request, response);
    }

    // Helper to ensure this filter doesn't run on the login path itself (optional optimization)
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getServletPath().equals("/apiLogin");
    }
}