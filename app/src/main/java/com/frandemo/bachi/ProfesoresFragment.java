package com.frandemo.bachi;

import android.content.Context;
import android.os.Bundle;
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


public class ProfesoresFragment extends Fragment{
    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseRecyclerAdapter<Profesor, PeopleViewHolder>
            mFirebaseAdapter;

    private RecyclerView mMessageRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private ProgressBar mProgressBar;
    private View view;
    private Context c;


    private static class PeopleViewHolder extends RecyclerView.ViewHolder {
        TextView fecha;
        TextView nombre;

        public PeopleViewHolder(View v) {
            super(v);
            fecha = (TextView) itemView.findViewById(R.id.fecha);
            nombre = (TextView) itemView.findViewById(R.id.nombre);
        }
    }

    public ProfesoresFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profesores, container, false);
        c = getContext();
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mMessageRecyclerView = (RecyclerView) view.findViewById(R.id.peopleList);
        mLinearLayoutManager = new LinearLayoutManager(c);
        mMessageRecyclerView.setLayoutManager(mLinearLayoutManager);

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mFirebaseAdapter = new FirebaseRecyclerAdapter<Profesor,
                PeopleViewHolder>(
                Profesor.class,
                R.layout.custom_row,
                PeopleViewHolder.class,
                mFirebaseDatabaseReference.child("profesores").orderByChild("nombre")) {
            @Override
            protected void populateViewHolder(final PeopleViewHolder viewHolder,
                                              Profesor profesor, int position) {
                mProgressBar.setVisibility(ProgressBar.INVISIBLE);
                String helper = "";
                if (profesor.getFecha() != null) {
                    String str = profesor.getFecha();
                    Date date = null;
                    try {
                        date = new SimpleDateFormat("yyyy-mm-dd").parse(str);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    // format the java.util.Date object to the desired format
                    String formattedDate = new SimpleDateFormat("dd MMM yyyy").format(date);
                    helper += "Nacimiento: " + formattedDate + "\n";
                }
                if (profesor.getTelefono() != 0) {
                    helper += "Teléfono: " + profesor.getTelefono() + "\n";
                }
                if (profesor.getEmail() != null) {
                    helper += "Email: " + profesor.getEmail() + "\n";
                }
                
                viewHolder.fecha.setText(helper);
                viewHolder.nombre.setText(profesor.getNombre());

            }
        };

        mMessageRecyclerView.setLayoutManager(mLinearLayoutManager);
        mMessageRecyclerView.setAdapter(mFirebaseAdapter);

        return view;
    }

}
