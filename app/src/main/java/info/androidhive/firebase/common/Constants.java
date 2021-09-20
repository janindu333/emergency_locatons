package info.androidhive.firebase.common;

public interface Constants {
    String IDENTITY_SERVER_BASE_URL = "https://slemrgencycall.herokuapp.com/";
    String SOCKET_BASE_URL = "http://api.evcosmos.chargenet.lk:3000";
    String PORTAL_BASE_URL = "https://evcosmos.chargenet.lk/";
    String TRANSACTION_BASE_URL = "https://api.evcosmos.chargenet.lk:13000/";
    String NEWS_BASE_URL = "https://api.evcosmos.chargenet.lk:12100/";
    String IMAGE_BASE_URL = "https://s3-us-west-2.amazonaws.com/chargenet-images/";
    String SAMPATH_BASE_URL = "https://chargenet.lk/top-sampath-vishwa?userdata=";
    String PROMOTION_BASE_URL = "https://api.evcosmos.chargenet.lk:12100/PromotionService.asmx/";
    String RELOAD_BASE_URL = "https://evcosmos.chargenet.lk/Reload?";
    String RELOAD_BASE_URL_VISA_MATER = "https://evcosmos.chargenet.lk/Dashboards/ReloadGenieCardSave.aspx?";
    String VERIFY_BASE_URL_VISA_MATER = "https://evcosmos.chargenet.lk/Dashboards/Customer/CustomerVerifyCardApp.aspx?";
    String RELOAD_BASE_URL_AMEX = "https://evcosmos.chargenet.lk/Dashboards/ReloadAmex.aspx?";
    String RELOAD_BASE_URL_VISHWA = "https://evcosmos.chargenet.lk/Dashboards/ReloadSampathVishwa.aspx?";
    String PRIVACY_POLICY_URL = "https://evcosmos.chargenet.lk/PrivacyPolicy";
    String TERMS_CONDITIONS_URL = "https://evcosmos.chargenet.lk/TermsAndConditions";
    String PAYEE_ID = "1000000048";
    String SAMPATH_TOKEN = "WgeG_IsINkjN6VbEWqruAchpQO4kpr6c";
    String ERROR = "Error";
    String SUCCESS = "SUCCESS!";
    String EMPTY_STRING = "";
    String SOME_WRONG = "Something went wrong. Please try again";
    int EC_NoError = 0;
    int EC_PasswordLengthInvalid = 1;
    int EC_InvalidRequest = 2;
    int EC_InvalidEmail = 3;
    int EC_InvalidMobile = 4;
    int EC_UserExists = 5;
    int EC_NoSuchUserFound = 6;
    int EC_UserInsertionFailed = 7;
    int EC_UserUpdateFailed = 8;
    int EC_InvalidToken = 9;
    int EC_RequiredFieldsMissing = 10;
    int EC_ThirdPartyValidationFailed = 11;
    int EC_UserPassInvalid = 12;
    int EC_UnhandledError = 13;
    int EC_ERROR_TEXT_INSUFFICIENT_BALANCE = 14;
    int EC_ERROR_TEXT_USER_OR_CHARGE_POINT_NOT_ACTIVE = 15;
    int EC_ERROR_TEXT_NOT_ALLOWED_TO_OTHERS = 16;
    int EC_MessageNotSent = 17;
    int EC_InvalidLoginID = 18;
    int EC_UserNotActive = 19;
    int EC_NoSuchUserFoundOrPinIsIncorrect = 20;
    int EC_InvalidPromotionID = 21;
    int EC_OperationSpecificError = 22;
    int EC_InvalidChargeCardNumber = 23;
    int EC_RFIDExists = 24;
}
