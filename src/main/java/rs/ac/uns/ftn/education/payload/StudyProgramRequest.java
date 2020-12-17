package rs.ac.uns.ftn.education.payload;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;
import rs.ac.uns.ftn.education.model.Course;
import lombok.NoArgsConstructor;
import java.util.Set;

@Getter @Setter @NoArgsConstructor
public class StudyProgramRequest {

  private Long id;
  
  @NotBlank
  private String name;

  @NotBlank
  private String prefix;

  @NotBlank
  private String studyField;

  Set<Course> courses; 
}
