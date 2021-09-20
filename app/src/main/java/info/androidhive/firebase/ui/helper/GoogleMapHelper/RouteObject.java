package info.androidhive.firebase.ui.helper.GoogleMapHelper;

import java.util.List;

import lombok.Getter;

public class RouteObject {

    @Getter
    private List<LegsObject> legs;

    public RouteObject(List<LegsObject> legs) {
        this.legs = legs;
    }
}
