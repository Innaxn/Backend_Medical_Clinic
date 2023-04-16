package semester3.project.sanomed.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import semester3.project.sanomed.business.*;
import semester3.project.sanomed.business.interfaces.appointment.*;
import semester3.project.sanomed.domain.*;
import semester3.project.sanomed.domain.request.CreateAppointmentRequest;
import semester3.project.sanomed.domain.response.CreateAppointmentResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AppointmentController.class)
class AppointmentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateAppointmentUseCase createAppointmentUseCase;
    @MockBean
    private DeleteAppointmentUseCase deleteAppointmentUseCase;
    @MockBean
    private GetAppointmentByIdUseCase getAppointmentByIdUseCase;
    @MockBean
    private GetAppointmentByDocIdAndFromDatesUseCase getAppointmentByDocIdAndFromDatesUseCase;
    @MockBean
    private GetAppointmentByPatientIdAndDateUseCase getAppointmentByPatientIdAndDateUseCase;
    @MockBean
    private  UpdateAppointmentUseCase updateAppointmentUseCase;

    @MockBean
    private AccessTokenDecoder accessTokenDecoderMock;
    Person personOne = Person.builder().firstName("Ivana").lastName("Nedelkova").email("nrdelkova@gmail.com").phoneNumber("0898939914")
            .birthdate(LocalDate.of(1980, 10, 11)).build();

    Employee employee = Employee.builder().id(54L)
            .person(personOne)
            .description("des")
            .status(Status_Employee.ACTIVE)
            .build();

    Patient patient = Patient.builder().id(54L)
            .person(personOne)
            .bsn(5422)
            .healthInsuranceNumber(5422)
            .build();

    @Test
    @WithMockUser(username = "doctor5422@gmail.com", roles = {"DOCTOR"})
    void createAppointment() throws Exception {

        CreateAppointmentRequest request = CreateAppointmentRequest
                .builder()
                .patientId(54L)
                .doctorId(54L)
                .purpose("test")
                .startTime(LocalDateTime.of(2022, 12, 28, 9, 30))
                .build();

        when(createAppointmentUseCase.createAppointment(request))
                .thenReturn(CreateAppointmentResponse.builder()
                        .id(100L)
                        .build());

        mockMvc.perform(post("/appointments")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content("""
                                         {"doctorId": 54, "patientId": 54, "startTime": "2022-12-28T09:30:00", "purpose":"test"}
                                         """))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string("Content-Type",
                        APPLICATION_JSON_VALUE))
                .andExpect(content().json("""
                            {"id":100}
                                    """));

        verify(createAppointmentUseCase).createAppointment(request);
    }

    @Test
    @WithMockUser(username = "secretary@gmail.com", roles = {"SECRETARY"})
    void deleteAppointment() throws Exception {
        this.mockMvc.perform(delete("/appointments/delete/1")
                        .contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "doctor@gmail.com", roles = {"DOCTOR"})
    void getAppointment() throws Exception {
        Appointment appointment = Appointment.builder().id(54L)
                .doctor(employee)
                .patient(patient)
                .purpose("test")
                .start(LocalDateTime.of(2022, 12, 28, 9, 30))
                .build();

        when(getAppointmentByIdUseCase.getAppointment(54L))
                .thenReturn(appointment);


        mockMvc.perform(get("/appointments/54"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().json("""
                        {"id":54, "doctor": {"id":54, "description":"des", "person": {"firstName":"Ivana", "lastName":"Nedelkova","email":"nrdelkova@gmail.com","phoneNumber": "0898939914", "birthdate":"1980-10-11" }, "status":"ACTIVE"},
                         "patient": {"id":54, "bsn":5422, "healthInsuranceNumber":5422, "person": {"firstName":"Ivana", "lastName":"Nedelkova","email":"nrdelkova@gmail.com","phoneNumber": "0898939914", "birthdate":"1980-10-11" }},
                           "start": "2022-12-28T09:30:00", "purpose": "test"}
                        """));

        verify(getAppointmentByIdUseCase).getAppointment(54L);
    }

    @Test
    @WithMockUser(username = "secretary@gmail.com", roles = {"SECRETARY"})
    public void deleteAppointment_shouldNoContentSecond() throws Exception {

        doNothing().when(deleteAppointmentUseCase).deleteAppointment(1);

        mockMvc.perform(delete("/appointments/delete/1"))
                .andExpect(status().isNoContent());

        verify(deleteAppointmentUseCase).deleteAppointment(1);
    }
}