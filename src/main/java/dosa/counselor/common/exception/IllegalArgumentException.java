package dosa.counselor.common.exception;

import lombok.Getter;

@Getter
public class IllegalArgumentException extends RuntimeException{

    private ErrorCode errorCode = ErrorCode.ILLEGAL_ARGUMENT;

    public IllegalArgumentException(String message, ErrorCode errorCode){
        super(message);
        this.errorCode = errorCode;
    }

    public IllegalArgumentException(ErrorCode errorCode){
        this.errorCode = errorCode;
    }

    public IllegalArgumentException(String message){
        super(message);
        this.errorCode.setMessage(message);
    }
}