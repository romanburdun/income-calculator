package co.uk.soletradercalculator.income_calculator.services.authentication;

import co.uk.soletradercalculator.income_calculator.domain.Role;
import co.uk.soletradercalculator.income_calculator.domain.RoleName;
import co.uk.soletradercalculator.income_calculator.domain.User;
import co.uk.soletradercalculator.income_calculator.security.jwt.JwtTokenProvider;
import co.uk.soletradercalculator.income_calculator.security.requests.AuthRequest;
import co.uk.soletradercalculator.income_calculator.security.responses.AuthToken;
import co.uk.soletradercalculator.income_calculator.api.v1.mappers.UserMapper;
import co.uk.soletradercalculator.income_calculator.exceptions.ApiRequestException;
import co.uk.soletradercalculator.income_calculator.repositories.RoleRepository;
import co.uk.soletradercalculator.income_calculator.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@AllArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider tokenProvider;
    private UserRepository userRepository;
    private UserMapper userMapper;
    private RoleRepository roleRepository;

    @Override
    public AuthToken register(AuthRequest request) {

        if(userRepository.existsByEmail(request.getEmail())) {
            throw new ApiRequestException("User already exists", HttpStatus.CONFLICT);
        }

        Role role = roleRepository.findByName(RoleName.ROLE_ADMINISTRATOR);

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Collections.singleton(role));



        User newUser = userRepository.save(user);

        if(newUser == null) {
            throw new ApiRequestException("New User was not created", HttpStatus.BAD_REQUEST);
        }

        Authentication auth = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmail(),
                                request.getPassword()
                        ));

        SecurityContextHolder.getContext().setAuthentication(auth);

        return new AuthToken(tokenProvider.getToken(auth));
    }

    @Override
    public AuthToken login(AuthRequest request) {

        Authentication auth = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmail(),
                                request.getPassword()
                        ));

        SecurityContextHolder.getContext().setAuthentication(auth);


        return new AuthToken(tokenProvider.getToken(auth));
    }
}
