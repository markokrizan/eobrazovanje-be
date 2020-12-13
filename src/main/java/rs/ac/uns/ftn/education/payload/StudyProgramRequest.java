package rs.ac.uns.ftn.education.payload;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter @Setter @NoArgsConstructor
public class StudyProgramRequest {

  private Long id;
  
  @NotBlank
  private String name;

  @NotBlank
  private String prefix;

  @NotBlank
  private String studyField;
}
