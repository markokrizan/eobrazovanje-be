package rs.ac.uns.ftn.education.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "students", uniqueConstraints = {
    @UniqueConstraint(columnNames = {
        "schoolIdNumber"
    })
})
@Getter @Setter @NoArgsConstructor
public class Student extends User {

    private String schoolIdNumber;

}
