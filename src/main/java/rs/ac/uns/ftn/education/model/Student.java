package rs.ac.uns.ftn.education.model;

import java.util.Set;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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

    private Year currentStudyYear;

    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "study_program_id", nullable=true)
    @JsonIgnoreProperties("courses")
    private StudyProgram studyProgram;

    private FinancialStatus financialStatus;

    public List<Year> getCurrentAndPreviousStudyYears() {
        List<Year> years = Arrays.asList(Year.values());

        return years
            .stream()
            .filter(year -> year.ordinal() <= currentStudyYear.ordinal())
            .collect(Collectors.toList());
    }

    @OneToMany(mappedBy = "student")
    @OrderBy
    @JsonIgnoreProperties("student")
    private Set<Grade> grades;

    public double getAverageGrade() {
        return (double) grades.stream()
            .mapToInt(grade -> grade.getGradeValue())
            .sum() / grades.size();
    }

    @OneToMany(mappedBy="student")
    @OrderBy
    @JsonIgnoreProperties("student")
    private Set<Document> documents;
}
