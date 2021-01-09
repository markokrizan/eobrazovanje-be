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
@Constraint(validatedBy = GradeDateValidator.class)
public @interface GradeDate {
    String message() default "Cannot enter grade - exam hasn't started yet";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
