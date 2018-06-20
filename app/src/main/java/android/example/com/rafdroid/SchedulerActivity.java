package android.example.com.rafdroid;

import android.example.com.rafdroid.Model.Day;
import android.example.com.rafdroid.Model.Exam;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.alamkanak.weekview.WeekViewEvent;

import java.util.ArrayList;
import java.util.List;

public class SchedulerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kolokvijumi);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
//
//        String search = "309";
//        String type = "CURRICULUM";
//
        Singleton singleton = Singleton.Instance();
        ArrayList<Day> days = singleton.getDays();


        RecyclerView mRecycleView = (RecyclerView) findViewById(R.id.dummyCards);
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mRecycleView.setAdapter(new SchedulerAdapter(days, this));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            finish();
        }
        return  true;
    }
}
