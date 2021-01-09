package rs.ac.uns.ftn.education.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import rs.ac.uns.ftn.education.model.Exam;

public interface ExamRepository extends BaseRepository<Exam, Long> {
  @Query(
    value = 
      "SELECT * " + 
      "FROM exams " + 
      "JOIN courses ON exams.course_id = courses.id " +
      "JOIN study_program_course ON study_program_course.course_id = courses.id " +
      "JOIN study_programs ON study_program_course.study_program_id = study_programs.id " +
      "LEFT OUTER JOIN grades ON grades.student_id = :studentId " +
      "WHERE " + 
        "study_program_course.study_program_id = :studyProgramId AND " +
        "courses.year IN :studyYears AND " +
        "exams.term_id = :currentTermId AND " +
        "(grades.grade_type IS NULL OR grades.grade_type = 0)"
    , 
    nativeQuery = true
  )
  List<Exam> getPossibleStudentExamRegistrationExams(
    @Param("studentId") Long studentId, 
    @Param("studyProgramId") Long studyProgramId,
    @Param("studyYears") List<Integer> studyYears,
    @Param("currentTermId") Long currentTermId
  );

  @Query(
    value = 
      "SELECT * " + 
      "FROM exams " + 
      "JOIN courses ON exams.course_id = courses.id " +
      "JOIN study_program_course ON study_program_course.course_id = courses.id " +
      "JOIN study_programs ON study_program_course.study_program_id = study_programs.id " +
      "JOIN grades ON grades.student_id = :studentId " +
      "WHERE " + 
        "study_program_course.study_program_id = :studyProgramId AND " +
        "grades.grade_type > 0"
    , 
    nativeQuery = true
  )
  List<Exam> getPassedExams(
    @Param("studentId") Long studentId, 
    @Param("studyProgramId") Long studyProgramId
  );
}
