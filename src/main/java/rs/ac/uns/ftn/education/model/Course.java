package rs.ac.uns.ftn.education.model;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "courses", uniqueConstraints = {
    @UniqueConstraint(columnNames = {
        "name"
    })
})
@Getter @Setter @NoArgsConstructor
public class Course {
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Semester semester;

    private Integer espbPoints;

    @OneToMany(mappedBy="course")
    private Set<Engagement> engagements;

    @OneToMany(mappedBy="course")
    private Set<Exam> exams;

    @ManyToMany(mappedBy = "courses")
    private Set<StudyProgram> studyPrograms;

    public Long getTotalExamPoints() {
        return exams.stream().mapToLong(exam -> exam.getPoints()).sum();
    }
}
