package com.example.evetagenda;


import com.example.evetagenda.model.Event;
import com.example.evetagenda.model.EventResponse;
import com.example.evetagenda.model.Medicine;
import com.example.evetagenda.model.MedicineResponse;
import com.example.evetagenda.model.NoteResponse;
import com.example.evetagenda.model.PrescriptionDetails;
import com.example.evetagenda.model.ProducerResponse;
import com.example.evetagenda.model.ToDoResponse;
import com.example.evetagenda.model.UserInfoResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {

    String BASE_URL = "https://evetagendas.000webhostapp.com/api/";

    @GET("GET/fetchProducers.php")
    Call<ProducerResponse> getProducers(@Query("id") String userId);

    @GET("GET/fetchEvents.php")
    Call<EventResponse> getEvents(@Query("id") String userId);

    @GET("GET/fetchNotes.php")
    Call<NoteResponse> getNotes(@Query("id") String userId);

    @GET("GET/fetchToDo.php")
    Call<ToDoResponse> getToDo(@Query("id") String userId);

    @GET("GET/fetchMedicines.php")
    Call<MedicineResponse> getMedicines(@Query("id") String userId);

    @GET("GET/fetchPrescriptionDetails.php")
    Call<PrescriptionDetails> getPrescriptionDetails(@Query("id") String userId);

    @FormUrlEncoded
    @POST("POST/postUser.php")
    Call<UserInfoResponse> getUser(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("POST/postNote.php")
    Call<com.example.evetagenda.model.Error> postNote(@Field("noteText") String noteText,@Field("uid") String userId);

    @FormUrlEncoded
    @POST("POST/postToDo.php")
    Call<com.example.evetagenda.model.Error> postToDo(@Field("toDoText") String noteText,@Field("uid") String userId);

    @FormUrlEncoded
    @POST("POST/postPrescription.php")
    Call<com.example.evetagenda.model.Error> postPrescription(@Field("currentDate") String currentDate,
                                                              @Field("department") String department,
                                                              @Field("address") String address,
                                                              @Field("location") String location,
                                                              @Field("producerId") int producerId,
                                                              @Field("code") String code,
                                                              @Field("animalType") String animalType,
                                                              @Field("cause") String cause,
                                                              @Field("medicineId") int medicineId,
                                                              @Field("dose") String dose,
                                                              @Field("manual") String manual,
                                                              @Field("meat") String meat,
                                                              @Field("milk") String milk,
                                                              @Field("egg") String egg,
                                                              @Field("honey") String honey,
                                                              @Field("otherNet") String otherNet,
                                                              @Field("comments") String comments,
                                                              @Field("prescriptionNumber") String prescriptionNumber,
                                                              @Field("uid") String uid
                                                              );

    @FormUrlEncoded
    @POST("POST/postCustomer.php")
    Call<com.example.evetagenda.model.Error> postCustomer(@Field("prodFLname") String prodFLname,
                                                          @Field("prodPhone") String prodPhone,
                                                          @Field("prodEmail") String prodEmail,
                                                          @Field("prodCodeEktrofis") String prodCodeEktrofis,
                                                          @Field("prodAFM") String prodAFM,
                                                          @Field("prodDOY") String prodDOY,
                                                          @Field("prodArea") String prodArea,
                                                          @Field("prodNumAnimals") String prodNumAnimals,
                                                          @Field("prodTypeAnimals") String prodTypeAnimals,
                                                          @Field("prodFiliAnimals") String prodFiliAnimals,
                                                          @Field("uid") String uid
    );


    @POST("POST/postMedicine.php")
    Call<com.example.evetagenda.model.Error> postMedicine(@Body Medicine med);

    @GET("DELETE/deleteToDo.php")
    Call<com.example.evetagenda.model.Error> deleteToDo(@Query("id") String userId, @Query("itemId") String toDoId);

    @GET("PUT/updateToDo.php")
    Call<com.example.evetagenda.model.Error> updateToDo(@Query("id") String userId, @Query("itemId") String toDoId, @Query("isDone") String isDone, @Query("item") String item);

    @GET("DELETE/deleteNote.php")
    Call<com.example.evetagenda.model.Error> deleteNote(@Query("id") String userId, @Query("commentID") String toDoId);

    @GET("PUT/updateNote.php")
    Call<com.example.evetagenda.model.Error> updateNote(@Query("id") String userId, @Query("commentID") String toDoId, @Query("message") String item);

    @GET("DELETE/deleteCustomer.php")
    Call<com.example.evetagenda.model.Error> deleteCustomer(@Query("id") String userId, @Query("prodID") String toDoId);

    @GET("PUT/updateCustomer.php")
    Call<com.example.evetagenda.model.Error> updateCustomer(@Query("id") String userId,@Query("customerId") String customerId, @Query("name") String name, @Query("phone") String phone, @Query("email") String email, @Query("code") String code,@Query("afm") String afm,@Query("doy") String doy,@Query("area") String area,@Query("num_of_animals") String num_of_animals,@Query("animal_type") String animal_type,@Query("animal_tribe") String animal_tribe);

    @GET("DELETE/deleteMedicine.php")
    Call<com.example.evetagenda.model.Error> deleteMedicine(@Query("id") String userId, @Query("medicinesID") String toDoId);

    @POST("PUT/updateMedicine.php")
    Call<com.example.evetagenda.model.Error> updateMedicine(@Body Medicine med);

    @POST("POST/postEvent.php")
    Call<com.example.evetagenda.model.Error> postEvent(@Body Event event);

    @GET("DELETE/deleteEvent.php")
    Call<com.example.evetagenda.model.Error> deleteEvent(@Query("id") String userId, @Query("eventId") String toDoId);

    @POST("PUT/updateEvent.php")
    Call<com.example.evetagenda.model.Error> updateEvent(@Body Event event);



}