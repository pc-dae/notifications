package mn.dae.pc.notifications.controller;

import mn.dae.pc.notifications.entity.Email;
import mn.dae.pc.notifications.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
public class NotificationController {

    private static final Logger log = LoggerFactory.getLogger(NotificationController.class);

    @Autowired
    private NotificationService notificationService;

    @ResponseStatus(HttpStatus.CREATED) // 201
    @PostMapping
    public void sendEmail(@RequestBody Email email) {

        notificationService.sendEmail(email);
    }
}
