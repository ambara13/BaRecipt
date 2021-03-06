package com.example.barecipt;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class CreateRecipt extends AppCompatActivity {
    private TextView namaResep, bahanMasakan, langkahMemasak, waktu, status, inputMasakanLain;
    private SeekBar seekbarWaktuMasak;
    private String resep_nama, hasilWaktuMasak, resep_pilihan, resep_bahan, resep_langkah;
    private String hasilJenisMasakan = "";
    private RadioGroup radioGroup_pilihanMasakan, radiogroupWaktuMasak;
    private RadioButton radioButtonPilihanMasakan;
    private Button btnSubmit;
    private CheckBox masakanBali, masakanIndonesia, masakanEropa, masakanChina, masakanLain;
    private boolean isValidasi = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipt_input);

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
        btnSubmit = findViewById(R.id.btn_submit);

        //seekbar
        seekbarWaktuMasak.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                hasilWaktuMasak = String.valueOf(i);
                waktu.setText(hasilWaktuMasak);
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
        //button submit
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

                isValidasi = validasi();
                if(isValidasi){
                    AlertDialog.Builder builder = new AlertDialog.Builder(CreateRecipt.this);
                    builder.setIcon(R.drawable.warning_round);
                    builder.setTitle("Tambah Resep");
                    builder.setMessage(
                            "Apakah kamu sudah selesai dengan resep ini ?\n\n-------------------------------------------------------------------\n\n" +
                                    "Nama Masakan  : " +resep_nama + "\n\n" +
                                    "Lama Memasak  : " +resep_waktu + resep_status_waktu +"\n\n" +
                                    "Pilihan Masakan  : " + resep_pilihan + "\n\n" +
                                    "Jenis Masakan  : \n" + hasilJenisMasakan + "\n" +
                                    "Bahan Masakan  : \n" + resep_bahan + "\n\n" +
                                    "Langkah Memasak  : \n" + resep_langkah + ""
                    );
                    builder.setPositiveButton("Selesai", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(getApplicationContext(), "Masakan berhasil kamu simpan !", Toast.LENGTH_SHORT).show();
                            DbHelper db = new DbHelper(getApplicationContext());
                            ReciptHandler resepHandler = new ReciptHandler();
                            resepHandler.setNamaResep(resep_nama);
                            resepHandler.setLamaMemasak(hasilWaktuMasak);
                            resepHandler.setStatusLamaMemasak(resep_status_waktu);
                            resepHandler.setPilihan(resep_pilihan);
                            resepHandler.setJenis(hasilJenisMasakan);
                            resepHandler.setBahan(resep_bahan);
                            resepHandler.setLangkah(resep_langkah);

                            boolean tambahResep = db.insertData(resepHandler);

                            if(tambahResep){
                                Toast.makeText(CreateRecipt.this, "Berhasil Tambah Data", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(CreateRecipt.this, "Gagal Tambah Data", Toast.LENGTH_SHORT).show();
                            }

                            Intent intent = new Intent(CreateRecipt.this, MainActivity.class);
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
            }
        });
    }

    private boolean validasi(){
        if(namaResep.length() == 0){
            Toast.makeText(CreateRecipt.this, "Nama Masakan Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(radioButtonPilihanMasakan.length() == 0){
            Toast.makeText(CreateRecipt.this, "Pilihan Masakan Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(bahanMasakan.length() == 0){
            Toast.makeText(CreateRecipt.this, "Bahan Masakan Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(langkahMemasak.length() == 0){
            Toast.makeText(CreateRecipt.this, "Langkah Memasak Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!masakanBali.isChecked() && !masakanIndonesia.isChecked() && !masakanEropa.isChecked() && !masakanChina.isChecked() && !masakanLain.isChecked()){
            Toast.makeText(CreateRecipt.this, "Jenis Masakan Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            return true;
        }
    }
}
