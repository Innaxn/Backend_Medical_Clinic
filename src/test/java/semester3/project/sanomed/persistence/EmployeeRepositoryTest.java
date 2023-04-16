package semester3.project.sanomed.persistence;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import semester3.project.sanomed.persistence.Entity.PersonEmbeddable;
import semester3.project.sanomed.domain.Status_Employee;
import semester3.project.sanomed.persistence.Entity.EmployeeEntity;

import javax.persistence.EntityManager;

import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(locations = "classpath:application-dev.properties")
class EmployeeRepositoryTest {

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Test
    void save_shouldSaveEmployeeRequest() {
        PersonEmbeddable personEmbeddable = PersonEmbeddable.builder().firstName("Ivana").lastName("Nedelkova")
                .phoneNumber("0898939914").birthdate(LocalDate.of(1980,10,1))
                .email( "nrdelkova@gmail.com").build();

        EmployeeEntity employee = EmployeeEntity.builder()
                .personEmbeddable(personEmbeddable)
                .description("description")
                .status(Status_Employee.ACTIVE)
                .build();

        EmployeeEntity savedEmployee = employeeRepository.save(employee);
        assertNotNull(savedEmployee.getId());

        savedEmployee = entityManager.find(EmployeeEntity.class, savedEmployee.getId());
        EmployeeEntity expectedEmployee =EmployeeEntity.builder()
                .id(savedEmployee.getId())
                .personEmbeddable(personEmbeddable)
                .description("description")
                .status(Status_Employee.ACTIVE).build();
        assertEquals(expectedEmployee, savedEmployee);
    }
}