package info.androidhive.firebase.Model;

public class FormData {

    public String name;
    public String mobileNumber;
    public String email;
    public String bankName;
    public String branchName;
    public String accountNumber;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String id;

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int point;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBranchName() {
        return branchName;
    }

    public FormData(String name, String mobileNumber, String email, String bankName, String branchName, String accountNumber, String id, int point) {
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.bankName = bankName;
        this.branchName = branchName;
        this.accountNumber = accountNumber;
        this.id = id;
        this.point = point;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }



    public FormData() {
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

}