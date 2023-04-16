package semester3.project.sanomed.business.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import semester3.project.sanomed.business.AccessTokenEncoder;
import semester3.project.sanomed.business.LoginUseCase;
import semester3.project.sanomed.business.exceptions.InvalidCredentialsException;
import semester3.project.sanomed.business.exceptions.InvalidUserException;
import semester3.project.sanomed.domain.AccessToken;
import semester3.project.sanomed.domain.request.LoginRequest;
import semester3.project.sanomed.domain.response.LoginResponse;
import semester3.project.sanomed.persistence.Entity.RoleEnum;
import semester3.project.sanomed.persistence.Entity.UserEntity;
import semester3.project.sanomed.persistence.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoginUseCaseImpl implements LoginUseCase {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccessTokenEncoder accessTokenEncoder;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        UserEntity user = userRepository.findByEmail(loginRequest.getEmail());
        if (user == null) {
            throw new InvalidUserException();
        }

        if (!matchesPassword(loginRequest.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        String accessToken = generateAccessToken(user);
        return LoginResponse.builder().accessToken(accessToken).build();
    }

    private boolean matchesPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    private String generateAccessToken(UserEntity user) {
        Long empId = user.getEmp() != null ? user.getEmp().getId() : null;
        Long patientId = user.getPatient() != null ? user.getPatient().getId() : null;
        List<String> roles = user.getUserRoles().stream()
                .map(userRole -> userRole.getRole().toString())
                .toList();

        if(roles.contains(RoleEnum.PATIENT.toString()))
        {
            return accessTokenEncoder.encode(
                    AccessToken.builder()
                            .subject(user.getEmail())
                            .roles(roles)
                            .patientId(patientId)
                            .build());
        }

        return accessTokenEncoder.encode(
                AccessToken.builder()
                        .subject(user.getEmail())
                        .roles(roles)
                        .employeeId(empId)
                        .build());
    }
}

