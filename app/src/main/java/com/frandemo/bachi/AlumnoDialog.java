package com.frandemo.bachi;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;

import com.afollestad.materialdialogs.MaterialDialog;

/**
 * Created by demo on 17/05/17.
 */

public class AlumnoDialog  extends DialogFragment {

    public static void show(AppCompatActivity context) {
        AlumnoDialog dialog = new AlumnoDialog();
        dialog.show(context.getSupportFragmentManager(), "[ALUMNO_DIALOG]");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        boolean wrapInScrollView = true;
        return new MaterialDialog.Builder(getActivity())
                .title(R.string.dialog_alumno_titulo)
                .customView(R.layout.dialog_alumno, wrapInScrollView)
                .positiveText(R.string.dialog_alumno_send)
                .show();
    }
}