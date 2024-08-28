package vn.edu.usth.usth.weather;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import vn.edu.usth.usth.weather.R;

public class WeatherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        Log.i ("ID", "onCreate");
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