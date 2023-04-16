package semester3.project.sanomed.business.impl.employee;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import semester3.project.sanomed.business.exceptions.InvalidUserException;
import semester3.project.sanomed.business.interfaces.employee.UpdateEmployeeUseCase;
import semester3.project.sanomed.business.exceptions.InvalidEmployeeException;
import semester3.project.sanomed.business.validations.EmailValidator;
import semester3.project.sanomed.persistence.Entity.PersonEmbeddable;
import semester3.project.sanomed.domain.request.UpdateBasicEmployeeRequest;
import semester3.project.sanomed.persistence.EmployeeRepository;
import semester3.project.sanomed.persistence.Entity.EmployeeEntity;
import semester3.project.sanomed.persistence.Entity.UserEntity;
import semester3.project.sanomed.persistence.UserRepository;


import javax.transaction.Transactional;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UpdateEmployeeUseCaseImpl implements UpdateEmployeeUseCase {

    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    private EmailValidator emailValidator;

    @Override
    @Transactional
    public void updateEmployee(UpdateBasicEmployeeRequest request){
        Optional<EmployeeEntity> optinalEmployee = employeeRepository.findById(request.getId());
        Optional<UserEntity> optionalUserEntity = userRepository.findByEmp_Id(request.getId());

        if(optinalEmployee.isEmpty()){
            throw new InvalidEmployeeException();
        }
        if(optionalUserEntity.isEmpty()){
            throw new InvalidUserException();
        }

        UserEntity user = optionalUserEntity.get();
        EmployeeEntity employee = optinalEmployee.get();

        String email = request.getEmail();
        String emailTwo = employee.getPersonEmbeddable().getEmail();
        if(!email.equals(emailTwo)){
            emailValidator.validate(request.getEmail());
        }

        updateObjectFieldsEmployee(request, employee);
        updateObjectFieldsUser(request, user);
    }

    private void updateObjectFieldsUser(UpdateBasicEmployeeRequest request, UserEntity user) {

        user.setEmail(request.getEmail());

        userRepository.save(user);
    }

    public void updateObjectFieldsEmployee(UpdateBasicEmployeeRequest request, EmployeeEntity employee){

        employee.setPersonEmbeddable(new PersonEmbeddable(request.getFirstName(), request.getLastName(),
                request.getEmail(), request.getPhone(), employee.getPersonEmbeddable().getBirthdate()));
        employee.setDescription(request.getDescription());
        employee.getStatus();

        employeeRepository.save(employee);
    }
}
