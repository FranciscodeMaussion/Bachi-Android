package com.frandemo.bachi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

public class EntradaInfo extends AppCompatActivity {
    private static final String TAG = "TareasList";

    private DatabaseReference refEntrada;
    private EntradaBase mEntrada;
    private Toolbar mActionBarToolbar;

    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseRecyclerAdapter<TareaBase, EntradaInfo.TareaViewHolder>
            mFirebaseAdapter;

    private RecyclerView mMessageRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private ProgressBar mProgressBar;
    private View view;
    private Context c;

    private static class TareaViewHolder extends RecyclerView.ViewHolder{
        TextView nombre;
        ImageView asignatura;
        TextView puntuacion;

        public TareaViewHolder(View v) {
            super(v);
            nombre = (TextView) itemView.findViewById(R.id.nombre);
            puntuacion = (TextView) itemView.findViewById(R.id.puntuacion);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entrada_info);
        mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
        mActionBarToolbar.setTitle("");
        setSupportActionBar(mActionBarToolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();//Todo add_tarea
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String alumnoId = getIntent().getStringExtra("EXTRA_ALUMNO_ID");
        String entradaId = getIntent().getStringExtra("EXTRA_ENTRADA_ID");
        refEntrada = FirebaseDatabase.getInstance().getReference().child("entradas").child(alumnoId).child(entradaId);
        refEntrada.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mEntrada = dataSnapshot.getValue(EntradaBase.class);
                System.out.println("Here I am");
                String str = mEntrada.getFecha();
                Date date = null;
                try {
                    date = new SimpleDateFormat("yyyy-MM-dd").parse(str);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                // format the java.util.Date object to the desired format
                String formattedDate = new SimpleDateFormat("EEEE dd, MMMM").format(date);
                mActionBarToolbar.setTitle(formattedDate);
                TextView observacion = (TextView) findViewById(R.id.observacion);
                observacion.setText(mEntrada.getObservaciones());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        c = getApplicationContext();
        mMessageRecyclerView = (RecyclerView) findViewById(R.id.tareas_list);
        mLinearLayoutManager = new LinearLayoutManager(c);
        mMessageRecyclerView.setLayoutManager(mLinearLayoutManager);

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference().child("tareas").child(getIntent().getStringExtra("EXTRA_ENTRADA_ID"));
        mFirebaseAdapter = new FirebaseRecyclerAdapter<TareaBase,
                EntradaInfo.TareaViewHolder>(
                TareaBase.class,
                R.layout.tarea_row,
                EntradaInfo.TareaViewHolder.class,
                mFirebaseDatabaseReference) {
            @Override
            protected void populateViewHolder(final EntradaInfo.TareaViewHolder viewHolder,
                                              TareaBase tareaBase, int position) {

                viewHolder.nombre.setText(tareaBase.getNombre());
                viewHolder.puntuacion.setText(tareaBase.getPuntuacion()+"/5");//TODO puntuacion en estrellitas
                //TODO Asignatura
                Log.e(TAG, ""+tareaBase.getNombre());

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

        mFirebaseDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
                mProgressBar.setVisibility(ProgressBar.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
