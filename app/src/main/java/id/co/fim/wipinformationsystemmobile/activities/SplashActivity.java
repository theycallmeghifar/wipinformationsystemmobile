package id.co.fim.wipinformationsystemmobile.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import id.co.fim.wipinformationsystemmobile.R;
import id.co.fim.wipinformationsystemmobile.responses.ApiEndPoint;
import id.co.fim.wipinformationsystemmobile.responses.StatusResponse;
import id.co.fim.wipinformationsystemmobile.services.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    private SharedPreferences prefLogin;
    private boolean isLogin;
    private Handler handler = new Handler();
    private boolean isConnected = false;
    private int retryCount = 0; // Menyimpan jumlah percobaan koneksi
    private static final int MAX_RETRY = 5; // Batas maksimal retry

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        prefLogin = getSharedPreferences("loginPref", MODE_PRIVATE);
        isLogin = prefLogin.getString("login", "0").equals("1");

        // Mulai pengecekan server
        checkServerConnection();
    }

    private void checkServerConnection() {
        ApiEndPoint apiEndPoint = ApiClient.getClient().create(ApiEndPoint.class);
        Call<StatusResponse> call = apiEndPoint.checkConnection();

        call.enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getResponses()) {
                    isConnected = true;
                    navigateToNextScreen();
                } else {
                    retryConnection();
                }
            }

            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {
                Log.e("SplashActivity", "Gagal terhubung ke server: " + t.getMessage());
                Toast.makeText(SplashActivity.this, "Tidak dapat terhubung dengan server!", Toast.LENGTH_SHORT).show();
                retryConnection();
            }
        });
    }

    private void retryConnection() {
        if (!isConnected && retryCount < MAX_RETRY) {
            retryCount++;
            handler.postDelayed(this::checkServerConnection, 2000);
        } else if (retryCount >= MAX_RETRY) {
            Toast.makeText(SplashActivity.this, "Server tidak merespons. Coba lagi nanti!", Toast.LENGTH_LONG).show();
            navigateToNextScreen();
        }
    }

    private void navigateToNextScreen() {
        Class<?> nextActivity = isLogin ? MenuActivity.class : LoginActivity.class;
        Intent intent = new Intent(SplashActivity.this, nextActivity);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish(); // Menutup SplashActivity setelah navigasi
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideSystemUI();
    }

    private void hideSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
}
