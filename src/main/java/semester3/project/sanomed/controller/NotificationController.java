package semester3.project.sanomed.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import semester3.project.sanomed.Generated;
import semester3.project.sanomed.domain.NotificationMessage;

@RestController
@AllArgsConstructor
@RequestMapping("notifications")
@Generated
public class NotificationController {
    private final SimpMessagingTemplate messagingTemplate;

    @PostMapping
    public ResponseEntity<Void> sendNotificationToUser(@RequestBody NotificationMessage message) {
        messagingTemplate.convertAndSend("/topic/private", message);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
