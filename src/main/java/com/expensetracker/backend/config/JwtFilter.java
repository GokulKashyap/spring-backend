package com.expensetracker.backend.config;

import com.expensetracker.backend.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;
@Override
protected void doFilterInternal(HttpServletRequest request,
                                HttpServletResponse response,
                                FilterChain filterChain)
        throws ServletException, IOException {
            String path = request.getRequestURI();

        if (path.startsWith("/api/auth/login") || path.startsWith("/api/auth/register")) {
            filterChain.doFilter(request, response);
            return;
        }
    String authHeader = request.getHeader("Authorization");
    System.out.println("Auth Header: " + authHeader);

    if (authHeader != null && authHeader.startsWith("Bearer ")) {
        String token = authHeader.substring(7); // remove "Bearer "

        try {
            Claims claims = jwtUtil.extractAllClaims(token); // will throw error if invalid
            String email = claims.getSubject();
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(email, null, new ArrayList<>());

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            request.setAttribute("email", email);

        } catch (Exception e) {
            System.out.println("Token invalid: " + e.getMessage()); // ✅ DEBUG
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
            return;
        }
    } else if (!request.getRequestURI().equals("/api/auth/login")) {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid Authorization header");
        return;
    }

    filterChain.doFilter(request, response);
}

}
