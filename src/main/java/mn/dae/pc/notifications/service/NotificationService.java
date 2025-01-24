package mn.dae.pc.notifications.service;

import mn.dae.pc.notifications.entity.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.spullara.mustache.java.compiler;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    public void sendEmail(Email email) {

        // Invoke email service
        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache m = mf.compile(email.);

    }
}
