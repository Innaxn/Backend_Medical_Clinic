package semester3.project.sanomed.business;

import semester3.project.sanomed.domain.request.UpdateUserPaswordRequest;

public interface UpdateUserPasswordUseCase {
    void updatePassword(UpdateUserPaswordRequest request);
}
