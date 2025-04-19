package com.example.reservation.api.error;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.reservation.domain.error.Error;
import com.example.reservation.domain.error.ErrorEnum;
import jakarta.persistence.EntityNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@ControllerAdvice
public class ErrorHandler {

  private static final Map<String, String> CONSTRAINS_MAP = Map.of();
  private static final Map<String, Integer> CONSTRAINS_DEVELOPER_CODE_MAP = Map.of();

  private final MessageSource messageSource;

  public ErrorHandler(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  @ExceptionHandler(value = {Exception.class})
  public ResponseEntity<ErrorResponse> handleException(Exception exception) {
    String message = getMessage(ErrorEnum.GENERAL_INTERNAL_SERVER_ERROR.getMessageKey());
    HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    int developerCode = HttpStatus.INTERNAL_SERVER_ERROR.value();

    if (exception instanceof Error error) {
      String errorMessage = exception.getMessage();
      String messageKey = error.getErrorMessageKey();
      developerCode = error.getDeveloperCode();

      if (messageKey != null) {
        message = getMessage(messageKey);
      }

      if (errorMessage != null) {
        message = errorMessage;
      }

      if (error.getHttpStatus() != null) {
        httpStatus = ((Error) exception).getHttpStatus();
      }
    } else {
      log.error("Exception: ", exception);
    }

    ErrorResponse body = new ErrorResponse(message, httpStatus.value(), developerCode);

    return new ResponseEntity<>(body, httpStatus);
  }

  @ExceptionHandler(value = {
      IllegalArgumentException.class,
      IllegalStateException.class,
      UnsupportedEncodingException.class
  })
  public ResponseEntity<Object> handleIllegalArgumentException(Exception exception) {
    String message = exception.getMessage();
    HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
    int developerCode = HttpStatus.BAD_REQUEST.value();

    ErrorResponse body = new ErrorResponse(message, httpStatus.value(), developerCode);

    return new ResponseEntity<>(body, httpStatus);
  }

  @ExceptionHandler(value = {DataAccessException.class})
  public ResponseEntity<Object> handleDataAccessException(DataAccessException exception) {
    log.info("DataAccessException with error message: {}", exception.getMessage());

    String message = getMessage(ErrorEnum.GENERAL_INTERNAL_SERVER_ERROR.getMessageKey());
    HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    int developerCode = HttpStatus.INTERNAL_SERVER_ERROR.value();

    ErrorResponse body = new ErrorResponse(message, httpStatus.value(), developerCode);

    return new ResponseEntity<>(body, httpStatus);
  }

  @ExceptionHandler(value = {EntityNotFoundException.class})
  public ResponseEntity<Object> handleEntityNotFoundException(Exception exception) {
    String message = exception.getMessage();
    HttpStatus httpStatus = HttpStatus.NOT_FOUND;
    int developerCode = HttpStatus.NOT_FOUND.value();

    ErrorResponse body = new ErrorResponse(message, httpStatus.value(), developerCode);

    return new ResponseEntity<>(body, httpStatus);
  }

  @ExceptionHandler(value = {IOException.class})
  public ResponseEntity<Object> handleIOException(Exception exception) {
    String message = exception.getMessage();
    HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    int developerCode = HttpStatus.INTERNAL_SERVER_ERROR.value();

    ErrorResponse body = new ErrorResponse(message, httpStatus.value(), developerCode);

    return new ResponseEntity<>(body, httpStatus);
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Object> handleValidationExceptions(
      MethodArgumentNotValidException exception
  ) {
    Map<String, String> errorMap = new HashMap<>();
    exception.getBindingResult().getAllErrors().forEach(error -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errorMap.put(fieldName, errorMessage);
    });

    String json = null;
    try {
      json = new ObjectMapper().writeValueAsString(errorMap);
    } catch (JsonProcessingException e) {
      log.error("Exception", e);
    }

    HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
    int developerCode = HttpStatus.BAD_REQUEST.value();

    ErrorResponse body = new ErrorResponse(json, httpStatus.value(), developerCode);

    return new ResponseEntity<>(body, httpStatus);
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<Object> handleMissingParameterExceptions(
      MissingServletRequestParameterException exception
  ) {
    String message = exception.getMessage();
    HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
    int developerCode = HttpStatus.BAD_REQUEST.value();

    ErrorResponse body = new ErrorResponse(message, httpStatus.value(), developerCode);

    return new ResponseEntity<>(body, httpStatus);
  }

  @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<Object> handleMethodNotAllowedExceptions(
      HttpRequestMethodNotSupportedException exception
  ) {
    String message = exception.getMessage();
    HttpStatus httpStatus = HttpStatus.METHOD_NOT_ALLOWED;
    int developerCode = HttpStatus.METHOD_NOT_ALLOWED.value();

    ErrorResponse body = new ErrorResponse(message, httpStatus.value(), developerCode);

    return new ResponseEntity<>(body, httpStatus);
  }

  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<Object> handleBadCredentialsException(
      BadCredentialsException exception
  ) {
    String message = getMessage(ErrorEnum.UNAUTHORIZED.getMessageKey());
    HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
    int developerCode = HttpStatus.UNAUTHORIZED.value();

    ErrorResponse body = new ErrorResponse(message, httpStatus.value(), developerCode);

    return new ResponseEntity<>(body, httpStatus);
  }

  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<Object> handleAuthenticationException(
      AuthenticationException exception
  ) {
    String message = getMessage(ErrorEnum.UNAUTHORIZED.getMessageKey());
    HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
    int developerCode = HttpStatus.UNAUTHORIZED.value();

    ErrorResponse body = new ErrorResponse(message, httpStatus.value(), developerCode);

    return new ResponseEntity<>(body, httpStatus);
  }

  @ResponseStatus(HttpStatus.FORBIDDEN)
  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<Object> handleAccessDeniedException(
      AccessDeniedException exception
  ) {
    String message = getMessage(ErrorEnum.GENERAL_ACCESS_DENIED.getMessageKey());
    HttpStatus httpStatus = HttpStatus.FORBIDDEN;
    int developerCode = HttpStatus.FORBIDDEN.value();

    ErrorResponse body = new ErrorResponse(message, httpStatus.value(), developerCode);

    return new ResponseEntity<>(body, httpStatus);
  }

  @ExceptionHandler(BindException.class)
  public ResponseEntity<Object> handleBindExceptions(BindException exception) {
    Map<String, String> errorMap = new HashMap<>();
    exception.getBindingResult().getAllErrors().forEach(error -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errorMap.put(fieldName, errorMessage);
    });

    String json = null;
    try {
      json = new ObjectMapper().writeValueAsString(errorMap);
    } catch (JsonProcessingException e) {
      log.error("Exception", e);
    }

    HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
    int developerCode = HttpStatus.BAD_REQUEST.value();

    ErrorResponse body = new ErrorResponse(json, httpStatus.value(), developerCode);

    return new ResponseEntity<>(body, httpStatus);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<Object> handleDataIntegrity(DataIntegrityViolationException exception) {
    String errorMessage = exception.getMessage();
    log.info("DataIntegrityViolationException with error message: {}", errorMessage);

    if (errorMessage != null && errorMessage.contains("Duplicate")) {
      String message = getMessage("error_general_duplication");
      HttpStatus httpStatus = HttpStatus.CONFLICT;
      int developerCode = 40901;

      if (exception.getMessage() != null) {
        String exceptionMessage = exception.getMessage().toLowerCase();
        for (Map.Entry<String, String> entry : CONSTRAINS_MAP.entrySet()) {
          if (exceptionMessage.contains(entry.getKey())) {
            message = getMessage(entry.getValue());
            developerCode = CONSTRAINS_DEVELOPER_CODE_MAP.get(entry.getKey());
          }
        }
      }

      ErrorResponse body = new ErrorResponse(message, httpStatus.value(), developerCode);

      return new ResponseEntity<>(body, httpStatus);
    } else {
      String message = getMessage(ErrorEnum.GENERAL_BAD_REQUEST.getMessageKey());
      HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
      int developerCode = ErrorEnum.GENERAL_BAD_REQUEST.getCode();

      ErrorResponse body = new ErrorResponse(message, httpStatus.value(), developerCode);

      return new ResponseEntity<>(body, httpStatus);
    }
  }

  @ExceptionHandler(NoResourceFoundException.class)
  public ResponseEntity<Object> handleDataIntegrity(NoResourceFoundException exception) {
    String message = getMessage(ErrorEnum.GENERAL_NOT_FOUND.getMessageKey());
    HttpStatus httpStatus = HttpStatus.NOT_FOUND;
    int developerCode = ErrorEnum.GENERAL_NOT_FOUND.getCode();

    ErrorResponse body = new ErrorResponse(message, httpStatus.value(), developerCode);

    return new ResponseEntity<>(body, httpStatus);
  }

  private String getMessage(String code) {
    return messageSource.getMessage(code, new String[]{}, LocaleContextHolder.getLocale());
  }

}
