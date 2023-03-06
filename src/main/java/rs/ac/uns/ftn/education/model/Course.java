package rs.ac.uns.ftn.education.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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

    private Year year;

    private Semester semester;

    private Integer espbPoints;

    @OneToMany(mappedBy="course", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Engagement> engagements;

    @JsonIgnoreProperties("courses")
    @ManyToMany(mappedBy = "courses", cascade = CascadeType.ALL)
    private Set<StudyProgram> studyPrograms;

    public boolean isInStudyProgram(StudyProgram studyProgram) {
        return studyPrograms.contains(studyProgram);
    }
}
