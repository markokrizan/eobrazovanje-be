package rs.ac.uns.ftn.education.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "teachers")
@Getter @Setter @NoArgsConstructor
public class Teacher extends User {

    private String academicTitle;

    @OneToMany(mappedBy="teacher")
    @OrderBy
    private Set<Engagement> engagements;
}
