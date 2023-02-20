package dosa.counselor.common.exception;

import lombok.Getter;

@Getter
public class WrongRequestException extends RuntimeException{

    private ErrorCode errorCode;

    public WrongRequestException(String message, ErrorCode errorCode){
        super(message);
        this.errorCode = errorCode;
    }

    public WrongRequestException(ErrorCode errorCode){
        this.errorCode = errorCode;
    }

    public WrongRequestException(String message){
        super(message);
        this.errorCode.setMessage(message);
    }
}