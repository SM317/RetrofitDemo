package com.example.demoretrofit;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.demoretrofit.DataModel.HeroViewModel;
import com.example.demoretrofit.WebAPI.RetroFitClient;

import java.util.List;

import androidx.core.content.ContextCompat;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieNameActivity extends BaseActivity{
    ListView listView;
    private ProgressBar loadingPB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        setupActionBar();
        loadingPB = findViewById(R.id.idLoadingPB);
        listView = findViewById(R.id.listViewHeroes);
        listView.setVisibility(View.GONE);
        loadingPB.setVisibility(View.VISIBLE);
        SystemClock.sleep(1000);
        //calling the method to display the heroes
        getHeroes();
    }

    private void getHeroes() {
        // below line is for displaying our progress bar.
        Call<List<HeroViewModel>> callHero = RetroFitClient.getInstance().getMyApi().getHeroes();
        callHero.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<HeroViewModel>> call, Response<List<HeroViewModel>> response) {
                loadingPB.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
                List<HeroViewModel> heroList = response.body();

                //Creating an String array for the ListView
                String[] heroes = new String[heroList.size()];

                //looping through all the heroes and inserting the names inside the string array
                for (int i = 0; i < heroList.size(); i++) {
                    heroes[i] = heroList.get(i).getName();
                }
                //displaying the string array into listview
                int count = heroes.length;
                //displaying the string array into listview
                listView.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, heroes));
            }
            @Override
            public void onFailure(Call<List<HeroViewModel>> call, Throwable t) {
                loadingPB.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
