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

import tech.babashnik.olimp.inventory.R;
import tech.babashnik.olimp.inventory.data.DataBase;
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
        ((EditText) v.findViewById(R.id.titleEdit)).setText(title);
        ((EditText) v.findViewById(R.id.descriptionEdit)).setText(desc);
        ((EditText) v.findViewById(R.id.hrefEdit)).setText(href);
        final EditText titleEdit = (EditText) v.findViewById(R.id.titleEdit);
        final EditText descriptionEdit = (EditText) v.findViewById(R.id.descriptionEdit);
        final EditText hrefEdit = (EditText) v.findViewById(R.id.hrefEdit);

        v.findViewById(R.id.saveButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//TODO: сделать сохранение. ну.. хотя бы получение введенных данных!
                String j = titleEdit.getText().toString();
                descriptionEdit.getText().toString();
                hrefEdit.getText().toString();
                j = new StringBuilder(j).insert(j.length(), "j").toString();
                //мы хз что тут делать..выше наше предположение но там не понятно что в скобках,я запуталась..не злись,выздоравливай))


            }
        });

        return v;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        ((MainActivity) getActivity()).scannerView.resumeCameraPreview(((MainActivity) getActivity()));
    }
}