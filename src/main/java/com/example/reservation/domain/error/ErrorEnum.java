package com.example.reservation.domain.error;

import lombok.Getter;

@Getter
public enum ErrorEnum {

    GENERAL_INTERNAL_SERVER_ERROR(500101, 500, "error_general_internal_server_error"),
    UNAVAILABLE_SERVICE(500102, 500, "unavailable_service_error"),

    GENERAL_BAD_REQUEST(400101, 400, "error_general_bad_request"),

    UNAUTHORIZED(401101, 401, "error_unauthorized"),

    GENERAL_NOT_FOUND(404101, 404, "error_general_not_found"),
    AVAILABLE_SLOT_NOT_FOUND(404102, 404, "error_available_slot_not_found"),
    RESERVATION_NOT_FOUND(404102, 404, "error_reservation_not_found"),

    GENERAL_ACCESS_DENIED(403101, 403, "error_general_access_denied"),

    GENERAL_DUPLICATION(409101, 409, "error_general_duplication"),
    ;

    /**
     * The code should be unique.0.00001
     * The correct format is: 123456 => 123: Http Status Code.
     *                                  4:   Service Code (General is zero).
     *                                  56:  Error Code.
     */

    private int code;
    private int status;
    private String messageKey;

    ErrorEnum(int code, int status, String messageKey) {
        this.code = code;
        this.status = status;
        this.messageKey = messageKey;
    }

}
