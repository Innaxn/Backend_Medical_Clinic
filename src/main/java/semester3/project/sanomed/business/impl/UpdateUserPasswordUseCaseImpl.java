package semester3.project.sanomed.business.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import semester3.project.sanomed.business.UpdateUserPasswordUseCase;
import semester3.project.sanomed.business.exceptions.InvalidUserException;
import semester3.project.sanomed.business.validations.PasswordValidator;
import semester3.project.sanomed.domain.request.UpdateUserPaswordRequest;
import semester3.project.sanomed.persistence.Entity.RoleEnum;
import semester3.project.sanomed.persistence.Entity.UserEntity;
import semester3.project.sanomed.persistence.UserRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UpdateUserPasswordUseCaseImpl implements UpdateUserPasswordUseCase {
    private UserRepository userRepository;
    private PasswordValidator passwordValidator;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void updatePassword(UpdateUserPaswordRequest request) {

        UserEntity user = getUser(request.getId(), request.getRole());

        passwordValidator.comparePasswords(request.getCurrentPassword(),user.getPassword());
        updateObjectFields(request, user);
    }

    public void updateObjectFields(UpdateUserPaswordRequest request, UserEntity user){
        String encodedPassword = passwordEncoder.encode(request.getNewPassword());

        user.setEmail(user.getEmail());
        user.setPassword(encodedPassword);

        userRepository.save(user);
    }

    public UserEntity getUser(long id, RoleEnum role){

        if(role != RoleEnum.PATIENT){
            Optional<UserEntity> userEmp = userRepository.findByEmp_Id(id);
            if(!userEmp.isPresent()){
                throw new InvalidUserException();
            }
            UserEntity user = userEmp.get();
            return user;
        }
        else{
            Optional<UserEntity> userPatient = userRepository.findByPatient_Id(id);
            if(!userPatient.isPresent()){
                throw new InvalidUserException();
            }
            UserEntity user = userPatient.get();
            return user;
        }
    }


}
