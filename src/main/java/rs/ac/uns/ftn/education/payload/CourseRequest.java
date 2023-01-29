package rs.ac.uns.ftn.education.payload;

import java.util.Set;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.uns.ftn.education.model.Semester;
import rs.ac.uns.ftn.education.model.Year;
import rs.ac.uns.ftn.education.model.Engagement;
import rs.ac.uns.ftn.education.model.StudyProgram;

@Getter @Setter @NoArgsConstructor
public class CourseRequest {

  private Long id;
  
  @NotBlank
  private String name;

  @NotNull
  private Semester semester;

  @NotNull
  private Year year;

  @NotNull
  private Integer espbPoints;

  private Set<Engagement> engagements;

  private Set<StudyProgram> studyPrograms;
}
