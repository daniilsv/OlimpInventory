package tech.babashnik.olimp.inventory.ui.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.zxing.Result;

import org.jetbrains.annotations.NotNull;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tech.babashnik.olimp.inventory.data.App;
import tech.babashnik.olimp.inventory.data.DataBase;
import tech.babashnik.olimp.inventory.data.components.olimp.OlimpApi;
import tech.babashnik.olimp.inventory.data.components.olimp.inventory.InventoryItem;
import tech.babashnik.olimp.inventory.ui.fragments.OlimpInventoryItemEditDialog;
import tech.babashnik.olimp.inventory.ui.fragments.OlimpInventoryItemViewDialog;

public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    public ZXingScannerView scannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
        }

        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);
    }

    public void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
        scannerView.resumeCameraPreview(this);
    }

    public void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    public void handleResult(final Result result) {
        OlimpApi oA = App.Companion.getOlimp();
        if (oA == null)
            return;
        oA.getInventoryItem(result.getText()).enqueue(new Callback<InventoryItem>() {
            public void onResponse(@NotNull Call call, Response response) {
                if (response == null) {
                    MainActivity.this.scannerView.resumeCameraPreview(MainActivity.this);
                    return;
                }
                InventoryItem ii = (InventoryItem) response.body();
                if (ii != null && ii.getTitle() != null) {
                    DataBase db = new DataBase(MainActivity.this);
                    db.insertOrUpdate("olimp_inventory_items", "name='" + ii.getName() + "'", ii.getMap());
                    db.close();
                    OlimpInventoryItemViewDialog.newInstance(ii.getName()).show(getFragmentManager(), "ViewDialog");
                } else {
                    OlimpInventoryItemEditDialog.newInstance(result.getText()).show(getFragmentManager(), "EditDialog");
                }
            }

            public void onFailure(@NotNull Call call, @NotNull Throwable t) {

                DataBase db = new DataBase(MainActivity.this);
                String name = result.getText();
                Cursor c = db.query("olimp_inventory_items", null, "name='" + name + "'", null, null, null, null);
                if (c == null || !c.moveToFirst()) {
                    db.close();
                    MainActivity.this.scannerView.resumeCameraPreview(MainActivity.this);
                    return;
                }
                db.close();
                OlimpInventoryItemViewDialog.newInstance(name).show(getFragmentManager(), "ViewDialog");
            }
        });
    }

    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length == 0 || grantResults[0] != 0) {
                    Toast.makeText(this, "Please grant camera permission to use the QR Scanner", Toast.LENGTH_SHORT).show();
                }
                return;
            default:
        }
    }
}
