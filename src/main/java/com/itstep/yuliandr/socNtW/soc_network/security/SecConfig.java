package com.itstep.yuliandr.socNtW.soc_network.security;

import com.itstep.yuliandr.socNtW.soc_network.service.CustomerUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity//переадресация встроенной защиты
@EnableGlobalMethodSecurity(
        securedEnabled = true, jsr250Enabled = true, proxyTargetClass = true)//определение области защиты
public class SecConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JWTAuthenticationEntryPoint entryPoint;
    @Autowired
    private CustomerUserDetailService userDetailService;

    @Override
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean//шифровщик паролей
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JWTAuthenticationFilter getAuthenticationFilter() {
        return new JWTAuthenticationFilter();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().exceptionHandling().authenticationEntryPoint(entryPoint)//перенаправление на нашего обработчика
                .and().authorizeRequests().antMatchers(ConstanceSec.SIGN_UP_PATH).permitAll()//разрешаем доступ всем по пути авторизации
                .anyRequest().authenticated();//остальные пути только после авторизации
        http.addFilterBefore(getAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);//добавляем кастомный фильтр

    }

    @Override//шифрование при аутентификации
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
    }
}
