package semester3.project.sanomed.business.impl.Patient;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import semester3.project.sanomed.business.exceptions.InvalidPatientException;
import semester3.project.sanomed.business.exceptions.InvalidUserException;
import semester3.project.sanomed.business.exceptions.UnauthorizedDataAccessException;
import semester3.project.sanomed.business.interfaces.patient.UpdatePatientUseCase;
import semester3.project.sanomed.business.validations.EmailValidator;
import semester3.project.sanomed.domain.AccessToken;
import semester3.project.sanomed.persistence.Entity.PersonEmbeddable;
import semester3.project.sanomed.domain.request.UpdateBasicPatientRequest;
import semester3.project.sanomed.persistence.Entity.PatientEntity;
import semester3.project.sanomed.persistence.Entity.RoleEnum;
import semester3.project.sanomed.persistence.Entity.UserEntity;
import semester3.project.sanomed.persistence.PatientRepository;
import semester3.project.sanomed.persistence.UserRepository;

import javax.transaction.Transactional;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UpdatePatientUseCaseImpl implements UpdatePatientUseCase {

    private final PatientRepository patientRepository;
    private final UserRepository userRepository;
    private EmailValidator emailValidator;
    private AccessToken requestAccessToken;

    @Transactional
    @Override
    public void updatePatient(UpdateBasicPatientRequest request) {

        if(requestAccessToken.hasRole(RoleEnum.PATIENT.name())){
            if(!Objects.equals(requestAccessToken.getPatientId(), request.getId())){
                throw new UnauthorizedDataAccessException();
            }
        }

        Optional<PatientEntity> optionalPatientEntity = patientRepository.findById(request.getId());
        Optional<UserEntity> optionalUserEntity = userRepository.findByPatient_Id(request.getId());

        if(optionalPatientEntity.isEmpty()){
            throw new InvalidPatientException();
        }
        if(optionalUserEntity.isEmpty()){
            throw new InvalidUserException();
        }

        UserEntity user = optionalUserEntity.get();
        PatientEntity patient = optionalPatientEntity.get();

        String email = request.getEmail();
        String emailTwo = patient.getPersonEmbeddable().getEmail();
        if(!email.equals(emailTwo)){
            emailValidator.validate(request.getEmail());
        }

        updateObjectFieldsPatient(request, patient);
        updateObjectFieldsUser(request, user);
    }

    private void updateObjectFieldsUser(UpdateBasicPatientRequest request, UserEntity user) {

        user.setEmail(request.getEmail());

        userRepository.save(user);
    }

    public void updateObjectFieldsPatient(UpdateBasicPatientRequest request, PatientEntity patient){

        patient.setPersonEmbeddable(new PersonEmbeddable(request.getFirstName(), request.getLastName(),
                request.getEmail(), request.getPhone(), patient.getPersonEmbeddable().getBirthdate()));
        patient.setHealthInsuranceNumber(request.getHealthInsNum());

        patientRepository.save(patient);
    }


}
