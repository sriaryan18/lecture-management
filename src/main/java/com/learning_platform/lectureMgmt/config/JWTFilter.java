package com.learning_platform.lectureMgmt.config;

import com.learning_platform.lectureMgmt.utils.JWTUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    ApiClientConfig apiClientConfig;

    private final JWTUtils jwtUtils = new JWTUtils();



//    private Map validateTokenFromAuthService(String token){
//        return  apiClientConfig.callService(AppConstants.AUTH_SERVICE
//                ,AppConstants.VERIFY_TOKEN,
//                null,
//                Map.of("token",token),
//                token,
//                Map.class);
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        filterChain.doFilter(request,response);
        return;
//        String token = request.getHeader("Authorization");
//        if(token.contains("Bearer")){
//            token = token.replace("Bearer ","");
//
//        }
////        Map<String,Object> userDetails = validateTokenFromAuthService(token);
//
//        Claims claims = jwtUtils.decodeJWTClaims(token);
//
//
//        String username = claims.getSubject();
//        List<String> roles = claims.get("roles",List.class);
//
//        List<GrantedAuthority> authorities = roles != null ?
//                roles.stream().map(role -> (GrantedAuthority) new SimpleGrantedAuthority(role))
//                        .toList()
//                : List.of();
//
//        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
//            Map<String, Object> userDetails = new HashMap<>();
//            userDetails.put("username", username);
//            userDetails.put(AppConstants.SUBSCRIPTION, claims.get(AppConstants.SUBSCRIPTION));
//
//            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null,authorities );
//            authToken.setDetails(new WebAuthenticationDetailsSource()
//                    .buildDetails(request));
//            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
////            authToken.setDetails(claims);
//            SecurityContextHolder.getContext().setAuthentication(authToken);
//        }
//        filterChain.doFilter(request,response);
    }
}
