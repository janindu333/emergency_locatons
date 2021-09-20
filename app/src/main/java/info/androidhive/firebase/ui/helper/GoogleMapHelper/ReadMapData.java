package info.androidhive.firebase.ui.helper.GoogleMapHelper;

import android.os.AsyncTask;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by cg on 7/31/2017.
 */

public class ReadMapData extends AsyncTask<String, Void , String> {

    public ObservableEmitter<PathDataObject> emitter;

    @Override
    protected String doInBackground(String... params) {
        String data = "";
        try {
            MapHttpConnection http = new MapHttpConnection();
            data = http.readUr(params[0]);
        } catch (Exception e) {
        }
        return data;
    }

    @Override
    protected void onPostExecute(final String result) {
        super.onPostExecute(result);

        Observable<PathDataObject> locationListObservable = Observable.create(e -> {
            ParserTask parserTask = new ParserTask();
            parserTask.emitter = e;
            parserTask.execute(result);
        });

        Observer locationListObserver = new Observer<PathDataObject>() {

            @Override
            public void onError(Throwable e) { }

            @Override
            public void onComplete() { }

            @Override
            public void onSubscribe(@NonNull Disposable d) { }

            @Override
            public void onNext(PathDataObject locations) {
                emitter.onNext(locations);
            }
        };

        locationListObservable.subscribe(locationListObserver);
    }
}
