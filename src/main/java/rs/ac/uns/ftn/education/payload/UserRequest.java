package rs.ac.uns.ftn.education.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class UserRequest {

  private Long id;
  
  @NotBlank
  @Size(max = 40)
  private String firstName;

  @NotBlank
  @Size(max = 40)
  private String lastName;

  @NotBlank
  @Size(min = 3, max = 15)
  private String username;

  @NotBlank
  @Email
  private String email;

  @NotBlank
  private String phoneNumber;

  @NotBlank
  private String personalIdNumber;

  private String role;
}
