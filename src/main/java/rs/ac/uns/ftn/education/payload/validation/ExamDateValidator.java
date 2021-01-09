package rs.ac.uns.ftn.education.payload.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import rs.ac.uns.ftn.education.model.Term;
import rs.ac.uns.ftn.education.payload.ExamRequest;
import rs.ac.uns.ftn.education.service.TermService;

public class ExamDateValidator implements ConstraintValidator<ExamDate, ExamRequest> {

  @Autowired
  TermService termService;

  @Override
  public boolean isValid(ExamRequest examRequest, ConstraintValidatorContext context) {
    Term examTerm = termService.getOne(examRequest.getTerm().getId());

    if (
      !examTerm.getFrom().before(examRequest.getExamDate()) || 
      !examTerm.getTo().after(examRequest.getExamDate())) {
      return false;
    }

    return true;
  }
}
