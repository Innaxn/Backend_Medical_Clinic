package semester3.project.sanomed.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import semester3.project.sanomed.business.UpdateUserPasswordUseCase;
import semester3.project.sanomed.configuration.security.isauthenticated.IsAuthenticated;
import semester3.project.sanomed.domain.request.UpdateUserPaswordRequest;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private final UpdateUserPasswordUseCase updateUserPasswordUseCase;

    @IsAuthenticated
    @PutMapping("pwd/{id}")
    public ResponseEntity<Void> updatePassword(@PathVariable("id") long id, @RequestBody @Valid UpdateUserPaswordRequest request){
        request.setId(id);
        updateUserPasswordUseCase.updatePassword(request);
        return ResponseEntity.noContent().build();
    }
}
