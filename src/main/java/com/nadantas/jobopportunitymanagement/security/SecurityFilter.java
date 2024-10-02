package com.nadantas.jobopportunitymanagement.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.nadantas.jobopportunitymanagement.providers.JWTProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private JWTProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        SecurityContextHolder.getContext().setAuthentication(null);
        String tokenHeader = request.getHeader("Authorization");
        if (tokenHeader != null) {
            DecodedJWT token = jwtProvider.validateToken(tokenHeader, getSecretKeyByUrl(request));
            if(token == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            String subjectToken = token.getSubject();
            request.setAttribute("id", subjectToken);
            List<String> roles = token.getClaim("roles").asList(String.class);
            List grantedAuthorityList = roles.stream()
                    .map( role -> new SimpleGrantedAuthority(role))
                    .toList();
            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(subjectToken, null, grantedAuthorityList);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request, response);
    }

    private String getSecretKeyByUrl(HttpServletRequest request) {
        if(request.getRequestURI().toString().startsWith("/company")) {
            return "company";
        }
        if(request.getRequestURI().toString().startsWith("/candidate")) {
            return "candidate";
        }
        return "";
    }
}
