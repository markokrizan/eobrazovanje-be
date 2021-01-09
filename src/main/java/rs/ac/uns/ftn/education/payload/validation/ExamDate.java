package rs.ac.uns.ftn.education.payload.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ExamDateValidator.class)
public @interface ExamDate {
    String message() default "Exam date must be between selected term's from and to date";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
