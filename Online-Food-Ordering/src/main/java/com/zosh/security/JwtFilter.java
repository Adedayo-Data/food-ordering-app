package com.zosh.security;

import com.zosh.model.MyUserDetailsService;
import com.zosh.model.UserPrincipal;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    JwtService jwtService;

    @Autowired
    MyUserDetailsService myUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("Entered Filter chain");
        String token = null;
        String username = null;

        String authHeader = request.getHeader(JwtConstant.GET_HEADER);
        System.out.println("Authorization Header: " + request.getHeader("Authorization"));
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            System.out.println("Entered auth header check");
            token = authHeader.substring(7);
            username = jwtService.extractUsername(token);
            System.out.println(token);
            System.out.println(username);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // validate the token - check if username matches the one in the database and issue and expiry date are valid
            // get the user details
            System.out.println("Entered username header check");
            try {
                UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);

                boolean isvalid = jwtService.validateToken(token, userDetails);
                System.out.println("Status "+isvalid);
                if (isvalid) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    if(SecurityContextHolder.getContext().getAuthentication() != null){
                        System.out.println("Authenticated!");
                    }else{
                        System.out.println("Not auth!");
                    }
                }

            } catch (UsernameNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Leaving filter chain");
        filterChain.doFilter(request, response);

    }

}
