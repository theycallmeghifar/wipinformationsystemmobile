package id.co.fim.wipinformationsystemmobile.activities;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
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
import id.co.fim.wipinformationsystemmobile.adapter.ListViewItemAdapter;
import id.co.fim.wipinformationsystemmobile.model.ItemInBox;
import id.co.fim.wipinformationsystemmobile.responses.ApiEndPoint;
import id.co.fim.wipinformationsystemmobile.responses.ItemInBoxResponse;
import id.co.fim.wipinformationsystemmobile.responses.LocationResponse;
import id.co.fim.wipinformationsystemmobile.responses.StatusResponse;
import id.co.fim.wipinformationsystemmobile.services.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransferActivity extends AppCompatActivity {
    private SharedPreferences loginPref;
    private SharedPreferences boxInfoPref;
    private SharedPreferences locationPref;
    private SharedPreferences scanTypePref;
    private TextView tvTitle;
    private TextView tvPreScan;
    private CardView blueCard;
    private ImageView btnBack;
    private ImageView imgPreScan;
    private EditText etBoxCode;
    private EditText etCurrentLocation;
    private EditText etDestinationLocation;
    private TextView tvNumber;
    private TextView tvStack;
    private Spinner spnNumber;
    private Spinner spnStack;
    private ListView lvItemInBox;
    private Button btnScanBox;
    private Button btnScanLocation;
    private Button btnTransfer;
    private ProgressBar progressBar;

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
        tvTitle = findViewById(R.id.tvTitle);
        imgPreScan = findViewById(R.id.imgPreScan);
        tvPreScan = findViewById(R.id.tvPreScan);
        etBoxCode = findViewById(R.id.etBoxCode);
        etCurrentLocation = findViewById(R.id.etCurrentLocation);
        etDestinationLocation = findViewById(R.id.etDestinationLocation);
        tvNumber = findViewById(R.id.tvNumber);
        tvStack = findViewById(R.id.tvStack);
        spnNumber = findViewById(R.id.spnNumber);
        spnStack = findViewById(R.id.spnStack);
        blueCard = findViewById(R.id.blueCard);
        lvItemInBox = findViewById(R.id.lvItemInBox);
        btnScanBox = findViewById(R.id.btnScanBox);
        btnScanLocation = findViewById(R.id.btnScanLocation);
        btnTransfer = findViewById(R.id.btnTransfer);
        progressBar = findViewById(R.id.progressBar);

        // Tampilkan loading
        progressBar.setVisibility(View.VISIBLE);

        //inisiasi preference data
        loginPref = getSharedPreferences("loginPref",MODE_PRIVATE);
        boxInfoPref = getSharedPreferences("transferBoxInfo",MODE_PRIVATE);
        locationPref = getSharedPreferences("transferLocation", MODE_PRIVATE);
        scanTypePref = getSharedPreferences("scanType", MODE_PRIVATE);
        SharedPreferences.Editor editor = scanTypePref.edit();
        editor.putString("type", "");
        editor.putString("transaction", "transfer");
        editor.apply();

        //ambil data lokasi box saat ini
        if (boxInfoPref.getString("area", "").equals("")) {
            getLocation(boxInfoPref.getInt("locationId", 0));
        }

        //ambil data itemInBox
        if(itemInBoxList.isEmpty()) {
            getItemInBox(boxInfoPref.getInt("wipBoxId", 0));
        }

        //hapus elemen halaman default
        clearPage();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
                if (boxInfoPref.getInt("status", 0) == 0) {
                    //saat box belum di scan
                    imgPreScan.setVisibility(View.VISIBLE);
                    tvPreScan.setVisibility(View.VISIBLE);
                    btnScanBox.setVisibility(View.VISIBLE);
                } else if (boxInfoPref.getInt("status", 0) == 1) {
                    //saat box sudah di scan dan status box siap produksi
                    if (boxInfoPref.getInt("destinationLocationId", 0) == 0) {
                        // sebelum scan lokasi tujuan
                        tvTitle.setVisibility(View.VISIBLE);
                        findViewById(R.id.textInputBoxCode).setVisibility(View.VISIBLE);
                        findViewById(R.id.textInputCurrentLocation).setVisibility(View.VISIBLE);
                        findViewById(R.id.textInputDestinationLocation).setVisibility(View.VISIBLE);
                        btnScanLocation.setVisibility(View.VISIBLE);
                        blueCard.setVisibility(View.VISIBLE);
                        etBoxCode.setVisibility(View.VISIBLE);
                        etCurrentLocation.setVisibility(View.VISIBLE);
                        etDestinationLocation.setVisibility(View.VISIBLE);

                        etBoxCode.setText(boxInfoPref.getString("boxCode", ""));
                        etCurrentLocation.setText(boxInfoPref.getString("area", "") + " " + boxInfoPref.getString("line", "") +
                                (boxInfoPref.getInt("wipLineNumber", 0) == 0 ? "" : " No. " + boxInfoPref.getInt("wipLineNumber", 0)) +
                                (boxInfoPref.getInt("stack", 0) == 0 ? "" : " Tumpukan " + boxInfoPref.getInt("stack", 0)));
                    } else {
                        //setelah lokasi tujuan di scan
                        if (boxInfoPref.getString("area", "").equals("Finishing") && locationPref.getString("area", "").equals("WIP")) {
                            //lokasi tujuan WIP
                            tvTitle.setVisibility(View.VISIBLE);
                            btnTransfer.setVisibility(View.VISIBLE);
                            findViewById(R.id.textInputBoxCode).setVisibility(View.VISIBLE);
                            findViewById(R.id.textInputCurrentLocation).setVisibility(View.VISIBLE);
                            findViewById(R.id.textInputDestinationLocation).setVisibility(View.VISIBLE);
                            blueCard.setVisibility(View.VISIBLE);
                            tvNumber.setVisibility(View.VISIBLE);
                            tvStack.setVisibility(View.VISIBLE);
                            spnNumber.setVisibility(View.VISIBLE);
                            spnStack.setVisibility(View.VISIBLE);
                            etBoxCode.setVisibility(View.VISIBLE);
                            etCurrentLocation.setVisibility(View.VISIBLE);
                            etDestinationLocation.setVisibility(View.VISIBLE);

                            //inisiasi spinner
                            Integer[] numberOptions = {
                                    1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
                                    11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
                                    21, 22, 23, 24, 25, 26, 27, 28, 29, 30};
                            Integer[] stackOptions = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
                            ArrayAdapter<Integer> numberAdapter = new ArrayAdapter<>(
                                    TransferActivity.this,
                                    R.layout.spinner_item,
                                    numberOptions
                            );
                            numberAdapter.setDropDownViewResource(R.layout.spinner_option);
                            spnNumber.setAdapter(numberAdapter);

                            ArrayAdapter<Integer> stackAdapter = new ArrayAdapter<>(
                                    TransferActivity.this,
                                    R.layout.spinner_item,
                                    stackOptions
                            );
                            stackAdapter.setDropDownViewResource(R.layout.spinner_option);
                            spnStack.setAdapter(stackAdapter);

                            etBoxCode.setText(boxInfoPref.getString("boxCode", ""));
                            etCurrentLocation.setText(boxInfoPref.getString("area", "") + " " + boxInfoPref.getString("line", ""));
                            etDestinationLocation.setText(locationPref.getString("area", "") + " " + locationPref.getString("line", ""));
                        } else if (boxInfoPref.getString("area", "").equals("WIP") && locationPref.getString("area", "").equals("Machining")) {
                            //lokasi tujuan machining
                            tvTitle.setVisibility(View.VISIBLE);
                            btnTransfer.setVisibility(View.VISIBLE);
                            findViewById(R.id.textInputBoxCode).setVisibility(View.VISIBLE);
                            findViewById(R.id.textInputCurrentLocation).setVisibility(View.VISIBLE);
                            findViewById(R.id.textInputDestinationLocation).setVisibility(View.VISIBLE);
                            blueCard.setVisibility(View.VISIBLE);
                            etBoxCode.setVisibility(View.VISIBLE);
                            etCurrentLocation.setVisibility(View.VISIBLE);
                            etDestinationLocation.setVisibility(View.VISIBLE);

                            etBoxCode.setText(boxInfoPref.getString("boxCode", ""));
                            etCurrentLocation.setText(boxInfoPref.getString("area", "") + " " + boxInfoPref.getString("line", "") +
                                    " No. " + boxInfoPref.getInt("wipLineNumber", 0) + " Tumpukan " + boxInfoPref.getInt("stack", 0));
                            etDestinationLocation.setText(locationPref.getString("area", "") + " " + locationPref.getString("line", ""));

                            //set stack = 0
                            SharedPreferences.Editor editor = scanTypePref.edit();
                            editor = boxInfoPref.edit();
                            editor.putInt("stack", 0);
                            editor.apply();
                        } else {
                            //salah lokasi tujuan
                            showAlert(SweetAlertDialog.WARNING_TYPE, "Peringatan", "Tidak dapat melakukan transfer dari " + boxInfoPref.getString("area", "") + " ke " + locationPref.getString("area", ""));

                            getItemInBox(boxInfoPref.getInt("wipBoxId", 0));

                            tvTitle.setVisibility(View.VISIBLE);
                            findViewById(R.id.textInputBoxCode).setVisibility(View.VISIBLE);
                            findViewById(R.id.textInputCurrentLocation).setVisibility(View.VISIBLE);
                            findViewById(R.id.textInputDestinationLocation).setVisibility(View.VISIBLE);
                            etBoxCode.setVisibility(View.VISIBLE);
                            etCurrentLocation.setVisibility(View.VISIBLE);
                            etDestinationLocation.setVisibility(View.VISIBLE);
                            blueCard.setVisibility(View.VISIBLE);
                            btnScanLocation.setVisibility(View.VISIBLE);

                            etBoxCode.setText(boxInfoPref.getString("boxCode", ""));
                            etCurrentLocation.setText(boxInfoPref.getString("area", "") + " " + boxInfoPref.getString("line", "") +
                                    (boxInfoPref.getInt("wipLineNumber", 0) == 0 ? "" : " No. " + boxInfoPref.getInt("wipLineNumber", 0)) +
                                    (boxInfoPref.getInt("stack", 0) == 0 ? "" : " Tumpukan " + boxInfoPref.getInt("stack", 0)));
                        }
                    }
                } else {
                    scanBoxPage();
                    if (boxInfoPref.getInt("status", 0) == 4) {
                        new SweetAlertDialog(TransferActivity.this, SweetAlertDialog.WARNING_TYPE)
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
                    } else if (boxInfoPref.getInt("status", 0) == 3 || boxInfoPref.getInt("status", 0) == 0) {
                        new SweetAlertDialog(TransferActivity.this, SweetAlertDialog.WARNING_TYPE)
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
                    } else if (boxInfoPref.getInt("status", 0) == 2) {
                        new SweetAlertDialog(TransferActivity.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Peringatan")
                                .setContentText("Box sedang dalam proses machining.")
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
            }
        }, 1000);

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
                        ListViewItemAdapter adapter = new ListViewItemAdapter(TransferActivity.this, itemInBoxList);
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
        int status = (locationPref.getString("area", "").equals("WIP")) ? 1 : 2;

        int wipBoxId = boxInfoPref.getInt("wipBoxId", 0);
        int locationId = locationPref.getInt("locationId", 0);

        if (wipBoxId == 0 || locationId == 0) {
            showAlert(SweetAlertDialog.ERROR_TYPE, "Error", "Data tidak lengkap untuk transfer box.");
            return;
        }



        ApiEndPoint apiEndPoint = ApiClient.getClient().create(ApiEndPoint.class);
        Call<StatusResponse> call = apiEndPoint.transferBox(wipBoxId, locationId, selectedNumber, selectedStack, modifiedBy, status);

        call.enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getResponses()) {
                    if (!isFinishing() && !isDestroyed()) {
                        new SweetAlertDialog(TransferActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Berhasil")
                                .setContentText("Berhasil transfer box " + boxInfoPref.getString("boxCode", ""))
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
                    showAlert(SweetAlertDialog.ERROR_TYPE, "Error", "Terjadi kesalahan saat transfer box " + boxInfoPref.getString("boxCode", ""));
                    Toast.makeText(getApplicationContext(), "Terjadi kesalahan saat transfer box " + boxInfoPref.getString("boxCode", ""), Toast.LENGTH_SHORT).show();
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

    public void scanBoxPage () {
        imgPreScan.setVisibility(View.VISIBLE);
        tvPreScan.setVisibility(View.VISIBLE);
        findViewById(R.id.textInputBoxCode).setVisibility(View.INVISIBLE);
        findViewById(R.id.textInputCurrentLocation).setVisibility(View.INVISIBLE);
        findViewById(R.id.textInputDestinationLocation).setVisibility(View.INVISIBLE);
        etBoxCode.setVisibility(View.INVISIBLE);
        etCurrentLocation.setVisibility(View.INVISIBLE);
        etDestinationLocation.setVisibility(View.INVISIBLE);
        tvNumber.setVisibility(View.INVISIBLE);
        tvStack.setVisibility(View.INVISIBLE);
        spnNumber.setVisibility(View.INVISIBLE);
        spnStack.setVisibility(View.INVISIBLE);
        blueCard.setVisibility(View.INVISIBLE);
        btnScanBox.setVisibility(View.VISIBLE);
        btnScanLocation.setVisibility(View.INVISIBLE);
        btnTransfer.setVisibility(View.INVISIBLE);
    }

    public void clearPage () {
        tvTitle.setVisibility(View.INVISIBLE);
        imgPreScan.setVisibility(View.INVISIBLE);
        tvPreScan.setVisibility(View.INVISIBLE);
        findViewById(R.id.textInputBoxCode).setVisibility(View.INVISIBLE);
        findViewById(R.id.textInputCurrentLocation).setVisibility(View.INVISIBLE);
        findViewById(R.id.textInputDestinationLocation).setVisibility(View.INVISIBLE);
        etBoxCode.setVisibility(View.INVISIBLE);
        etCurrentLocation.setVisibility(View.INVISIBLE);
        etDestinationLocation.setVisibility(View.INVISIBLE);
        tvNumber.setVisibility(View.INVISIBLE);
        tvStack.setVisibility(View.INVISIBLE);
        spnNumber.setVisibility(View.INVISIBLE);
        spnStack.setVisibility(View.INVISIBLE);
        blueCard.setVisibility(View.INVISIBLE);
        btnScanBox.setVisibility(View.INVISIBLE);
        btnScanLocation.setVisibility(View.INVISIBLE);
        btnTransfer.setVisibility(View.INVISIBLE);
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
