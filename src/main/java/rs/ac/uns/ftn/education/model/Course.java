package rs.ac.uns.ftn.education.model;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="courses")
@Getter @Setter @NoArgsConstructor @SuppressWarnings("unused")
public class Course {
    
    @Id
    private Long id;

    private String name;

    @OneToMany(mappedBy="course")
    private List<Engagement> engagements;

    @OneToMany(mappedBy="course")
    private List<Exam> exams;

    public Long getTotalExamPoints() {
        return exams.stream().mapToLong(exam -> exam.getPoints()).sum();
    }
}
