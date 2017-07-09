package br.com.emmanuelneri.test.data;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import java.util.Set;

public final class ConstraintViolationMock {

   private ConstraintViolationMock() {
   }

   public static ConstraintViolationException getFakeConstraintViolationException() {
       final Bill bill = new Bill();

       final Set<ConstraintViolation<Bill>> constraintViolations =
               Validation.buildDefaultValidatorFactory().getValidator().validate(bill);

       return new ConstraintViolationException(constraintViolations);
   }

   static class Bill {

       @NotEmpty(message = "The identifier is required")
       private String identifier;

       public String getIdentifier() {
           return identifier;
       }

       public void setIdentifier(String identifier) {
           this.identifier = identifier;
       }
   }

}
