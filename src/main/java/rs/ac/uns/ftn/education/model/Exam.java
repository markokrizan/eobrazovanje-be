package rs.ac.uns.ftn.education.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.uns.ftn.education.exception.AppException;
import lombok.AccessLevel;

@Entity
@Table(name = "exams")
@Getter @Setter @NoArgsConstructor @SuppressWarnings("unused")
public class Exam {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private ExamType examType;

    @Setter(AccessLevel.NONE) private Long points;

    @ManyToOne
    @JoinColumn(name="course_id", nullable=false)
    private Course course;

    public void setPoints(Long points) {
        Long currentPoints = course.getTotalExamPoints();

        if(points >= currentPoints) {
            throw new AppException("Current exam total: " + currentPoints.toString() + " points");
        }

        this.points = points;
    }
}
