package info.androidhive.firebase.ui.helper.GoogleMapHelper;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import lombok.Getter;

/**
 * Created by cg on 9/14/2017.
 */
@Getter
public class PathDataObject {

    private List<LatLng> routes;
    private String distance;
    private String time;

    public PathDataObject(List<LatLng> routes, String distance, String time) {
        this.routes = routes;
        this.distance = distance;
        this.time = time;
    }

}
