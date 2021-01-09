package rs.ac.uns.ftn.education.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import rs.ac.uns.ftn.education.model.Exam;
import rs.ac.uns.ftn.education.payload.ExamRequest;
import rs.ac.uns.ftn.education.security.CurrentUser;
import rs.ac.uns.ftn.education.security.UserPrincipal;
import rs.ac.uns.ftn.education.service.ExamService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class ExamController {

  @Autowired
  private ExamService examService;

  @Autowired
  private ModelMapper modelMapper;
  
  @GetMapping("/exams")
  @PreAuthorize("hasRole('ADMIN')")
  public Page<Exam> getAll(@PageableDefault(size = 10) Pageable pageable) {
    return examService.getAll(pageable);
  }

  @GetMapping("/students/{studentId}/registrable-exams")
  @PreAuthorize("hasRole('ADMIN')" + " || @securityService.isRoleAccessingSelf('ROLE_STUDENT', #studentId, #currentUser)")
  public Page<Exam> getPossibleStudentExamRegistrationExams(
    @PathVariable("studentId") Long studentId,
    @CurrentUser UserPrincipal currentUser, 
    Pageable pageable  
  ) {
    return examService.getPossibleStudentExamRegistrationExams(studentId, pageable);
  }

  @GetMapping("/students/{studentId}/passed-exams")
  @PreAuthorize("hasRole('ADMIN')" + " || @securityService.isRoleAccessingSelf('ROLE_STUDENT', #studentId, #currentUser)")
  public Page<Exam> getPassedExams(
    @PathVariable("studentId") Long studentId,
    @CurrentUser UserPrincipal currentUser,
    Pageable pageable
  ) {
    return examService.getPassedExams(studentId, pageable);
  }

  @GetMapping("/exams/{examId}")
  //TODO: Security: admin, teacher that has an engagement for that course, student that is in a study program that contains this course
  public Exam getOne(@PathVariable("examId") Long examId) {
    return examService.getOne(examId);
  }

  @PostMapping("/exams")
  //TODO: Security: admin, teacher only when the exam is for a course that he has an engagement for
  public Exam save(@Valid @RequestBody ExamRequest examRequest) {
    Exam exam = modelMapper.map(examRequest, Exam.class);

    return examService.save(exam);
  }

  @DeleteMapping("/exams/{examId}")
  @PreAuthorize("hasRole('ADMIN')")
  public void delete(@PathVariable("examId") Long examId) {
    examService.delete(examId);
  }
}
