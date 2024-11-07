package vn.edu.usth.weather;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link aForecastFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class aForecastFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public aForecastFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment aForecastFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static aForecastFragment newInstance(String param1, String param2) {
        aForecastFragment fragment = new aForecastFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        View v = new View(getContext());
//        v.setBackgroundColor(0x20FF0000);
//
//        LinearLayout LL = new LinearLayout(getContext());
//        LL.setOrientation(LinearLayout.VERTICAL);
//
////        ImageView sunny = new ImageView(getContext());
////        sunny.setImageResource(R.drawable.sunny);
//        ImageView cloudy = new ImageView(getContext());
//        cloudy.setImageResource(R.drawable.cloudynew);
//
//        TextView text = new TextView(getContext());
//        text.setText("today:");
//        text.setTextSize(30);
//        LL.addView(text);
////      LL.addView(sunny);
//        LL.addView(cloudy);
//        LL.addView(v);
//        return LL;
        // Inflate the layout for this fragment(this is real comment)
      return inflater.inflate(R.layout.fragment_a_forecast, container, false);
    }
}

