package info.androidhive.firebase.ui.helper.GoogleMapHelper;

import android.os.AsyncTask;

import com.google.gson.Gson;

import io.reactivex.ObservableEmitter;

/**
 * Created by cg on 7/31/2017.
 */

public class ParserTask extends AsyncTask<String, Integer, PathDataObject> {

    public ObservableEmitter<PathDataObject> emitter;

    @Override
    protected PathDataObject doInBackground(
            String... jsonData) {
        PathDataObject pathData = null;
        try {
            Gson gson = new Gson();
            DirectionObject directionObject = gson.fromJson(jsonData[0], DirectionObject.class);
            PathJSONParser parser = new PathJSONParser();
            pathData = parser.getDirectionPolylines(directionObject.getRoutes());
        } catch (Exception e) {
        }
        return pathData;
    }

    @Override
    protected void onPostExecute(PathDataObject pathData) {
        emitter.onNext(pathData);
    }
}
