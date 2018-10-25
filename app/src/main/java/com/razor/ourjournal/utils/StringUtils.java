package com.razor.ourjournal.utils;

import android.support.annotation.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    private static final String CELLPHONE_NUMBER = "[0](\\d{9})|([0](\\d{2})([ -])(\\d{3})([ -])(\\d{4}))|[0](\\d{2})([ -])(\\d{7})";
    private static final String EMAIL_EXPRESSION = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String ID_EXPRESSION = "([0-9][0-9])(([0][1-9])|([1][0-2]))(([0-2][0-9])|" +
            "([3][0-1]))([0-9])([0-9]{3})([0-9])([0-9])([0-9])";
    private static final int CHECKSUM_OFFSET_QUALIFIER = 5;
    private static final int CHECKSUM_OFFSET = 9;
    private static final int EVEN_DIGIT_MOD = 2;

    public static boolean isAlphanumericSpace(String str) {
        if (str == null) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if ((!Character.isLetterOrDigit(str.charAt(i))) && (str.charAt(i) != ' ')) {
                return false;
            }
        }
        return true;
    }

    public static boolean isCellphoneNumber(String cellphoneNumber){
        return !cellphoneNumber.isEmpty() && Pattern.compile(CELLPHONE_NUMBER).matcher(cellphoneNumber).matches();
    }

    public static boolean isEmail(String text){
        return text.matches(EMAIL_EXPRESSION);
    }

    public static boolean isValidId(String id){
        if(isNullorEmpty(id)) {
            return false;
        }

        if (id.length() != 13) {
            return false;
        }

        Pattern mPattern = Pattern.compile(ID_EXPRESSION);
        Matcher matcher = mPattern.matcher(id);

        return matcher.matches() && validateIdChecksum(id);
    }

    public static boolean isNullorEmpty(@Nullable CharSequence line){
        boolean flag = false;
        if (line == null || line.length() == 0){
            flag = true;
        }
        return flag;
    }

    private static boolean validateIdChecksum(CharSequence id) {
        int sumOfId = 0;
        for (int index = 1; index <= id.length(); index++) {
            int currentDigit = Character.getNumericValue(id.charAt(id.length() - index));
            if (index % EVEN_DIGIT_MOD != 0) {
                sumOfId += currentDigit;
            } else {
                // If the digit multiplied by 2 results in a 2 digit number, then get the sum of the
                // first and second digit of the result. We already know that if the current digit is more
                // (or equal to) 5 then the result will be 2 digits
                int result = currentDigit * 2;
                sumOfId += currentDigit < CHECKSUM_OFFSET_QUALIFIER ?
                        result : result - CHECKSUM_OFFSET;
            }
        }
        return (sumOfId % 10) == 0;
    }

    public static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((!Character.isWhitespace(str.charAt(i)))) {
                return false;
            }
        }
        return true;
    }
}
