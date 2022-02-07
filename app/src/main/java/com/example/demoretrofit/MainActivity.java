package com.example.demoretrofit;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demoretrofit.DataModel.HeroViewModel;
import com.example.demoretrofit.DataModel.JobDataModel;
import com.example.demoretrofit.WebAPI.RetroAPI;
import com.example.demoretrofit.WebAPI.RetroFitClient;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    // creating variables for our edittext,
    // button, textview and progressbar.
    private EditText nameEdt, jobEdt;
    private Button postDataBtn;
    private TextView responseTV;
    private ProgressBar loadingPB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        // initializing our views
        nameEdt = findViewById(R.id.idEdtName);
        jobEdt = findViewById(R.id.idEdtJob);
        postDataBtn = findViewById(R.id.idBtnPost);
        responseTV = findViewById(R.id.idTVResponse);
        loadingPB = findViewById(R.id.idLoadingPB);
        // adding on click listener to our button.
        postDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // validating if the text field is empty or not.
                if (nameEdt.getText().toString().isEmpty() && jobEdt.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter both the values", Toast.LENGTH_SHORT).show();
                    return;
                }

                // calling a method to post the data and passing our name and job.
                postJobData(nameEdt.getText().toString(), jobEdt.getText().toString());
               // getHeroes();
            }
        });
    }

    private void postJobData(String name, String job) {
        // passing data from our text fields to our modal class.
        JobDataModel modal = new JobDataModel(name, job);
        Call<JobDataModel> callJob = RetroFitClient.getInstance().getMyApi().createPost(modal);
        callJob.enqueue(new Callback<JobDataModel>() {
            @Override
            public void onResponse(Call<JobDataModel> call, Response<JobDataModel> response) {
                // this method is called when we get response from our api.
                Toast.makeText(MainActivity.this, "Data added to API", Toast.LENGTH_SHORT).show();

                // below line is for hiding our progress bar.
                loadingPB.setVisibility(View.GONE);

                // on below line we are setting empty text
                // to our both edit text.
                jobEdt.setText("");
                nameEdt.setText("");

                // we are getting response from our body
                // and passing it to our modal class.
                JobDataModel responseFromAPI = response.body();

                // on below line we are getting our data from modal class and adding it to our string.
                String responseString = "Response Code : " + response.code() + "\nName : " + responseFromAPI.getName() + "\n" + "Job : " + responseFromAPI.getJobName();

                // below line we are setting our
                // string to our text view.
                responseTV.setText(responseString);
            }

            @Override
            public void onFailure(Call<JobDataModel> call, Throwable t) {
                loadingPB.setVisibility(View.GONE);
                // setting text to our text view when
                // we get error response from API.
                responseTV.setText("Error found is : " + t.getMessage());
            }
        });
    }

    private void getHeroes() {
        // below line is for displaying our progress bar.
        loadingPB.setVisibility(View.VISIBLE);
        Call<List<HeroViewModel>> callHero = RetroFitClient.getInstance().getMyApi().getHeroes();
        callHero.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<HeroViewModel>> call, Response<List<HeroViewModel>> response) {
                loadingPB.setVisibility(View.GONE);
                List<HeroViewModel> heroList = response.body();

                //Creating an String array for the ListView
                String[] heroes = new String[heroList.size()];

                //looping through all the heroes and inserting the names inside the string array
                for (int i = 0; i < heroList.size(); i++) {
                    heroes[i] = heroList.get(i).getName();
                }
                //displaying the string array into listview
                int count = heroes.length;
            }
            @Override
            public void onFailure(Call<List<HeroViewModel>> call, Throwable t) {
                loadingPB.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}