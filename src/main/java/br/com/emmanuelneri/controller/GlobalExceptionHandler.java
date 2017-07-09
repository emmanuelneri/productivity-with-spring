package br.com.emmanuelneri.controller;

import br.com.emmanuelneri.exception.BusinessException;
import br.com.emmanuelneri.exception.ExceptionUtil;
import br.com.emmanuelneri.to.ExceptionTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.RollbackException;
import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Set<ExceptionTO>> handleError(BusinessException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singleton(new ExceptionTO(ex.getMessage())));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Set<ExceptionTO>> handleError(ConstraintViolationException ex) {
        final Set<ExceptionTO> erros = ex.getConstraintViolations().stream()
                .map(c -> new ExceptionTO(c.getMessage()))
                .collect(Collectors.toSet());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erros);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Set<ExceptionTO>> handleError(DataIntegrityViolationException divex) {
        return ExceptionUtil.isUniqueConstraintError(divex)
                ? ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singleton(new ExceptionTO("This registry already exists in the database.")))
                : ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singleton(new ExceptionTO(divex.getMessage())));
    }

    @ExceptionHandler(TransactionSystemException.class)
    public ResponseEntity<Set<ExceptionTO>> handleError(TransactionSystemException tse) {
        if (tse.getCause() != null && tse.getCause() instanceof RollbackException) {
            final RollbackException re = (RollbackException) tse.getCause();

            if (re.getCause() != null && re.getCause() instanceof ConstraintViolationException) {
                return handleError((ConstraintViolationException) re.getCause());
            }
        }
        throw tse;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleError(Exception ex) {
        log.error("Internal error server", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("The system is unavailable.");
    }
}
