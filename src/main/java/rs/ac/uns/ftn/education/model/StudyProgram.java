package rs.ac.uns.ftn.education.model;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="study_programs")
@Getter @Setter @NoArgsConstructor
public class StudyProgram {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String studyField;

  private String prefix;

  private String name; 
}
