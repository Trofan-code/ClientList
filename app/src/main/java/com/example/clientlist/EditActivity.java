package com.example.clientlist;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.clientlist.dataBase.AppDataBase;
import com.example.clientlist.dataBase.AppExecuter;
import com.example.clientlist.dataBase.Client;
import com.example.clientlist.utils.Constans;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class EditActivity extends AppCompatActivity {
    private EditText edName, edSecondName, edPhoneNumber, edDiscription;
    private CheckBox checkBoxImportance_1, checkBoxImportance_2, checkBoxImportance_3, checkBoxSpecial;
    private AppDataBase myDataBase;
    private int importance = 3;
    private int special = 0;
    private FloatingActionButton fab;
    private CheckBox[] checkBoxArray = new CheckBox[3];
    private boolean isEdit = false;
    private boolean newUser = false;
    private int id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.edit_layout);
        init();
        getMyIntent();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImportanceFromCheckBox();
                if (!TextUtils.isEmpty(edName.getText().toString()) && !TextUtils.isEmpty(edSecondName.getText().toString())
                        && !TextUtils.isEmpty(edPhoneNumber.getText().toString()) && !TextUtils.isEmpty(edDiscription.getText().toString())) {

                    AppExecuter.getInstance().getDiscIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            if(isEdit){
                                Client client = new Client(edName.getText().toString(), edSecondName.getText().toString(), edPhoneNumber.getText().toString(),
                                        importance, edDiscription.getText().toString(), special);
                                client.setId(id);
                                myDataBase.clientDAO().updateClient(client);
                                finish();

                            }else{
                                Client client = new Client(edName.getText().toString(), edSecondName.getText().toString(), edPhoneNumber.getText().toString(),
                                        importance, edDiscription.getText().toString(), special);
                                myDataBase.clientDAO().insertClient(client);
                                finish();
                            }

                        }
                    });
                }

            }
        });
    }

    private void init() {
        fab = findViewById(R.id.floatingActionButtonSave);
        myDataBase = AppDataBase.getInstance(getApplicationContext());
        edName = findViewById(R.id.edName);
        edSecondName = findViewById(R.id.edSecName);
        edPhoneNumber = findViewById(R.id.edPhoneNum);
        edDiscription = findViewById(R.id.tvDescription);
        checkBoxImportance_1 = findViewById(R.id.checkBoxImportance_1);
        checkBoxImportance_2 = findViewById(R.id.checkBoxImportance_2);
        checkBoxImportance_3 = findViewById(R.id.checkBoxImportance_3);
        checkBoxSpecial = findViewById(R.id.checkBoxSpecial);
        checkBoxArray[0] = checkBoxImportance_1;
        checkBoxArray[1] = checkBoxImportance_2;
        checkBoxArray[2] = checkBoxImportance_3;
    }

    public void onClickCheckBox1(View view) {
        checkBoxImportance_2.setChecked(false);
        checkBoxImportance_3.setChecked(false);
    }

    public void onClickCheckBox2(View view) {
        checkBoxImportance_1.setChecked(false);
        checkBoxImportance_3.setChecked(false);
    }

    public void onClickCheckBox3(View view) {
        checkBoxImportance_1.setChecked(false);
        checkBoxImportance_2.setChecked(false);
    }

    private void getImportanceFromCheckBox() {
        if (checkBoxImportance_1.isChecked()) {
            importance = 0;
        } else if (checkBoxImportance_2.isChecked()) {
            importance = 1;
        }
        if (checkBoxImportance_3.isChecked()) {
            importance = 2;
        }
        if (checkBoxSpecial.isChecked()) {
            special = 1;
        }
    }

    private void getMyIntent() {
        Intent i = getIntent();
        if (i != null) {
            if (i.getStringExtra(Constans.NAME_KEY) != null) {
                setIsEdit(false);
                edName.setText(i.getStringExtra(Constans.NAME_KEY));
                edSecondName.setText(i.getStringExtra(Constans.SECOND_NAME_KEY));
                edPhoneNumber.setText(i.getStringExtra(Constans.PHONE_NUMBER_KEY));
                edDiscription.setText(i.getStringExtra(Constans.DESCRIPTION_KEY));
                checkBoxArray[i.getIntExtra(Constans.IMPORTANCE_KEY, 0)].setChecked(true);
                if (i.getIntExtra(Constans.SPECIAL_KEY, 0) == 1)checkBoxSpecial.setChecked(true);
                newUser = false;
                id = i.getIntExtra(Constans.ID_KEY,0);
            }else {
                newUser = true;
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!newUser) {
            getMenuInflater().inflate(R.menu.main, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.id_edit) {
             setIsEdit(true);
        }else if(id == R.id.id_delete){
            deleteDialog();
        }
        return true;
    }

    private void setIsEdit(boolean isEdit){
        if(isEdit) {
            fab.show();
        }else {
            fab.hide();
        }
        this.isEdit = isEdit;
        checkBoxImportance_1.setClickable(isEdit);
        checkBoxImportance_2.setClickable(isEdit);
        checkBoxImportance_3.setClickable(isEdit);
        checkBoxSpecial.setClickable(isEdit);
        edName.setClickable(isEdit);
        edName.setFocusable(isEdit);
        edSecondName.setClickable(isEdit);
        edSecondName.setFocusable(isEdit);
        edPhoneNumber.setClickable(isEdit);
        edPhoneNumber.setFocusable(isEdit);
        edDiscription.setClickable(isEdit);
        edDiscription.setFocusable(isEdit);
        edName.setFocusableInTouchMode(isEdit);
        edSecondName.setFocusableInTouchMode(isEdit);
        edPhoneNumber.setFocusableInTouchMode(isEdit);
        edDiscription.setFocusableInTouchMode(isEdit);
    }
    private void deleteDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_message);
        builder.setTitle(R.string.delete);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AppExecuter.getInstance().getDiscIO().execute(new Runnable() {
                    @Override
                    public void run() {

                            Client client = new Client(edName.getText().toString(), edSecondName.getText().toString(), edPhoneNumber.getText().toString(),
                                    importance, edDiscription.getText().toString(), special);
                            client.setId(id);
                            myDataBase.clientDAO().deleteClient(client);
                            finish();
                    }
                });

            }
        });

        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();

    }

}
