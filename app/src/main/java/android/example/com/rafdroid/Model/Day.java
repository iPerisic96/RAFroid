package android.example.com.rafdroid.Model;

import java.util.Date;

public class Day {

    private Date start_date;
    private Date end_date;
    private String type;
    private String startDateString;
    private String endDateString;

    public Day(Date start_date, Date end_date, String type, String startDateString, String endDateString) {
        this.start_date = start_date;
        this.end_date = end_date;
        this.type = type;
        this.startDateString = startDateString;
        this.endDateString = endDateString;
    }

    public String getStartDateString() {
        return startDateString;
    }

    public void setStartDateString(String startDateString) {
        this.startDateString = startDateString;
    }

    public String getEndDateString() {
        return endDateString;
    }

    public void setEndDateString(String endDateString) {
        this.endDateString = endDateString;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
