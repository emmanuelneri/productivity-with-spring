package br.com.emmanuelneri.exception;

import com.google.common.base.Throwables;
import org.springframework.dao.DataIntegrityViolationException;

public final class ExceptionUtil {

    private static final String SQL_VIOLATION_CODE_UNIQUE_CONSTRAINT = "23505";

    private ExceptionUtil() {
    }

    public static boolean isUniqueConstraintError(DataIntegrityViolationException divex) {
        return Throwables.getCausalChain(divex).stream().anyMatch(ex -> {
            if (ex instanceof org.hibernate.exception.ConstraintViolationException) {
                org.hibernate.exception.ConstraintViolationException cvex = (org.hibernate.exception.ConstraintViolationException) ex;
                if (cvex.getSQLState().equals(SQL_VIOLATION_CODE_UNIQUE_CONSTRAINT)) {
                    return true;
                }
            }
            return false;
        });
    }
}
