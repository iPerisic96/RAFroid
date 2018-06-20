package android.example.com.rafdroid.Model;

import java.util.Calendar;
import java.util.Date;

public class Exam {

    private String name;
    private Date start_time;
    private Date end_time;
    private Classroom classroom;
    private Professor professor;
    private String type;
    private int id;

    public Exam(int id, String name, Date start_time, Date end_time, Classroom classroom, Professor professor, String type) {
        this.name = name;
        this.start_time = start_time;
        this.end_time = end_time;
        this.classroom = classroom;
        this.professor = professor;
        this.type = type;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDayName(){

        Calendar pomCal = Calendar.getInstance();
        pomCal.setTime(start_time);
        int dayOfWeek = pomCal.get(Calendar.DAY_OF_WEEK);

        String day = "";
        switch((dayOfWeek + 5) % 7) {
            case 0:
                day = "PON";
                break;
            case 1:
                day = "UTO";
                break;
            case 2:
                day = "SRE";
                break;
            case 3:
                day = "ČET";
                break;
            case 4:
                day = "PET";
                break;
            case 5:
                day = "SUB";
                break;
            default:
                day = "NED";
                break;
        }

        return  day;
    }
}
