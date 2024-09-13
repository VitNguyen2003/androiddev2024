package vn.edu.usth.usth.weather;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import vn.edu.usth.usth.weather.R;

public class WeatherActivity extends AppCompatActivity {

    private static final String TAG = "WeatherActivity";
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        Log.i ("ID", "onCreate");

        Toolbar toolbar = findViewById(R.id.toolbar);
        SetSupportActionBar(toolbar);

        //Create a new Fragment to be placed in the activity layout
        ForecastFragment firstFragment= new ForecastFragment();
        WeatherFragment weatherFragment = new WeatherFragment();

        //Add the fragment to the 'container' FrameLayout
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction().add(R.id.fragment_forecast).commit();
//        getSupportFragmentManager().beginTransaction().add(R.id.container,firstFragment).commit();
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction().add(R.id.main, ForecastFragment).commit();
        // Add a ViewPager into WeatherActivity
        PagerAdapter adapter = new HomeFragmentPagerAdapter(getSupportFragmentManager());

        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setOffscreenPageLimit(3);
        pager.setAdapter(adapter);
    }

    private void SetSupportActionBar(Toolbar toolbar) {
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i ("ID", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i ("ID", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i ("ID", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
       Log.i("ID", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i ("ID", "onDestroy");
    }
}
