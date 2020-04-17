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
    @Insert
    void insertClient(Client client);
    @Update
    void updetClient(Client client);
    @Delete
    void deleteClient(Client client);
}
