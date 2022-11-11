package com.example.personapiclient;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPersonActivity extends AppCompatActivity {

    Button btnCancel,btnAdd;
    Spinner spnHairColor;
    int haircolor = 0;
    EditText txtNavn, txtTlf, txtAddress;
    Person person=new Person();
    CheckBox chbxFavorit;
    RadioGroup radioGroup;
    RadioButton radioJava, radioCsharp, radioPython;
    String radioSettings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);

        ArrayAdapter<HairColor> hairColorAdapter =
                new ArrayAdapter<HairColor>(this, android.R.layout.simple_spinner_item, HairColor.values());
        btnCancel=findViewById(R.id.btnCancel);
        btnAdd=findViewById(R.id.btnAdd);

        spnHairColor = findViewById(R.id.spnHairColor);
          //getting enums key
       // Log.d("Haircolor ", HairColor.valueOf());
        spnHairColor.setAdapter(hairColorAdapter);

        txtNavn = findViewById(R.id.txtNavn);
        txtTlf = findViewById(R.id.txtTlf);
        txtAddress = findViewById(R.id.txtAddress);
        chbxFavorit = findViewById(R.id.chbxFavorit);
        radioGroup = findViewById(R.id.radioGroup);
        radioJava = findViewById(R.id.radioJava);
        radioCsharp = findViewById(R.id.radioCsharp);
        radioPython = findViewById(R.id.radioPython);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                person.navn = txtNavn.getText().toString();
                if (person.navn.matches(""))
                {
                    Toast.makeText(AddPersonActivity.this, "You did not enter persons name", Toast.LENGTH_SHORT).show();
                    return;
                }
                person.addresse = txtAddress.getText().toString();
                if (person.addresse.matches(""))
                {
                    Toast.makeText(AddPersonActivity.this, "You did not enter persons address", Toast.LENGTH_SHORT).show();
                    return;
                }
                person.tlf = txtTlf.getText().toString();
                if (person.tlf.matches(""))
                {
                    Toast.makeText(AddPersonActivity.this, "You did not enter persons telephone number", Toast.LENGTH_SHORT).show();
                    return;
                }

                haircolor = spnHairColor.getSelectedItemPosition();
                person.hairFarve = haircolor;
                person.favorit = chbxFavorit.isChecked();
                if(radioJava.isChecked())
                {
                    person.programSprog= "Java";
                }
                else if(radioCsharp.isChecked())
                {
                    person.programSprog= "C#";
                }
                else if(radioPython.isChecked())
                {
                    person.programSprog= "Python";
                }
                else
                {
                    Toast.makeText(AddPersonActivity.this, "Please select one of the radiobutton", Toast.LENGTH_SHORT).show();
                    return;
                }


                addPerson(person);



            }
        });
        btnCancel = findViewById(R.id.btnCancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }






    private void addPerson(Person person) {
        Log.d("Added ", person.navn);
        PersonService personService = ServiceBuilder.buildService(PersonService.class);
        Call<Void> request = personService.addPerson(person);

        request.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                Log.d("Added", person.navn);

                finish();

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

                Log.d("Added",t.getMessage());
            }
        });


    }

}