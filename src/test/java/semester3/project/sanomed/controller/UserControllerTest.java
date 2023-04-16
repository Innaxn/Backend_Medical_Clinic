package semester3.project.sanomed.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import semester3.project.sanomed.business.AccessTokenDecoder;
import semester3.project.sanomed.business.UpdateUserPasswordUseCase;
import semester3.project.sanomed.domain.request.UpdateBasicEmployeeRequest;
import semester3.project.sanomed.domain.request.UpdateUserPaswordRequest;
import semester3.project.sanomed.persistence.Entity.RoleEnum;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private  UpdateUserPasswordUseCase updateUserPasswordUseCaseMock;
    @MockBean
    private AccessTokenDecoder accessTokenDecoderMock;

    @Test
    @WithMockUser(username = "patient@gmail.com", password = "pwd", roles = {"PATIENT"})
    public void updatePassword_shouldReturn204Response() throws Exception {
        UpdateUserPaswordRequest request = UpdateUserPaswordRequest.builder().id(1L).currentPassword("pwd").newPassword("5422").role(RoleEnum.PATIENT).build();

        doNothing().when(updateUserPasswordUseCaseMock).updatePassword(request);

        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(put("/user/pwd/{id}", request.getId())
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent());

        verify(updateUserPasswordUseCaseMock,times(1)).updatePassword(request);
    }
}