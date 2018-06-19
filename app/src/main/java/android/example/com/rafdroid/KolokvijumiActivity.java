package android.example.com.rafdroid;

import android.example.com.rafdroid.Model.Class;
import android.example.com.rafdroid.Model.Exam;

import com.alamkanak.weekview.WeekViewEvent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class KolokvijumiActivity extends BaseView{

    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        // Populate the week view with some events.
        List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();

        String search = "309";
        String type = "CURRICULUM";

        Singleton singleton = Singleton.Instance();
        ArrayList<Exam> exams = singleton.getExams();

        Calendar startTime = Calendar.getInstance();
        Calendar endTime   = Calendar.getInstance();


        WeekViewEvent event;
        int sat, minut, mesec, dan;
        for(Exam exam : exams){

            if(!exam.getType().equals(type))
                continue;

            sat = exam.getStart_time().getHours();
            minut = exam.getStart_time().getMinutes();


            startTime = Calendar.getInstance();
            startTime.set(Calendar.HOUR_OF_DAY, sat);
            startTime.set(Calendar.MINUTE, minut);
            startTime.set(Calendar.MONTH, exam.getStart_time().getMonth());
            startTime.set(Calendar.YEAR, newYear);
            startTime.set(Calendar.DAY_OF_MONTH, exam.getStart_time().getDay());


            sat = exam.getEnd_time().getHours();
            minut = exam.getEnd_time().getMinutes();

            endTime = (Calendar) startTime.clone();
            endTime.set(Calendar.HOUR_OF_DAY, sat);
            endTime.set(Calendar.MINUTE, minut);

            event = new WeekViewEvent(exam.getId(), exam.getName(), startTime, endTime);

//            if (cl.getTypeClass().equalsIgnoreCase("predavanja")) {
//                event.setColor(getResources().getColor(R.color.event_color_02));
//            } else if (cl.getTypeClass().equalsIgnoreCase("vezbe"))
//                event.setColor(getResources().getColor(R.color.event_color_03));
//            else
            event.setColor(getResources().getColor(R.color.event_color_01));
            events.add(event);

        }

        return events;
    }
}
