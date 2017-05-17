package com.frandemo.bachi;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class AlumnosFragment extends Fragment{
    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseRecyclerAdapter<Alumno, AlumnoViewHolder>
            mFirebaseAdapter;

    private RecyclerView mMessageRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private ProgressBar mProgressBar;
    private View view;
    private Context c;


    private static class AlumnoViewHolder extends RecyclerView.ViewHolder {
        TextView fecha;
        TextView nombre;
        TextView escolar;

        public AlumnoViewHolder(View v) {
            super(v);
            fecha = (TextView) itemView.findViewById(R.id.fecha);
            nombre = (TextView) itemView.findViewById(R.id.nombre);
            escolar = (TextView) itemView.findViewById(R.id.escolar);
        }
    }

    public AlumnosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_alumnos, container, false);
        c = getContext();
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mMessageRecyclerView = (RecyclerView) view.findViewById(R.id.peopleList);
        mLinearLayoutManager = new LinearLayoutManager(c);
        mMessageRecyclerView.setLayoutManager(mLinearLayoutManager);

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mFirebaseAdapter = new FirebaseRecyclerAdapter<Alumno,
                AlumnoViewHolder>(
                Alumno.class,
                R.layout.custom_row,
                AlumnoViewHolder.class,
                mFirebaseDatabaseReference.child("alumnos").orderByChild("nombre")) {
            @Override
            protected void populateViewHolder(final AlumnoViewHolder viewHolder,
                                              Alumno alumno, int position) {
                mProgressBar.setVisibility(ProgressBar.INVISIBLE);
                if (alumno.getFecha() != null) {
                    String str = alumno.getFecha();
                    Date date = null;
                    try {
                        date = new SimpleDateFormat("yyyy-mm-dd").parse(str);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    // format the java.util.Date object to the desired format
                    String formattedDate = new SimpleDateFormat("dd MMM yyyy").format(date);
                    viewHolder.fecha.setText("Nacimiento: " + formattedDate);
                }


                viewHolder.nombre.setText(alumno.getNombre());
                if (alumno.getEscolar() != 0) {
                    viewHolder.escolar.setText(alumno.getEscolar()+"° Año");
                    viewHolder.escolar.setVisibility(TextView.VISIBLE);
                }

            }
        };

        mMessageRecyclerView.setLayoutManager(mLinearLayoutManager);
        mMessageRecyclerView.setAdapter(mFirebaseAdapter);

        return view;
    }

}
