package android.example.com.rafdroid;

import android.example.com.rafdroid.Model.Class;
import android.example.com.rafdroid.Model.Classroom;
import android.example.com.rafdroid.Model.Consultation;
import android.example.com.rafdroid.Model.Day;
import android.example.com.rafdroid.Model.Exam;
import android.example.com.rafdroid.Model.Group;
import android.example.com.rafdroid.Model.Professor;
import android.example.com.rafdroid.Model.Subject;
import android.support.v7.widget.CardView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
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
    private HashMap<String, Group> groups;
    private HashMap<String, Classroom> classrooms;
    private ArrayList<Class> classes;
    private ArrayList<Exam> exams;
    private ArrayList<Day> days;
    private ArrayList<Consultation> consultations;

    private CardView kalendarCard;
    private CardView ispitiCard;
    private CardView kolokvijumCard;
    private static Singleton singleton = null;

    private int exams_version;
    private int classes_version;
    private int calendar_version;
    private int consultations_version;

    private String searchQuery;

    private String trenutnoOtvoreni;



    private Singleton(){
        FileInputStream fis = null;
        ObjectInputStream in = null;

        subjects = new HashMap<>();
        professors = new HashMap<>();
        groups = new HashMap<>();
        classrooms = new HashMap<>();
        classes = new ArrayList<>();
        exams = new ArrayList<>();
        days = new ArrayList<>();
        consultations = new ArrayList<>();
        searchQuery = "";
        trenutnoOtvoreni = "";

//        try {
//
//            fis = new FileInputStream("classes_version");
//            in = new ObjectInputStream(fis);
//            classes_version = (int) in.readObject();
//
//            classes = (ArrayList<Class>) in.readObject();
//
//
//            in.close();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }

    }

    public static Singleton Instance(){
        if(singleton == null)
            singleton = new Singleton();

        return singleton;
    }

    public void execute(){

        DownloadTask taskClassis = new DownloadTask() {

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                fillClasses(result);

            }

        };
        taskClassis.execute("https://api.raf.ninja/v1/classes");

        DownloadTask taskExams = new DownloadTask() {

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                fillExams(result);

            }

        };
        taskExams.execute("https://api.raf.ninja/v1/exams");

        DownloadTask taskCalendar = new DownloadTask() {

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                fillCalendar(result);

            }

        };
        taskCalendar.execute("https://api.raf.ninja/v1/calendar");

        DownloadTask taskConsultations = new DownloadTask() {

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                fillConsultations(result);

            }

        };
        taskConsultations.execute("https://api.raf.ninja/v1/consultations");

    }

    private void fillConsultations(String result){

        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray arr = new JSONArray(jsonObject.getString("schedule"));

            for (int i = 0; i < arr.length(); i++){
                JSONObject jsonPart = arr.getJSONObject(i);

                String dayInWeek = jsonPart.getString("day");;
                Date start_date = new Date(0);
                Date end_date   = new Date(0);
                Professor professor = new Professor(jsonPart.getString("lecturer"));
                String cl = jsonPart.getString("class_name");
                Classroom classroom = new Classroom(jsonPart.getString("classroom"));

                String vreme = jsonPart.getString("time");

                SimpleDateFormat dateFormat = new SimpleDateFormat("HH");
                try {
                    start_date = dateFormat.parse(vreme.substring(0, 2));
                    end_date   = dateFormat.parse(vreme.substring(3));

                } catch (ParseException e) {
                }

                consultations.add(new Consultation(i, dayInWeek, start_date, end_date, professor, cl, classroom));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("Parsing JSON", "CONSULTATIONS - SUCCESSFULLY DONE");
    }
    private void fillCalendar(String result){
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray arr = new JSONArray(jsonObject.getString("schedule"));

            for (int i = 0; i < arr.length(); i++){
                JSONObject jsonPart = arr.getJSONObject(i);

                String start = jsonPart.getString("start_date");
                String end = jsonPart.getString("end_date");
                String type = jsonPart.getString("type");


                Date dateFrom = new Date(0);
                Date dateTo = new Date(0);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                try {
                    dateFrom = dateFormat.parse(start);
                    dateTo   = dateFormat.parse(end);

                } catch (ParseException e) {
                }

            days.add(new Day(dateFrom, dateTo, type));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("Parsing JSON", "CALENDAR - SUCCESSFULLY DONE");

    }
    private void fillExams(String result){
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray arr = new JSONArray(jsonObject.getString("schedule"));

            for (int i = 0; i < arr.length(); i++){
                JSONObject jsonPart = arr.getJSONObject(i);

                String name = jsonPart.getString("test_name");
                Date start_time = new Date();
                Date end_time = new Date();
                Classroom classroom = new Classroom(jsonPart.getString("classroom"));
                Professor professor = new Professor(jsonPart.getString("professor"));
                String type = jsonPart.getString("type");

                String termin = jsonPart.getString("date_and_time");


                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyydd.MM.HHmm");
                try {
                    int crtica = termin.indexOf("|");
                    String datum = termin.substring(0, crtica);
                    String start = termin.substring(crtica+1, crtica + 3);
                    String end = termin.substring(crtica+4, crtica + 6);
                    start_time = dateFormat.parse("2018"+datum + start+"00");
                    end_time   = dateFormat.parse("2018"+datum + end+"00");

                } catch (ParseException e) {
                }

                exams.add(new Exam(i, name, start_time, end_time, classroom, professor, type));
                exams.get(i).getDayName();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("Parsing JSON", "EXAM - SUCCESSFULLY DONE");

    }
    private void fillClasses(String result){
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray arr = new JSONArray(jsonObject.getString("schedule"));

            for (int i = 0; i < arr.length(); i++){
                JSONObject jsonPart = arr.getJSONObject(i); //uzimamo i-ti objekat i pretvaramo ga u json obj

                String predmet = jsonPart.getString("class_name");
                String tip = jsonPart.getString("type");
                String nastavnik = jsonPart.getString("lecturer");
                String grupe = jsonPart.getString("student_groups");
                String dan = jsonPart.getString("day_of_week");
                String termin = jsonPart.getString("time");
                String ucionica = jsonPart.getString("classroom");

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
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
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
                classes.add(new android.example.com.rafdroid.Model.Class(i, subject ,professor, tip, groupsArray, dateFrom, dateTo, classroom, dan));
            }

        ArrayList<Class> bla = getAllClassesForGroup("103");
            int av = 5;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("Parsing JSON", "CLASSES - SUCCESSFULLY DONE");
    }

    public ArrayList<Class> getAllSearchResults(String search){

        if(search.isEmpty())
            return  classes;

        search = search.toUpperCase();
        ArrayList<Class> currentClasses = new ArrayList();

        for(Class cs : classes){

            //NAZIV PREDMETA
            if (cs.getSubject().getName().contains(search))
                currentClasses.add(cs);

            //NAZIV PROFESORA
            else if (cs.getProfesor().getName().toUpperCase().contains(search))
                currentClasses.add(cs);

            else{
                for(Group gr : cs.getGroups())
                    if (gr.getName().toUpperCase().contains(search))
                        currentClasses.add(cs);
            }
        }

        return currentClasses;
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

    public Class getClassById(int id){
        for(Class cl : classes){
            if(cl.getId() == id)
                return cl;
        }
        return null;
    }

    public Consultation getConsultationById(int id){
        for(Consultation cons : consultations){
            if(cons.getId() == id)
                return cons;
        }
        return null;
    }

    public String getDayType(Date current){
        String type = "";

        for(Day day : days){
            if(!current.before(day.getStart_date()) && !current.after(day.getEnd_date())){
                type = day.getType();
            }
        }

        return type;
    }

    public ArrayList<Exam> getExams() {
        return exams;
    }

    public ArrayList<Exam> getExamsSearch(String search, String type) {

        ArrayList<Exam> currentExams = new ArrayList();

        for(Class cs : classes){

            //NAZIV PREDMETA
            if (cs.getSubject().getName().contains(search))
                currentExams.add(getExam(cs.getSubject().getName(), type));

                //NAZIV PROFESORA
            else if (cs.getProfesor().getName().contains(search))
                currentExams.add(getExam(cs.getSubject().getName(), type));

            else{
                for(Group gr : cs.getGroups())
                    if (gr.getName().contains(search))
                        currentExams.add(getExam(cs.getSubject().getName(), type));
            }
        }
        return currentExams;
    }

    private Exam getExam(String name, String type){

        for(Exam ex : exams){
            if(ex.getName().equals(name) && ex.getType().equals(type))
                return  ex;
        }

        return null;
    }

    public ArrayList<Consultation> getAllConsultations() {
        return consultations;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public String getTrenutnoOtvoreni() {
        return trenutnoOtvoreni;
    }

    public void setTrenutnoOtvoreni(String trenutnoOtvoreni) {
        this.trenutnoOtvoreni = trenutnoOtvoreni;
    }
}
