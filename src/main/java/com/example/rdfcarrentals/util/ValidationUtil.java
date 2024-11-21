package com.example.rdfcarrentals.util;

import javafx.scene.control.TextField;

public class ValidationUtil {
    private static final String nicPattern = "^[0-9]{9}[vVxX]||[0-9]{12}$";
    private static final String namePattern = "^[A-Za-z ]+$";
    private static final String addressPattern = "^[A-z0-9 ,/]{4,40}$";
    private static final String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    private static final String contactNumberPattern = "^(070|072|074|075|076|077|078)[0-9]{7}$";
    private static final String pricePattern = "^(0|[1-9]\\d*)(\\.\\d{2})?$";
    private static final String timePattern = "^([0-1]?[0-9]|[2][0-3]):([0-5][0-9])(:[0-5][0-9])?$";
    private static final String validStyle = ";-fx-border-color: #7367F0;";
    private static final String invalidStyle = ";-fx-border-color: red;";

    private static boolean validateField(TextField textField, String pattern) {
        boolean isValid = textField.getText().matches(pattern);
        textField.setStyle(isValid ? validStyle : invalidStyle);
        return isValid;
    }

    public static boolean isValidNic(TextField textField) { return validateField(textField, nicPattern); }

    public static boolean isValidName(TextField textField) {
        return validateField(textField, namePattern);
    }

    public static boolean isValidAddress(TextField textField) {
        return validateField(textField, addressPattern);
    }

    public static boolean isValidEmail(TextField textField) {
        return validateField(textField, emailPattern);
    }

    public static boolean isValidContactNumber(TextField textField) { return validateField(textField, contactNumberPattern); }

    public static boolean isValidPrice(TextField textField) { return validateField(textField, pricePattern); }

    public static boolean isValidTime(TextField textField) { return validateField(textField, timePattern); }
}
