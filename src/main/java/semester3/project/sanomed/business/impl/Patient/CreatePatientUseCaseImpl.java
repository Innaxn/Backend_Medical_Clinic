package semester3.project.sanomed.business.impl.Patient;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import semester3.project.sanomed.business.interfaces.patient.CreatePatientUseCase;
import semester3.project.sanomed.business.validations.BsnValidator;
import semester3.project.sanomed.business.validations.EmailValidator;
import semester3.project.sanomed.business.validations.HealthInsNumValidator;
import semester3.project.sanomed.domain.request.CreatePatientRequest;
import semester3.project.sanomed.domain.response.CreatePatientResponse;
import semester3.project.sanomed.persistence.Entity.*;
import semester3.project.sanomed.persistence.PatientRepository;
import semester3.project.sanomed.persistence.UserRepository;

import javax.transaction.Transactional;
import java.util.Set;

@Service
@AllArgsConstructor
public class CreatePatientUseCaseImpl implements CreatePatientUseCase {
    private final PatientRepository patientRepository;
    //TODO--emailvalidation to be fixed after authentication private  EmailValidator emailValidator;
    private BsnValidator bsnValidator;
    private HealthInsNumValidator healthInsNumValidator;
    private final UserRepository userRepository;
    private EmailValidator emailValidator;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    @Override
    public CreatePatientResponse createPatient(CreatePatientRequest request) {

        //TODO--emailvalidation to be fixed after authentication private emailValidator.validate(request.getEmail());
        emailValidator.validate(request.getEmail());
        bsnValidator.validateBsn(request.getBsn());
        healthInsNumValidator.validateHealthInsNum(request.getHealthInsuranceNumber());

        PatientEntity savedPatient = saveNewPatient(request);

        saveNewUser(savedPatient, request.getPassword(), RoleEnum.PATIENT);

        return CreatePatientResponse.builder()
                .id(savedPatient.getId())
                .build();
    }

    private void saveNewUser(PatientEntity patient, String password, RoleEnum givenRole) {
        String encodedPassword = passwordEncoder.encode(password);

        UserEntity newUser = UserEntity.builder()
                .email(patient.getPersonEmbeddable().getEmail())
                .password(encodedPassword)
                .patient(patient)
                .build();

        newUser.setUserRoles(Set.of(
                UserRoleEntity.builder()
                        .user(newUser)
                        .role(givenRole)
                        .build()));
        userRepository.save(newUser);
    }


    private PatientEntity saveNewPatient(CreatePatientRequest request) {

        PatientEntity newPatient = PatientEntity.builder()
                .personEmbeddable(new PersonEmbeddable(request.getFirstName(), request.getLastName(), request.getEmail(),
                        request.getPhoneNumber(), request.getBirthdate()))
                .bsn(request.getBsn())
                .healthInsuranceNumber(request.getHealthInsuranceNumber())
                .build();

        return patientRepository.save(newPatient);
    }

}
