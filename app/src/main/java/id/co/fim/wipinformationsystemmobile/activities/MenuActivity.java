package id.co.fim.wipinformationsystemmobile.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import cn.pedant.SweetAlert.SweetAlertDialog;
import id.co.fim.wipinformationsystemmobile.R;

public class MenuActivity extends AppCompatActivity {
    private SharedPreferences loginPref;
    private ImageView imgMenu;
    private CardView cardTransfer;
    private CardView cardRetur;
    private CardView cardClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        loginPref = getSharedPreferences("loginPref",MODE_PRIVATE);
        int role = loginPref.getInt("role", 0);

        //inisiasi objek pada view
        imgMenu = findViewById(R.id.imgMenu);

        cardTransfer = findViewById(R.id.cardViewTransfer);
        cardRetur = findViewById(R.id.cardViewRetur);
        cardClear = findViewById(R.id.cardViewClear);

        //set imgWip
        if (role == 1) {
            imgMenu.setImageResource(R.drawable.wip); // Jika role = 1, gunakan wip
        } else if (role == 2) {
            imgMenu.setImageResource(R.drawable.mc);  // Jika role = 2, gunakan mc
        }

        //set menu berdasarkan role
        if (role == 2) {
            cardTransfer.setVisibility(View.GONE); // Sembunyikan Transfer jika role = 2
        }

        //eventlistener
        cardTransfer.setOnClickListener(v -> {
            startActivity(new Intent(MenuActivity.this, TransferActivity.class));
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
        });

        cardRetur.setOnClickListener(v -> {

        });

        cardClear.setOnClickListener(v -> {

        });
    }

    @Override
    public void onBackPressed() {
        new SweetAlertDialog(MenuActivity.this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Peringatan")
                .setContentText("Apakah anda ingin logout?")
                .setConfirmText("Ya")
                .setConfirmButtonBackgroundColor(Color.parseColor("#121481"))
                .setCancelButtonBackgroundColor(Color.parseColor("#db0f15"))
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();

                        SharedPreferences.Editor editor = loginPref.edit();
                        editor.putString("login", "0");
                        editor.putInt("role", 0);

                        editor.commit();

                        startActivity(new Intent(MenuActivity.this, LoginActivity.class));
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        finish();
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
