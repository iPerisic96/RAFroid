package android.example.com.rafdroid.Model;

import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

public class Singleton {

    private HashMap<String, Subject> subjects;
    private HashMap<String, Professor> professors;
    private ArrayList<Class> classes;
    private HashMap<String, Group> groups;
    private HashMap<String, Classroom> classrooms;
    private CardView kalendarCard;
    private CardView ispitiCard;
    private CardView kolokvijumCard;
    private static Singleton singleton = null;



    private Singleton(){
    }

    public static Singleton Instance(){
        if(singleton == null)
            singleton = new Singleton();

        return singleton;
    }

    public void execute(){

        subjects = new HashMap<>();
        professors = new HashMap<>();
        groups = new HashMap<>();
        classrooms = new HashMap<>();
        classes = new ArrayList<>();

        DownloadTask task = new DownloadTask();
        task.execute("https://rfidis.raf.edu.rs/raspored/json.php");

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



    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {

            StringBuilder result = new StringBuilder();
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL(urls[0]);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty("apikey","850nmHTsPyGL4MXNG2Hl");
                InputStream in = urlConnection.getInputStream();

                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();
                while (data != -1){
                    char current = (char) data;
                    result.append(current);
                    data = reader.read();
                }
                return result.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);



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

            Log.d("Parsing JSON", "SUCCESSFULLY DONE");
        }
    }
}
