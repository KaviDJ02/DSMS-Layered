package com.drivesmart.dsms.util;

import javafx.scene.control.TextField;
import java.util.regex.Pattern;

public class Validate {

    // Validate if a string is not null and not empty
    public static boolean isNotNullOrEmpty(String str) {
        return str != null && !str.trim().isEmpty();
    }

    // Validate if a string is a valid email
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return email != null && pattern.matcher(email).matches();
    }

    // Validate if a string is a valid phone number (example for US phone numbers)
    public static boolean isValidPhoneNumber(String phoneNumber) {
        String phoneRegex = "^(\\+1|001)?\\d{10}$";
        Pattern pattern = Pattern.compile(phoneRegex);
        return phoneNumber != null && pattern.matcher(phoneNumber).matches();
    }

    // Validate if a string is a valid password (example: at least 8 characters, 1 uppercase, 1 lowercase, 1 digit)
    public static boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$";
        Pattern pattern = Pattern.compile(passwordRegex);
        return password != null && pattern.matcher(password).matches();
    }

    // Validate if a string is a valid name (example: only letters and spaces)
    public static boolean isValidName(String name) {
        String nameRegex = "^[a-zA-Z\\s]+$";
        Pattern pattern = Pattern.compile(nameRegex);
        return name != null && pattern.matcher(name).matches();
    }

    // Validate if a string is a valid international phone number
    public static boolean isValidSriLankanPhoneNumber(String phoneNumber) {
        String phoneRegex = "^07\\d{8}$";
        Pattern pattern = Pattern.compile(phoneRegex);
        return phoneNumber != null && pattern.matcher(phoneNumber).matches();
    }

    public static boolean isValidNIC(String nic) {
        String nicRegex = "^\\d{9}[vV]$";
        String nicRegex2 = "^\\d{12}$";
        Pattern pattern = Pattern.compile(nicRegex);
        Pattern pattern2 = Pattern.compile(nicRegex2);
        return nic != null && pattern.matcher(nic).matches() || nic != null && pattern2.matcher(nic).matches();
    }

    // Validate if a string is a valid integer
    public static boolean isValidInteger(String str) {
        if (str == null) {
            return false;
        }
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isValidDate(String date) {
        String dateRegex = "^\\d{4}-\\d{2}-\\d{2}$";
        Pattern pattern = Pattern.compile(dateRegex);
        return date != null && pattern.matcher(date).matches();
    }

    // Validate TextField and change border color
    public static boolean validateTextField(TextField textField, String validationType) {
        String text = textField.getText();
        boolean isValid = false;

        switch (validationType) {
            case "NotNullOrEmpty":
                isValid = isNotNullOrEmpty(text);
                break;
            case "Email":
                isValid = isValidEmail(text);
                break;
            case "PhoneNumber":
                isValid = isValidPhoneNumber(text);
                break;
            case "Password":
                isValid = isValidPassword(text);
                break;
            case "Name":
                isValid = isValidName(text);
                break;
            case "SriLankanPhoneNumber":
                isValid = isValidSriLankanPhoneNumber(text);
                break;
            case "Integer":
                isValid = isValidInteger(text);
                break;
            case "NIC":
                isValid = isValidNIC(text);
                break;
            case "Date":
                isValid = isValidDate(text);
                break;
        }

        if (isValid) {
            textField.setStyle("-fx-border-color: #5fef5f;");
        } else {
            textField.setStyle("-fx-border-color: #f35a5a;");
        }

        return isValid;
    }
}