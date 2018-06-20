package android.example.com.rafdroid.Model;

import java.util.Date;

public class Consultation {

    private int id;
    private String dayInWeek;
    private Date start_date;
    private Date end_date;
    private Professor professor;
    private String class_name;
    private Classroom classroom;

    public Consultation(int id, String dayInWeek, Date start_date, Date end_date, Professor professor, String class_name, Classroom classroom) {
        this.id = id;
        this.dayInWeek = dayInWeek;
        this.start_date = start_date;
        this.end_date = end_date;
        this.professor = professor;
        this.class_name = class_name;
        this.classroom = classroom;
    }

    public Date getStart_date() {
        return start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDayOfWeek() {
        return dayInWeek;
    }

    public void setDayInWeek(String dayInWeek) {
        this.dayInWeek = dayInWeek;
    }

    public Date getStart_time() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_time() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
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
            case "Ponedeljak":
                day = 1;
                break;
            case "Utorak":
                day = 2;
                break;
            case "Sreda":
                day = 3;
                break;
            case "Četvrtak":
                day = 4;
                break;
            case "Petak":
                day = 5;
                break;

        }

        return day;
    }
}
