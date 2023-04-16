package semester3.project.sanomed.business.impl.employee;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import semester3.project.sanomed.business.interfaces.employee.CreateEmployeeUseCase;
import semester3.project.sanomed.business.validations.EmailValidator;
import semester3.project.sanomed.persistence.Entity.PersonEmbeddable;
import semester3.project.sanomed.domain.Status_Employee;
import semester3.project.sanomed.domain.request.CreateAEmployeeRequest;
import semester3.project.sanomed.domain.response.CreateEmployeeResponse;
import semester3.project.sanomed.persistence.EmployeeRepository;
import semester3.project.sanomed.persistence.Entity.EmployeeEntity;
import semester3.project.sanomed.persistence.Entity.RoleEnum;
import semester3.project.sanomed.persistence.Entity.UserEntity;
import semester3.project.sanomed.persistence.Entity.UserRoleEntity;
import semester3.project.sanomed.persistence.UserRepository;

import javax.transaction.Transactional;
import java.util.Set;

@Service
@AllArgsConstructor
public class CreateEmployeeUseCaseImpl implements CreateEmployeeUseCase {
    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    private EmailValidator emailValidator;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public CreateEmployeeResponse createEmployee(CreateAEmployeeRequest request) {

        emailValidator.validate(request.getEmail());

        EmployeeEntity savedEmployee = saveNewEmployee(request);

        saveNewUser(savedEmployee, request.getPassword(), request.getRole());

        return CreateEmployeeResponse.builder()
                .id(savedEmployee.getId())
                .build();
    }

    private void saveNewUser(EmployeeEntity employee, String password, RoleEnum givenRole) {
        String encodedPassword = passwordEncoder.encode(password);

        UserEntity newUser = UserEntity.builder()
                .email(employee.getPersonEmbeddable().getEmail())
                .password(encodedPassword)
                .emp(employee)
                .build();

        newUser.setUserRoles(Set.of(
                UserRoleEntity.builder()
                        .user(newUser)
                        .role(givenRole)
                        .build()));
        userRepository.save(newUser);
    }

    private EmployeeEntity saveNewEmployee(CreateAEmployeeRequest request) {

         EmployeeEntity newEmployee = EmployeeEntity.builder()
                 .personEmbeddable(new PersonEmbeddable(request.getFirstName(), request.getLastName(), request.getEmail(),
                         request.getPhoneNumber(), request.getBirthdate()))
                 .description(request.getDescription())
                 .status(Status_Employee.ACTIVE)
                 .build();

        EmployeeEntity emp = employeeRepository.save(newEmployee);

       return emp;
    }
}
