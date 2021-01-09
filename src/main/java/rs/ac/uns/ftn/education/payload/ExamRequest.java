package rs.ac.uns.ftn.education.payload;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.uns.ftn.education.model.Term;
import rs.ac.uns.ftn.education.model.Course;

@Getter @Setter @NoArgsConstructor
public class ExamRequest {
  @NotNull
  private Date examDate;

  @NotBlank
  private String location;

  @NotNull
  private Term term;

  @NotNull
  private Course course;
}
