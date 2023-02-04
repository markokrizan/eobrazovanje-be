package rs.ac.uns.ftn.education.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import rs.ac.uns.ftn.education.model.Exam;

public interface ExamRepository extends BaseRepository<Exam, Long> {
  @Query(
    value = 
      "SELECT * FROM exams " +
      "JOIN courses ON exams.course_id = courses.id " +
      "JOIN study_program_course ON study_program_course.course_id = courses.id " +
      "JOIN study_programs ON study_program_course.study_program_id = study_programs.id " +
      "LEFT OUTER JOIN grades ON exams.id = grades.exam_id " +
      "LEFT OUTER JOIN exam_registrations ON exams.id = exam_registrations.exam_id " +
      "WHERE " + 
        "study_program_course.study_program_id = :studyProgramId AND " +
        "courses.year IN :studyYears AND " +
        "exams.term_id = :currentTermId AND " +
        "(exam_registrations.student_id <> :studentId OR exam_registrations.student_id IS NULL) AND " +
        "(grades.student_id IS NULL OR (grades.student_id = :studentId AND grades.grade_type = 0))"
      ,
      nativeQuery = true
  )
  Page<Exam> getPossibleStudentExamRegistrationExams(
    @Param("studentId") Long studentId, 
    @Param("studyProgramId") Long studyProgramId,
    @Param("studyYears") List<Integer> studyYears,
    @Param("currentTermId") Long currentTermId,
    Pageable pageable
  );

  @Query(
    value = 
      "SELECT * " + 
      "FROM exams " + 
      "JOIN courses ON exams.course_id = courses.id " +
      "JOIN study_program_course ON study_program_course.course_id = courses.id " +
      "JOIN study_programs ON study_program_course.study_program_id = study_programs.id " +
      "JOIN grades ON grades.student_id = :studentId AND grades.exam_id = exams.id " +
      "WHERE " + 
        "study_program_course.study_program_id = :studyProgramId AND " +
        "grades.grade_type > 0"
    , 
    nativeQuery = true
  )
  Page<Exam> getPassedExams(
    @Param("studentId") Long studentId, 
    @Param("studyProgramId") Long studyProgramId,
    Pageable pageable
  );

  Page<Exam> findByCourse_Engagements_Teacher_Id(Long teacherId, Pageable pageable);
}
