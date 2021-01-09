package rs.ac.uns.ftn.education.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "exams")
@Getter @Setter @NoArgsConstructor
public class Exam extends BaseModel {

    private Date examDate;

    private String location;

    @OneToOne
    @JoinColumn(name="term_id", nullable=false)
    private Term term;

    @OneToOne
    @JoinColumn(name="course_id", nullable=false)
    private Course course;
}
