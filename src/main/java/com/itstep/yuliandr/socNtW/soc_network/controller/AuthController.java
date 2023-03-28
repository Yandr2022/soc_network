package com.itstep.yuliandr.socNtW.soc_network.controller;

import com.itstep.yuliandr.socNtW.soc_network.payload.request.LoginRequest;
import com.itstep.yuliandr.socNtW.soc_network.payload.request.SignUpRequest;
import com.itstep.yuliandr.socNtW.soc_network.payload.responce.JWTTokenSuccessResponse;
import com.itstep.yuliandr.socNtW.soc_network.payload.responce.MessageResponse;
import com.itstep.yuliandr.socNtW.soc_network.security.ConstanceSec;
import com.itstep.yuliandr.socNtW.soc_network.security.JWTTokenProvider;
import com.itstep.yuliandr.socNtW.soc_network.service.UserService;
import com.itstep.yuliandr.socNtW.soc_network.validations.ResponseErrorValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin//разрешает все источники и HTTP-методы аннотации @RequestMapping
@RequestMapping("/api/auth")
@PreAuthorize("permitAll()")
public class AuthController {
    @Autowired
    private JWTTokenProvider tokenProvider;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private ResponseErrorValidation errorValidation;
    @Autowired
    private UserService userService;

    //endpoint и API куда отправляем данные пользователя для авторизации
    @PostMapping("/signin")
    public ResponseEntity<Object> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result) {
        ResponseEntity<Object> errors = errorValidation.mapValidationService(result);//получаем ошибки
        if (!ObjectUtils.isEmpty(errors)) return errors;//если есть, останавливаем авториззацию и возвращаем ошибки

        Authentication authentication = authenticationManager.authenticate
                (new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));
//если нет, генерируем токен, передаем все данные пользователя и передаем дальше на клоиента
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = ConstanceSec.TOKEN_PREFIX+tokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JWTTokenSuccessResponse(jwt,true));

    }


    //регистрация пользователя /api/auth/signup
    @PostMapping("/signup")
    public ResponseEntity<Object> registerUser(@Valid @RequestBody SignUpRequest signUpRequest, BindingResult result) {
        ResponseEntity<Object> errors = errorValidation.mapValidationService(result);//получаем ошибки
        if (!ObjectUtils.isEmpty(errors)) return errors;//если нет ошибок
        userService.createUser(signUpRequest);//создаем нового пользователя
        return ResponseEntity.ok(new MessageResponse("User registered succesfully"));//  и возвращаем сообщение об успешной регистрации

    }
}
