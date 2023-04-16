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
import semester3.project.sanomed.business.interfaces.patient.*;
import semester3.project.sanomed.domain.Employee;
import semester3.project.sanomed.domain.Patient;
import semester3.project.sanomed.domain.Person;
import semester3.project.sanomed.domain.Status_Employee;
import semester3.project.sanomed.domain.request.CreatePatientRequest;
import semester3.project.sanomed.domain.request.UpdateBasicEmployeeRequest;
import semester3.project.sanomed.domain.request.UpdateBasicPatientRequest;
import semester3.project.sanomed.domain.response.CreatePatientResponse;
import semester3.project.sanomed.domain.response.GetEmployeesResponse;
import semester3.project.sanomed.domain.response.GetPatientsResponse;


import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(PatientController.class)
class PatientControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreatePatientUseCase createPatientUseCaseMock;
    @MockBean
    private  DeletePatientUseCase deletePatientUseCaseMock;
    @MockBean
    private  GetPatientByIdUseCase getPatientByIdUseCaseMock;
    @MockBean
    private  GetPatientByBsnUseCase getPatientByBsnUseCaseMock;
    @MockBean
    private  UpdatePatientUseCase updatePatientUseCaseMock;
    @MockBean
    private  GetPatientsUseCase getPatientsUseCaseMock;

    @MockBean
    private  GetTopFivePatientUseCase getTopFivePatientUseCase;

    @MockBean
    private AccessTokenDecoder accessTokenDecoderMock;

    Person personOne = Person.builder().firstName("Ivana").lastName("Nedelkova").email("nrdelkova@gmail.com").phoneNumber("0898939914")
            .birthdate(LocalDate.of(1980, 10, 11)).build();
    Patient patient = Patient.builder().id(54L)
            .person(personOne)
            .bsn(5422)
            .healthInsuranceNumber(5422)
            .build();
    @Test
    void createPatient_shouldCreateAndReturn201_WhenRequestValid() throws Exception{
        CreatePatientRequest expectedPatient = CreatePatientRequest
                .builder()
                .firstName("Ivana")
                .lastName("Nedelkova")
                .email("nrdelkova@gmail.com")
                .password("123456")
                .bsn(123456)
                .healthInsuranceNumber(123456)
                .build();

        when(createPatientUseCaseMock.createPatient(expectedPatient))
                .thenReturn(CreatePatientResponse.builder()
                        .id(100L)
                        .build());

            mockMvc.perform(post("/patients")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content("""
                                  { "firstName":"Ivana", "lastName":"Nedelkova", "email":"nrdelkova@gmail.com", "password":"123456", "bsn": 123456, "healthInsuranceNumber": 123456  }
                                  """))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string("Content-Type",
                        APPLICATION_JSON_VALUE))
                .andExpect(content().json("""
                            {"id":100}
                                    """));

        verify(createPatientUseCaseMock).createPatient(expectedPatient);

    }


    @Test
    @WithMockUser(username = "patient@gmail.com", roles = {"PATIENT"})
    public void deleteEmployee_shouldNoContentSecond() throws Exception {

        long patientId = 123;

        doNothing().when(deletePatientUseCaseMock).deletePatient(patientId);

        mockMvc.perform(delete("/patients/delete/123"))
                .andExpect(status().isNoContent());

        verify(deletePatientUseCaseMock).deletePatient(patientId);
    }

    @Test
    void getPatient_shouldReturn200ResponseWithEmployee() throws Exception {
        Patient patient = Patient.builder().id(54L)
                .person(personOne)
                .bsn(5422)
                .healthInsuranceNumber(5422)
                .build();

        when(getPatientByIdUseCaseMock.getPatientById(54L))
                .thenReturn(patient);


        mockMvc.perform(get("/patients/get/54"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().json("""
                        {"id":54, "bsn":5422, "healthInsuranceNumber":5422, "person": {"firstName":"Ivana", "lastName":"Nedelkova","email":"nrdelkova@gmail.com","phoneNumber": "0898939914", "birthdate":"1980-10-11" }}
                        """));

        verify(getPatientByIdUseCaseMock).getPatientById(54L);
    }

    @Test
    @WithMockUser(username = "doctor5422@gmail.com", roles = {"DOCTOR", "SECRETARY"})
    void getPatientByBsn_shouldReturn200ResponseWithEmployeesArray() throws Exception {
        Patient patient = Patient.builder().id(54L)
                .person(personOne)
                .bsn(5422)
                .healthInsuranceNumber(5422)
                .build();
        GetPatientsResponse response = GetPatientsResponse.builder().patients(List.of(patient)).build();
        when(getPatientByBsnUseCaseMock.getPatientsByBsn(5422))
                .thenReturn(response);


        mockMvc.perform(get("/patients/getByBsn").param("bsn", patient.getBsn().toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().json("""
                        {"patients":[{"id":54, "bsn":5422, "healthInsuranceNumber":5422, "person": {"firstName":"Ivana", "lastName":"Nedelkova","email":"nrdelkova@gmail.com","phoneNumber": "0898939914", "birthdate":"1980-10-11" }}]}
                        """));

        verify(getPatientByBsnUseCaseMock).getPatientsByBsn(5422);
    }

    @Test
    @WithMockUser(username = "doctor5422@gmail.com", roles = {"DOCTOR"})
    void getAllPatients_shouldReturn200ResponseWithEmployeesArray() throws Exception {
        GetPatientsResponse response = GetPatientsResponse.builder()
                .patients(List.of(patient)).build();

        when(getPatientsUseCaseMock.getPatients())
                .thenReturn(response);


        mockMvc.perform(get("/patients"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().json("""
                        {"patients":[{"id":54, "bsn":5422, "healthInsuranceNumber":5422, "person": {"firstName":"Ivana", "lastName":"Nedelkova","email":"nrdelkova@gmail.com","phoneNumber": "0898939914", "birthdate":"1980-10-11" }}]}
                        """));

        verify(getPatientsUseCaseMock).getPatients();
    }

    @Test
    @WithMockUser(username = "patient@gmail.com", roles = {"PATIENT"})
    public void updatePatient_shouldReturn204Response() throws Exception {
        UpdateBasicPatientRequest request = UpdateBasicPatientRequest.builder().id(1L).firstName("Ivana").lastName("Nedelkova").email("email@gmail.com")
                .phone("0974635522").healthInsNum(5422).build();

        doNothing().when(updatePatientUseCaseMock).updatePatient(request);

        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(put("/patients/{id}", request.getId())
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent());

        verify(updatePatientUseCaseMock,times(1)).updatePatient(request);
    }
}