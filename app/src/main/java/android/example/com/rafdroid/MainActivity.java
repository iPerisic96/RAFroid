package android.example.com.rafdroid;

import android.example.com.rafdroid.Model.Class;
import android.example.com.rafdroid.Model.Classroom;
import android.example.com.rafdroid.Model.Group;
import android.example.com.rafdroid.Model.Professor;
import android.example.com.rafdroid.Model.Subject;
import android.os.AsyncTask;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private HashMap<String, Subject> subjects;
    private HashMap<String, Professor> professors;
    private ArrayList<Class> classes;
    private HashMap<String, Group> groups;
    private HashMap<String, Classroom> classrooms;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        subjects = new HashMap<>();
        professors = new HashMap<>();
        groups = new HashMap<>();
        classrooms = new HashMap<>();
        classes = new ArrayList<>();

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

                    String predmet = jsonPart.getString("predmet");
                    String tip = jsonPart.getString("tip");
                    String nastavnik = jsonPart.getString("nastavnik");
                    String grupe = jsonPart.getString("grupe");
                    String dan = jsonPart.getString("dan");
                    String termin = jsonPart.getString("termin");
                    String ucionica = jsonPart.getString("ucionica");

                    Subject subject;
                    if(subjects.containsKey(predmet))
                        subject = subjects.get(predmet);
                    else {
                        subject = new Subject(predmet);
                        subjects.put(predmet, subject);
                    }

                    Professor professor;
                    if(professors.containsKey(nastavnik))
                        professor = professors.get(nastavnik);
                    else {
                        professor = new Professor(nastavnik);
                        professors.put(nastavnik, professor);
                    }
                    ArrayList<Group> groupsArray = new ArrayList<>();
                    List<String> groupsString = Arrays.asList(grupe.split("\\s*,\\s*"));
                    for(String gr : groupsString){

                        Group group;
                        if(groups.containsKey(gr))
                            group = groups.get(gr);
                        else {
                            group = new Group(gr);
                            groups.put(gr, group);
                        }

                        groupsArray.add(group);
                    }

                    Date dateFrom = new Date();
                    Date dateTo = new Date();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");
                    try {
                        dateFrom = dateFormat.parse(termin.substring(0, termin.indexOf("-")));
                        dateTo   = dateFormat.parse(termin.substring(termin.indexOf("-") + 1) + ":00");

                    } catch (ParseException e) {
                    }

                    Classroom classroom;
                    if(classrooms.containsKey(ucionica))
                        classroom = classrooms.get(ucionica);
                    else {
                        classroom = new Classroom(ucionica);
                        classrooms.put(ucionica, classroom);
                    }
                    classes.add(new android.example.com.rafdroid.Model.Class(subject ,professor, tip, groupsArray, dateFrom, dateTo, classroom, dan));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            ArrayList<Class> proba1 = getAllClassesForGroup("309");
            ArrayList<Class> proba2 = getAllClassesForProfessor("Cerovic Milutin");
            ArrayList<Class> proba3 = getAllClassesForSubject("Masinsko ucenje");
        }




    public ArrayList<Class> getAllClassesForGroup(String group){
        ArrayList<Class> currentClasses = new ArrayList();

        for(Class cs : classes){
            for(Group gr : cs.getGroups())
                if (gr.getName().equals(group))
                    currentClasses.add(cs);
        }

        return currentClasses;
    }

    public ArrayList<Class> getAllClassesForSubject(String subject){
        ArrayList<Class> currentClasses = new ArrayList();

        for(Class cs : classes){
            if (cs.getSubject().getName().equals(subject))
                currentClasses.add(cs);
        }

        return currentClasses;
    }

    public ArrayList<Class> getAllClassesForProfessor(String professor){
        ArrayList<Class> currentClasses = new ArrayList();

        for(Class cs : classes){
            if (cs.getProfesor().getName().equals(professor))
                currentClasses.add(cs);
        }

        return currentClasses;
    }
    }
}
