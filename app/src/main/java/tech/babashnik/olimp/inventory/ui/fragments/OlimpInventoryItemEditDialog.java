package tech.babashnik.olimp.inventory.ui.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tech.babashnik.olimp.inventory.R;
import tech.babashnik.olimp.inventory.data.App;
import tech.babashnik.olimp.inventory.data.DataBase;
import tech.babashnik.olimp.inventory.data.components.olimp.OlimpApi;
import tech.babashnik.olimp.inventory.data.components.olimp.inventory.InventoryItem;
import tech.babashnik.olimp.inventory.ui.activities.MainActivity;


public class OlimpInventoryItemEditDialog extends DialogFragment {
    String name, title = "", desc = "", href = "";


    public static OlimpInventoryItemEditDialog newInstance(String inventoryName) {
        OlimpInventoryItemEditDialog f = new OlimpInventoryItemEditDialog();
        Bundle args = new Bundle();
        args.putString("name", inventoryName);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        name = getArguments().getString("name");
        DataBase db = new DataBase(getActivity().getApplicationContext());
        Cursor c = db.query("olimp_inventory_items", null, "name='" + name + "'", null, null, null, null);
        if (c != null && c.moveToFirst()) {
            title = c.getString(c.getColumnIndex("title"));
            desc = c.getString(c.getColumnIndex("description"));
            href = c.getString(c.getColumnIndex("href"));
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.olimp_inventory_item_edit, container, false);
        ((TextView) v.findViewById(R.id.nameView)).setText(name);
        final EditText titleEdit = (EditText) v.findViewById(R.id.titleEdit);
        titleEdit.setText(title);
        final EditText descriptionEdit = (EditText) v.findViewById(R.id.descriptionEdit);
        descriptionEdit.setText(desc);
        final EditText hrefEdit = (EditText) v.findViewById(R.id.hrefEdit);
        hrefEdit.setText(href);
        v.findViewById(R.id.saveButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//TODO: сделать сохранение. ну.. хотя бы получение введенных данных!
                title = titleEdit.getText().toString();
                desc = descriptionEdit.getText().toString();
                href = hrefEdit.getText().toString();
                OlimpApi oA = App.Companion.getOlimp();
                if (oA == null)
                    return;
                oA.insertInventoryItem(name, title, href, desc).enqueue(new Callback<InventoryItem>() {
                    @Override
                    public void onResponse(Call call, Response response) {
                        if (response == null) {
                            return;
                        }
                        InventoryItem ii = (InventoryItem) response.body();
                        if (ii != null && ii.getTitle() != null) {
                            DataBase db = new DataBase(OlimpInventoryItemEditDialog.this.getActivity().getApplicationContext());
                            db.insertOrUpdate("olimp_inventory_items", "name='" + ii.getName() + "'", ii.getMap());
                            db.close();

                        }
                    }

                    @Override
                    public void onFailure(Call<InventoryItem> call, Throwable t) {

                    }
                });


            }
        });

        return v;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        ((MainActivity) getActivity()).scannerView.resumeCameraPreview(((MainActivity) getActivity()));
    }
}