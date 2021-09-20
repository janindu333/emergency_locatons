package info.androidhive.firebase.Model;

public class PointData {

    public String id;
    public String email;
    public int point;

    public PointData() {
    }

    public PointData(String id, String email, int point) {
        this.id = id;
        this.email = email;
        this.point = point;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}