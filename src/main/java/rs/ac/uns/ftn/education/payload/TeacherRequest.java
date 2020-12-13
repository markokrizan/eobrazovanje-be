package rs.ac.uns.ftn.education.payload;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class TeacherRequest extends UserRequest {


  @NotBlank
  private String academicTitle;
}
