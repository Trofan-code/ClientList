package com.example.clientlist.dataBase;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity (tableName = "client_list")

public class Client {
    @PrimaryKey (autoGenerate = true)
    private int id;
    @ColumnInfo (name = "name")
    private String name;
    @ColumnInfo (name = "second_name")
    private String second_name;
    @ColumnInfo (name = "phone_number")
    private String phone_number;
    @ColumnInfo (name = "importance")
    private int importance;
    @ColumnInfo (name = "description")
    private String description;
    @ColumnInfo (name = "special")
    private int special;


    public Client(int id, String name, String second_name, String phone_number, int importance, String description, int special) {
        this.id = id;
        this.name = name;
        this.second_name = second_name;
        this.phone_number = phone_number;
        this.importance = importance;
        this.description = description;
        this.special = special;
    }
    @Ignore
    public Client(String name, String second_name, String phone_number, int importance, String description, int special) {
        this.name = name;
        this.second_name = second_name;
        this.phone_number = phone_number;
        this.importance = importance;
        this.description = description;
        this.special = special;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecond_name() {
        return second_name;
    }

    public void setSecond_name(String second_name) {
        this.second_name = second_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public int getImportance() {
        return importance;
    }

    public void setImportance(int importance) {
        this.importance = importance;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSpecial() {
        return special;
    }

    public void setSpecial(int special) {
        this.special = special;
    }
}
