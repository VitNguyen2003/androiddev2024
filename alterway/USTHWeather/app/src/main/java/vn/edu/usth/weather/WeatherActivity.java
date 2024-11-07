package vn.edu.usth.weather;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.InputStream;

import java.net.URL;
import java.net.HttpURLConnection;

import com.google.android.material.tabs.TabLayout;

public class WeatherActivity extends AppCompatActivity {
    private static final String SERVER_RESPONSE = "server_response";
    private static int duration;
    private Bitmap bitmap;
    private ImageView usthlogo;
    String USTHLogo = "https://cdn.haitrieu.com/wp-content/uploads/2022/11/Logo-Truong-Dai-hoc-Khoa-hoc-va-Cong-nghe-Ha-Noi.png";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


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
//            Handler handler = new Handler(Looper.getMainLooper()) {
//                @Override
//                public void handleMessage(Message msg) {
//                    String content = msg.getData().getString(SERVER_RESPONSE);
//                    Toast.makeText(getBaseContext(), content, duration).show();
//                }
//            };
//            Thread t = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//
//                    Bundle bundle = new Bundle();
//                    bundle.putString(SERVER_RESPONSE, getString(R.string.fetch_success));
//
//                    Message msg = new Message();
//                    msg.setData(bundle);
//                    handler.sendMessage(msg);
//                }
//            });
//            t.start();
//            Toast.makeText(getBaseContext(), R.string.refresh_message, Toast.LENGTH_LONG).show();
//            return true;
            ExecutorService service = Executors.newSingleThreadExecutor();
            service.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        URL url = new URL(USTHLogo);
                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                        httpURLConnection.setRequestMethod("GET");
                        httpURLConnection.setDoInput(true);
                        httpURLConnection.connect();
                        int response = httpURLConnection.getResponseCode();
                        Log.i("USTH Weather", String.format("Response Code: %d", response));
                        InputStream inputStream = httpURLConnection.getInputStream();
                        bitmap = BitmapFactory.decodeStream(inputStream);
                        httpURLConnection.disconnect();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (bitmap != null) {
                                usthlogo = findViewById(R.id.logo);
                                usthlogo.setImageBitmap(bitmap);
                                Toast.makeText(getBaseContext(), "Image is set", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getBaseContext(), "There is some problem", Toast.LENGTH_LONG).show();
                            }

                        }
                    });
                }
            });
            Handler handler = new Handler(Looper.getMainLooper());
        } else if (id == R.id.action_settings) {

            Intent intent = new Intent(this, PrefActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}