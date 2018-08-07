package com.frandemo.bachi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AlumnoInfo extends AppCompatActivity {
    private DatabaseReference refAlumno;
    private AlumnoBase mAlumno;
    private Toolbar mActionBarToolbar;

    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseRecyclerAdapter<EntradaBase, AlumnoInfo.EntradaViewHolder>
            mFirebaseAdapter;

    private RecyclerView mMessageRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private ProgressBar mProgressBar;
    private View view;
    private Context c;

    private static class EntradaViewHolder extends RecyclerView.ViewHolder{
        TextView fecha;
        ImageView profesor;
        TextView observaciones;
        TextView tareas;

        public EntradaViewHolder(View v) {
            super(v);
            fecha = (TextView) itemView.findViewById(R.id.fecha);
            observaciones = (TextView) itemView.findViewById(R.id.observaciones);
            tareas = (TextView) itemView.findViewById(R.id.tareas);
            profesor = (ImageView) itemView.findViewById(R.id.profesor);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alumno_info);
        mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
        mActionBarToolbar.setTitle("");
        setSupportActionBar(mActionBarToolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();//TODO Make add_entrada
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String alumnoId = getIntent().getStringExtra("EXTRA_ALUMNO_ID");
        refAlumno = FirebaseDatabase.getInstance().getReference().child("alumnos").child(alumnoId);
        refAlumno.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mAlumno = dataSnapshot.getValue(AlumnoBase.class);
                System.out.println("Here I am");
                mActionBarToolbar.setTitle(mAlumno.getNombre());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        c = getApplicationContext();
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mMessageRecyclerView = (RecyclerView) findViewById(R.id.entries_list);
        mLinearLayoutManager = new LinearLayoutManager(c);
        mMessageRecyclerView.setLayoutManager(mLinearLayoutManager);

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mFirebaseAdapter = new FirebaseRecyclerAdapter<EntradaBase,
                AlumnoInfo.EntradaViewHolder>(
                EntradaBase.class,
                R.layout.entrada_row, //Nose
                AlumnoInfo.EntradaViewHolder.class,
                mFirebaseDatabaseReference.child("entradas").child(getIntent().getStringExtra("EXTRA_ALUMNO_ID")).orderByChild("fecha")) {
            @Override
            protected void populateViewHolder(final AlumnoInfo.EntradaViewHolder viewHolder,
                                              EntradaBase entradaBase, int position) {
                mProgressBar.setVisibility(ProgressBar.INVISIBLE);
                if (entradaBase.getFecha() != null) {
                    String str = entradaBase.getFecha();
                    Date date = null;
                    try {
                        date = new SimpleDateFormat("yyyy-MM-dd").parse(str);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    // format the java.util.Date object to the desired format
                    String formattedDate = new SimpleDateFormat("dd MMMM yyyy").format(date);
                    viewHolder.fecha.setText(formattedDate);
                }
                viewHolder.observaciones.setText(entradaBase.getObservaciones());

                viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        //TODO multiple selection
                        Toast.makeText(v.getContext(), "LongClick "+viewHolder.getAdapterPosition(), Toast.LENGTH_LONG).show();
                        return true;
                    }
                });
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String entradaHelper = mFirebaseAdapter.getRef(viewHolder.getAdapterPosition()).getKey();
                        //Toast.makeText(v.getContext(), "Click "+alumnoHelper.getKey(), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(v.getContext(), EntradaInfo.class);
                        intent.putExtra("EXTRA_ALUMNO_ID", getIntent().getStringExtra("EXTRA_ALUMNO_ID"));
                        intent.putExtra("EXTRA_ENTRADA_ID", entradaHelper);
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
    }
}
