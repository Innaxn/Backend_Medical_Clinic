package semester3.project.sanomed.business;

import semester3.project.sanomed.domain.request.LoginRequest;
import semester3.project.sanomed.domain.response.LoginResponse;

public interface LoginUseCase {
    LoginResponse login(LoginRequest loginRequest);
}
