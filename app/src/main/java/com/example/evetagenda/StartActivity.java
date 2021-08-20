package com.example.evetagenda;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import com.example.evetagenda.data.DatabaseHelper;
import com.example.evetagenda.databinding.ActivityStartBinding;
import com.example.evetagenda.model.UserInfoResponse;
import com.example.evetagenda.ui.login.LoginActivity;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StartActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityStartBinding binding;
    private String username;
    private String password;
    private String fullName;
    public static String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityStartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        DatabaseHelper myDb = new DatabaseHelper(StartActivity.this);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            Cursor res = myDb.getAllData();
            if (res.getCount() == 0) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
                overridePendingTransition(0, 0);
            } else {
                while (res.moveToNext()) {
                    username = res.getString(1);
                    password = res.getString(2);
                    id = res.getString(0);
                    fullName = res.getString(3)+" "+res.getString(4);

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(Api.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    Api api = retrofit.create(Api.class);
                    Call<UserInfoResponse> call = api.getUser(username,password);

                    call.enqueue(new Callback<UserInfoResponse>() {
                        @Override
                        public void onResponse(Call<UserInfoResponse> call, Response<UserInfoResponse> response) {
                            Intent intent;
                            if(response.body().getError().getErrorCode().equals("200")) {
                                intent = new Intent(StartActivity.this, UserActivity.class);
                                intent.putExtra("fullName", fullName);
                                intent.putExtra("email", username);
                            }else{
                                intent = new Intent(StartActivity.this, LoginActivity.class);
                            }
                            startActivity(intent);
                        }
                        @Override
                        public void onFailure(Call<UserInfoResponse> call, Throwable t) {
                            Log.d("StartActivity", t.toString());
                        }
                    });
                }
            }
        }, 2000);

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main2);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}