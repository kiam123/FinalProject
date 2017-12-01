package tw.edu.fcu.recommendedfood.Server;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by kiam on 6/16/2017.
 */

public class PostServer extends AsyncTask<Void, String, String> {
    @Override
    protected String doInBackground(Void... params) {
        URL targetUrl = null;
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
//            targetUrl = new URL("http://140.115.197.16/?school=fcu&app=美食推薦");
            targetUrl = new URL("http://0f80eca9.ngrok.io/api/fcu/location");
            urlConnection = (HttpURLConnection) targetUrl.openConnection();
            urlConnection.setRequestMethod("POST");
            reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));//do something

            String line = null;

            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
        } catch (Exception e) {
        } finally {
            urlConnection.disconnect();
        }
        Log.v("test", stringBuilder.toString());

        return stringBuilder.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        onResponse(s);
        Log.v("log_tag", s);

    }

    public void onResponse(String response) {

    }
}
