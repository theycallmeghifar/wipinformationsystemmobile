package id.co.fim.wipinformationsystemmobile.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import cn.pedant.SweetAlert.SweetAlertDialog;
import id.co.fim.wipinformationsystemmobile.R;

public class MenuActivity extends AppCompatActivity {
    private SharedPreferences prefLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        prefLogin = getSharedPreferences("loginPref",MODE_PRIVATE);
    }

    @Override
    public void onBackPressed() {
        new SweetAlertDialog(MenuActivity.this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Peringatan")
                .setContentText("Apakah anda ingin sign out?")
                .setConfirmText("Ya")
                .setConfirmButtonBackgroundColor(Color.parseColor("#003c8f"))
                .setCancelButtonBackgroundColor(Color.parseColor("#003c8f"))
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();

                        SharedPreferences.Editor editor = prefLogin.edit();
                        editor.putString("login", "0");

                        editor.commit();

                        startActivity(new Intent(MenuActivity.this, LoginActivity.class));
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }
                })
                .setCancelButton("Tidak", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                })
                .show();
    }
}
