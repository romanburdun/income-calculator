package co.uk.soletradercalculator.income_calculator.controllers;

import co.uk.soletradercalculator.income_calculator.exceptions.ApiRequestException;
import co.uk.soletradercalculator.income_calculator.security.requests.AuthRequest;
import co.uk.soletradercalculator.income_calculator.security.responses.AuthResponse;
import co.uk.soletradercalculator.income_calculator.security.responses.AuthToken;
import co.uk.soletradercalculator.income_calculator.services.authentication.AuthenticationService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
@Api(tags = {"Authentication controller"})

public class AuthController {


    private AuthenticationService authenticationService;

    @Autowired
    private Environment environment;

    @PostMapping("/register")
    private AuthResponse register(@RequestBody AuthRequest request, HttpServletResponse response) {

        AuthToken token = authenticationService.register(request);

        String[] activeProfiles = environment.getActiveProfiles();
        List<String> profiles = Arrays.asList(activeProfiles);

        if(!StringUtils.hasText(token.getJwt())) {
            throw new ApiRequestException("Could not obtain an access token", HttpStatus.NOT_FOUND);
        }

        Cookie cookie = new Cookie("token", token.getJwt());
        if(profiles.contains("prod")) {

            cookie.setSecure(true);
        }

        cookie.setHttpOnly(true);
        cookie.setMaxAge(60*60);
        cookie.setPath("/");
        response.addCookie(cookie);
        response.addHeader("Access-Control-Expose-Headers", "Set-Cookie");
        response.addHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Origin", environment.getProperty("app.host"));




        return new AuthResponse(true);
    }

    @PostMapping("/login")
    private AuthResponse login(@RequestBody AuthRequest request, HttpServletResponse response) {

        AuthToken token = authenticationService.login(request);

        if(!StringUtils.hasText(token.getJwt())) {
            throw new ApiRequestException("Could not obtain an access token", HttpStatus.NOT_FOUND);
        }


        String[] activeProfiles = environment.getActiveProfiles();
        List<String> profiles = Arrays.asList(activeProfiles);



        Cookie cookie = new Cookie("token", token.getJwt());

        if(profiles.contains("prod")) {
            cookie.setSecure(true);
        }
        cookie.setHttpOnly(true);

        cookie.setMaxAge(60*60);
        cookie.setPath("/");
        response.addCookie(cookie);
        response.addHeader("Access-Control-Expose-Headers", "Set-Cookie");
        response.addHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Origin", environment.getProperty("app.host"));

        return new AuthResponse(true);
    }
}
