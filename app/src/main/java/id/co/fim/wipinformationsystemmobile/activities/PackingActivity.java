package id.co.fim.wipinformationsystemmobile.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.gson.Gson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import id.co.fim.wipinformationsystemmobile.APIResponseCallback;
import id.co.fim.wipinformationsystemmobile.R;
import id.co.fim.wipinformationsystemmobile.adapter.AddListViewItemAdapter;
import id.co.fim.wipinformationsystemmobile.adapter.EditListViewItemAdapter;
import id.co.fim.wipinformationsystemmobile.model.ItemInBox;
import id.co.fim.wipinformationsystemmobile.request.EditWipBoxDetail;
import id.co.fim.wipinformationsystemmobile.responses.ApiEndPoint;
import id.co.fim.wipinformationsystemmobile.responses.ItemInBoxResponse;
import id.co.fim.wipinformationsystemmobile.responses.ItemResponse;
import id.co.fim.wipinformationsystemmobile.responses.LocationResponse;
import id.co.fim.wipinformationsystemmobile.responses.StatusResponse;
import id.co.fim.wipinformationsystemmobile.services.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PackingActivity extends AppCompatActivity {
    private SharedPreferences loginPref;
    private SharedPreferences boxInfoPref;
    private SharedPreferences locationPref;
    private SharedPreferences scanTypePref;
    private CardView blueCard;
    private ImageView btnBack;
    private TextView tvTitle;
    private EditText etBoxCode;
    private EditText etProductionDate;
    private EditText etCavity;
    private ListView lvItem;
    private Button btnAddItem;
    private Button btnSave;
    private ProgressBar progressBar;

    private AddListViewItemAdapter adapter;
    private List<ItemInBox> allItemList = new ArrayList<>();
    private List<ItemInBox> itemList = new ArrayList<>();
    private final Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packing);

        //inisiasi objek pada view
        btnBack = findViewById(R.id.btnBack);
        tvTitle = findViewById(R.id.tvTitle);
        etBoxCode = findViewById(R.id.etBoxCode);
        etProductionDate = findViewById(R.id.etProductionDate);
        etCavity = findViewById(R.id.etCavity);
        blueCard = findViewById(R.id.blueCard);
        lvItem = findViewById(R.id.lvItem);
        btnAddItem = findViewById(R.id.btnAddItem);
        btnSave = findViewById(R.id.btnSave);
        progressBar = findViewById(R.id.progressBar);

        //
        getAllItem();
        adapter = new AddListViewItemAdapter(this, allItemList, new ArrayList<>());
        lvItem.setAdapter(adapter);

        Log.d("Adapter", "itemList size after setting adapter: " + adapter.getCount());
        //

        //inisiasi preference data
        loginPref = getSharedPreferences("loginPref",MODE_PRIVATE);
        boxInfoPref = getSharedPreferences("pendingBoxInfo",MODE_PRIVATE);
        locationPref = getSharedPreferences("pendingLocation", MODE_PRIVATE);
        scanTypePref = getSharedPreferences("scanType", MODE_PRIVATE);
        SharedPreferences.Editor editor = scanTypePref.edit();
        editor.putString("type", "");
        editor.putString("transaction", "pending");
        editor.apply();

        //
        setUpPage(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        //
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.INVISIBLE);
                setUpPage(View.VISIBLE);
            }
        }, 1000);

        //eventlistener
        btnBack.setOnClickListener(v -> {
            confirmationMessageCancel();
        });

        etProductionDate.setOnClickListener(v -> showDatePicker());

        btnAddItem.setOnClickListener(v -> {
            // Buat item baru
            ItemInBox newItem = new ItemInBox();
            newItem.setItemCode(allItemList.get(0).getItemCode());
            newItem.setItemName("");
            newItem.setQuantity(0);

            adapter.addListItemData(newItem);
        });

        btnSave.setOnClickListener(v -> {
            savePacking(new APIResponseCallback() {
                @Override
                public void onResult(boolean isTrue) {
                    if (isTrue) {

                    }
                }
            });
        });
    }

    private void getAllItem() {
        ApiEndPoint apiEndPoint = ApiClient.getClient().create(ApiEndPoint.class);
        Call<ItemResponse> call = apiEndPoint.getAllItem();

        call.enqueue(new Callback<ItemResponse>() {
            @Override
            public void onResponse(Call<ItemResponse> call, Response<ItemResponse> response) {
                if (response.isSuccessful() && response.body() != null) {

                    if (response.body().getResponses()) {
                        allItemList.clear();

                        for (ItemInBox item : response.body().getItems()) {
                            item.setItemCode(item.getItemCode().trim());
                            item.setItemName(item.getItemName().trim());
                            allItemList.add(item);
                        }

                        adapter.updateAllItemListData(allItemList);
                    } else {
                        Toast.makeText(PackingActivity.this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(PackingActivity.this, "Gagal mendapatkan data: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ItemResponse> call, Throwable t) {
                Log.e("API_ERROR", "onFailure: " + t.getMessage(), t);
                Toast.makeText(PackingActivity.this, "Gagal memuat data: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void savePacking(APIResponseCallback callback) {
        String modifiedBy = (loginPref.getInt("role", 0) == 1) ? "wip" : "mc";
        ApiEndPoint apiEndPoint = ApiClient.getClient().create(ApiEndPoint.class);
        Call<StatusResponse> call = apiEndPoint.pending(boxInfoPref.getInt("wipBoxId", 0), locationPref.getInt("locationId", 0), 0, 0, modifiedBy, 4);
        call.enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getResponses()) {
                    if (!isFinishing() && !isDestroyed()) {
                        callback.onResult(response.body().getResponses());
                    }
                } else {
                    callback.onResult(false);
                    Log.e("API_RESPONSE", "Response gagal: " + response.code() + " - " + response.message());
                    showAlert(SweetAlertDialog.ERROR_TYPE, "Error", "Terjadi kesalahan saat retur box " + boxInfoPref.getString("boxCode", ""));
                    Toast.makeText(getApplicationContext(), "Terjadi kesalahan saat retur box " + boxInfoPref.getString("boxCode", ""), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {
                Log.e("API_ERROR", "Error: " + t.getMessage(), t);
                showAlert(SweetAlertDialog.ERROR_TYPE, "Kesalahan Jaringan", "Gagal menghubungi server. Periksa koneksi internet Anda.");

                final Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        savePacking(callback);
                    }
                }, 6000);
            }
        });
    }

    private void showDatePicker() {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    calendar.set(Calendar.YEAR, selectedYear);
                    calendar.set(Calendar.MONTH, selectedMonth);
                    calendar.set(Calendar.DAY_OF_MONTH, selectedDay);
                    updateLabel();
                },
                year, month, day
        );
        datePickerDialog.show();
    }

    private void updateLabel() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        etProductionDate.setText(sdf.format(calendar.getTime()));
    }

    public void showAlert (int type, String title, String contentText) {
        if (isFinishing()) return;

        new SweetAlertDialog(PackingActivity.this, type)
                .setTitleText(title)
                .setContentText(contentText)
                .setConfirmText("Ok")
                .setConfirmButtonBackgroundColor(Color.parseColor("#121481"))
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                })
                .show();
    }

    public void confirmationMessageCancel() {
        new SweetAlertDialog(PackingActivity.this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Peringatan")
                .setContentText("Ingin ke halaman menu?")
                .setConfirmText("Ya")
                .setConfirmButtonBackgroundColor(Color.parseColor("#121481"))
                .setCancelButtonBackgroundColor(Color.parseColor("#db0f15"))
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();

                        clearPref();

                        startActivity(new Intent(PackingActivity.this, MenuActivity.class));
                        finish();
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
    public void clearPref(){
        SharedPreferences.Editor editor = boxInfoPref.edit();
        editor.clear();
        editor.apply();

        editor = locationPref.edit();
        editor.clear();
        editor.apply();

        editor = scanTypePref.edit();
        editor.clear();
        editor.apply();
    }

    public void setUpPage (int set) {
        tvTitle.setVisibility(set);
        findViewById(R.id.textInputBoxCode).setVisibility(set);
        findViewById(R.id.textInputProductionDate).setVisibility(set);
        findViewById(R.id.textInputCavity).setVisibility(set);
        etBoxCode.setVisibility(set);
        etProductionDate.setVisibility(set);
        etCavity.setVisibility(set);
        blueCard.setVisibility(set);
        btnSave.setVisibility(set);
    }

    @Override
    public void onBackPressed()
    {
        confirmationMessageCancel();
    }
}
