package id.co.fim.wipinformationsystemmobile.activities;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

import id.co.fim.wipinformationsystemmobile.R;
import id.co.fim.wipinformationsystemmobile.responses.ApiEndPoint;
import id.co.fim.wipinformationsystemmobile.responses.BoxInfoResponse;
import id.co.fim.wipinformationsystemmobile.responses.LocationResponse;
import id.co.fim.wipinformationsystemmobile.services.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScanActivity extends AppCompatActivity {
    private SharedPreferences boxInfoPref;
    private SharedPreferences locationPref;
    private SharedPreferences scanTypePref;
    private SurfaceView surfaceView;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private TextView tvError;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    private String intentData = "";
    private String scanType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        // Inisialisasi UI
        surfaceView = findViewById(R.id.surfaceViewScan);
        tvError = findViewById(R.id.tvError);
        tvError.setVisibility(View.INVISIBLE);

        //Inisialisasi preferences
        boxInfoPref = getSharedPreferences("boxInfo", MODE_PRIVATE);
        locationPref = getSharedPreferences("location", MODE_PRIVATE);
        scanTypePref = getSharedPreferences("scanType", MODE_PRIVATE);
        scanType = scanTypePref.getString("type","");

        // Cek izin kamera sebelum memulai scanner
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            initialiseDetectorsAndSources();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        }
    }

    private void initialiseDetectorsAndSources() {
        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true)
                .build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                startCamera();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                if (cameraSource != null) {
                    cameraSource.stop();
                }
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {}

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() > 0) {
                    new Handler(Looper.getMainLooper()).post(() -> {
                        intentData = barcodes.valueAt(0).displayValue;
                        if (scanType.equals("box")) {
                            getBoxInfo(intentData);
                        } else if (scanType.equals("location")) {
                            getLocation(Integer.parseInt(intentData));
                        }
                        Log.d("DATA FROM SERVER", "QR CODE: " + intentData);
                    });
                }
            }
        });
    }

    private void startCamera() {
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) {
                cameraSource.start(surfaceView.getHolder());
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("ScanActivity", "Gagal memulai kamera: " + e.getMessage());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initialiseDetectorsAndSources();
            } else {
                tvError.setVisibility(View.VISIBLE);
                tvError.setText("Izin Kamera Diperlukan");
            }
        }
    }

    public void getBoxInfo(String boxCode) {
        ApiEndPoint apiEndPoint = ApiClient.getClient().create(ApiEndPoint.class);
        Call<BoxInfoResponse> call = apiEndPoint.getBoxInfo(boxCode);

        call.enqueue(new Callback<BoxInfoResponse>() {
            @Override
            public void onResponse(Call<BoxInfoResponse> call, Response<BoxInfoResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getResponses()) {
                    BoxInfoResponse boxInfoResponse = response.body();
                    SharedPreferences.Editor editor = boxInfoPref.edit();

                    editor.putInt("wipBoxId", boxInfoResponse.getWipBoxId());
                    editor.putInt("wipBoxDetailId", boxInfoResponse.getWipBoxDetailId());
                    editor.putString("boxCode", boxInfoResponse.getBoxCode());
                    editor.putInt("currentLocationId", boxInfoResponse.getLocationId());
                    editor.putInt("wipLineNumber", boxInfoResponse.getWipLineNumber());
                    editor.putInt("stack", boxInfoResponse.getStack());
                    editor.putInt("status", boxInfoResponse.getStatus());
                    editor.putString("scanned", "1");
                    editor.apply();

                    editor = scanTypePref.edit();
                    editor.putString("type", "");
                    editor.apply();

                    Log.d("DATA FROM SERVER", "wipBoxId: " + boxInfoResponse.getWipBoxId());
                    Log.d("DATA FROM SERVER", "wipBoxDetailId: " + boxInfoResponse.getWipBoxDetailId());
                    Log.d("DATA FROM SERVER", "boxCode: " + boxInfoResponse.getBoxCode());
                    Log.d("DATA FROM SERVER", "currentLocationId: " + boxInfoResponse.getLocationId());
                    Log.d("DATA FROM SERVER", "stack: " + boxInfoResponse.getStack());
                    Log.d("DATA FROM SERVER", "status: " + boxInfoResponse.getStatus());
                    Log.d("DATA FROM SERVER", "scanned: " + boxInfoPref.getString("scanned", ""));

                    Intent i = new Intent(ScanActivity.this, TransferActivity.class);
                    startActivity(i);
                    finish();
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                } else {
                    showError("DATA TIDAK DITEMUKAN");
                }
            }

            @Override
            public void onFailure(Call<BoxInfoResponse> call, Throwable t) {
                Log.e("API_ERROR", "Error: " + t.getMessage(), t); // Tambahkan log error
                tvError.setVisibility(View.VISIBLE);
                tvError.setText("KONEKSI BERMASALAH: " + t.getMessage()); // Tampilkan pesan error

                final Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tvError.setVisibility(View.INVISIBLE);
                    }
                }, 6000);
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
                    SharedPreferences.Editor editor = locationPref.edit();

                    editor.putInt("locationId", locationResponse.getLocationId());
                    editor.putString("area", locationResponse.getArea());
                    editor.putString("line", locationResponse.getLine());
                    editor.putString("scanned", "1");
                    editor.apply();

                    editor = boxInfoPref.edit();

                    editor.putInt("destinationLocationId", locationResponse.getLocationId());
                    editor.apply();

                    editor = scanTypePref.edit();
                    editor.putString("type", "");
                    editor.apply();

                    Intent i = new Intent(ScanActivity.this, TransferActivity.class);
                    startActivity(i);
                    finish();
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
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

    private void showError(String message) {
        tvError.setVisibility(View.VISIBLE);
        tvError.setText(message);
        new Handler(Looper.getMainLooper()).postDelayed(() -> tvError.setVisibility(View.INVISIBLE), 6000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (cameraSource != null) {
            cameraSource.release();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cameraSource != null) {
            cameraSource.release();
        }
    }
}
