package dosa.counselor.common.exception;

import lombok.Getter;

@Getter
public class AccessDeniedException extends RuntimeException{

    private ErrorCode errorCode;

    public AccessDeniedException(String message, ErrorCode errorCode){
        super(message);
        this.errorCode = errorCode;
        this.errorCode.setMessage(message);
    }

    public AccessDeniedException(ErrorCode errorCode){
        this.errorCode = errorCode;
    }

}