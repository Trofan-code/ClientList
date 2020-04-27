package com.example.clientlist;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.clientlist.dataBase.AppDataBase;
import com.example.clientlist.dataBase.AppExecuter;
import com.example.clientlist.dataBase.Client;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EditActivity extends AppCompatActivity {
    private EditText edName, edSecondName, edPhoneNumber, edDiscription;
    private CheckBox checkBoxImportance_1, checkBoxImportance_2, checkBoxImportance_3, checkBoxSpecial;
    private AppDataBase myDataBase;
    private int importance = 3;
    private int special = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.edit_layout);
        init();
        FloatingActionButton fab = findViewById(R.id.floatingActionButtonSave);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImportanceFromCheckBox();

                if (!TextUtils.isEmpty(edName.getText().toString()) && !TextUtils.isEmpty(edSecondName.getText().toString())
                        && !TextUtils.isEmpty(edPhoneNumber.getText().toString()) && !TextUtils.isEmpty(edDiscription.getText().toString())){

                    AppExecuter.getInstance().getDiscIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            Client client = new Client(edName.getText().toString(), edSecondName.getText().toString(), edPhoneNumber.getText().toString(),
                                    importance,edDiscription.getText().toString(), special);
                            myDataBase.clientDAO().insertClient(client);
                        }
                    });
            }

            }
        });
    }
    private void init(){
        myDataBase = AppDataBase.getInstance(getApplicationContext());

        edName = findViewById(R.id.edName);
        edSecondName = findViewById(R.id.edSecName);
        edPhoneNumber = findViewById(R.id.edPhoneNum);
        edDiscription = findViewById(R.id.tvDiscription);
        checkBoxImportance_1 = findViewById(R.id.checkBoxImportance_1);
        checkBoxImportance_2 = findViewById(R.id.checkBoxImportance_2);
        checkBoxImportance_3 = findViewById(R.id.checkBoxImportance_3);
        checkBoxSpecial = findViewById(R.id.checkBoxSpecial);
    }

    public void onClickCheckBox1 (View view){
        checkBoxImportance_2.setChecked(false);
        checkBoxImportance_3.setChecked(false);

    }
    public void onClickCheckBox2 (View view){
        checkBoxImportance_1.setChecked(false);
        checkBoxImportance_3.setChecked(false);

    }
    public void onClickCheckBox3 (View view){
        checkBoxImportance_1.setChecked(false);
        checkBoxImportance_2.setChecked(false);

    }
    private void getImportanceFromCheckBox(){
        if (checkBoxImportance_1.isChecked()){
            importance = 0;
        } else if (checkBoxImportance_2.isChecked()){
            importance = 1;
        } if (checkBoxImportance_3.isChecked()){
            importance = 2;
        }
        if (checkBoxSpecial.isChecked()){
            special =1;
        }
    }


}
