package tech.babashnik.olimp.inventory.ui.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import tech.babashnik.olimp.inventory.R;
import tech.babashnik.olimp.inventory.ui.activities.MainActivity;


public class OlimpInventoryItemViewDialog extends DialogFragment {
    String name, title, desc, href;

    public static OlimpInventoryItemViewDialog newInstance(String inventoryName, String inventoryTitle, String inventoryDesc, String inventoryHref) {
        OlimpInventoryItemViewDialog f = new OlimpInventoryItemViewDialog();
        Bundle args = new Bundle();
        args.putString("name", inventoryName);
        args.putString("title", inventoryTitle);
        args.putString("desc", inventoryDesc);
        args.putString("href", inventoryHref);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        name = getArguments().getString("name");
        title = getArguments().getString("title");
        desc = getArguments().getString("desc");
        href = getArguments().getString("href");
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

        View v = inflater.inflate(R.layout.olimp_inventory_item_view, container, false);
        ((TextView) v.findViewById(R.id.nameView)).setText(name);
        ((TextView) v.findViewById(R.id.titleView)).setText(title);
        ((TextView) v.findViewById(R.id.descriptionView)).setText(desc);
        ((TextView) v.findViewById(R.id.hrefView)).setText(href);
        return v;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        ((MainActivity) getActivity()).scannerView.resumeCameraPreview(((MainActivity) getActivity()));
    }
}