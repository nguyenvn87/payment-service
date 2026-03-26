package com.uit.exception;

import com.uit.common.constant.ErrorCode;
import com.uit.common.exceptions.BusinessException;
import com.uit.common.exceptions.HttpStatusResponse;
import com.uit.common.exceptions.PaymentException;
import com.uit.dto.response.ErrorPaymentResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
@Slf4j
public class ExceptionHandlerController {

    private final Logger errorLog = LoggerFactory.getLogger("ELK-ERROR-LOG");

    @ExceptionHandler(PaymentException.class)
    public ResponseEntity<ErrorPaymentResponse> handlePaymentExceptions(PaymentException ex) {


        //TODO den nua se bo error message ra khoi data tra ve . chi tra ve ma loi
        ErrorPaymentResponse errorResponse = new ErrorPaymentResponse(
                ex.getError(),
                ex.getErrorCode(),
                ex.getErrorStatusCode(),
                ex.getErrorMessage()
        );

        return ResponseEntity
                .status(ex.getErrorStatusCode())
                .body(errorResponse);
    }

    @ExceptionHandler({Exception.class, RuntimeException.class})
    @ResponseBody
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    protected ResponseEntity<HttpStatusResponse> handleException(Exception ex) {
        ex.printStackTrace();
        errorLog.error(ex.getMessage(), ex);
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new HttpStatusResponse("500", INTERNAL_SERVER_ERROR.value(), ex.getMessage()));
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    protected ResponseEntity<HttpStatusResponse> handleBusinessException(BusinessException ex) {
        return ResponseEntity.status(BAD_REQUEST).body(new HttpStatusResponse(ex.getErrorCode(), ex.getContent()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.status(BAD_REQUEST).body(new HttpStatusResponse(BAD_REQUEST.toString(), errors));
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(BAD_REQUEST)
    public ResponseEntity<?> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex) {
        String mess = "Trong 1 lần tải không thể tải lên vượt quá dung lượng 100M, vui lòng kiểm tra lại";
        return ResponseEntity.status(BAD_REQUEST).body(new HttpStatusResponse(ErrorCode.INVALID_FILE_SIZE, BAD_REQUEST.value(), mess));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handle(ConstraintViolationException constraintViolationException) {
        Set<ConstraintViolation<?>> violations = constraintViolationException.getConstraintViolations();
        String errorMessage = "";
        if (!violations.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            violations.forEach(violation -> builder.append(" " + violation.getMessage()));
            errorMessage = builder.toString();
        } else {
            errorMessage = "ConstraintViolationException occured.";
        }
        return ResponseEntity.status(BAD_REQUEST).body(new HttpStatusResponse(BAD_REQUEST.toString(), errorMessage));
    }
}
