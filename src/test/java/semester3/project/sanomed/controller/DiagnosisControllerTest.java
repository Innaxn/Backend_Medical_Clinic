package semester3.project.sanomed.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import semester3.project.sanomed.business.AccessTokenDecoder;
import semester3.project.sanomed.business.interfaces.diagnose.*;
import semester3.project.sanomed.domain.*;
import semester3.project.sanomed.domain.request.CreateDiagnosisRequest;
import semester3.project.sanomed.domain.request.UpdateDiagnoseRequest;
import semester3.project.sanomed.domain.response.CreateDiagnoseResponse;
import semester3.project.sanomed.domain.response.GetDiagnosisResponse;
import semester3.project.sanomed.domain.response.GetDiagnosisResponse.DiagnoseData;
import semester3.project.sanomed.domain.response.GetPrescriptionsResponse;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(DiagnosisController.class)
class DiagnosisControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private  CreateDiagnoseUseCase createDiagnoseUseCaseMock;
    @MockBean
    private  DeleteDiagnosisUseCase deleteDiagnoseUseCaseMock;
    @MockBean
    private  GetDiagnoseUseCase getDiagnoseUseCaseMock;
    @MockBean
    private  GetDiagnosisByPatientIdUseCase getDiagnosisByPatientIdUseCase;
    @MockBean
    private  UpdateDiagnoseUseCase updateDiagnoseUseCase;
    @MockBean
    private AccessTokenDecoder accessTokenDecoder;


    Person personOne = Person.builder().firstName("Ivana").lastName("Nedelkova").email("nrdelkova@gmail.com").phoneNumber("0898939914")
            .birthdate(LocalDate.of(1980, 10, 11)).build();

    Employee empOne = Employee.builder().id(1L).person(personOne).description("des").status(Status_Employee.ACTIVE).build();
    Patient patient = Patient.builder().id(1L).person(personOne).bsn(5422).healthInsuranceNumber(5422).build();
    DiagnoseData diagnose = GetDiagnosisResponse.DiagnoseData.builder().id(54L)
            .name("test").details("test").FirstNamePatient("Ivana").LastNamePatient("Nedelkova").bsn(5422).healthInsuranceNumber(5422)
            .FirstNameDoctor("Shar").LastNameDoctor("Peeters")
            .build();
    @Test
    @WithMockUser(username="doctor5422@gmail.com", roles = {"DOCTOR"})
    void create_shouldCreateAndReturn201_WhenRequestValid()throws Exception {
        CreateDiagnosisRequest expectedDiagnose = CreateDiagnosisRequest
                .builder()
                .name("name")
                .details("details")
                .doctorId(1L)
                .patientId(1L)
                .build();

        when(createDiagnoseUseCaseMock.createDiagnose(expectedDiagnose))
                .thenReturn(CreateDiagnoseResponse.builder()
                        .id(100L)
                        .build());

        mockMvc.perform(post("/diagnosis")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content("""
                                  { "name":"name", "details":"details", "doctorId": 1, "patientId": 1 }
                                 """))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string("Content-Type",
                        APPLICATION_JSON_VALUE))
                .andExpect(content().json("""
                            {"id":100}
                             """));

        verify(createDiagnoseUseCaseMock).createDiagnose(expectedDiagnose);
    }


    @Test
    void getDiagnose_shouldReturn200ResponseWithDiagnose() throws Exception {
        DiagnoseData diagnose = GetDiagnosisResponse.DiagnoseData.builder().id(54L)
                .name("test").details("test").FirstNamePatient("Ivana").LastNamePatient("Nedelkova").bsn(5422).healthInsuranceNumber(5422)
                .FirstNameDoctor("Shar").LastNameDoctor("Peeters")
                .build();

        when(getDiagnoseUseCaseMock.getDiagnose(54L))
                .thenReturn(Optional.of(diagnose));


        mockMvc.perform(get("/diagnosis/get/54"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().json("""
                        {"id":54, "name":"test", "details":"test", "bsn": 5422,"healthInsuranceNumber": 5422, 
                        "firstNamePatient":"Ivana", "lastNamePatient":"Nedelkova","firstNameDoctor":"Shar", "lastNameDoctor":"Peeters"}
                      
                        """));

        verify(getDiagnoseUseCaseMock).getDiagnose(54L);
    }

    //pid not found??
//    @Test
//    @WithMockUser(username="doctor5422@gmail.com", roles = {"DOCTOR", "PATIENT"})
//    void getDiagnoseByPatientId_shouldReturn200ResponseWithEmployee() throws Exception {
//       DiagnoseData d =  DiagnoseData.builder().id(54L).pId(23L)
//                .name("test").details("test").FirstNamePatient("Ivana").LastNamePatient("Nedelkova").bsn(5422).healthInsuranceNumber(5422)
//                .FirstNameDoctor("Shar").LastNameDoctor("Peeters").build();
//
//        GetDiagnosisResponse diagnose = GetDiagnosisResponse.builder().diagnosis(List.of(d))
//                .build();
//
//        when(getDiagnosisByPatientIdUseCase.getDiagnosisByPatientId(23L))
//                .thenReturn(diagnose);
//
//
//        mockMvc.perform(get("/diagnosis/getByPId/23"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
//                .andExpect(content().json("""
//                        {"diagnosis":[{"id":54, "pId":23, "name":"test", "details":"test", "bsn": 5422,"healthInsuranceNumber": 5422,
//                        "firstNamePatient":"Ivana", "lastNamePatient":"Nedelkova","firstNameDoctor":"Shar", "lastNameDoctor":"Peeters"}]}
//                        """));
//
//        verify(getDiagnosisByPatientIdUseCase).getDiagnosisByPatientId(23L);
//    }

//    @Test
//    public void getDiagnoseByPatientId_shouldReturn200Response() throws Exception {
//        ObjectMapper objectMapper = new ObjectMapper();
//        GetDiagnosisResponse d =  GetDiagnosisResponse.builder().diagnosis(List.of(diagnose)).build();
//
//        when(getDiagnosisByPatientIdUseCase.getDiagnosisByPatientId(1L)).thenReturn(d);
//
//        MvcResult result = mockMvc.perform(get("/diagnosis/getByPId/1"))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        verify(getDiagnosisByPatientIdUseCase).getDiagnosisByPatientId(1L);
//
//        String responseBody = result.getResponse().getContentAsString();
//        assertEquals(objectMapper.writeValueAsString(d), objectMapper.writeValueAsString(responseBody));
//
//    }
//    @Test
//    public void testGetDiagnoseByPatientId() throws Exception {
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        GetDiagnosisResponse d =  GetDiagnosisResponse.builder().diagnosis(List.of(diagnose)).build();
//
//        when(getDiagnosisByPatientIdUseCase.getDiagnosisByPatientId(1L)).thenReturn(d);
//
//        MvcResult result = mockMvc.perform(get("/diagnosis/getByPId/1"))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        verify(getDiagnosisByPatientIdUseCase).getDiagnosisByPatientId(1L);
//
//        String responseBody = result.getResponse().getContentAsString();
//
//        String expectedResponseJson = objectMapper.writeValueAsString(d);
//        String responseBodyJson = objectMapper.writeValueAsString(responseBody);
//
//        assertEquals(expectedResponseJson, responseBodyJson);
//    }


    @Test
    @WithMockUser(username = "doctor5422@gmail.com", roles = {"DOCTOR"})
    public void deletePrescription_shouldNoContentSecond() throws Exception {

        doNothing().when(deleteDiagnoseUseCaseMock).deleteDiagnose(1);

        mockMvc.perform(delete("/diagnosis/delete/1"))
                .andExpect(status().isNoContent());

        verify(deleteDiagnoseUseCaseMock).deleteDiagnose(1);
    }

    @Test
    @WithMockUser(username = "doctor5422@gmail.com", roles = {"DOCTOR"})
    void UpdateDiagnose_shouldReturnNoContent() throws Exception {
        UpdateDiagnoseRequest request = UpdateDiagnoseRequest.builder().id(1L).doctorId(1L).name("test")
                .description("test").build();

        doNothing().when(updateDiagnoseUseCase).updateDiagnose(request);

        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(put("/diagnosis/1")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent());

        verify(updateDiagnoseUseCase).updateDiagnose(request);
    }
}