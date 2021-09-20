package info.androidhive.firebase.Model;

public class VideoData {

    public String id;
    public String email;
    public boolean isWatched;
    public String videoUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isWatched() {
        return isWatched;
    }

    public void setWatched(boolean isWatched) {
        isWatched = isWatched;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public VideoData() {
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public VideoData(String id, String email, String videoUrl, boolean isWatched) {
        this.id = id;
        this.email = email;
        this.videoUrl = videoUrl;
        this.isWatched = isWatched;
    }
}