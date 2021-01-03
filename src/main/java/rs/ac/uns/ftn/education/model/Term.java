package rs.ac.uns.ftn.education.model;

import java.time.MonthDay;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "terms")
@Getter @Setter @NoArgsConstructor
public class Term extends BaseModel {
    private String name;

    @JsonFormat(pattern = "dd/MM")
    private MonthDay from;

    @JsonFormat(pattern = "dd/MM")
    private MonthDay to;
}
