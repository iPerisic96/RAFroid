package android.example.com.rafdroid.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Class implements Serializable {

    private Subject subject;
    private Professor profesor;
    private String typeClass;
    private ArrayList<Group> groups;
    private Date start_time;
    private Date end_time;
    private Classroom classroom;
    private String DayOfWeek;
    private int id;

    public Class(int id, Subject subject, Professor profesor, String typeClass, ArrayList<Group> groups, Date start_time, Date end_time, Classroom classroom, String dayOfWeek) {
        this.id = id;
        this.subject = subject;
        this.profesor = profesor;
        this.typeClass = typeClass;
        this.groups = groups;
        this.start_time = start_time;
        this.end_time = end_time;
        this.classroom = classroom;
        DayOfWeek = dayOfWeek;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public String getDayOfWeek() {
        return DayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        DayOfWeek = dayOfWeek;
    }

    public Professor getProfesor() {
        return profesor;
    }

    public void setProfesor(Professor profesor) {
        this.profesor = profesor;
    }

    public String getTypeClass() {
        return typeClass;
    }

    public void setTypeClass(String typeClass) {
        this.typeClass = typeClass;
    }

    public ArrayList<Group> getGroups() {
        return groups;
    }

    public void setGroups(ArrayList<Group> groups) {
        this.groups = groups;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public int getDayInWeek(){
        int day = -1;

        switch(getDayOfWeek()) {
            case "PON":
                day = 1;
                break;
            case "UTO":
                day = 2;
                break;
            case "SRE":
                day = 3;
                break;
            case "CET":
                day = 4;
                break;
            case "PET":
                day = 5;
                break;

        }

        return day;
    }

    public String getGroupsString(){

        String rez = "";
        for(Group gr : groups){
            rez+= gr.getName() + " ";
        }
        return rez;
    }
}
