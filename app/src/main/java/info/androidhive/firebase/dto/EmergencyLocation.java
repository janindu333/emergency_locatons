package info.androidhive.firebase.dto;

import lombok.Getter;
import lombok.Setter;

public class EmergencyLocation {

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    private String longitude;
    private String latitude;
    private String location;
    private String description;
    private String mobile;

}
