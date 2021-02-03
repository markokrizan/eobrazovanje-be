package rs.ac.uns.ftn.education.payload;

import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class MailMessageDTO {
  private String from;
  private String to;
  private String subject;
  private String templateName;
  private Map<String, Object> parameters;
}
