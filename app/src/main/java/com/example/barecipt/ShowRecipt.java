package com.example.barecipt;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ShowRecipt extends AppCompatActivity {
    private TextView tampilNamaResep, tampilWaktuMasak, tampilPilihanMasakan, tampilJenisMasakan,
            tampilBahanMasakan, tampilLangkahMemasak;
//    private String
    private Button btnHapus, btnEdit;
    private Integer id = 0;
    private ArrayList<ReciptHandler> resepHandler = new ArrayList<ReciptHandler>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipt_show);

        tampilNamaResep = findViewById(R.id.namaMasakan);
        tampilWaktuMasak = findViewById(R.id.inputLamaMemasak);
        tampilPilihanMasakan = findViewById(R.id.pilihanMasakan);
        tampilJenisMasakan = findViewById(R.id.jenisMasakan);
        tampilBahanMasakan = findViewById(R.id.bahanMasakan);
        tampilLangkahMemasak = findViewById(R.id.langkahMemasak);

        tampilNamaResep.setEnabled(false);
        tampilWaktuMasak.setEnabled(false);
        tampilPilihanMasakan.setEnabled(false);
        tampilJenisMasakan.setEnabled(false);
        tampilBahanMasakan.setEnabled(false);
        tampilLangkahMemasak.setEnabled(false);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);

        if(id>0){
            final DbHelper dbh = new DbHelper(getApplicationContext());
            Cursor cursor = dbh.showDetail(id);
            cursor.moveToFirst();
            if (cursor.getCount()>0){
                while(!cursor.isAfterLast()){
                    tampilNamaResep.setText(cursor.getString(cursor.getColumnIndexOrThrow("nama_resep")));
                    tampilWaktuMasak.setText(cursor.getString(cursor.getColumnIndexOrThrow("lama_masakan")) + cursor.getString(cursor.getColumnIndexOrThrow("status_lama_masakan")));
                    tampilPilihanMasakan.setText(cursor.getString(cursor.getColumnIndexOrThrow("pilihan")));
                    tampilJenisMasakan.setText(cursor.getString(cursor.getColumnIndexOrThrow("jenis")));
                    tampilBahanMasakan.setText(cursor.getString(cursor.getColumnIndexOrThrow("bahan")));
                    tampilLangkahMemasak.setText(cursor.getString(cursor.getColumnIndexOrThrow("langkah")));
                    cursor.moveToNext();
                }
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "Menampilkan Activity", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this,"Menjeda Activity", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Toast.makeText(this," Memulai Activity Kembali", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this,"Menghancurkan Activity", Toast.LENGTH_SHORT).show();
    }
}