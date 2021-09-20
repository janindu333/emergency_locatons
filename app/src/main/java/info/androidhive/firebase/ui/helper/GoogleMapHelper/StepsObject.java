package info.androidhive.firebase.ui.helper.GoogleMapHelper;

import lombok.Getter;

/**
 * Created by cg on 9/14/2017.
 */

public class StepsObject {

    @Getter
    private PolylineObject polyline;
    public StepsObject(PolylineObject polyline) {
        this.polyline = polyline;
    }

}
