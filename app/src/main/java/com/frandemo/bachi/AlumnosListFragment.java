package com.frandemo.bachi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class AlumnosListFragment extends Fragment{
    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseRecyclerAdapter<AlumnoBase, AlumnoViewHolder>
            mFirebaseAdapter;

    private RecyclerView mMessageRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private ProgressBar mProgressBar;
    private View view;
    private Context c;


    private static class AlumnoViewHolder extends RecyclerView.ViewHolder{
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

    public AlumnosListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.alumnos_list_fragment, container, false);
        c = getContext();
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mMessageRecyclerView = (RecyclerView) view.findViewById(R.id.peopleList);
        mLinearLayoutManager = new LinearLayoutManager(c);
        mMessageRecyclerView.setLayoutManager(mLinearLayoutManager);

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mFirebaseAdapter = new FirebaseRecyclerAdapter<AlumnoBase,
                AlumnoViewHolder>(
                AlumnoBase.class,
                R.layout.person_row,
                AlumnoViewHolder.class,
                mFirebaseDatabaseReference.child("alumnos").orderByChild("nombre")) {
            @Override
            protected void populateViewHolder(final AlumnoViewHolder viewHolder,
                                              AlumnoBase alumnoBase, int position) {
                mProgressBar.setVisibility(ProgressBar.INVISIBLE);
                if (alumnoBase.getFecha() != null) {
                    String str = alumnoBase.getFecha();
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


                viewHolder.nombre.setText(alumnoBase.getNombre());
                if (alumnoBase.getEscolar() != 0) {
                    viewHolder.escolar.setText(alumnoBase.getEscolar()+"° Año");
                    viewHolder.escolar.setVisibility(TextView.VISIBLE);
                }
                viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        //TODO multiple selection
                        Toast.makeText(v.getContext(), "LongClick "+viewHolder.getAdapterPosition(), Toast.LENGTH_LONG).show();
                        //mFirebaseAdapter.getRef(viewHolder.getAdapterPosition()).removeValue();
                        //mFirebaseAdapter.notifyDataSetChanged();
                        return true;
                    }
                });
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //mFirebaseAdapter.getRef(viewHolder.getAdapterPosition()).removeValue();
                        //mFirebaseAdapter.notifyDataSetChanged();
                        DatabaseReference alumnoHelper = mFirebaseAdapter.getRef(viewHolder.getAdapterPosition());
                        //Toast.makeText(v.getContext(), "Click "+alumnoHelper.getKey(), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(v.getContext(), AlumnoInfo.class);
                        intent.putExtra("EXTRA_ALUMNO_ID", alumnoHelper.getKey());
                        startActivity(intent);
                    }
                });

            }

            @Override
            protected void onCancelled(DatabaseError error) {
                super.onCancelled(error);
                Snackbar.make(view, ""+error, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        };

        mMessageRecyclerView.setLayoutManager(mLinearLayoutManager);
        mMessageRecyclerView.setAdapter(mFirebaseAdapter);
        return view;
    }

}
