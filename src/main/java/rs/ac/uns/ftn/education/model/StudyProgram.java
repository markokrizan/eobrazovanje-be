package rs.ac.uns.ftn.education.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "study_programs", uniqueConstraints = @UniqueConstraint(columnNames = { "name" }))
@Getter
@Setter
@NoArgsConstructor
public class StudyProgram {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String studyField;

  private String prefix;

  private String name;

  private Integer espbPoints;

  private StudyProgramType studyProgramType;

  @JsonIgnoreProperties("studyPrograms")
  @ManyToMany
  @JoinTable(name = "study_program_course", joinColumns = @JoinColumn(name = "study_program_id"), inverseJoinColumns = @JoinColumn(name = "course_id"))
  private Set<Course> courses = new HashSet<>();

  @Override
  public boolean equals(Object other) {
    if (this == other)
      return true;
    if (other == null || getClass() != other.getClass())
      return false;
    StudyProgram that = (StudyProgram) other;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }
}
