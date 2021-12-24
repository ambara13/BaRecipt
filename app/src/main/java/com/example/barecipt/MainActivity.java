package com.example.barecipt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private DbHelper db;
    protected RecyclerView recyclerView;
    protected RecyclerView.Adapter mainAdapter;
    private ArrayList<ReciptHandler> resepHandler = new ArrayList<ReciptHandler>();
    private TextView namaUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview);
        
        db = new DbHelper(this);

        final DbHelper dbh = new DbHelper(getApplicationContext());
        Cursor cursor = dbh.showData();
        cursor.moveToFirst();
        if(cursor.getCount()>0){
            while(!cursor.isAfterLast()){
                ReciptHandler resepHandlerList = new ReciptHandler();
                resepHandlerList.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                resepHandlerList.setNamaResep(cursor.getString(cursor.getColumnIndexOrThrow("nama_resep")));
                resepHandlerList.setPilihan(cursor.getString(cursor.getColumnIndexOrThrow("pilihan")));
                resepHandler.add(resepHandlerList);
                cursor.moveToNext();
            }
        }

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        mainAdapter = new MainAdapter(resepHandler, MainActivity.this, recyclerView);
        recyclerView.setAdapter(mainAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.about:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("About BaRecipt");
                builder.setMessage(
                        "BaRecipt merupakan aplikasi pencatatan resep makanan dimana user dapat" +
                                " menginputkan resep masakannya sendiri ke dalam aplikasi yang" +
                                " disimpan ke dalam database\n\n" +
                                "Nama  : I Gede Nyoman Ambara Yasa\n" +
                                "NIM     : 1905551115"
                );
                builder.setNegativeButton("Kembali", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }


    public void addRecipt(View view) {
        Intent intent = new Intent(MainActivity.this, CreateRecipt.class);
        startActivity(intent);

    }
}