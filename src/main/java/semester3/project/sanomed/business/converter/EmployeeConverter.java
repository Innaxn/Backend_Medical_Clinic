package semester3.project.sanomed.business.converter;

import semester3.project.sanomed.domain.Employee;
import semester3.project.sanomed.persistence.Entity.EmployeeEntity;

public class EmployeeConverter {

    private EmployeeConverter() {
    }

    public static Employee convert(EmployeeEntity employee) {
       return Employee.builder()
                .id(employee.getId())
                .person(PersonConverter.convert(employee.getPersonEmbeddable()))
                .description(employee.getDescription())
                .status(employee.getStatus())
                .build();
    }
}
