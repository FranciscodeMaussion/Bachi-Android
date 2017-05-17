package com.frandemo.bachi;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;

import com.afollestad.materialdialogs.MaterialDialog;

/**
 * Created by demo on 17/05/17.
 */

public class ProfesorDialog  extends DialogFragment {

    public static void show(AppCompatActivity context) {
        ProfesorDialog dialog = new ProfesorDialog();
        dialog.show(context.getSupportFragmentManager(), "[ALUMNO_DIALOG]");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new MaterialDialog.Builder(getActivity())
                .title("Añadir Profesor")
                .content("Crear un profesor nuevo.")
                .input("Nombre", "", new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        // Do something
                    }
                }).show();
    }
}
