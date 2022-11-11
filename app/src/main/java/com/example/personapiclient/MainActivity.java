package com.example.personapiclient;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
{
    //TextView txtName;
    ListView lstPersons;
    List<Person> persons;
    Person person=new Person();
    Intent intent = getIntent();
    Button btnAdd;
    ImageView imgAdd;
    ActivityResultLauncher<Intent> itemClickLauncher,AddPersonLauncher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //txtName=findViewById(R.id.txtName);
        //btnAdd=findViewById(R.id.btnAdd);

        imgAdd=findViewById(R.id.imgAdd);
        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this,AddPersonActivity.class);
                //secondLauncher.launch(intent);
                //startActivity(intent);

                AddPersonLauncher.launch(intent);
            }
        });
        AddPersonLauncher= registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result)
                    {
                        if(result.getResultCode() == Activity.RESULT_OK)
                        {
                            Intent data=result.getData();
                            //inputFromThirdAcitivity.setText(data.getStringExtra(INPUT_FROM_THIRD));
                            //txtGetName.setTextColor();


                        }
                    }
                });

        lstPersons= findViewById(R.id.lstPersons);
        lstPersons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 person = persons.get(position);
                 int idClick = persons.get(position).id;
                Log.d("Person:" ,person.navn);
                intent = new Intent(MainActivity.this,EditDeleteActivity.class);
                intent.putExtra("person",person);
                //secondLauncher.launch(intent);
                //startActivity(intent);
                itemClickLauncher.launch(intent);
            }


        });
        itemClickLauncher= registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result)
                    {
                        if(result.getResultCode() == Activity.RESULT_OK)
                        {
                            startActivity(intent);

                        }
                    }
                });

        //For the connection with api of eclipse
        PersonService personService= ServiceBuilder.buildService(PersonService.class);
        Call<Person> request= personService.getPersonById(1);

        request.enqueue(new Callback<Person>() {
            @Override
            public void onResponse(Call<Person> call, Response<Person> response)
            {

                Person person= response.body();
                //txtName.setText(person.navn);
            }

            @Override
            public void onFailure(Call<Person> call, Throwable t) {

                //txtName.setText(t.getMessage());
            }
        });

        Call<List<Person>> requestForAllPersons= personService.getAllPersons();
        requestForAllPersons.enqueue(new Callback<List<Person>>() {
            @Override
            public void onResponse(Call<List<Person>> call, Response<List<Person>> response) {
             persons =response.body();

               // Log.d("Persons:" ,persons.get(1).navn);
                PersonAdapter personAdapter= new PersonAdapter( persons, MainActivity.this);
                lstPersons.setAdapter(personAdapter);
            }

            @Override
            public void onFailure(Call<List<Person>> call, Throwable t) {
                        Log.d("Persons:", t.getMessage().toString());
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        Rsults();
    }

    private void Rsults() {
        PersonService personService= ServiceBuilder.buildService(PersonService.class);
        Call<List<Person>> requestForAllPersons= personService.getAllPersons();
        requestForAllPersons.enqueue(new Callback<List<Person>>() {
            @Override
            public void onResponse(Call<List<Person>> call, Response<List<Person>> response) {
                persons =response.body();

                // Log.d("Persons:" ,persons.get(1).navn);
                PersonAdapter personAdapter= new PersonAdapter( persons, MainActivity.this);
                lstPersons.setAdapter(personAdapter);
            }

            @Override
            public void onFailure(Call<List<Person>> call, Throwable t) {
                Log.d("Persons:", t.getMessage().toString());
            }
        });
    }

}