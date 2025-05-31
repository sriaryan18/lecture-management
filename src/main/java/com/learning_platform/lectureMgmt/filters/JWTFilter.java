package com.learning_platform.lectureMgmt.filters;

import com.learning_platform.lectureMgmt.constants.AppConstants;
import com.learning_platform.lectureMgmt.utils.JWTUtils;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JWTFilter extends OncePerRequestFilter {

    // @Autowired
    // ApiClientConfig apiClientConfig;

    private final JWTUtils jwtUtils = new JWTUtils();

    public String getTokenFromCookies(HttpServletRequest request) {
        // log.info("Request: {}", request.getHeader("Authorization"));
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("token".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        } 
        
        return null; // token cookie not found
    }
    // private Map validateTokenFromAuthService(String token){
    // return apiClientConfig.callService(AppConstants.AUTH_SERVICE
    // ,AppConstants.VERIFY_TOKEN,
    // null,
    // Map.of("token",token),
    // token,
    // Map.class);
    // }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        Enumeration<String> headers = request.getHeaderNames();
        while (headers.hasMoreElements()) {
            String headerName = headers.nextElement();
            System.out.println("Header: " + headerName + " = " + request.getHeader(headerName));
        }
       
        String token = getTokenFromCookies(request);

        if (token != null) {
            token = token.replace("Bearer ", "");
            Claims claims = jwtUtils.decodeJWTClaims(token);

            String username = claims.getSubject();
            List<String> roles = claims.get("roles", List.class);

            List<GrantedAuthority> authorities = roles != null
                    ? roles.stream().map(role -> (GrantedAuthority) new SimpleGrantedAuthority(role))
                            .toList()
                    : List.of();

            if (username != null) {
                Map<String, Object> userDetails = new HashMap<>();
                userDetails.put("username", username);
                userDetails.put(AppConstants.SUBSCRIPTION, claims.get(AppConstants.SUBSCRIPTION));
                userDetails.put(AppConstants.CLAIM_USER, claims.get(AppConstants.CLAIM_USER));
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
                        null, authorities);
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
                filterChain.doFilter(request, response);
                return;
            }
        }
        filterChain.doFilter(request, response);
        // If token is missing or invalid, send 401 Unauthorized
        // response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        // response.getWriter().write("Unauthorized: Invalid or missing token");
    }
}
