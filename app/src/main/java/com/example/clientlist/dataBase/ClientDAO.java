package com.example.clientlist.dataBase;


import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao

public interface ClientDAO {
    @Query("Select * from client_list")
    List<Client> getClientList();
    @Query("Select * from client_list where special is 1")
    List<Client> getClientListSpecial();
    @Query("Select * from client_list where importance is :importance")
    List<Client> getClientListImportant(int importance);
    @Query("Select * from client_list where name Like '%' || :name || '%'")
    List<Client> getClientListName(String name);

    // @Query("Select phone_number from client_list ")
   // @Query("Select phone_number from client_list ")
   // List<Client> getClientListPhone();
    //error: Entities and POJOs must have a usable public constructor. You can have an empty constructor or a constructor
    // whose parameters match the fields (by name and type).
    //Tried the following constructors but they failed to match:
    //Client(int,java.lang.String,java.lang.String,java.lang.String,int,java.lang.String,int) ->
    // [param:id -> matched field:unmatched, param:name -> matched field:name, param:second_name -> matched field:second_name,
    // param:phone_number -> matched field:phone_number, param:importance -> matched field:importance,
    // param:description -> matched field:description, param:special -> matched field:special]

    //error: The columns returned by the query does not have the fields [importance,special] in com.example.clientlist.dataBase.
    // Client even though they are annotated as non-null or primitive. Columns returned by the query: [phone_number]

    @Insert
    void insertClient(Client client);
    @Update
    void updateClient(Client client);
    @Delete
    void deleteClient(Client client);
}
