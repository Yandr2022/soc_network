package com.yuliandr.learn.socNtW.security;


import com.google.gson.Gson;
import com.yuliandr.learn.socNtW.payload.responce.InvalidLoginResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {
    //ловит ошибку авторизации, возвращает статус 401 при попытке доступа к защищенному ресурсу без авторизации и возвращает кастомный объект ответа InvalidLoginResponse
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        InvalidLoginResponse InvResponse = new InvalidLoginResponse();
        String jsonLoginResponse = new Gson().toJson(InvResponse);//конвертация в JSON кастомного объекта
        response.setContentType(ConstanceSec.CONTENT_TYPE);//устанавливаем ответу формат JSON
        response.setStatus(HttpStatus.UNAUTHORIZED.value());//статус 401
        response.getWriter().println(jsonLoginResponse);//выводим наш ответ

    }




}
