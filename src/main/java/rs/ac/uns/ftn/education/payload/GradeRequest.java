package rs.ac.uns.ftn.education.payload;

import javax.validation.constraints.NotNull;

import rs.ac.uns.ftn.education.model.Exam;
import rs.ac.uns.ftn.education.model.ExamRegistration;
import rs.ac.uns.ftn.education.model.GradeType;
import rs.ac.uns.ftn.education.model.Student;
import rs.ac.uns.ftn.education.payload.validation.GradeDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @GradeDate
public class GradeRequest {
  private Long id;

  @NotNull
  private Student student;

  @NotNull
  private Exam exam;

  @NotNull
  private ExamRegistration examRegistration;

  @NotNull
  private GradeType gradeType;
}
