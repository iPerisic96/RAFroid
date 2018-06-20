package android.example.com.rafdroid;

import android.example.com.rafdroid.Model.Class;

import com.alamkanak.weekview.WeekViewEvent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * A basic example of how to use week view library.
 * Created by Raquib-ul-Alam Kanak on 1/3/2014.
 * Website: http://alamkanak.github.io
 */
public class PredavanjaActivity extends BaseView {

    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        // Populate the week view with some events.
        List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();

        Singleton singleton = Singleton.Instance();
        ArrayList<Class> classes = singleton.getAllSearchResults(singleton.getSearchQuery());
        Calendar calendarPom = GregorianCalendar.getInstance();
        singleton.setTrenutnoOtvoreni("p");


        Calendar startTime = Calendar.getInstance();
        Calendar endTime   = Calendar.getInstance();

        WeekViewEvent event;

        int sat, minut;

        int id = 0;
        for(Class cl : classes){

            sat = cl.getStart_time().getHours();
            minut = cl.getStart_time().getMinutes();

            startTime = Calendar.getInstance();
            startTime.set(Calendar.HOUR_OF_DAY, sat);
            startTime.set(Calendar.MINUTE, minut);
            startTime.set(Calendar.MONTH, newMonth-1);
            startTime.set(Calendar.YEAR, newYear);
//            startTime.set(Calendar.DAY_OF_MONTH, startTime.get(Calendar.DAY_OF_MONTH));
            startTime.set(Calendar.DAY_OF_WEEK, cl.getDayInWeek() + 1);


            sat = cl.getEnd_time().getHours();
            minut = cl.getEnd_time().getMinutes();

            endTime = (Calendar) startTime.clone();
            endTime.set(Calendar.HOUR_OF_DAY, sat);
            endTime.set(Calendar.MINUTE, minut);
            endTime.set(Calendar.MONTH, newMonth-1);

            event = new WeekViewEvent(cl.getId(), cl.getSubject().getName() + "\n" + cl.getTypeClass(), startTime, endTime);

            if (cl.getTypeClass().equalsIgnoreCase("predavanja")) {
                event.setColor(getResources().getColor(R.color.event_color_02));
            } else if (cl.getTypeClass().equalsIgnoreCase("vezbe"))
                event.setColor(getResources().getColor(R.color.event_color_03));
            else
                event.setColor(getResources().getColor(R.color.event_color_01));
            events.add(event);

        }

        return events;
    }

}
