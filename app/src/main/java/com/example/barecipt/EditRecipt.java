package com.example.barecipt;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class EditRecipt extends AppCompatActivity {
    private TextView namaResep, bahanMasakan, langkahMemasak, waktu, status, inputMasakanLain;
    private SeekBar seekbarWaktuMasak;
    private String resep_nama, hasilWaktuMasak, resep_pilihan, resep_bahan, resep_langkah, waktu_sementara;
    private String hasilJenisMasakan = "";
    private RadioGroup radioGroup_pilihanMasakan, radiogroupWaktuMasak;
    private RadioButton radioButtonPilihanMasakan, vegetarian, nonVegetarian, status_menit, status_jam, status_hari;
    private Button btnSubmit;
    private CheckBox masakanBali, masakanIndonesia, masakanEropa, masakanChina, masakanLain;
    private Integer id = 0;
    private Button btnHapus;
    private ArrayList<ReciptHandler> resepHandler = new ArrayList<ReciptHandler>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        namaResep = findViewById(R.id.inputNamaResep);
        waktu = findViewById(R.id.nilaiWaktuMasak);
        status = findViewById(R.id.statusWaktuMasak);
        seekbarWaktuMasak = findViewById(R.id.seekbarWaktuMasak);
        radioGroup_pilihanMasakan = findViewById(R.id.radioGroup_pilihanMasakan);
        radiogroupWaktuMasak = findViewById(R.id.radiogroup_waktuMasak);
        masakanBali = findViewById(R.id.masakanBali);
        masakanIndonesia = findViewById(R.id.masakanIndonesia);
        masakanEropa = findViewById(R.id.masakanEropa);
        masakanChina = findViewById(R.id.masakanChina);
        masakanLain = findViewById(R.id.masakanLain);
        inputMasakanLain = findViewById(R.id.inputMasakanLain);
        bahanMasakan = findViewById(R.id.inputBahanMasakan);
        langkahMemasak = findViewById(R.id.inputLangkahMemasak);
        vegetarian = findViewById(R.id.veget);
        nonVegetarian = findViewById(R.id.nonVeget);
        status_menit = findViewById(R.id.radio_menit);
        status_jam = findViewById(R.id.radio_jam);
        status_hari = findViewById(R.id.radio_hari);
        btnSubmit = findViewById(R.id.btn_submit);
        btnHapus = findViewById(R.id.btn_delete);
        String result = "";

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);

        if(id>0){
            final DbHelper dbh = new DbHelper(getApplicationContext());
            Cursor cursor = dbh.showDetail(id);
            cursor.moveToFirst();
            if (cursor.getCount()>0){
                while(!cursor.isAfterLast()){
                    namaResep.setText(cursor.getString(cursor.getColumnIndexOrThrow("nama_resep")));
                    waktu.setText(cursor.getString(cursor.getColumnIndexOrThrow("lama_masakan")));
                    status.setText(cursor.getString(cursor.getColumnIndexOrThrow("status_lama_masakan")));
                    if(((cursor.getString(cursor.getColumnIndexOrThrow("status_lama_masakan")))).equals(" menit")){
                        status_menit.setChecked(true);
                    }
                    else if(((cursor.getString(cursor.getColumnIndexOrThrow("status_lama_masakan")))).equals(" jam")){
                        status_jam.setChecked(true);
                    }
                    else if(((cursor.getString(cursor.getColumnIndexOrThrow("status_lama_masakan")))).equals(" hari")){
                        status_hari.setChecked(true);
                    }
                    if(((cursor.getString(cursor.getColumnIndexOrThrow("pilihan")))).equals("Vegetarian")){
                        vegetarian.setChecked(true);
                    }
                    else if(((cursor.getString(cursor.getColumnIndexOrThrow("pilihan")))).equals("Non Vegetarian")){
                        nonVegetarian.setChecked(true);
                    }
                    result=((cursor.getString(cursor.getColumnIndexOrThrow("jenis"))));

                    if(result.contains("Masakan Bali")){
                        masakanBali.setChecked(true);
                    }
                    if(result.contains("Masakan Indonesia")){
                        masakanIndonesia.setChecked(true);
                    }
                    if(result.contains("Masakan Eropa")){
                        masakanEropa.setChecked(true);
                    }
                    if(result.contains("Masakan China")){
                        masakanChina.setChecked(true);
                    }
                    if(!result.contains("Masakan Bali") && !result.contains("Masakan Indonesia") && !result.contains("Masakan Eropa") && !result.contains("Masakan China")){
                        masakanLain.setChecked(true);
                    }

                    bahanMasakan.setText(cursor.getString(cursor.getColumnIndexOrThrow("bahan")));
                    langkahMemasak.setText(cursor.getString(cursor.getColumnIndexOrThrow("langkah")));
                    cursor.moveToNext();
                }
            }
        }
        //seekbar
        waktu_sementara = waktu.getText().toString();
        seekbarWaktuMasak.setProgress(Integer.parseInt(waktu_sementara));
        seekbarWaktuMasak.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                waktu_sementara = String.valueOf(i);
                waktu.setText(waktu_sementara);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        radiogroupWaktuMasak.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId){
                    case R.id.radio_menit:
                        status.setText(" menit" );
                        break;
                    case R.id.radio_jam:
                        status.setText(" jam" );
                        break;
                    case R.id.radio_hari:
                        status.setText(" hari" );
                        break;
                }
            }
        });

        masakanLain.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    inputMasakanLain.setVisibility(View.VISIBLE);
                }
                if(!b){
                    inputMasakanLain.setVisibility(View.GONE);
                }
            }
        });

        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialogAlertBuilder = new AlertDialog.Builder(EditRecipt.this);
                dialogAlertBuilder.setTitle("Konfirmasi");
                dialogAlertBuilder
                        .setMessage("Yakin menghapus data?")
                        .setPositiveButton("Yakin", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                DbHelper dbh = new DbHelper(getApplicationContext());

                                boolean hapusData = dbh.deleteData(id);

                                if (hapusData) {
                                    Toast.makeText(EditRecipt.this, "Hapus Data Berhasil", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(EditRecipt.this, "Hapus Data Gagal", Toast.LENGTH_SHORT).show();
                                }
                                Intent mainIntent = new Intent(EditRecipt.this,MainActivity.class);
                                startActivity(mainIntent);
                            }
                        })
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog dialog = dialogAlertBuilder.create();
                dialog.show();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //radiobutton
                hasilJenisMasakan = "";

                if(masakanBali.isChecked()){
                    hasilJenisMasakan +=masakanBali.getText().toString()+"\n";
                }
                if(masakanIndonesia.isChecked()){
                    hasilJenisMasakan +=masakanIndonesia.getText().toString()+"\n";
                }
                if(masakanEropa.isChecked()){
                    hasilJenisMasakan +=masakanEropa.getText().toString()+"\n";
                }
                if(masakanChina.isChecked()){
                    hasilJenisMasakan +=masakanChina.getText().toString()+"\n";
                }
                if(masakanLain.isChecked()){
                    hasilJenisMasakan +=inputMasakanLain.getText().toString()+"\n";
                }

                int radioId = radioGroup_pilihanMasakan.getCheckedRadioButtonId();
                radioButtonPilihanMasakan = findViewById(radioId);

                resep_nama = namaResep.getText().toString();
                resep_pilihan = radioButtonPilihanMasakan.getText().toString();
                resep_bahan = bahanMasakan.getText().toString();
                resep_langkah = langkahMemasak.getText().toString();
                String resep_status_waktu = status.getText().toString();
                String resep_waktu = waktu.getText().toString();

                    AlertDialog.Builder builder = new AlertDialog.Builder(EditRecipt.this);
                    builder.setIcon(R.drawable.warning_round);
                    builder.setTitle("Edit Resep");
                    builder.setMessage(
                            "Apakah kamu sudah selesai dengan resep ini ?\n\n" +
                                    "Nama Masakan  : " +resep_nama + "\n\n" +
                                    "Lama Memasak  : " +resep_waktu + resep_status_waktu +"\n\n" +
                                    "Pilihan Masakan  : " + resep_pilihan + "\n\n" +
                                    "Jenis Masakan  : " + hasilJenisMasakan + "\n" +
                                    "Bahan Masakan  : " + resep_bahan + "\n\n" +
                                    "Langkah Memasak  : " + resep_langkah + ""
                    );
                    builder.setPositiveButton("Selesai", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(getApplicationContext(), "Masakan berhasil kamu simpan !", Toast.LENGTH_SHORT).show();
                            DbHelper db = new DbHelper(getApplicationContext());
                            ReciptHandler resepHandler = new ReciptHandler();
                            resepHandler.setNamaResep(resep_nama);
                            resepHandler.setLamaMemasak(waktu_sementara);
                            resepHandler.setStatusLamaMemasak(resep_status_waktu);
                            resepHandler.setPilihan(resep_pilihan);
                            resepHandler.setJenis(hasilJenisMasakan);
                            resepHandler.setBahan(resep_bahan);
                            resepHandler.setLangkah(resep_langkah);

                            boolean tambahResep = db.editData(resepHandler, id);

                            if(tambahResep){
                                Toast.makeText(EditRecipt.this, "Berhasil Edit Data", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(EditRecipt.this, "Gagal Edit Data", Toast.LENGTH_SHORT).show();
                            }
                            db.close();

                            Intent intent = new Intent(EditRecipt.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });

                    builder.setNegativeButton("Belum", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
            }
        });

    }
}
