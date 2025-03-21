package id.co.fim.wipinformationsystemmobile.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import id.co.fim.wipinformationsystemmobile.R;
import id.co.fim.wipinformationsystemmobile.responses.ApiEndPoint;
import id.co.fim.wipinformationsystemmobile.responses.StatusResponse;
import id.co.fim.wipinformationsystemmobile.responses.UserResponse;
import id.co.fim.wipinformationsystemmobile.services.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private SharedPreferences prefLogin;
    private Button btnLogin;
    private EditText etUsername, etPassword;
    private TextView txtError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        prefLogin = getSharedPreferences("loginPref",MODE_PRIVATE);
        etUsername = (EditText) findViewById(R.id.username);
        etPassword = (EditText)findViewById(R.id.password);
        txtError = findViewById(R.id.errorText);
        txtError.setVisibility(View.INVISIBLE);

        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                loginProcess(etUsername.getText().toString(), etPassword.getText().toString());
            }
        });
    }

    public void loginProcess(String username, String password) {

        if (etUsername.getText().toString().equals("") && etPassword.getText().toString().equals("")){
            txtError.setVisibility(View.VISIBLE);
            txtError.setText("Username dan password tidak boleh kosong !");
        }else if(etUsername.getText().toString().equals("")){
            txtError.setVisibility(View.VISIBLE);
            txtError.setText("Username tidak boleh kosong !");
        }else if(etPassword.getText().toString().equals("")){
            txtError.setVisibility(View.VISIBLE);
            txtError.setText("Password tidak boleh kosong !");
        }else {
            ApiEndPoint apiEndPoint = ApiClient.getClient().create(ApiEndPoint.class);
            Call<UserResponse> call = apiEndPoint.loginProcess(username, password);

            call.enqueue(new Callback<UserResponse>() {
                @Override
                public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                    Log.e("API Response", response.toString());
                    Log.e("API Body", new Gson().toJson(response.body()));

                    final UserResponse userResponse = response.body();

                    if (userResponse.getResponses()) {

                        SharedPreferences.Editor editor = prefLogin.edit();
                        editor.putString("login", "1");
                        editor.putString("username", etUsername.getText().toString());
                        editor.putInt("role", userResponse.getRole());
                        editor.commit();

                        Intent i = new Intent(LoginActivity.this, MenuActivity.class);
                        startActivity(i);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                    } else {
                        txtError.setText("Username atau password salah !");
                        txtError.setTextColor(Color.parseColor("#ff3030"));
                        txtError.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<UserResponse> call, Throwable t) {
                    Log.e("LoginError", "Error: " + t.getMessage());
                    txtError.setText("Tidak dapat terhubung dengan server !");
                    txtError.setTextColor(Color.parseColor("#ff3030"));
                    txtError.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed()
    {
        finishAffinity();
    }
}
