package info.androidhive.firebase.ui.helper.GoogleMapHelper;

import lombok.Getter;

/**
 * Created by cg on 9/14/2017.
 */

public class DataObject {

    @Getter
    private String text;
    @Getter
    private String value;

    public DataObject(String text, String value) {
        this.text = text;
        this.value = value;
    }

}
