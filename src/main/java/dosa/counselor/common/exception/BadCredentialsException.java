package dosa.counselor.common.exception;

import lombok.Getter;

@Getter
public class BadCredentialsException extends RuntimeException{

    private ErrorCode errorCode = ErrorCode.BAD_CREDENTIALS;

    public BadCredentialsException(String message, ErrorCode errorCode){
        super(message);
        this.errorCode = errorCode;
    }

    public BadCredentialsException(ErrorCode errorCode){
        this.errorCode = errorCode;
    }

    public BadCredentialsException(String message){
        super(message);
        this.errorCode.setMessage(message);
    }
}