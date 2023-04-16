package semester3.project.sanomed.business.converter;

import semester3.project.sanomed.domain.Person;
import semester3.project.sanomed.persistence.Entity.PersonEmbeddable;

public class PersonConverter {
    private PersonConverter() {
    }

    public static Person convert(PersonEmbeddable person) {
        return Person.builder()
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .email(person.getEmail())
                .phoneNumber(person.getPhoneNumber())
                .birthdate(person.getBirthdate())
                .build();
    }
}
