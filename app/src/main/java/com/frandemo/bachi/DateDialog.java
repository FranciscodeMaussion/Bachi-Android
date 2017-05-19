package com.frandemo.bachi;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;

import com.afollestad.materialdialogs.MaterialDialog;

/**
 * Created by demo on 18/05/17.
 */

public class DateDialog  extends DialogFragment {

    public static void show(AppCompatActivity context) {
        DateDialog dialog = new DateDialog();
        dialog.show(context.getSupportFragmentManager(), "[DATE_DIALOG]");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        boolean wrapInScrollView = true;
        return new MaterialDialog.Builder(getActivity())
                .customView(R.layout.dialog_date, wrapInScrollView)
                .positiveText(R.string.dialog_send)
                .show();
    }
}