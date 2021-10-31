package com.backend.alkemy.service;

import com.backend.alkemy.config.SendgridConfig;
import com.backend.alkemy.entity.Usuario;
import com.sendgrid.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.logging.Logger;

@Service
public class SendGridService {
    @Autowired
    SendGrid sendGrid;

    @Value("${app.sendgrid.key}")
    private String appKey;
   // private static final Logger logger = (Logger) LoggerFactory.getLogger(SendGridService.class);

    public String sendTextEmail(Usuario usuario) throws IOException {
        // the sender email should be the same as we used to Create a Single Sender Verification
        Usuario user = usuario;
        Email from = new Email("alkemy@example");
        String subject = "Welcome";
        Email to = new Email(user.getEmail());
        Content content = new Content("text/plain", "This is a test email");
        Mail mail = new Mail(from, subject, to, content);
        SendGrid sg = new SendGrid(appKey);

        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
          //  logger.info(response.getBody());
            return response.getBody();
        } catch (IOException ex) {
            throw ex;
        }
    }
}
