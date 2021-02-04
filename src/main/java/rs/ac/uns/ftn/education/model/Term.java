package rs.ac.uns.ftn.education.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "terms", uniqueConstraints = {
    @UniqueConstraint(columnNames = {
        "name"
    })
})
@Getter @Setter @NoArgsConstructor
public class Term extends BaseModel {
    private String name;

    private Date from;

    private Date to;
}
