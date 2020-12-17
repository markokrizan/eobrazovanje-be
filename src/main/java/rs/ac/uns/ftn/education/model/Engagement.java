package rs.ac.uns.ftn.education.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="engagements")
@Getter @Setter @NoArgsConstructor
public class Engagement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private EngagementType engagementType;

    @ManyToOne
    @JoinColumn(name="course_id", nullable=false)
    private Course course;

    @ManyToOne
    @JoinColumn(name="teacher_id", nullable=false)
    private Teacher teacher;
}
