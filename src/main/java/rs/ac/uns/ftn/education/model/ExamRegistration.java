package rs.ac.uns.ftn.education.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.uns.ftn.education.model.audit.DateAudit;

@Entity
@Table(
  name = "exam_registrations",
  uniqueConstraints = @UniqueConstraint(columnNames={"course_id", "student_id", "term"})
)
@Getter @Setter @NoArgsConstructor
public class ExamRegistration extends DateAudit {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne
  @JoinColumn(name = "course_id", nullable = false)
  @JsonIgnoreProperties("studyPrograms")
  private Course course;

  @OneToOne
  @JoinColumn(name = "student_id", nullable = false)
  @JsonIgnoreProperties(value={"studyProgram", "roles"})
  private Student student;

  private TermType term;

  private ExamRegistrationStatus examRegistrationStatus = ExamRegistrationStatus.REGISTERED;
}
