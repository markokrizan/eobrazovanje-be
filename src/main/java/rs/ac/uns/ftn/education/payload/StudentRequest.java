package rs.ac.uns.ftn.education.payload;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.uns.ftn.education.model.StudyProgram;
import rs.ac.uns.ftn.education.model.Year;

@Getter @Setter @NoArgsConstructor
public class StudentRequest extends UserRequest {

  @NotNull
  private StudyProgram studyProgram;

  @NotNull
  private Year currentStudyYear;
}
