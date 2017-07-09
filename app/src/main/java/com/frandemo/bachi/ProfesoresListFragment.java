package com.frandemo.bachi;

import android.content.Context;
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

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ProfesoresListFragment extends Fragment{
    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseRecyclerAdapter<ProfesorBase, PeopleViewHolder>
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

    public ProfesoresListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.profesores_list_fragment, container, false);
        c = getContext();
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mMessageRecyclerView = (RecyclerView) view.findViewById(R.id.peopleList);
        mLinearLayoutManager = new LinearLayoutManager(c);
        mMessageRecyclerView.setLayoutManager(mLinearLayoutManager);

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mFirebaseAdapter = new FirebaseRecyclerAdapter<ProfesorBase,
                PeopleViewHolder>(
                ProfesorBase.class,
                R.layout.person_row,
                PeopleViewHolder.class,
                mFirebaseDatabaseReference.child("profesores").orderByChild("nombre")) {
            @Override
            protected void populateViewHolder(final PeopleViewHolder viewHolder,
                                              ProfesorBase profesorBase, int position) {
                mProgressBar.setVisibility(ProgressBar.INVISIBLE);
                String helper = "";
                if (profesorBase.getFecha() != null) {
                    String str = profesorBase.getFecha();
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
                if (profesorBase.getTelefono() != 0) {
                    helper += "Teléfono: " + profesorBase.getTelefono() + "\n";
                }
                if (profesorBase.getEmail() != null) {
                    helper += "Email: " + profesorBase.getEmail() + "\n";
                }
                if (profesorBase.getAprobado() != 1) {
                    helper += "Personal no autorizado";//TODO Add button to authorize
                }
                viewHolder.fecha.setText(helper);
                viewHolder.nombre.setText(profesorBase.getNombre());

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
