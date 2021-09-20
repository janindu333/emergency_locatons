package info.androidhive.firebase.ui.helper.GoogleMapHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by cg on 7/31/2017.
 */

public class MapHttpConnection {
    public String readUr(String mapsApiDirectionsUrl) throws IOException {
        String data = "";
        InputStream istream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(mapsApiDirectionsUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            istream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(istream));
            StringBuffer sb = new StringBuffer();
            String line ="";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            data = sb.toString();
            br.close();
        }
        catch (Exception e) {
        } finally {
            istream.close();
            urlConnection.disconnect();
        }
        return data;
    }
}
