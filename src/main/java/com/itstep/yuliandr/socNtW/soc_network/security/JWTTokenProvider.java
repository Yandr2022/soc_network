package com.itstep.yuliandr.socNtW.soc_network.security;

import com.itstep.yuliandr.socNtW.soc_network.entity.User;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTTokenProvider {
    public static final Logger LOGGER= LoggerFactory.getLogger(JWTTokenProvider.class);
    public String generateToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();//хранит данные пользователя
        Date now = new Date(System.currentTimeMillis());
        Date expiryDate = new Date(now.getTime() + ConstanceSec.EXPIRATION_TIME);//время жизни токена=тек время + заданное
//создаем claims для передачи данных пользователя в JWT
        String userId = Long.toString(user.getId());
        Map<String, Object> claimsMap = new HashMap<>();
        claimsMap.put("id", userId);
        claimsMap.put("username", user.getEmail());
        claimsMap.put("firstname", user.getName());
        claimsMap.put("lastname", user.getLastname());
//собираем токен
        return Jwts.builder()
                .setSubject(userId)
                .addClaims(claimsMap)//данные пользователя
                .setIssuedAt(now)//время генерации токена
                .setExpiration(expiryDate)//срок действия
                .signWith(SignatureAlgorithm.HS512, ConstanceSec.KEY)//шифрование
                .compact();//упаковка
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()//разбираем токен
                    .setSigningKey(ConstanceSec.KEY)//декодируем
                    .parseClaimsJws(token);

            return true;
            //отлавливаем исключения токена
        } catch (SignatureException | MalformedJwtException |
                 ExpiredJwtException | UnsupportedJwtException |
                 IllegalArgumentException ex) {
            LOGGER.error(ex.getMessage());//пишем в лог
            return false;
        }
    }

    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(ConstanceSec.KEY)
                .parseClaimsJws(token)
                .getBody();//парсим токен и достаем id
        String id = (String) claims.get("id");
        return Long.parseLong(id);
    }
}
