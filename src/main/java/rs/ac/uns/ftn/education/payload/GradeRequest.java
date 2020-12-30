package rs.ac.uns.ftn.education.payload;

import javax.validation.constraints.NotNull;

import rs.ac.uns.ftn.education.model.Course;
import rs.ac.uns.ftn.education.model.ExamRegistration;
import rs.ac.uns.ftn.education.model.GradeType;
import rs.ac.uns.ftn.education.model.Student;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class GradeRequest {
  private Long id;

  @NotNull
  private Course course;

  @NotNull
  private Student student;

  @NotNull
  private ExamRegistration examRegistration;

  @NotNull
  private GradeType gradeType;
}
