package id.co.fim.wipinformationsystemmobile.activities;

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
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import id.co.fim.wipinformationsystemmobile.APIResponseCallback;
import id.co.fim.wipinformationsystemmobile.R;
import id.co.fim.wipinformationsystemmobile.adapter.EditListViewItemAdapter;
import id.co.fim.wipinformationsystemmobile.adapter.ListViewItemAdapter;
import id.co.fim.wipinformationsystemmobile.model.ItemInBox;
import id.co.fim.wipinformationsystemmobile.request.EditWipBoxDetail;
import id.co.fim.wipinformationsystemmobile.responses.ApiEndPoint;
import id.co.fim.wipinformationsystemmobile.responses.ItemInBoxResponse;
import id.co.fim.wipinformationsystemmobile.responses.LocationResponse;
import id.co.fim.wipinformationsystemmobile.responses.StatusResponse;
import id.co.fim.wipinformationsystemmobile.services.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClearActivity extends AppCompatActivity {
    private SharedPreferences loginPref;
    private SharedPreferences boxInfoPref;
    private SharedPreferences locationPref;
    private SharedPreferences scanTypePref;
    private CardView blueCard;
    private ImageView btnBack;
    private TextView tvTitle;
    private TextView tvPreScan;
    private ImageView imgPreScan;
    private EditText etBoxCode;
    private EditText etCurrentLocation;
    private ListView lvItemInBox;
    private Button btnScanBox;
    private Button btnClear;
    private ProgressBar progressBar;

    private EditListViewItemAdapter adapter;
    private List<ItemInBox> itemInBoxList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear);

        //inisiasi objek pada view
        btnBack = findViewById(R.id.btnBack);
        tvTitle = findViewById(R.id.tvTitle);
        imgPreScan = findViewById(R.id.imgPreScan);
        tvPreScan = findViewById(R.id.tvPreScan);
        etBoxCode = findViewById(R.id.etBoxCode);
        etCurrentLocation = findViewById(R.id.etCurrentLocation);
        blueCard = findViewById(R.id.blueCard);
        lvItemInBox = findViewById(R.id.lvItemInBox);
        btnScanBox = findViewById(R.id.btnScanBox);
        btnClear = findViewById(R.id.btnClear);
        progressBar = findViewById(R.id.progressBar);

        // Tampilkan loading
        progressBar.setVisibility(View.VISIBLE);

        //inisiasi preference data
        loginPref = getSharedPreferences("loginPref",MODE_PRIVATE);
        boxInfoPref = getSharedPreferences("clearBoxInfo",MODE_PRIVATE);
        locationPref = getSharedPreferences("clearLocation", MODE_PRIVATE);
        scanTypePref = getSharedPreferences("scanType", MODE_PRIVATE);
        SharedPreferences.Editor editor = scanTypePref.edit();
        editor.putString("type", "");
        editor.putString("transaction", "clear");
        editor.apply();

        //ambil data lokasi box saat ini
        if (boxInfoPref.getString("area", "").equals("")){
           getLocation(boxInfoPref.getInt("locationId", 0));
        }

        //ambil data lokasi finishing
        if (locationPref.getInt("locationId", 0) == 0){
            getFinishingLocation("Finishing");
        }

        //ambil data itemInBox
        if(itemInBoxList.isEmpty()) {
            getItemInBox(boxInfoPref.getInt("wipBoxId", 0));
        }

        //hapus elemen halaman default
        clearPage();

        //logic tampilan dan data pada halaman
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
                if (boxInfoPref.getInt("status", 0) == 0) {
                    //saat box belum di scan
                    imgPreScan.setVisibility(View.VISIBLE);
                    tvPreScan.setVisibility(View.VISIBLE);
                    btnScanBox.setVisibility(View.VISIBLE);
                } else if (boxInfoPref.getInt("status", 0) == 2) {
                    //saat box sudah di scan dan status box sedang dalam produksi
                    tvTitle.setVisibility(View.VISIBLE);
                    findViewById(R.id.textInputBoxCode).setVisibility(View.VISIBLE);
                    findViewById(R.id.textInputCurrentLocation).setVisibility(View.VISIBLE);
                    blueCard.setVisibility(View.VISIBLE);
                    etBoxCode.setVisibility(View.VISIBLE);
                    etCurrentLocation.setVisibility(View.VISIBLE);
                    btnClear.setVisibility(View.VISIBLE);

                    etBoxCode.setText(boxInfoPref.getString("boxCode", ""));
                    etCurrentLocation.setText(boxInfoPref.getString("area", "") +
                            " " + boxInfoPref.getString("line", ""));
                } else if (boxInfoPref.getInt("status", 0) == 3) {
                    scanBoxPage();
                    new SweetAlertDialog(ClearActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Peringatan")
                            .setContentText("Box masih kosong.")
                            .setConfirmText("Ok")
                            .setConfirmButtonBackgroundColor(Color.parseColor("#121481"))
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                    clearPref();
                                }
                            })
                            .show();
                } else if (boxInfoPref.getInt("status", 0) == 4) {
                    scanBoxPage();
                    new SweetAlertDialog(ClearActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Peringatan")
                            .setContentText("Box sedang di pending.")
                            .setConfirmText("Ok")
                            .setConfirmButtonBackgroundColor(Color.parseColor("#121481"))
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                    clearPref();
                                }
                            })
                            .show();
                } else {
                    scanBoxPage();
                    new SweetAlertDialog(ClearActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Peringatan")
                            .setContentText("Box belum masuk proses machining.")
                            .setConfirmText("Ok")
                            .setConfirmButtonBackgroundColor(Color.parseColor("#121481"))
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                    clearPref();
                                }
                            })
                            .show();
                }
            }
        }, 1000);

        //eventlistener
        btnBack.setOnClickListener(v -> {
            confirmationMessageCancel();
        });

        btnScanBox.setOnClickListener(v -> {
            scanTypePref.edit().putString("type", "box").apply();
            startActivity(new Intent(ClearActivity.this, ScanActivity.class));
            finish();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });

        btnClear.setOnClickListener(v -> {
            if (locationPref.getInt("locationId", 0) == 0) {
                showAlert(SweetAlertDialog.WARNING_TYPE, "Peringatan", "Tidak ada data lokasi untuk clear box ini");
            } else {
                clear(new APIResponseCallback() {
                    @Override
                    public void onResult(boolean isTrue) {
                        if (isTrue) {
                            editWipBoxDetail();
                        }
                    }
                });
            }
        });
    }

    public void getLocation(int locationId) {
        ApiEndPoint apiEndPoint = ApiClient.getClient().create(ApiEndPoint.class);
        Call<LocationResponse> call = apiEndPoint.getLocation(locationId);

        call.enqueue(new Callback<LocationResponse>() {
            @Override
            public void onResponse(Call<LocationResponse> call, Response<LocationResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getResponses()) {
                    LocationResponse locationResponse = response.body();
                    SharedPreferences.Editor editor = boxInfoPref.edit();
                    editor.putString("area", locationResponse.getArea());
                    editor.putString("line", locationResponse.getLine());
                    editor.apply();
                }
            }

            @Override
            public void onFailure(Call<LocationResponse> call, Throwable t) {
                Log.e("API_ERROR", "Error: " + t.getMessage(), t); // Tambahkan log error

                final Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                    }
                }, 6000);
            }
        });
    }

    public void getFinishingLocation(String area) {
        ApiEndPoint apiEndPoint = ApiClient.getClient().create(ApiEndPoint.class);
        Call<LocationResponse> call = apiEndPoint.getFinishingLocation(area);

        call.enqueue(new Callback<LocationResponse>() {
            @Override
            public void onResponse(Call<LocationResponse> call, Response<LocationResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getResponses()) {
                    LocationResponse locationResponse = response.body();
                    SharedPreferences.Editor editor = locationPref.edit();
                    editor.putInt("locationId", locationResponse.getLocationId());
                    editor.putString("area", locationResponse.getArea());
                    editor.putString("line", locationResponse.getLine());
                    editor.apply();
                }
            }

            @Override
            public void onFailure(Call<LocationResponse> call, Throwable t) {
                Log.e("API_ERROR", "Error: " + t.getMessage(), t); // Tambahkan log error

                final Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                    }
                }, 6000);
            }
        });
    }

    public void getItemInBox(int wipBoxId) {
        ApiEndPoint apiEndPoint = ApiClient.getClient().create(ApiEndPoint.class);
        Call<ItemInBoxResponse> call = apiEndPoint.getItemInBox(wipBoxId);

        call.enqueue(new Callback<ItemInBoxResponse>() {
            @Override
            public void onResponse(Call<ItemInBoxResponse> call, Response<ItemInBoxResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getResponses()) {
                    ItemInBoxResponse itemInBoxResponse = response.body();

                    itemInBoxList.clear();
                    for (ItemInBox item : itemInBoxResponse.getData()) {
                        itemInBoxList.add(new ItemInBox(item.getItemCode(), item.getItemName(), item.getQuantity()));
                    }

                    if (lvItemInBox != null) {
                        ListViewItemAdapter adapter = new ListViewItemAdapter(ClearActivity.this, itemInBoxList);
                        lvItemInBox.setAdapter(adapter);
                    } else {
                        Log.e("UI_ERROR", "ListView lvItemInBox tidak ditemukan");
                    }
                }
            }

            @Override
            public void onFailure(Call<ItemInBoxResponse> call, Throwable t) {
                Log.e("API_ERROR", "Error: " + t.getMessage(), t); // Tambahkan log error

                final Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                    }
                }, 6000);
            }
        });
    }

    public void clear(APIResponseCallback callback) {
        String modifiedBy = (loginPref.getInt("role", 0) == 1) ? "wip" : "mc";
        ApiEndPoint apiEndPoint = ApiClient.getClient().create(ApiEndPoint.class);
        Call<StatusResponse> call = apiEndPoint.clear(boxInfoPref.getInt("wipBoxId", 0), locationPref.getInt("locationId", 0), 0, 0, modifiedBy, 3);
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
                    showAlert(SweetAlertDialog.ERROR_TYPE, "Error", "Terjadi kesalahan saat clear box " + boxInfoPref.getString("boxCode", ""));
                    Toast.makeText(getApplicationContext(), "Terjadi kesalahan saat clear box " + boxInfoPref.getString("boxCode", ""), Toast.LENGTH_SHORT).show();
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
                        clear(callback);
                    }
                }, 6000);
            }
        });
    }

    public void editWipBoxDetail() {
        for (int i = 0; i < itemInBoxList.size(); i++) {
            itemInBoxList.get(i).setQuantity(0);
        }
        String modifiedBy = (loginPref.getInt("role", 0) == 1) ? "wip" : "mc";
        EditWipBoxDetail request = new EditWipBoxDetail(boxInfoPref.getInt("wipBoxId", 0), itemInBoxList);

        // Log request sebelum dikirim ke API
        Gson gson = new Gson();
        Log.d("REQUEST_BODY", gson.toJson(request));

        ApiEndPoint apiEndPoint = ApiClient.getClient().create(ApiEndPoint.class);
        Call<StatusResponse> call = apiEndPoint.editWipBoxDetail(request);

        call.enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                Log.d("API_RESPONSE", "HTTP Code: " + response.code());
                try {
                    // Log response yang diterima
                    String responseBody = response.body() != null ? gson.toJson(response.body()) : "NULL";
                    Log.d("API_RESPONSE", "Response Body: " + responseBody);

                    new SweetAlertDialog(ClearActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Berhasil")
                            .setContentText("Berhasil clear box " + boxInfoPref.getString("boxCode", ""))
                            .setConfirmText("Ok")
                            .setConfirmButtonBackgroundColor(Color.parseColor("#121481"))
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    clearPref();
                                    sDialog.dismissWithAnimation();
                                    startActivity(new Intent(ClearActivity.this, MenuActivity.class));
                                    finish();
                                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                }
                            })
                            .show();

                    if (!response.isSuccessful()) {
                        String errorBody = response.errorBody() != null ? response.errorBody().string() : "NULL";
                        Log.e("API_ERROR", "Error Body: " + errorBody);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {
                Log.e("API_ERROR", "Error: " + t.getMessage(), t);
                showAlert(SweetAlertDialog.ERROR_TYPE, "Kesalahan Jaringan", "Gagal menghubungi server. Periksa koneksi internet Anda.");

                final Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(() -> editWipBoxDetail(), 6000);
            }
        });
    }

    public void showAlert (int type, String title, String contentText) {
        if (isFinishing()) return;

        new SweetAlertDialog(ClearActivity.this, type)
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
        new SweetAlertDialog(ClearActivity.this, SweetAlertDialog.WARNING_TYPE)
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

                        startActivity(new Intent(ClearActivity.this, MenuActivity.class));
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

    public void scanBoxPage () {
        imgPreScan.setVisibility(View.VISIBLE);
        tvPreScan.setVisibility(View.VISIBLE);
        findViewById(R.id.textInputBoxCode).setVisibility(View.INVISIBLE);
        findViewById(R.id.textInputCurrentLocation).setVisibility(View.INVISIBLE);
        etBoxCode.setVisibility(View.INVISIBLE);
        etCurrentLocation.setVisibility(View.INVISIBLE);
        blueCard.setVisibility(View.INVISIBLE);
        btnScanBox.setVisibility(View.VISIBLE);
        btnClear.setVisibility(View.INVISIBLE);
    }

    public void clearPage () {
        tvTitle.setVisibility(View.INVISIBLE);
        imgPreScan.setVisibility(View.INVISIBLE);
        tvPreScan.setVisibility(View.INVISIBLE);
        findViewById(R.id.textInputBoxCode).setVisibility(View.INVISIBLE);
        findViewById(R.id.textInputCurrentLocation).setVisibility(View.INVISIBLE);
        etBoxCode.setVisibility(View.INVISIBLE);
        etCurrentLocation.setVisibility(View.INVISIBLE);
        blueCard.setVisibility(View.INVISIBLE);
        btnScanBox.setVisibility(View.INVISIBLE);
        btnClear.setVisibility(View.INVISIBLE);
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

    @Override
    public void onBackPressed()
    {
        confirmationMessageCancel();
    }
}
