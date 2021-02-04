package rs.ac.uns.ftn.education.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.util.Objects;

@Entity
@Table(
  name = "engagements",
  uniqueConstraints = @UniqueConstraint(columnNames={"engagement_type", "course_id", "teacher_id", })
)
@Getter
@Setter
@NoArgsConstructor
public class Engagement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="engagement_type")
    private EngagementType engagementType;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    @JsonIgnoreProperties("engagements")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    @JsonIgnoreProperties("engagements")
    private Teacher teacher;

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Engagement that = (Engagement) other;
        return Objects.equals(id, that.id);
    }
}
