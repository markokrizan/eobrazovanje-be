package rs.ac.uns.ftn.education.payload.validation;

import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import rs.ac.uns.ftn.education.model.Exam;
import rs.ac.uns.ftn.education.payload.GradeRequest;
import rs.ac.uns.ftn.education.service.ExamService;

public class GradeDateValidator implements ConstraintValidator<GradeDate, GradeRequest> {

  @Autowired
  ExamService examService;

  @Override
  public boolean isValid(GradeRequest gradeRequest, ConstraintValidatorContext context) {
    Exam gradeExam = examService.getOne(gradeRequest.getExam().getId());

    if(!gradeExam.getExamDate().before(new Date())) {
      return false;
    }

    return true;
  }
}
