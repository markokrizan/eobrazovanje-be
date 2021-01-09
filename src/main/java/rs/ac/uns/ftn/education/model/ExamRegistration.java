package rs.ac.uns.ftn.education.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
  name = "exam_registrations",
  uniqueConstraints = @UniqueConstraint(columnNames={"exam_id", "student_id"})
)
@Getter @Setter @NoArgsConstructor
public class ExamRegistration extends BaseModel {

  @OneToOne
  @JoinColumn(name = "exam_id", nullable = false)
  private Exam exam;

  @OneToOne
  @JoinColumn(name = "student_id", nullable = false)
  @JsonIgnoreProperties(value={"roles", "studyProgram"})
  private Student student;

  private ExamRegistrationStatus examRegistrationStatus = ExamRegistrationStatus.REGISTERED;
}
