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
import semester3.project.sanomed.business.interfaces.employee.*;
import semester3.project.sanomed.domain.Employee;
import semester3.project.sanomed.domain.Person;
import semester3.project.sanomed.domain.request.UpdateBasicEmployeeRequest;
import semester3.project.sanomed.domain.request.UpdateStatusEmployeeRequest;
import semester3.project.sanomed.persistence.Entity.EmployeeEntity;
import semester3.project.sanomed.persistence.Entity.PersonEmbeddable;
import semester3.project.sanomed.domain.Status_Employee;
import semester3.project.sanomed.domain.request.CreateAEmployeeRequest;
import semester3.project.sanomed.domain.response.CreateEmployeeResponse;
import semester3.project.sanomed.domain.response.GetEmployeesResponse;
import semester3.project.sanomed.persistence.Entity.RoleEnum;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private  CreateEmployeeUseCase createEmployeeUseCaseMock;
    @MockBean
    private  GetEmployeesUseCase getEmployeesUseCaseMock;
    @MockBean
    private  GetEmployeeUseCase getEmployeeUseCaseMock;
    @MockBean
    private  GetEmployeeByLastNameUseCase getEmployeeByLastNameUseCaseMock;
    @MockBean
    private  UpdateEmployeeUseCase updateEmployeeUseCaseMock;
    @MockBean
    private  GetEmployeesByRole getEmployeesByRoleMock;
    @MockBean
    private  UpdateEmployeeStatusUseCase updateEmployeeStatusMock;
    @MockBean
    private  GetFirstFiveUseCase getFirstFiveUseCaseMock;
    @MockBean
    private GetEmployeesByRoleAndStatusUseCase getEmployeesByRoleAndStatus;
    @MockBean
    private GetTopFiveEmployeesForTheMonthUseCase getTopFiveEmployeesForTheMonthUseCaseMock;
    @MockBean
    private  GetDoctorByIdStatusPublicUseCase getDoctorByIdStatusPublic;

    @MockBean
    private AccessTokenDecoder accessTokenDecoderMock;


    PersonEmbeddable personEmbeddable = PersonEmbeddable.builder().firstName("Ivana").lastName("Nedelkova").email("nrdelkova@gmail.com").phoneNumber("0898939914")
            .birthdate(LocalDate.of(1980, 10, 11)).build();
    Person personOne = Person.builder().firstName("Ivana").lastName("Nedelkova").email("nrdelkova@gmail.com").phoneNumber("0898939914")
            .birthdate(LocalDate.of(1980, 10, 11)).build();
    Person personTwo = Person.builder().firstName("Shar").lastName("Peeters").email("shar@gmail.com").phoneNumber("0898939915")
            .birthdate(LocalDate.of(1970,10, 11)).build();
    Employee empOne = Employee.builder().id(1L).person(personOne).description("des").status(Status_Employee.ACTIVE).build();
    Employee empTwo = Employee.builder().id(2L).person(personTwo).description("desTwo").status(Status_Employee.ACTIVE).build();
    EmployeeEntity employeeEntity = EmployeeEntity.builder().id(1L).personEmbeddable(personEmbeddable).description("des").status(Status_Employee.ACTIVE).build();


    @Test
    @WithMockUser(username = "admin@gmail.com", roles = {"ADMINISTARTOR"})
    void getAllEmployees_shouldReturn200ResponseWithEmployeesArray() throws Exception {
        GetEmployeesResponse response = GetEmployeesResponse.builder()
                .employees(List.of(empOne, empTwo)).build();
        when(getEmployeesUseCaseMock.getEmployees())
                .thenReturn(response);


        mockMvc.perform(get("/employees"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().json("""
                        {"employees":[{"id":1, "description":"des", "person": {"firstName":"Ivana", "lastName":"Nedelkova","email":"nrdelkova@gmail.com","phoneNumber": "0898939914", "birthdate":"1980-10-11" }, "status":"ACTIVE"}
                        ,{"id":2, "description":"desTwo","person": {"firstName":"Shar", "lastName":"Peeters","email":"shar@gmail.com","phoneNumber": "0898939915", "birthdate":"1970-10-11"}, "status":"ACTIVE"}]}
                        """));

        verify(getEmployeesUseCaseMock).getEmployees();
    }

    @Test
    void getEmployee_shouldReturn200ResponseWithEmployee() throws Exception {
        Employee employee = Employee.builder().id(54L)
                .person(personOne)
                .description("des")
                .status(Status_Employee.ACTIVE)
                .build();

        when(getEmployeeUseCaseMock.getEmployee(54L))
                .thenReturn(employee);


        mockMvc.perform(get("/employees/54"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().json("""
                        {"id":54, "description":"des", "person": {"firstName":"Ivana", "lastName":"Nedelkova","email":"nrdelkova@gmail.com","phoneNumber": "0898939914", "birthdate":"1980-10-11" }, "status":"ACTIVE"}
                        """));

        verify(getEmployeeUseCaseMock).getEmployee(54L);
    }



    @Test
    @WithMockUser(username = "admin@gmail.com", roles = {"ADMINISTARTOR"})
    void createEmployee_shouldCreateAndReturn201_WhenRequestValid()throws Exception{

        CreateAEmployeeRequest expectedEmployee = CreateAEmployeeRequest
                .builder()
                .firstName("Ivana")
                .lastName("Nedelkova")
                .email("nrdelkova@gmail.com")
                .password("123456")
                .description("description")
                .role(RoleEnum.DOCTOR)
                .birthdate(LocalDate.of(1980,10,11))
                .phoneNumber("0898939914")
                .build();

                 when(createEmployeeUseCaseMock.createEmployee(expectedEmployee))
                    .thenReturn(CreateEmployeeResponse.builder()
                            .id(100L)
                            .build());

                 mockMvc.perform(post("/employees")
                                 .contentType(APPLICATION_JSON_VALUE)
                                 .content("""
                                         { "firstName":"Ivana", "lastName":"Nedelkova", "phoneNumber": "0898939914", "birthdate":"1980-10-11", "email":"nrdelkova@gmail.com", "password":"123456", "description":"description", "role":"DOCTOR"  }
                                         """))
                         .andDo(print())
                         .andExpect(status().isCreated())
                         .andExpect(header().string("Content-Type",
                                 APPLICATION_JSON_VALUE))
                         .andExpect(content().json("""
                            {"id":100}
                                    """));

                 verify(createEmployeeUseCaseMock).createEmployee(expectedEmployee);
    }

    @Test
    @WithMockUser(username = "admin@gmail.com", roles = {"ADMINISTARTOR"})
    void createEmployee_shouldNotCreateAndReturn400_WhenMissingFields() throws Exception {

        mockMvc.perform(post("/employees")
                .contentType(APPLICATION_JSON_VALUE)
                        .content("""
                                { "firstName": "", "lastName": "", "email": "", "password": "",
                                "description": ""}
                                """))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().json("""
                [ {"field":"firstName","error":"must not be blank"},
                {"field":"lastName","error":"must not be blank"},
                {"field":"email","error":"must not be blank"},
                 {"field":"password","error":"must not be blank"},
                 {"field":"description","error":"must not be blank"}]
                    """));

        verifyNoInteractions(createEmployeeUseCaseMock);
    }


//    @Test
//    @WithMockUser(username = "admin@gmail.com", password = "admin5422", roles = {"ADMINISTRATOR"})
//    public void deleteEmployee_shouldNoContentSecond() throws Exception {
//
//        long employeeId = 123;
//
//        doNothing().when(deleteEmployeeUseCaseMock).deleteEmployee(employeeId);
//
//        mockMvc.perform(delete("/employees/delete/{employeeId}", employeeId))
//                .andExpect(status().isNoContent());
//
//        verify(deleteEmployeeUseCaseMock).deleteEmployee(employeeId);
//    }

    @Test
    void getEmployeesByLastname_shouldReturn200ResponseWithEmployeesArray() throws Exception {
        GetEmployeesResponse response = GetEmployeesResponse.builder()
                .employees(List.of(empOne)).build();
        when(getEmployeeByLastNameUseCaseMock.getEmployeeByLastName("Nedelkova"))
                .thenReturn(response);


        mockMvc.perform(get("/employees/lastName").param("ln", empOne.getPerson().getLastName()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().json("""
                        {"employees":[{"id":1, "description":"des", "person": {"firstName":"Ivana", "lastName":"Nedelkova","email":"nrdelkova@gmail.com","phoneNumber": "0898939914", "birthdate":"1980-10-11" }, "status":"ACTIVE"}]}
                        """));

        verify(getEmployeeByLastNameUseCaseMock).getEmployeeByLastName("Nedelkova");
    }


    @Test
    void getEmployeesByRole_shouldReturn200ResponseWithEmployeesArray() throws Exception {
        GetEmployeesResponse response = GetEmployeesResponse.builder()
                .employees(List.of(empOne)).build();
        when(getEmployeesByRoleMock.getEmployees(RoleEnum.DOCTOR))
                .thenReturn(response);


        mockMvc.perform(get("/employees/role").param("role", RoleEnum.DOCTOR.toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().json("""
                        {"employees":[{"id":1, "description":"des", "person": {"firstName":"Ivana", "lastName":"Nedelkova","email":"nrdelkova@gmail.com","phoneNumber": "0898939914", "birthdate":"1980-10-11" }, "status":"ACTIVE"}]}
                        """));

        verify(getEmployeesByRoleMock).getEmployees(RoleEnum.DOCTOR);
    }

    @Test
    @WithMockUser(username = "admin@gmail.com", password = "admin5422", roles = {"ADMINISTRATOR"})
    public void updateBasicEmployee_shouldReturn204Response() throws Exception {
        UpdateBasicEmployeeRequest request = UpdateBasicEmployeeRequest.builder().id(1L).firstName("Ivana").lastName("Nedelkova").email("email@gmail.com")
                .phone("0974635522").description("test").build();

        doNothing().when(updateEmployeeUseCaseMock).updateEmployee(request);

        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(put("/employees/{id}", request.getId())
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent());

        verify(updateEmployeeUseCaseMock,times(1)).updateEmployee(request);
    }

    @Test
    @WithMockUser(username = "admin@gmail.com", roles = {"ADMINSTRATOR"})
    public void updateStatusEmployee_shouldReturn204Response() throws Exception {
        UpdateStatusEmployeeRequest request = UpdateStatusEmployeeRequest.builder().id(1L).status(Status_Employee.FIRED).build();

        doNothing().when(updateEmployeeStatusMock).updateEmployeeStatus(request);

        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(put("/employees/status/{id}", request.getId())
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent());

        verify(updateEmployeeStatusMock,times(1)).updateEmployeeStatus(request);
    }
}