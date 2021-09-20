package info.androidhive.firebase.ui.helper.GoogleMapHelper;

import java.util.ArrayList;

import lombok.Getter;

/**
 * Created by cg on 9/14/2017.
 */

public class DirectionObject {

    @Getter
    private ArrayList<RouteObject> routes;
    @Getter
    private String status;

    public DirectionObject(ArrayList<RouteObject> routes, String status) {
        this.routes = routes;
        this.status = status;
    }
}
