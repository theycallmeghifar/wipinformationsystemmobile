package id.co.fim.wipinformationsystemmobile.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import id.co.fim.wipinformationsystemmobile.R;

public class MenuActivity extends AppCompatActivity {
    private SharedPreferences prefLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        prefLogin = getSharedPreferences("loginPref",MODE_PRIVATE);

    }
}
