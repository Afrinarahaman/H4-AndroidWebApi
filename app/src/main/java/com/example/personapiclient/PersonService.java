package com.example.personapiclient;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PersonService {

    @GET("Person/{id}")
    Call<Person> getPersonById(@Path("id")int id);

    @GET("Person")
    Call<List<Person>> getAllPersons();

    @POST("Person")
    Call <Void> addPerson(@Body Person person);

    @PUT("Person")
    Call <Void> updatePerson(@Body Person person);

    @DELETE("Person/{id}")
    Call<Void> deletePerson(@Path("id")int id);


}



