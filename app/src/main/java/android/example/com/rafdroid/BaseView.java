package android.example.com.rafdroid;

import android.content.Context;
import android.example.com.rafdroid.Model.Class;
import android.example.com.rafdroid.Model.Consultation;
import android.example.com.rafdroid.Model.Exam;
import android.graphics.RectF;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.alamkanak.weekview.DateTimeInterpreter;
import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public abstract class BaseView extends AppCompatActivity implements WeekView.EventClickListener, MonthLoader.MonthChangeListener, WeekView.EventLongPressListener, WeekView.EmptyViewLongPressListener {

    private TextView mTextMessage;
    private static final int TYPE_DAY_VIEW = 1;
    private static final int TYPE_WEEK_VIEW = 3;
    private int mWeekViewType = TYPE_WEEK_VIEW;
    private WeekView mWeekView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kalendar_view);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get a reference for the week view in the layout.
        mWeekView = (WeekView) findViewById(R.id.weekView);

        // Show a toast message about the touched event.
        mWeekView.setOnEventClickListener(this);

        // The week view has infinite scrolling horizontally. We have to provide the events of a
        // month every time the month changes on the week view.
        mWeekView.setMonthChangeListener(this);

        // Set long press listener for events.
//        mWeekView.setEventLongPressListener(this);

        // Set long press listener for empty view
//        mWeekView.setEmptyViewLongPressListener(this);

        // Set up a date time interpreter to interpret how the date and time will be formatted in
        // the week view. This is optional.
        setupDateTimeInterpreter(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQuery("", true);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Singleton.Instance().setSearchQuery(query);
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.DAY_OF_MONTH, 22);
                cal.set(Calendar.MONTH, 9);
                cal.set(Calendar.YEAR, 2018);
                mWeekView.goToDate(cal);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                Singleton.Instance().setSearchQuery(newText);
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.DAY_OF_MONTH, 22);
                cal.set(Calendar.MONTH, 9);
                cal.set(Calendar.YEAR, 2018);
                mWeekView.goToDate(cal);

                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        setupDateTimeInterpreter(id == R.id.action_week_view);
        switch (id){
            case R.id.action_day_view:
                if (mWeekViewType != TYPE_DAY_VIEW) {
                    item.setChecked(!item.isChecked());
                    mWeekViewType = TYPE_DAY_VIEW;
                    mWeekView.setNumberOfVisibleDays(1);

                    // Lets change some dimensions to best fit the view.
                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                    mWeekView.setXScrollingSpeed(1);
                }
                return true;
            case R.id.action_week_view:
                if (mWeekViewType != TYPE_WEEK_VIEW) {
                    item.setChecked(!item.isChecked());
                    mWeekViewType = TYPE_WEEK_VIEW;
                    mWeekView.setNumberOfVisibleDays(7);

                    // Lets change some dimensions to best fit the view.
                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()));
                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));

                    mWeekView.setXScrollingSpeed(0);
                    Calendar cal = Calendar.getInstance();
                    cal.set(Calendar.DAY_OF_MONTH, 22);
                    cal.set(Calendar.MONTH, 9);
                    cal.set(Calendar.YEAR, 2018);
                    mWeekView.goToDate(cal);
                }
                return true;
            case android.R.id.home:
                this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Set up a date time interpreter which will show short date values when in week view and long
     * date values otherwise.
     * @param shortDate True if the date values should be short.
     */
    private void setupDateTimeInterpreter(final boolean shortDate) {
        mWeekView.setDateTimeInterpreter(new DateTimeInterpreter() {
            @Override
            public String interpretDate(Calendar date) {
                SimpleDateFormat weekdayNameFormat = new SimpleDateFormat("EEE", Locale.getDefault());
                String weekday = weekdayNameFormat.format(date.getTime());
                SimpleDateFormat format = new SimpleDateFormat(" M/d", Locale.getDefault());

                // All android api level do not have a standard way of getting the first letter of
                // the week day name. Hence we get the first char programmatically.
                // Details: http://stackoverflow.com/questions/16959502/get-one-letter-abbreviation-of-week-day-of-a-date-in-java#answer-16959657

                int dayOfWeek = date.get(Calendar.DAY_OF_WEEK);
                String day = "";
                switch((dayOfWeek - 2) % 7) {
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
                return day;
//                if (shortDate)
//                    weekday = String.valueOf(weekday.charAt(0));
//                return weekday.toUpperCase() + format.format(date.getTime());
            }

            @Override
            public String interpretTime(int hour) {
                return hour + ":00h";
            }
        });
    }

    protected String getEventTitle(Calendar time) {
        return String.format("Event of %02d:%02d %s/%d", time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.MONTH)+1, time.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onEventClick(WeekViewEvent event, RectF eventRect) {
       // Toast.makeText(this, "Clicked " + event.getName(), Toast.LENGTH_SHORT).show();
        Singleton singleton = Singleton.Instance();

        if(singleton.getTrenutnoOtvoreni().equals("p")) {
            Class cl = singleton.getClassById((int) event.getId());

            Calendar cal = Calendar.getInstance();
            cal.setTime(cl.getStart_time());
            int hoursStart = cal.get(Calendar.HOUR_OF_DAY);
            int minStart = cal.get(Calendar.MINUTE);

            cal.setTime(cl.getEnd_time());
            int hoursEnd = cal.get(Calendar.HOUR_OF_DAY);
            int minEnd = cal.get(Calendar.MINUTE);

            AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
            View myView = getLayoutInflater().inflate(R.layout.dialog_class, null);
            TextView tv_className = (TextView) myView.findViewById(R.id.class_name);
            TextView tv_classType = (TextView) myView.findViewById(R.id.class_type);
            TextView tv_classTime = (TextView) myView.findViewById(R.id.class_time); //ucionica, vreme (U2, 12:15-14:00h)
            TextView tv_classGroup = (TextView) myView.findViewById(R.id.class_group);
            TextView tv_classProf = (TextView) myView.findViewById(R.id.class_prof);

            tv_className.setText(cl.getSubject().getName());
            tv_classType.setText(cl.getTypeClass());
            tv_classTime.setText(cl.getClassroom().getName() + ", " + hoursStart + ":" + minStart + "-" + hoursEnd + ":" + minEnd + "0h");
            tv_classGroup.setVisibility(View.VISIBLE);
            tv_classProf.setVisibility(View.VISIBLE);
            tv_classGroup.setText(cl.getGroupsString().toString());
            tv_classProf.setText(cl.getProfesor().getName());

            mBuilder.setView(myView);
            AlertDialog dialog = mBuilder.create();
            dialog.setCancelable(true);
            dialog.show();
        }

        else{

            Consultation cons = singleton.getConsultationById((int) event.getId());

            Calendar cal = Calendar.getInstance();
            cal.setTime(cons.getStart_time());
            int hoursStart = cal.get(Calendar.HOUR_OF_DAY);
            int minStart = cal.get(Calendar.MINUTE);

            cal.setTime(cons.getEnd_time());
            int hoursEnd = cal.get(Calendar.HOUR_OF_DAY);
            int minEnd = cal.get(Calendar.MINUTE);

            AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
            View myView = getLayoutInflater().inflate(R.layout.dialog_class, null);
            TextView tv_className = (TextView) myView.findViewById(R.id.class_name);
            TextView tv_classType = (TextView) myView.findViewById(R.id.class_type);
            TextView tv_classTime = (TextView) myView.findViewById(R.id.class_time); //ucionica, vreme (U2, 12:15-14:00h)
            TextView tv_classGroup = (TextView) myView.findViewById(R.id.class_group);
            TextView tv_classProf = (TextView) myView.findViewById(R.id.class_prof);

            tv_className.setText(cons.getClass_name());
            tv_classType.setText(cons.getProfessor().getName());
            tv_classTime.setText(cons.getClassroom().getName() + ", " + hoursStart + ":0" + minStart + "-" + hoursEnd + ":" + minEnd + "0h");
//            tv_classGroup.setText(cl.getGroupsString().toString());
//            tv_classProf.setText(cl.getProfesor().getName());
            tv_classGroup.setVisibility(View.INVISIBLE);
            tv_classProf.setVisibility(View.INVISIBLE);

            mBuilder.setView(myView);
            AlertDialog dialog = mBuilder.create();
            dialog.setCancelable(true);
            dialog.show();



        }

    }

    @Override
    public void onEventLongPress(WeekViewEvent event, RectF eventRect) {
//        Toast.makeText(this, "Long pressed event: " + event.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEmptyViewLongPress(Calendar time) {
//        Toast.makeText(this, "Empty view long pressed: " + getEventTitle(time), Toast.LENGTH_SHORT).show();
    }

    public WeekView getWeekView() {
        return mWeekView;
    }

}
