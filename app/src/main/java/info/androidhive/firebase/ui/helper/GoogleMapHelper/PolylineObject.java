package info.androidhive.firebase.ui.helper.GoogleMapHelper;

import lombok.Getter;

/**
 * Created by cg on 9/14/2017.
 */

public class PolylineObject {

    @Getter
    private String points;

    public PolylineObject(String points) {
        this.points = points;
    }

}
