package com.example.clientlist;

import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clientlist.dataBase.AppDataBase;
import com.example.clientlist.dataBase.AppExecuter;
import com.example.clientlist.dataBase.Client;
import com.example.clientlist.dataBase.DataAdapter;
import com.example.clientlist.settings.SettingsActivity;
import com.example.clientlist.utils.Constans;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private AppDataBase myDb;
    private DataAdapter adapter;
    private List<Client> listClient;
    private List<Client> phoneList;
    private RecyclerView recyclerView;
    private DrawerLayout drawerLayout;
    private DataAdapter.AdapterOnItemClicked adapterOnItemClicked;

    private ImageView imViewForCall;
    private ImageButton btnForSms;
    private TextView tvPhoneNumber;
    private int i = 0;
    private  String phone = " ";
    private static final int REQUEST_CALL = 1;
    public DataAdapter.ViewHolderData mViewHolderData;
    public DataAdapter mDataAdapter;

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
            public void onAdapterItemClicked(final int position) {
                Intent i = new Intent(MainActivity.this, EditActivity.class);
                i.putExtra(Constans.NAME_KEY, listClient.get(position).getName());
                i.putExtra(Constans.SECOND_NAME_KEY, listClient.get(position).getSecond_name());
                i.putExtra(Constans.PHONE_NUMBER_KEY, listClient.get(position).getPhone_number());
                i.putExtra(Constans.DESCRIPTION_KEY, listClient.get(position).getDescription());
                i.putExtra(Constans.SPECIAL_KEY, listClient.get(position).getSpecial());
                i.putExtra(Constans.ID_KEY, listClient.get(position).getId());
                startActivity(i);

            }
        };
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        nav_view.setNavigationItemSelectedListener(this);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, EditActivity.class);
                startActivity(i);
            }
        });
    }
    public void onClickPhoneCall(View view){
        Intent t = new Intent();


        final String[] f = new String[1];
        String d = "000000";
        AppExecuter.getInstance().getDiscIO().execute(new Runnable() {
            @Override
            public void run() {
                listClient = myDb.clientDAO().getClientList();
                AppExecuter.getInstance().getMainIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        if(adapter != null){
                           // adapter.ge;
                        }
                    }
                });
            }
        });

        Log.d("MyLog","getClientList 22  :    " + listClient);
        Log.d("MyLog","getClientListFFF :    " + f[0]);
        if (d.trim().length()>0) {
            if (ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);
            }else {
                String s = "tel:" + d;
                Intent intent2 = new Intent(Intent.ACTION_CALL);
                intent2.setData(Uri.parse(s));
                startActivity(intent2);
            }
        } else {
            Toast.makeText(MainActivity.this, "Number is empty", Toast.LENGTH_SHORT).show();
        }
    }


    private void init (){

        tvPhoneNumber = findViewById(R.id.tvPhone);
        imViewForCall = findViewById(R.id.imageView_call);
        //btnForCall = findViewById(R.id.imageButton_for_sms);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myDb = AppDataBase.getInstance(getApplicationContext());
        listClient = new ArrayList<>();
        adapter = new DataAdapter(listClient,adapterOnItemClicked,this);
        recyclerView.setAdapter(adapter);
        }

    @Override
    protected void onResume() {
        init();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu,menu);
        SearchManager searchManager = (SearchManager) this.getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        assert searchManager != null;
        searchView.setSearchableInfo(searchManager.getSearchableInfo(this.getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {
                AppExecuter.getInstance().getDiscIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        listClient = myDb.clientDAO().getClientListName(newText);
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
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.id_client){
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
        else if(id == R.id.id_web){
            goTo(getString(R.string.browser_link));
        }else if(id == R.id.id_settings){
           Intent i = new Intent(MainActivity.this, SettingsActivity.class);
           startActivity(i);
        }else if(id == R.id.id_special){
            AppExecuter.getInstance().getDiscIO().execute(new Runnable() {
                @Override
                public void run() {
                    listClient = myDb.clientDAO().getClientListSpecial();
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
        }else if(id == R.id.id_important){
            AppExecuter.getInstance().getDiscIO().execute(new Runnable() {
                @Override
                public void run() {
                    listClient = myDb.clientDAO().getClientListImportant(0);
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
        else if(id == R.id.id_normal){
            AppExecuter.getInstance().getDiscIO().execute(new Runnable() {
                @Override
                public void run() {
                    listClient = myDb.clientDAO().getClientListImportant(1);
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
        }else if(id == R.id.id_no_important){
            AppExecuter.getInstance().getDiscIO().execute(new Runnable() {
                @Override
                public void run() {
                    listClient = myDb.clientDAO().getClientListImportant(2);
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
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    private void goTo(String url){
        Intent browserIntent;
        browserIntent = new Intent(Intent.ACTION_VIEW);
        browserIntent.setData(Uri.parse(url));
        Intent chooser = Intent.createChooser(browserIntent,getString(R.string.browser_message));
        if(browserIntent.resolveActivity(getPackageManager()) != null){
            startActivity(chooser);
        }
    }
}



