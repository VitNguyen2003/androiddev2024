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
import android.widget.TextView;
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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;

import javax.xml.datatype.Duration;

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
        if (id == R.id.action_changeInFragment) {
            imageDownload();
            return true;

        } else if (id == R.id.action_settings) {

            Intent intent = new Intent(this, PrefActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_refresh) {
            JsonTest();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void imageDownload(){
        RequestQueue queue = Volley.newRequestQueue(this);
        // a listener (kinda similar to onPostExecute())
        Response.Listener<Bitmap> listener =
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        if (response != null){
                        Toast.makeText(getBaseContext(), "load image success", duration).show();
                        usthlogo = findViewById(R.id.logo);
                        usthlogo.setImageBitmap(response);
                        } else {Toast.makeText(getBaseContext(), "load fail, check internet", duration).show();
                        }
                    }
                };
        // a simple request to the required image
        ImageRequest imageRequest = new ImageRequest(
                USTHLogo,
                listener, 0, 0, ImageView.ScaleType.CENTER,
                Bitmap.Config.ARGB_8888, null);
        // go!
        queue.add(imageRequest);
    }

    private void JsonTest(){
        TextView tv = findViewById(R.id.show_text);
        String url = "https://api.openweathermap.org/data/2.5/weather?lat=21.02&lon=105.85&appid=myAPIkey&units=metric";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject obj = response.getJSONObject("main");
                    Double temp = obj.getDouble("temp");
                    String feels_like = obj.getString("feels_like");
                    //int id = response.getInt("id");
                    String city = response.getString("name");
                    tv.setText("City: "+city+"\nTemparature in Celsius: "+temp+"\nWeather feels like: "+feels_like);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tv.setText("error");
            }
        });
        RequestQueue requestQueueTwo = Volley.newRequestQueue(this);
        requestQueueTwo.add(jsonObjectRequest);
    }



}