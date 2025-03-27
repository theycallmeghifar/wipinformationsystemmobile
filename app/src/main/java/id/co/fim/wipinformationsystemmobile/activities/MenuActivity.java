package id.co.fim.wipinformationsystemmobile.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import cn.pedant.SweetAlert.SweetAlertDialog;
import id.co.fim.wipinformationsystemmobile.R;

public class MenuActivity extends AppCompatActivity {
    private SharedPreferences loginPref;
    private ImageView imgMenu;
    private CardView cardTransfer;
    private CardView cardRetur;
    private CardView cardPacking;
    private CardView cardChangeType;
    private CardView cardPending;
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
        cardPacking = findViewById(R.id.cardViewPacking);

        //set imgWip
        if (role == 1) {
            imgMenu.setImageResource(R.drawable.wip);
        } else if (role == 2) {
            imgMenu.setImageResource(R.drawable.mc);
        } else {
            imgMenu.setImageResource(R.drawable.fn);
        }

        //set menu berdasarkan role
        if (role == 1) {
            cardPacking.setVisibility(View.GONE);
        } else if (role == 2) {
            cardTransfer.setVisibility(View.GONE);
            cardPacking.setVisibility(View.GONE);
        } else if (role == 3) {
            cardTransfer.setVisibility(View.GONE);
            cardRetur.setVisibility(View.GONE);
            cardClear.setVisibility(View.GONE);
        }

        //eventlistener
        if (role == 1) {
            cardTransfer.setOnClickListener(v -> {
                startActivity(new Intent(MenuActivity.this, TransferActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            });
        }

        if (role == 3) {
            cardPacking.setOnClickListener(v -> {
                startActivity(new Intent(MenuActivity.this, PackingActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            });
        }

        cardRetur.setOnClickListener(v -> {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MenuActivity.this);
            View view = LayoutInflater.from(MenuActivity.this).inflate(R.layout.retur_dialog, null);
            bottomSheetDialog.setContentView(view);
            view.setBackgroundResource(R.drawable.bottom_dialog_background);

            // Gunakan view.findViewById() untuk mencari komponen dalam bottomSheetDialog
            CardView cardChangeType = view.findViewById(R.id.cardViewChangeType);
            CardView cardPending = view.findViewById(R.id.cardViewPending);

            if (cardChangeType != null) {
                cardChangeType.setOnClickListener(v1 -> {
                    startActivity(new Intent(MenuActivity.this, ChangeTypeActivity.class));
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    bottomSheetDialog.dismiss();
                });
            } else {
                Log.e("MenuActivity", "cardChangeType is null");
            }

            if (cardPending != null) {
                cardPending.setOnClickListener(v1 -> {
                    startActivity(new Intent(MenuActivity.this, PendingActivity.class));
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    bottomSheetDialog.dismiss();
                });
            } else {
                Log.e("MenuActivity", "cardPending is null");
            }

            bottomSheetDialog.show();

            bottomSheetDialog.setOnDismissListener(dialogInterface -> {
                // Opsional: tambahkan aksi saat dialog ditutup
            });
        });

        cardClear.setOnClickListener(v -> {
            startActivity(new Intent(MenuActivity.this, ClearActivity.class));
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
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
