package com.example.personapiclient;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;

public class PersonAdapter extends BaseAdapter {

    List<Person> persons;
    Context context;

    public PersonAdapter(List<Person> persons, Context context) {
        this.persons = persons;
        this.context= context;
    }

    @Override
    public int getCount() {
        return persons.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater= LayoutInflater.from(context);
        View v =inflater.inflate(R.layout.person_item_layout, null);

        Person person=persons.get(position);

        //ImageView img=v.findViewById(R.id.imgBil);
        //img.setImageResource(person.getId());


        TextView txtNavn= v.findViewById(R.id.txtNavn);
        txtNavn.setText(person.getNavn());
        TextView txtTlf=v.findViewById(R.id.txtTlf);
        txtTlf.setText(person.getTlf());


        return v;
    }
}
