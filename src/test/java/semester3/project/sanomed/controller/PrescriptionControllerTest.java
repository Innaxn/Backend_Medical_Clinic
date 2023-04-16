package semester3.project.sanomed.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
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
import semester3.project.sanomed.business.interfaces.patient.*;
import semester3.project.sanomed.business.interfaces.prescription.CreatePrescriptionUseCase;
import semester3.project.sanomed.business.interfaces.prescription.DeletePrescriptionUseCase;
import semester3.project.sanomed.business.interfaces.prescription.GetPrescriptionsByDiagnoseIdUseCase;
import semester3.project.sanomed.business.interfaces.prescription.UpdatePrescriptionUseCase;
import semester3.project.sanomed.domain.*;
import semester3.project.sanomed.domain.request.CreatePatientRequest;
import semester3.project.sanomed.domain.request.CreatePrescriptionRequest;
import semester3.project.sanomed.domain.request.UpdatePrescriptionRequest;
import semester3.project.sanomed.domain.response.CreatePatientResponse;
import semester3.project.sanomed.domain.response.CreatePrescriptionResponse;
import semester3.project.sanomed.domain.response.GetEmployeesResponse;
import semester3.project.sanomed.domain.response.GetPrescriptionsResponse;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PrescriptionController.class)
class PrescriptionControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private  CreatePrescriptionUseCase createPrescriptionUseCase;
    @MockBean
    private  DeletePrescriptionUseCase deletePrescriptionUseCase;
    @MockBean
    private  UpdatePrescriptionUseCase updatePrescriptionUseCase;
    @MockBean
    private  GetPrescriptionsByDiagnoseIdUseCase getPrescriptionsByDiagnoseIdUseCase;
    @MockBean
    private AccessTokenDecoder accessTokenDecoderMock;


    Person personOne = Person.builder().firstName("Ivana").lastName("Nedelkova").email("nrdelkova@gmail.com").phoneNumber("0898939914")
            .birthdate(LocalDate.of(1980, 10, 11)).build();
    Employee empOne = Employee.builder().id(1L).person(personOne).description("des").status(Status_Employee.ACTIVE).build();
    Patient patient = Patient.builder().id(54L).person(personOne).bsn(5422).healthInsuranceNumber(5422).diagnose(List.of()).build();
    Diagnose diagnose = Diagnose.builder().id(1L).details("test").name("test").patient(patient).doctor(empOne).build();
    Prescription prescription = Prescription.builder().id(55L).start(LocalDate.of(2022,12,22)).end(LocalDate.of(2022,12,30))
            .medication("test").doctor(empOne)
            .build();
    Prescription prescriptionTwo = Prescription.builder().id(55L).start(LocalDate.of(2022,12,22)).end(LocalDate.of(2022,12,30))
            .medication("test").doctor(empOne).diagnose(diagnose)
            .build();

    @Test
    @WithMockUser(username = "doctor5422@gmail.com", roles = {"DOCTOR"})
    void create() throws Exception {
        CreatePrescriptionRequest request = CreatePrescriptionRequest
                .builder()
                .start(LocalDate.of(2022, 12, 28))
                .end(LocalDate.of(2023, 10, 11))
                .doctorId(1L)
                .diagnoseId(1L)
                .medication("test")
                .build();

        when(createPrescriptionUseCase.createPrescription(request))
                .thenReturn(CreatePrescriptionResponse.builder()
                        .id(100L)
                        .build());

        mockMvc.perform(post("/prescriptions")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content("""
                                  { "start":"2022-12-28", "end":"2023-10-11", "medication":"test", "doctorId":1, "diagnoseId": 1}
                                  """))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string("Content-Type",
                        APPLICATION_JSON_VALUE))
                .andExpect(content().json("""
                            {"id":100}
                                    """));

        verify(createPrescriptionUseCase).createPrescription(request);
    }

    @Test
    @WithMockUser(username = "doctor5422@gmail.com", roles = {"DOCTOR"})
    void deletePrescription() throws Exception {
        this.mockMvc.perform(delete("/prescriptions/delete/1")
                        .contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isNoContent());
    }
    @Test
    @WithMockUser(username = "doctor5422@gmail.com", roles = {"DOCTOR"})
    public void deletePrescription_shouldNoContentSecond() throws Exception {

        doNothing().when(deletePrescriptionUseCase).deletePrescription(1);

        mockMvc.perform(delete("/prescriptions/delete/1"))
                .andExpect(status().isNoContent());

        verify(deletePrescriptionUseCase).deletePrescription(1);
    }


    @Test
    @WithMockUser(username = "doctor5422@gmail.com", roles = {"DOCTOR"})
     void testUpdatePrescription() throws Exception {
        long id = 1;
        UpdatePrescriptionRequest request = UpdatePrescriptionRequest.builder().id(1L).start(LocalDate.of(2022, 12,22))
                .end(LocalDate.of(2022, 12,28)).doctorId(1L).medication("test").build();

        doNothing().when(updatePrescriptionUseCase).updatePrescription(request);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        mockMvc.perform(put("/prescriptions/{id}", id)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent());

        verify(updatePrescriptionUseCase).updatePrescription(request);
    }

//    @Test
//    @WithMockUser(username = "doctor5422@gmail.com", roles = {"DOCTOR"})
//    void getPrescriptionsByDiagnoseId() throws Exception {
//        GetPrescriptionsResponse response = GetPrescriptionsResponse.builder()
//                .prescriptions(List.of(prescription)).build();
//        when(getPrescriptionsByDiagnoseIdUseCase.getPrescriptionsByDiagnoseId(diagnose.getId()))
//                .thenReturn(response);
//
//
//        mockMvc.perform(get("/prescriptions/getByDId/").param("dId", diagnose.getId().toString()))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
//                .andExpect(content().json("""
//                        {"prescriptions": [{"id":55, "start": "2022-12-22","end": "2022-12-30","medication": "test", "doctor":{"id":1, "description":"des", "person": {"firstName":"Ivana", "lastName":"Nedelkova","email":"nrdelkova@gmail.com","phoneNumber": "0898939914", "birthdate":"1980-10-11" }, "status":"ACTIVE"}}]}
//                        """));
//
//        verify(getPrescriptionsByDiagnoseIdUseCase).getPrescriptionsByDiagnoseId(diagnose.getId());
//    }

//    @Test
//    @WithMockUser(username = "doctor5422@gmail.com", roles = {"DOCTOR"})
//    public void GetPrescriptionsByDiagnoseId() throws Exception {
//        ObjectMapper objectMapper = new Jackson2ObjectMapperBuilder()
//                .serializationInclusion(JsonInclude.Include.NON_NULL)
//                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
//                .dateFormat(new SimpleDateFormat("[yyyy,MM,dd]"))
//                .build();
//        GetPrescriptionsResponse expectedResponse = GetPrescriptionsResponse.builder().prescriptions(List.of(prescriptionTwo)).build();
//
//        when(getPrescriptionsByDiagnoseIdUseCase.getPrescriptionsByDiagnoseId(55L)).thenReturn(expectedResponse);
//
//        MvcResult result = mockMvc.perform(get("/prescriptions/getByDId/{dId}", 55))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        verify(getPrescriptionsByDiagnoseIdUseCase).getPrescriptionsByDiagnoseId(55L);
//        String responseBody = result.getResponse().getContentAsString();
//        assertEquals(objectMapper.writeValueAsString(expectedResponse),  objectMapper.writeValueAsString(responseBody));
//    }




}