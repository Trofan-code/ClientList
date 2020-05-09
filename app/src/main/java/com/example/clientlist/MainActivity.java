package com.example.clientlist;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.clientlist.dataBase.AppDataBase;
import com.example.clientlist.dataBase.AppExecuter;
import com.example.clientlist.dataBase.Client;
import com.example.clientlist.dataBase.DataAdapter;
import com.example.clientlist.utils.Constans;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private AppDataBase myDb;
    private DataAdapter adapter;
    private List<Client> listClient;
    private RecyclerView recyclerView;
    private DrawerLayout drawerLayout;
    private  DataAdapter.AdapterOnItemClicked adapterOnItemClicked;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        NavigationView nav_view = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.drawer_layout);
        adapterOnItemClicked = new DataAdapter.AdapterOnItemClicked() {
            @Override
            public void onAdapterItemClicked(int position) {
                Intent i = new Intent(MainActivity.this,EditActivity.class);
                i.putExtra(Constans.NAME_KEY,listClient.get(position).getName());
                i.putExtra(Constans.SECOND_NAME_KEY,listClient.get(position).getSecond_name());
                i.putExtra(Constans.PHONE_NUMBER_KEY,listClient.get(position).getPhone_number());
                i.putExtra(Constans.DESCRIPTION_KEY,listClient.get(position).getDescription());
                i.putExtra(Constans.SPECIAL_KEY,listClient.get(position).getSpecial());
                i.putExtra(Constans.ID_KEY,listClient.get(position).getId());
                startActivity(i);
            }
        };
        nav_view.setNavigationItemSelectedListener(this);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,EditActivity.class);
                startActivity(i);


            }
        });
    }


        private void init (){
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myDb = AppDataBase.getInstance(getApplicationContext());
        listClient = new ArrayList<>();
            adapter = new DataAdapter(listClient,adapterOnItemClicked);
            recyclerView.setAdapter(adapter);
        }

    @Override
    protected void onResume() {
        super.onResume();
        AppExecuter.getInstance().getDiscIO().execute(new Runnable() {
            @Override
            public void run() {
                listClient = myDb.clientDAO().getClientList();
                AppExecuter.getInstance().getMainIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        if(adapter != null){

                            adapter.updateAdapter(listClient);
                        }

                    }
                });
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.id_client){
            Toast.makeText(this,"CLIENT",Toast.LENGTH_SHORT).show();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}



