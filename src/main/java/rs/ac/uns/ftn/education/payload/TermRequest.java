package rs.ac.uns.ftn.education.payload;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class TermRequest {

  private Long id;

  @NotBlank
  private String name;
  
  @NotNull
  private Date from;

  @NotNull
  private Date to;
}
