package vn.edu.usth.weather;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.io.InputStream;

import com.google.android.material.tabs.TabLayout;

public class WeatherActivity extends AppCompatActivity {
    private static final String SERVER_RESPONSE = "server_response";
    private static int duration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ViewPager viewPager = findViewById(R.id.view_pager);
        TabLayout tabLayout = findViewById(R.id.tab_layout);

        WeatherPagerAdapter pagerAdapter = new WeatherPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        MediaPlayer music = MediaPlayer.create(WeatherActivity.this, R.raw.march7theme);
        music.start();



//        aForecastFragment firstFragment = new aForecastFragment();
//        getSupportFragmentManager().beginTransaction().add(R.id.forecast_frag, firstFragment).commit();
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.i("Start", "Start");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Resume", "Resume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("Pause", "Pause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Stop", "Stop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Destroy", "Destroy");
    }

    private class WeatherPagerAdapter extends FragmentPagerAdapter {
        private final String[] tabTitles = new String[]{"HaNoi VietNam", "Paris France", "Toulouse France"};

        public WeatherPagerAdapter(@NonNull FragmentManager fm) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return new WeatherAndForecastFragment();
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.tool_bar, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        duration = Toast.LENGTH_LONG;
        if (id == R.id.action_refresh) {
            Handler handler = new Handler(Looper.getMainLooper()) {
              @Override
              public void handleMessage(Message msg) {
                  String content = msg.getData().getString(SERVER_RESPONSE);
                  Toast.makeText(getBaseContext(), content, duration).show();
              }
            };
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Bundle bundle = new Bundle();
                    bundle.putString(SERVER_RESPONSE, getString(R.string.fetch_success));

                    Message msg = new Message();
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                }
            });
            t.start();
            Toast.makeText(getBaseContext(), R.string.refresh_message, Toast.LENGTH_LONG).show();
            return true;
        } else if (id == R.id.action_settings) {

            Intent intent = new Intent(this, PrefActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}