package co.uk.soletradercalculator.income_calculator.controllers;

import co.uk.soletradercalculator.income_calculator.security.requests.AuthRequest;
import co.uk.soletradercalculator.income_calculator.security.responses.AuthResponse;
import co.uk.soletradercalculator.income_calculator.security.responses.AuthToken;
import co.uk.soletradercalculator.income_calculator.services.authentication.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static co.uk.soletradercalculator.income_calculator.controllers.AbstractJsonController.asJsonString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthControllerTest {

    @Mock
    private AuthenticationService authenticationService;
    @Mock
    private Environment environment;

    @InjectMocks
    private AuthController authController;


    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    void login() throws Exception {


        String[] active = {"dev"};
        AuthToken authToken = new AuthToken("jwt_string");
        AuthResponse res =  new AuthResponse(true);
        Mockito.when(authenticationService.login(Mockito.any(AuthRequest.class))).thenReturn(authToken);
        Mockito.when(environment.getActiveProfiles()).thenReturn(active);
        Mockito.when(environment.getProperty(Mockito.anyString())).thenReturn("sitedev.co.uk");


        String response = mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(res)))
                .andExpect(status().isOk())
                .andReturn().getResponse()
                .getContentAsString();

        assertEquals("{\"success\":true}", response);

    }

 @Test
    void register() throws Exception {


        String[] active = {"dev"};
        AuthToken authToken = new AuthToken("jwt_string");
        AuthResponse res =  new AuthResponse(true);
        Mockito.when(authenticationService.register(Mockito.any(AuthRequest.class))).thenReturn(authToken);
        Mockito.when(environment.getActiveProfiles()).thenReturn(active);
        Mockito.when(environment.getProperty(Mockito.anyString())).thenReturn("sitedev.co.uk");


        String response = mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(res)))
                .andExpect(status().isOk())
                .andReturn().getResponse()
                .getContentAsString();

        assertEquals("{\"success\":true}", response);
    }

}
