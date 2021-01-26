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
import rs.ac.uns.ftn.education.exception.AppException;
import rs.ac.uns.ftn.education.model.audit.DateAudit;

@Entity
@Table(
  name = "grades",
  uniqueConstraints = @UniqueConstraint(columnNames={"exam_id", "student_id", "exam_registration_id"})
)
@Getter @Setter @NoArgsConstructor
public class Grade extends DateAudit {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne
  @JoinColumn(name = "student_id", nullable = false)
  @JsonIgnoreProperties(value={"studyProgram", "roles"})
  private Student student;

  @OneToOne
  @JoinColumn(name = "exam_id", nullable = false)
  private Exam exam;

  @OneToOne
  @JoinColumn(name = "exam_registration_id", nullable = false)
  @JsonIgnoreProperties(value={"course", "student", "exam"})
  private ExamRegistration examRegistration;

  private GradeType gradeType;

  public Integer getGradeValue() {
    switch(gradeType) {
      case FIVE:
        return 5;
      case SIX:
        return 6;
      case SEVEN:
        return 7;
      case EIGHT:
        return 8;
      case NINE:
        return 9;
      case TEN:
        return 10;
      default:
        throw new AppException("No such grade exists!");
    }
  }
}
