package rs.ac.uns.ftn.education.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import rs.ac.uns.ftn.education.payload.MailMessageDTO;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailService {

  @Autowired
  JavaMailSender mailSender;

  @Autowired
  private TemplateEngine templateEngine;

  public void sendEmail(MailMessageDTO mailMessageDTO) throws MessagingException {
      MimeMessage message = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, true);
      
      helper.setTo(mailMessageDTO.getTo());
      helper.setFrom(mailMessageDTO.getFrom());
      helper.setSubject(mailMessageDTO.getSubject());

      Context context = new Context();
      context.setVariables(mailMessageDTO.getParameters());

      String html = templateEngine.process(mailMessageDTO.getTemplateName(), context);

      helper.setText(html, true);

      mailSender.send(message);
  }
}
