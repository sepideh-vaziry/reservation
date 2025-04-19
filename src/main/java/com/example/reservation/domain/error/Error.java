package com.example.reservation.domain.error;

import org.springframework.http.HttpStatus;

public abstract class Error extends RuntimeException {

  private final ErrorEnum errorEnum;
  private final HttpStatus httpStatus;

  protected Error(ErrorEnum errorsEnum) {
    this.errorEnum = errorsEnum;
    this.httpStatus = HttpStatus.valueOf(errorsEnum.getStatus());
  }

  protected Error(ErrorEnum errorEnum, HttpStatus httpStatus) {
    this.errorEnum = errorEnum;
    this.httpStatus = httpStatus;
  }

  protected Error(String message, ErrorEnum errorEnum, HttpStatus httpStatus) {
    super(message);
    this.errorEnum = errorEnum;
    this.httpStatus = httpStatus;
  }

  public String getErrorMessageKey() {
    if (errorEnum != null) {
      return errorEnum.getMessageKey();
    }

    return null;
  }

  public int getDeveloperCode() {
    if (errorEnum != null) {
      return errorEnum.getCode();
    }

    else return 0;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }

  public static class NotImplementedException extends Error {

    public NotImplementedException(ErrorEnum errorEnum) {
      super(errorEnum, HttpStatus.NOT_IMPLEMENTED);
    }
  }

  public static class RequiredFieldException extends Error {

    public RequiredFieldException() {
      super(ErrorEnum.GENERAL_BAD_REQUEST, HttpStatus.BAD_REQUEST);
    }

    public RequiredFieldException(ErrorEnum errorEnum) {
      super(errorEnum, HttpStatus.BAD_REQUEST);
    }

  }

  public static class BadRequestException extends Error {

    public BadRequestException() {
      super(ErrorEnum.GENERAL_BAD_REQUEST, HttpStatus.BAD_REQUEST);
    }

    public BadRequestException(String message) {
      super(message, ErrorEnum.GENERAL_BAD_REQUEST, HttpStatus.BAD_REQUEST);
    }

    public BadRequestException(ErrorEnum errorEnum) {
      super(errorEnum, HttpStatus.BAD_REQUEST);
    }

  }

  public static class AccessDeniedException extends Error {

    public AccessDeniedException() {
      super(ErrorEnum.GENERAL_ACCESS_DENIED, HttpStatus.FORBIDDEN);
    }

    public AccessDeniedException(String message) {
      super(message, ErrorEnum.GENERAL_ACCESS_DENIED, HttpStatus.FORBIDDEN);
    }

    public AccessDeniedException(ErrorEnum errorEnum) {
      super(errorEnum, HttpStatus.FORBIDDEN);
    }

  }

  public static class UnauthorizedException extends Error {

    public UnauthorizedException() {
      super(ErrorEnum.UNAUTHORIZED, HttpStatus.UNAUTHORIZED);
    }

    public UnauthorizedException(String message) {
      super(message, ErrorEnum.UNAUTHORIZED, HttpStatus.UNAUTHORIZED);
    }
  }

  public static class NotFoundException extends Error {

    public NotFoundException() {
      super(ErrorEnum.GENERAL_NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    public NotFoundException(String message) {
      super(message, ErrorEnum.GENERAL_NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    public NotFoundException(ErrorEnum errorEnum) {
      super(errorEnum, HttpStatus.NOT_FOUND);
    }

  }

  public static class DuplicateException extends Error {

    public DuplicateException() {
      super(ErrorEnum.GENERAL_DUPLICATION, HttpStatus.CONFLICT);
    }

    public DuplicateException(String message) {
      super(message, ErrorEnum.GENERAL_DUPLICATION, HttpStatus.CONFLICT);
    }

    public DuplicateException(ErrorEnum errorEnum) {
      super(errorEnum, HttpStatus.CONFLICT);
    }

  }

  public static class InternalServerException extends Error {

    public InternalServerException() {
      super(ErrorEnum.GENERAL_INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public InternalServerException(String message) {
      super(message, ErrorEnum.GENERAL_INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  public static class ServiceUnavailableException extends Error {

    public ServiceUnavailableException() {
      super(ErrorEnum.UNAVAILABLE_SERVICE, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ServiceUnavailableException(ErrorEnum errorEnum) {
      super(errorEnum, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ServiceUnavailableException(String message) {
      super(message, ErrorEnum.UNAVAILABLE_SERVICE, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}