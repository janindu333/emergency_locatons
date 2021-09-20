package info.androidhive.firebase.ui.helper.GoogleMapHelper;

import java.util.List;

import lombok.Getter;

/**
 * Created by cg on 9/14/2017.
 */

public class LegsObject {

    @Getter
    private List<StepsObject> steps;
    @Getter
    private DataObject distance;
    @Getter
    private DataObject duration;

    public LegsObject(DataObject duration, DataObject distance, List<StepsObject> steps) {
        this.duration = duration;
        this.distance = distance;
        this.steps = steps;
    }

}
