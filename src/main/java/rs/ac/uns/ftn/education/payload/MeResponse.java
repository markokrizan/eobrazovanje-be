package rs.ac.uns.ftn.education.payload;

import rs.ac.uns.ftn.education.model.Student;
import rs.ac.uns.ftn.education.model.Teacher;
import rs.ac.uns.ftn.education.model.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class MeResponse {
  private User user;
  private Student student;
  private Teacher teacher;
}
