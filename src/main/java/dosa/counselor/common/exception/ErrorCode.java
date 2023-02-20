package dosa.counselor.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    NOT_FOUND(404,"COMMON-ERR-404","PAGE NOT FOUND"),
    INTERNAL_SERVER_ERROR(500,"COMMON-ERR-500","INTERNAL SERVER ERROR"),
    ACCESS_DENIED(403,"ACCESS-DENIED-403","token invalid"),
    ILLEGAL_ARGUMENT(500,"COMMON-ERR-500","illegal argument"),
    INVALID_REQUEST(500,"COMMON-ERR-500","invalid request"),
    BAD_CREDENTIALS(401,"BAD_CREDENTIALS-401","can't find the user"),
    ;

    private int status;
    private String errorCode;
    private @Setter String message;
}
