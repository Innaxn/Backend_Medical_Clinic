package semester3.project.sanomed.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import semester3.project.sanomed.business.AccessTokenDecoder;
import semester3.project.sanomed.business.LoginUseCase;
import semester3.project.sanomed.domain.request.LoginRequest;
import semester3.project.sanomed.domain.response.LoginResponse;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(LoginController.class)
class LoginControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private  LoginUseCase loginUseCaseMock;
    @MockBean
    private AccessTokenDecoder accessTokenDecoderMock;

    @Test
    void login() throws Exception {
        LoginRequest request = LoginRequest.builder().email("email@gmail.com").password("5422").build();
        when(loginUseCaseMock.login(request))
                .thenReturn(LoginResponse.builder().accessToken("asdasd")
                        .build());

        mockMvc.perform(post("/login")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content("""
                                { "email":"email@gmail.com", "password":"5422"}
                                """))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type",
                        APPLICATION_JSON_VALUE))
                .andExpect(content().json("""
                            {"accessToken":"asdasd"}
                                    """));


        verify(loginUseCaseMock).login(request);
    }
}