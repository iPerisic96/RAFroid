package android.example.com.rafdroid;

import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private CardView kalendarCard;
    private CardView ispitiCard;
    private CardView kolokvijumCard;
    private SearchView searchView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Singleton singleton = Singleton.Instance();
        singleton.execute();

        searchView = (SearchView) findViewById(R.id.search_view);

        kalendarCard = (CardView) findViewById(R.id.kalendarCard);
        ispitiCard = (CardView) findViewById(R.id.ispitiCard);
        kolokvijumCard = (CardView) findViewById(R.id.klkCard);

       // BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);

       // CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigation.getLayoutParams();
        //layoutParams.setBehavior(new BottomNavigationViewBehavior());

//        navigation.setBehaviorTranslationEnabled(true);

        kalendarCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BasicActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        return super.onCreateOptionsMenu(menu);
    }
}
