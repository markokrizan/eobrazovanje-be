package rs.ac.uns.ftn.education.payload;

import java.util.Set;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.uns.ftn.education.model.Semester;
import rs.ac.uns.ftn.education.model.Engagement;

@Getter @Setter @NoArgsConstructor
public class CourseRequest {

  private Long id;
  
  @NotBlank
  private String name;

  @NotNull
  private Semester semester;

  @NotNull
  private Integer espbPoints;

  private Set<Engagement> engagements;
}
