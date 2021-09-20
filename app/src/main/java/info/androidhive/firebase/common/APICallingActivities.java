package info.androidhive.firebase.common;

public interface APICallingActivities {
    String LOGIN = "LOGIN";
    String LOGIN_APP_MODE = "APP";
    String LOGIN_FACEBOOK_MODE = "FACEBOOK";
    String LOGIN_GOOGLE_MODE = "GOOGLE";
    String ACTIVE_USER = "ACTIVE_USER";
    String INACTIVE_USER = "INACTIVE_USER";
    String VALID = "VALID";
    String INVALID = "INVALID";
    String CREATE_ACCOUNT = "CREATE_ACCOUNT";
    String ACTIVATE_USER ="ACTIVATE_USER";
    String RESEND_PIN = "RESEND_PIN";
    String CUSTOMER = "CUSTOMER";
    String CUSTOMER_UPDATE = "CUSTOMER_UPDATE";
    String LOG_OUT = "LOG_OUT";
    String SUMMARY = "SUMMARY";
    String BALANCE_HISTORY = "BALANCE_HISTORY";
    String FAVOURITE = "FAVOURITE";
    String ONGOING_CHARGE = "ONGOING_CHARGE";
    String CHECK_CHARGE_POINT = "CHECK_CHARGE_POINT";
    String CHECK_USER = "CHECK_USER";
    String CHECK_EMAIL = "CHECK_EMAIL";
    String CARD_DETAILS = "CARD_DETAILS";
    String DELETE_CARD = "DELETE_CARD";
    String RATING = "RATING";
    String TRANSACTION = "TRANSACTION";
    String LOCATION_LIST = "LOCATION_LIST";
    String ADD_RFID = "ADD_RFID";
    String FORGOT_PASSWORD = "FORGOT_PASSWORD";
    String MQTT = "MQTT";
    enum VideoCallServiceType {
        Identity,
        Portal,
        Transaction,
        Promotion,
        News
    }
}
