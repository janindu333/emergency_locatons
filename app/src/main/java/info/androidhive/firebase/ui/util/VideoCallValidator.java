package info.androidhive.firebase.ui.util;

public class VideoCallValidator {
    private static VideoCallValidator instance;

    private VideoCallValidator() {}

    public static VideoCallValidator getInstance() {
        if (instance == null) instance = new VideoCallValidator();
        return instance;
    }

    public boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }

    public boolean isValidPassword(String password) {
        if (password == null) {
            return false;
        } else {
            return password.trim().length() >= 6;
        }
    }

    public boolean isValidPhoneNumber(String number) {
        String numberRegex = "^\\+?\\d{1,2}\\s?\\d{2}\\s?\\-?\\d{1}\\s?\\d{2}\\s?\\d{4}$";
        if (number == null) {
            return false;
        } else {
            return number.matches(numberRegex);
        }
    }

    public boolean isValidMobilePin(String pin, String value) {
        return !pin.isEmpty() && pin.contentEquals(value);
    }

    public  boolean isValidRFID(String value) {
        String regex = "\\d{12}";
        return  value.matches(regex);
    }
}
