package rs.ac.uns.ftn.education.payload;

import java.time.MonthDay;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class TermRequest {
  @NotBlank
  private String name;
  
  @NotNull
  @JsonFormat(pattern = "dd/MM")
  private MonthDay from;

  @NotNull
  @JsonFormat(pattern = "dd/MM")
  private MonthDay to;
}
