package android.example.com.rafdroid;

import android.os.AsyncTask;
import android.support.design.internal.BottomNavigationMenu;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DownloadTask task = new DownloadTask();
        task.execute("https://rfidis.raf.edu.rs/raspored/json.php");

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);

        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigation.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationViewBehavior());

//        navigation.setBehaviorTranslationEnabled(true);
    }

    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {

            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL(urls[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = urlConnection.getInputStream();

                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();
                while (data != -1){
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            //todo OVDE PARSIRATI JSON

            //pisati ovde kako bismo mogli da apdejtujemo UI jer doInBackground nece moci jer koristi
            try {

                JSONArray arr = new JSONArray(result);

                for (int i = 0; i < arr.length(); i++){
                    JSONObject jsonPart = arr.getJSONObject(i); //uzimamo i-ti objekat i pretvaramo ga u json obj

                    Log.i("predmet", jsonPart.getString("predmet"));
                    Log.i("tip", jsonPart.getString("tip"));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            //Log.i("RAspored", result);
        }
    }
}
