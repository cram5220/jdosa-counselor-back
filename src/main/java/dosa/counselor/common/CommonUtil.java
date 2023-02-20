package dosa.counselor.common;

import dosa.counselor.common.encryption.AES256;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil {
    // 휴대폰 번호 검증
    public static boolean validCellphoneNumber(String number) {
        number = number.replaceAll("[^0-9]","");
        Pattern pattern = Pattern.compile("\\d{11}");
        Matcher matcher = pattern.matcher(number);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }

    public static String ganerateRandomAuthcode(Integer length) {
        //인증코드 생성
        long seed = System.currentTimeMillis();
        Random rand = new Random(seed);

        Long bound = 9L;
        for(var i=1; i<length; i++){
            bound=(bound*10)+9;
        }
        return String.format("%0"+String.valueOf(length)+"d",rand.nextLong(bound));
    }


    // AES256 디코드 후 $ 구분자로 나누어 반환
    public static String[] splitEncryptedCode(String code){
        return AES256.decrypt(code).split("\\$");
    }
}
