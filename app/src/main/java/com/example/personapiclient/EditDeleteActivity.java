package com.example.personapiclient;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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

public class EditDeleteActivity extends AppCompatActivity  {

    EditText txtNavn, txtTlf, txtAddress;
    Button btnCancel, btnUpdate,btnDelete;
    CheckBox chbxFavorit;
    RadioGroup radioGroup;
    RadioButton radioJava, radioCsharp, radioPython;
    //Integer [] HairColor ={0, 1, 2,3,4,5};
    Spinner spnHairColor;
    Intent intent=getIntent();
   Person person=new Person();
   int haircolor = 0;
    String radioSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_delete);

        person = (Person) getIntent().getSerializableExtra("person");
        ArrayAdapter<HairColor> hairColorAdapter =
                new ArrayAdapter<HairColor>(this, android.R.layout.simple_spinner_item, HairColor.values());


        txtNavn = findViewById(R.id.txtNavn);
        txtTlf = findViewById(R.id.txtTlf);
        txtAddress = findViewById(R.id.txtAddress);
        chbxFavorit = findViewById(R.id.chbxFavorit);
        radioGroup = findViewById(R.id.radioGroup);
        radioJava = findViewById(R.id.radioJava);
        radioCsharp = findViewById(R.id.radioCsharp);
        radioPython = findViewById(R.id.radioPython);


        btnCancel = findViewById(R.id.btnCancel);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete=findViewById(R.id.btnDelete);
        spnHairColor = findViewById(R.id.spnHairColor);
        spnHairColor.setAdapter(hairColorAdapter);

       /* spnHairColor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int se= position;
            }
        });*/
        /*bLOND=0, black=1, white=2, red=3, lightblond=4, darkblond=5*/


        txtNavn.setText(person.navn);
        txtTlf.setText(person.tlf);
        txtAddress.setText(person.addresse);

        if (person.hairFarve == 0) {
            spnHairColor.setSelection(1);
        }
        if (person.hairFarve == 1) {
            spnHairColor.setSelection(2);
        }
        if (person.hairFarve == 2) {
            spnHairColor.setSelection(3);
        }
        if (person.hairFarve == 3) {
            spnHairColor.setSelection(4);
        }
        if (person.hairFarve == 4) {
            spnHairColor.setSelection(5);
        }
        if (person.favorit == false) {
            chbxFavorit.setChecked(false);

        }
        if (person.favorit == true) {
            chbxFavorit.setChecked(true);
        }
        if (person.programSprog.equals("Java") ) {

            radioGroup.check(R.id.radioJava);
        }
        else  if (person.programSprog.equals("C#")) {

            radioGroup.check(R.id.radioCsharp);
        }
        else if (person.programSprog.equals("Python")) {
            radioGroup.check(R.id.radioPython);
        }
        else if(person.programSprog.equals(null)){
            radioGroup.check(-1);
        }


        //HairColor.setVisibility(View.VISIBLE);
        //String select= spnHairColor.getSelectedItem().toString();

        //HairColor h= HairColor.valueOf(select);

       /* radioJava.setOnClickListener(this);
        radioPython.setOnClickListener(this);
        radioCsharp.setOnClickListener(this);*/

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                person.navn = txtNavn.getText().toString();
                if (person.navn.matches(""))
                {
                    Toast.makeText(EditDeleteActivity.this, "You did not enter persons name", Toast.LENGTH_SHORT).show();
                    return;
                }
                person.addresse = txtAddress.getText().toString();
                if (person.tlf.matches(""))
                {
                    Toast.makeText(EditDeleteActivity.this, "You did not enter persons address", Toast.LENGTH_SHORT).show();
                    return;
                }
                person.tlf = txtTlf.getText().toString();
                if (person.tlf.matches(""))
                {
                    Toast.makeText(EditDeleteActivity.this, "You did not enter persons telephone number", Toast.LENGTH_SHORT).show();
                    return;
                }
                haircolor = spnHairColor.getSelectedItemPosition();  //getting enums key
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
                    Toast.makeText(EditDeleteActivity.this, "Please select one of the radiobutton", Toast.LENGTH_SHORT).show();
                    return;
                }

                updatePerson(person);

            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(EditDeleteActivity.this);
                alert.setTitle("Delete Person?");
                alert.setMessage("Ønsker De at slette den person?");

                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton)
                    {
                        int id=person.id;
                        deletePerson(id);
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int whichButton)
                    {

                    }
                });

                alert.show();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });


    }

    private void deletePerson(int id) {
        PersonService personService = ServiceBuilder.buildService(PersonService.class);
        Call<Void> request = personService.deletePerson(id);

        request.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                Log.d("deleted:", person.navn);
                finish();

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

                //txtName.setText(t.getMessage());
            }
        });
    }

    private void updatePerson(Person person) {

        PersonService personService = ServiceBuilder.buildService(PersonService.class);
        Call<Void> request = personService.updatePerson(person);

        request.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                Log.d("updated:", person.navn);
                finish();

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

                //txtName.setText(t.getMessage());
            }
        });


    }




    /*@Override
    public void onClick(View v) {
        if (v == radioJava) {

            radioSettings="Java";

        }
        if (v == radioPython) {
            radioSettings="Python";
        }

        if (v == radioCsharp) {
            radioSettings="C#";
        }

    }*/
}