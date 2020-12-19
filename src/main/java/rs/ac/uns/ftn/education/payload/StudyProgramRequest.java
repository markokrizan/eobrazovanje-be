package rs.ac.uns.ftn.education.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import rs.ac.uns.ftn.education.model.Course;
import rs.ac.uns.ftn.education.model.StudyProgramType;
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

  @NotNull
  private Integer espbPoints;

  @NotNull
  private StudyProgramType studyProgramType;

  Set<Course> courses; 
}
