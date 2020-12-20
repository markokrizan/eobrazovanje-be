package rs.ac.uns.ftn.education.payload;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import rs.ac.uns.ftn.education.model.Course;
import rs.ac.uns.ftn.education.model.EngagementType;
import rs.ac.uns.ftn.education.model.Teacher;
import lombok.NoArgsConstructor;

@Getter @Setter @NoArgsConstructor
public class EngagementRequest {

  private Long id;

  @NotNull
  private EngagementType engagementType;

  @NotNull
  private Course course;

  @NotNull
  private Teacher teacher;
}
