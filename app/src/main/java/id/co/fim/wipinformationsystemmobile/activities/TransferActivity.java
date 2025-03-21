package id.co.fim.wipinformationsystemmobile.activities;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;
import static android.webkit.ConsoleMessage.MessageLevel.WARNING;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import id.co.fim.wipinformationsystemmobile.APIResponseCallback;
import id.co.fim.wipinformationsystemmobile.R;
import id.co.fim.wipinformationsystemmobile.adapter.ItemInBoxAdapter;
import id.co.fim.wipinformationsystemmobile.model.ItemInBox;
import id.co.fim.wipinformationsystemmobile.responses.ApiEndPoint;
import id.co.fim.wipinformationsystemmobile.responses.BoxInfoResponse;
import id.co.fim.wipinformationsystemmobile.responses.ItemInBoxResponse;
import id.co.fim.wipinformationsystemmobile.responses.LocationResponse;
import id.co.fim.wipinformationsystemmobile.responses.StatusResponse;
import id.co.fim.wipinformationsystemmobile.services.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransferActivity extends AppCompatActivity {
    private SharedPreferences pref;
    private SharedPreferences loginPref;
    private SharedPreferences locationPref;
    private SharedPreferences scanTypePref;
    private CardView blueCard;
    private ImageView btnBack;
    private ImageView imgPreScan;
    private TextView tvBoxCode;
    private TextView tvCurrentLocation;
    private TextView tvDestinationLocation;
    public void getLocation(int locationId) {
        ApiEndPoint apiEndPoint = ApiClient.getClient().create(ApiEndPoint.class);
        Call<LocationResponse> call = apiEndPoint.getLocation(locationId);

        call.enqueue(new Callback<LocationResponse>() {
            @Override
            public void onResponse(Call<LocationResponse> call, Response<LocationResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getResponses()) {
                    LocationResponse locationResponse = response.body();
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("currentArea", locationResponse.getArea());
                    editor.putString("currentLine", locationResponse.getLine());
                    editor.apply();
                } else {

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

    private TextView tvNumber;
    private Spinner spnNumber;
    private TextView tvStack;
    private Spinner spnStack;
    private ListView lvItemInBox;
    private Button btnScanBox;
    private Button btnScanLocation;
    private Button btnTransfer;

    private List<ItemInBox> itemInBoxList = new ArrayList<>();
    private int selectedNumber;
    private int selectedStack;
    private int clashStack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

        //inisiasi objek pada view
        btnBack = findViewById(R.id.btnBack);
        btnScanBox = findViewById(R.id.btnScanBox);
        btnScanLocation = findViewById(R.id.btnScanLocation);
        btnTransfer = findViewById(R.id.btnTransfer);
        imgPreScan = findViewById(R.id.imgPreScan);
        blueCard = findViewById(R.id.blueCard);
        tvBoxCode = findViewById(R.id.tvBoxCode);
        tvCurrentLocation = findViewById(R.id.tvCurrentLocation);
        tvDestinationLocation = findViewById(R.id.tvDestinationLocation);
        lvItemInBox = findViewById(R.id.lvItemInBox);
        tvNumber = findViewById(R.id.tvNumber);
        spnNumber = findViewById(R.id.spnNumber);
        tvStack = findViewById(R.id.tvStack);
        spnStack = findViewById(R.id.spnStack);

        //inisiasi preference data
        pref = getSharedPreferences("boxInfo",MODE_PRIVATE);
        loginPref = getSharedPreferences("loginPref",MODE_PRIVATE);
        locationPref = getSharedPreferences("location", MODE_PRIVATE);
        scanTypePref = getSharedPreferences("scanType", MODE_PRIVATE);
        SharedPreferences.Editor editor = scanTypePref.edit();
        editor.putString("type", "");
        editor.apply();

        //ambil data lokasi box saat ini
        getLocation(pref.getInt("currentLocationId", 0));

        if (pref.getInt("status", 0) == 0) {
            //button transfer n scan loc hide tampilin gambar perintah scan box
            btnTransfer.setVisibility(View.INVISIBLE);
            btnScanLocation.setVisibility(View.INVISIBLE);
            btnScanBox.setVisibility(View.VISIBLE);
            blueCard.setVisibility(View.INVISIBLE);
            imgPreScan.setVisibility(View.VISIBLE);
        } else if (pref.getInt("status", 0) == 1) {
            if (pref.getInt("destinationLocationId", 0) == 0) {
                getItemInBox(pref.getInt("wipBoxId", 0));

                btnTransfer.setVisibility(View.INVISIBLE);
                btnScanBox.setVisibility(View.INVISIBLE);
                btnScanLocation.setVisibility(View.VISIBLE);
                blueCard.setVisibility(View.VISIBLE);
                imgPreScan.setVisibility(View.INVISIBLE);
                tvNumber.setVisibility(View.INVISIBLE);
                spnNumber.setVisibility(View.INVISIBLE);
                tvStack.setVisibility(View.INVISIBLE);
                spnStack.setVisibility(View.INVISIBLE);

                tvBoxCode.setText("Kode Box: " + pref.getString("boxCode", ""));
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tvCurrentLocation.setText("Lokasi: " + pref.getString("currentArea", "") +
                                " " + pref.getString("currentLine", "") +
                                (pref.getInt("wipLineNumber", 0) == 0 ? "" : " No. " +
                                        pref.getInt("wipLineNumber", 0)) +
                                (pref.getInt("stack", 0) == 0 ? "" : " Tumpukan " +
                                        pref.getInt("stack", 0)));
                    }
                }, 100);
                tvDestinationLocation.setText("Lokasi Tujuan: -");

            } else {
                if (pref.getString("currentArea", "").equals("Finishing") && locationPref.getString("area", "").equals("WIP")) {
                    getItemInBox(pref.getInt("wipBoxId", 0));

                    btnScanBox.setVisibility(View.INVISIBLE);
                    btnScanLocation.setVisibility(View.INVISIBLE);
                    btnTransfer.setVisibility(View.VISIBLE);
                    blueCard.setVisibility(View.VISIBLE);
                    imgPreScan.setVisibility(View.INVISIBLE);

                    //inisiasi stack spinner
                    Integer[] stackOptions = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
                    ArrayAdapter<Integer> adapter = new ArrayAdapter<>(
                            this,
                            R.layout.spinner_item,
                            stackOptions
                    );
                    adapter.setDropDownViewResource(R.layout.spinner_option);
                    spnNumber.setAdapter(adapter);
                    spnStack.setAdapter(adapter);

                    tvNumber.setVisibility(View.VISIBLE);
                    spnNumber.setVisibility(View.VISIBLE);
                    tvStack.setVisibility(View.VISIBLE);
                    spnStack.setVisibility(View.VISIBLE);
                    //set blueCard height
                    ViewGroup.LayoutParams params = blueCard.getLayoutParams();
                    int heightInPx = (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP,
                            500,
                            getResources().getDisplayMetrics()
                    );
                    params.height = heightInPx;
                    blueCard.setLayoutParams(params);

                    tvBoxCode.setText("Kode Box: " + pref.getString("boxCode", ""));

                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            tvCurrentLocation.setText("Lokasi: " + pref.getString("currentArea", "") +
                                    " " + pref.getString("currentLine", "") +
                                    (pref.getInt("wipLineNumber", 0) == 0 ? "" : " No. " +
                                            pref.getInt("wipLineNumber", 0)) +
                                    (pref.getInt("stack", 0) == 0 ? "" : " Tumpukan " +
                                            pref.getInt("stack", 0)));

                            tvDestinationLocation.setText("Lokasi Tujuan: " + locationPref.getString("area", "") +
                                    " " + locationPref.getString("line", ""));
                        }
                    }, 100);

                } else if (pref.getString("currentArea", "").equals("WIP") && locationPref.getString("area", "").equals("Machining")) {
                    getItemInBox(pref.getInt("wipBoxId", 0));

                    btnScanBox.setVisibility(View.INVISIBLE);
                    btnScanLocation.setVisibility(View.INVISIBLE);
                    btnTransfer.setVisibility(View.VISIBLE);
                    blueCard.setVisibility(View.VISIBLE);
                    imgPreScan.setVisibility(View.INVISIBLE);
                    tvNumber.setVisibility(View.INVISIBLE);
                    spnNumber.setVisibility(View.INVISIBLE);
                    tvStack.setVisibility(View.INVISIBLE);
                    spnStack.setVisibility(View.INVISIBLE);

                    tvBoxCode.setText("Kode Box: " + pref.getString("boxCode", ""));
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            tvCurrentLocation.setText("Lokasi: " + pref.getString("currentArea", "") +
                                    " " + pref.getString("currentLine", "") +
                                    (pref.getInt("wipLineNumber", 0) == 0 ? "" : " No. " +
                                            pref.getInt("wipLineNumber", 0)) +
                                    (pref.getInt("stack", 0) == 0 ? "" : " Tumpukan " +
                                            pref.getInt("stack", 0)));

                            tvDestinationLocation.setText("Lokasi Tujuan: " + locationPref.getString("area", "") +
                                    " " + locationPref.getString("line", ""));
                        }
                    }, 100);

                    //set stack = 0
                    editor = pref.edit();
                    editor.putInt("stack", 0);
                    editor.apply();
                } else {
                    showAlert(SweetAlertDialog.WARNING_TYPE, "Peringatan", "Tidak dapat melakukan transfer dari " + pref.getString("currentArea", "") + " ke " + locationPref.getString("area", ""));

                    getItemInBox(pref.getInt("wipBoxId", 0));

                    btnTransfer.setVisibility(View.INVISIBLE);
                    btnScanBox.setVisibility(View.INVISIBLE);
                    btnScanLocation.setVisibility(View.VISIBLE);
                    blueCard.setVisibility(View.VISIBLE);
                    imgPreScan.setVisibility(View.INVISIBLE);
                    tvNumber.setVisibility(View.INVISIBLE);
                    spnNumber.setVisibility(View.INVISIBLE);
                    tvStack.setVisibility(View.INVISIBLE);
                    spnStack.setVisibility(View.INVISIBLE);

                    tvBoxCode.setText("Kode Box: " + pref.getString("boxCode", ""));
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            tvCurrentLocation.setText("Lokasi: " + pref.getString("currentArea", "") +
                                    " " + pref.getString("currentLine", "") +
                                    (pref.getInt("wipLineNumber", 0) == 0 ? "" : " No. " +
                                            pref.getInt("wipLineNumber", 0)) +
                                    (pref.getInt("stack", 0) == 0 ? "" : " Tumpukan " +
                                            pref.getInt("stack", 0)));
                        }
                    }, 100);
                    tvDestinationLocation.setText("Lokasi Tujuan: -");
                }
            }
        } else {
            if (pref.getInt("status", 0) == 4) {
                showAlert(SweetAlertDialog.WARNING_TYPE, "Peringatan", "Box sedang di pending.");
            } else if (pref.getInt("status", 0) == 3) {
                clearPref();
                showAlert(SweetAlertDialog.WARNING_TYPE, "Peringatan", "Box masih kosong.");
            } else if (pref.getInt("status", 0) == 2) {
                showAlert(SweetAlertDialog.WARNING_TYPE, "Peringatan", "Box sedang dalam produksi.");
            }
        }

        //eventlistener
        btnBack.setOnClickListener(v -> {
            confirmationMessageCancel();
        });

        btnScanBox.setOnClickListener(v -> {
            scanTypePref.edit().putString("type", "box").apply();
            startActivity(new Intent(TransferActivity.this, ScanActivity.class));
            finish();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });

        btnScanLocation.setOnClickListener(v -> {
            scanTypePref.edit().putString("type", "location").apply();
            startActivity(new Intent(TransferActivity.this, ScanActivity.class));
            finish();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });

        btnTransfer.setOnClickListener(v -> {
            getBoxOnSameStack(new APIResponseCallback() {
                @Override
                public void onResult(boolean isTrue) {
                    if (isTrue) {
                        Toast.makeText(v.getContext(), "Sudah ada box pada " +
                                locationPref.getString("area", "") + " " +
                                locationPref.getString("line", "") + " No. " +
                                selectedNumber + " Tumpukan " + selectedStack, Toast.LENGTH_SHORT).show();
                    } else {
                        transferBox();
                    }
                }
            });
        });

        spnNumber.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedNumber = (Integer) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedNumber = 0;
            }
        });

        spnStack.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedStack = (Integer) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedStack = 0;
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
                        ItemInBoxAdapter adapter = new ItemInBoxAdapter(TransferActivity.this, itemInBoxList);
                        lvItemInBox.setAdapter(adapter);
                    } else {
                        Log.e("UI_ERROR", "ListView lvItemInBox tidak ditemukan");
                    }


                } else {

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

    public void getBoxOnSameStack(APIResponseCallback callback) {
        ApiEndPoint apiEndPoint = ApiClient.getClient().create(ApiEndPoint.class);
        Call<StatusResponse> call = apiEndPoint.getBoxOnSameStack(locationPref.getInt("locationId", 0), selectedNumber, selectedStack);
        clashStack = 0;
        call.enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onResult(response.body().getResponses());  // Memanggil callback dengan hasil API
                } else {
                    callback.onResult(false); // Jika tidak ada data, callback FALSE
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

                    }
                }, 6000);
            }
        });
    }

    public void transferBox() {
        String modifiedBy = (loginPref.getInt("role", 0) == 1) ? "wip" : "mc";

        int wipBoxId = pref.getInt("wipBoxId", 0);
        int locationId = locationPref.getInt("locationId", 0);

        if (wipBoxId == 0 || locationId == 0) {
            showAlert(SweetAlertDialog.ERROR_TYPE, "Error", "Data tidak lengkap untuk transfer box.");
            return;
        }

        ApiEndPoint apiEndPoint = ApiClient.getClient().create(ApiEndPoint.class);
        Call<StatusResponse> call = apiEndPoint.transferBox(wipBoxId, locationId, selectedNumber, selectedStack, modifiedBy);

        call.enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getResponses()) {
                    if (!isFinishing() && !isDestroyed()) {
                        new SweetAlertDialog(TransferActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Berhasil")
                                .setContentText("Berhasil transfer box " + pref.getString("boxCode", ""))
                                .setConfirmText("Ok")
                                .setConfirmButtonBackgroundColor(Color.parseColor("#121481"))
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        clearPref();
                                        sDialog.dismissWithAnimation();
                                        startActivity(new Intent(TransferActivity.this, MenuActivity.class));
                                        finish();
                                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                    }
                                })
                                .show();
                    }
                } else {
                    Log.e("API_RESPONSE", "Response gagal: " + response.code() + " - " + response.message());
                    showAlert(SweetAlertDialog.ERROR_TYPE, "Error", "Terjadi kesalahan saat transfer box " + pref.getString("boxCode", ""));
                    Toast.makeText(getApplicationContext(), "Terjadi kesalahan saat transfer box " + pref.getString("boxCode", ""), Toast.LENGTH_SHORT).show();
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
                        transferBox(); // Coba ulangi setelah 6 detik
                    }
                }, 6000);
            }
        });
    }

    public void showAlert (int type, String title, String contentText) {
        if (isFinishing()) return;

        btnTransfer.setVisibility(View.INVISIBLE);
        btnScanLocation.setVisibility(View.INVISIBLE);
        btnScanBox.setVisibility(View.VISIBLE);
        blueCard.setVisibility(View.INVISIBLE);
        imgPreScan.setVisibility(View.VISIBLE);
        new SweetAlertDialog(TransferActivity.this, type)
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
        new SweetAlertDialog(TransferActivity.this, SweetAlertDialog.WARNING_TYPE)
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

                        startActivity(new Intent(TransferActivity.this, MenuActivity.class));
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
        SharedPreferences.Editor editor = pref.edit();
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
