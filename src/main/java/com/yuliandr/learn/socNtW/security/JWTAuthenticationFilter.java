package com.yuliandr.learn.socNtW.security;


import com.yuliandr.learn.socNtW.entity.User;
import com.yuliandr.learn.socNtW.service.CustomerUserDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;



//@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    public static final Logger LOGGER = LoggerFactory.getLogger(JWTAuthenticationFilter.class);
    @Autowired
    private  JWTTokenProvider jwtTokenProvider;
    @Autowired
    private CustomerUserDetailService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJWTFromRequest(request);// получаем токен из request
            if (StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)) {//проверка наличия и валидности
                Long userId = jwtTokenProvider.getUserIdFromToken(jwt);
                User userDetails = customUserDetailsService.loadUserById(userId);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, Collections.emptyList());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);//фвторизвция+детали пользователя
            }
        }catch   (Exception ex) {
            LOGGER.error("Could not set user authentication");
        }
      filterChain.doFilter(request, response);
    }
        private String getJWTFromRequest(HttpServletRequest request) {
            String bearToken = request.getHeader(ConstanceSec.HEADER_STRING);//при каждом запросе в данных авторизации передается токен
            if (StringUtils.hasText(bearToken) && bearToken.startsWith(ConstanceSec.TOKEN_PREFIX)) {//проверка валидности формата токена
                return bearToken.split(" ")[1];
            }
            return null;
        }

}
