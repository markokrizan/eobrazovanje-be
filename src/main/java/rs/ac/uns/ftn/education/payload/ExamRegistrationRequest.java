package rs.ac.uns.ftn.education.payload;

import javax.validation.constraints.NotNull;
import rs.ac.uns.ftn.education.model.Course;
import rs.ac.uns.ftn.education.model.ExamRegistrationStatus;
import rs.ac.uns.ftn.education.model.Student;
import rs.ac.uns.ftn.education.model.Term;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class ExamRegistrationRequest {

  private Long id;

  @NotNull
  private Term term;

  @NotNull
  private Course course;

  @NotNull
  private Student student;

  private ExamRegistrationStatus examRegistrationStatus = ExamRegistrationStatus.REGISTERED;
}
