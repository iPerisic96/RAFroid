package android.example.com.rafdroid.Model;

import java.util.Date;

public class Consultation {

    private int id;
    private String dayInWeek;
    private Date start_date;
    private Date end_date;
    private Professor professor;
    private String cl;
    private Classroom classroom;

    public Consultation(int id, String dayInWeek, Date start_date, Date end_date, Professor professor, String cl, Classroom classroom) {
        this.id = id;
        this.dayInWeek = dayInWeek;
        this.start_date = start_date;
        this.end_date = end_date;
        this.professor = professor;
        this.cl = cl;
        this.classroom = classroom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDayInWeek() {
        return dayInWeek;
    }

    public void setDayInWeek(String dayInWeek) {
        this.dayInWeek = dayInWeek;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
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

    public String getCl() {
        return cl;
    }

    public void setCl(String cl) {
        this.cl = cl;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }
}
