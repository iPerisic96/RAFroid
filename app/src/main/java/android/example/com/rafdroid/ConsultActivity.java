package android.example.com.rafdroid;

import android.example.com.rafdroid.Model.Class;
import android.example.com.rafdroid.Model.Consultation;

import com.alamkanak.weekview.WeekViewEvent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class ConsultActivity extends BaseView {

    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        // Populate the week view with some events.
        List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();

        Singleton singleton = Singleton.Instance();
        ArrayList<Consultation> consultation = singleton.getAllConsultations();
        Calendar calendarPom = GregorianCalendar.getInstance();
        singleton.setTrenutnoOtvoreni("k");

        Calendar startTime = Calendar.getInstance();
        Calendar endTime   = Calendar.getInstance();

        WeekViewEvent event;

        int sat, minut;

        int id = 0;
        for(Consultation cons : consultation){

            sat = cons.getStart_time().getHours();
            minut = cons.getStart_time().getMinutes();

            startTime = Calendar.getInstance();
            startTime.set(Calendar.HOUR_OF_DAY, sat);
            startTime.set(Calendar.MINUTE, minut);
            startTime.set(Calendar.MONTH, newMonth-1);
            startTime.set(Calendar.YEAR, newYear);
//            startTime.set(Calendar.DAY_OF_MONTH, startTime.get(Calendar.DAY_OF_MONTH));
            startTime.set(Calendar.DAY_OF_WEEK, cons.getDayInWeek() + 1);


            sat = cons.getEnd_time().getHours();
            minut = cons.getEnd_time().getMinutes();

            endTime = (Calendar) startTime.clone();
            endTime.set(Calendar.HOUR_OF_DAY, sat);
            endTime.set(Calendar.MINUTE, minut);
            endTime.set(Calendar.MONTH, newMonth-1);

            event = new WeekViewEvent(cons.getId(), cons.getClass_name() + "\n" + cons.getProfessor().getName(), startTime, endTime);

//            if (cons.getTypeClass().equalsIgnoreCase("predavanja")) {
//                event.setColor(getResources().getColor(R.color.event_color_02));
//            } else if (cons.getTypeClass().equalsIgnoreCase("vezbe"))
//                event.setColor(getResources().getColor(R.color.event_color_03));
//            else
                event.setColor(getResources().getColor(R.color.event_color_01));
            events.add(event);

        }

        this.refreshScreen();
        return events;
    }
}
