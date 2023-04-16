package semester3.project.sanomed.business.impl.employee;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import semester3.project.sanomed.business.interfaces.employee.UpdateEmployeeStatusUseCase;
import semester3.project.sanomed.business.exceptions.InvalidEmployeeException;
import semester3.project.sanomed.persistence.Entity.PersonEmbeddable;
import semester3.project.sanomed.domain.request.UpdateStatusEmployeeRequest;
import semester3.project.sanomed.persistence.EmployeeRepository;
import semester3.project.sanomed.persistence.Entity.EmployeeEntity;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UpdateEmployeeStatusUseCaseImpl implements UpdateEmployeeStatusUseCase {
    private final EmployeeRepository employeeRepository;

    @Override
    public void updateEmployeeStatus(UpdateStatusEmployeeRequest request) {
        Optional<EmployeeEntity> optinalEmployee = employeeRepository.findById(request.getId());

        if(optinalEmployee.isEmpty()){
            throw new InvalidEmployeeException();
        }

        EmployeeEntity employee = optinalEmployee.get();
        updateObjectFields(request, employee);
    }

    public void updateObjectFields(UpdateStatusEmployeeRequest request, EmployeeEntity employee){

        employee.setPersonEmbeddable(new PersonEmbeddable(employee.getPersonEmbeddable().getFirstName(), employee.getPersonEmbeddable().getLastName(),
                employee.getPersonEmbeddable().getEmail(),employee.getPersonEmbeddable().getPhoneNumber(), employee.getPersonEmbeddable().getBirthdate()));
        employee.setDescription(employee.getDescription());
        employee.setStatus(request.getStatus());

        employeeRepository.save(employee);
    }
}
