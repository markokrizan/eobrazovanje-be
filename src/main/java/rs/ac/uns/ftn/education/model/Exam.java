package rs.ac.uns.ftn.education.model;

import java.util.Date;

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
  name = "exams",
  uniqueConstraints = @UniqueConstraint(columnNames={"term_id", "course_id"})
)
@Getter @Setter @NoArgsConstructor
public class Exam extends BaseModel {

    private Date examDate;

    private String location;

    @OneToOne
    @JoinColumn(name="term_id", nullable=false)
    private Term term;

    @OneToOne
    @JoinColumn(name="course_id", nullable=false)
    @JsonIgnoreProperties(value={"studyPrograms", "engagements"})
    private Course course;    
}
