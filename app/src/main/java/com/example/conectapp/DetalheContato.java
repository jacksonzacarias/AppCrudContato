package com.example.conectapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DetalheContato extends AppCompatActivity {

    private TextView  nomeVt,telefoneTv,emailTv,addedTimeTv,updatedTimeTv, bioTv ;
    private ImageView fotoFileIv;
    private DataBase dataBase;

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_contato);


        dataBase = new DataBase(this);

        Intent intent = getIntent();
        id = intent.getStringExtra("ID");

        nomeVt = findViewById(R.id.nomeVt);
        telefoneTv = findViewById(R.id.telefoneTv);
        emailTv = findViewById(R.id.emailTv);
        addedTimeTv = findViewById(R.id.addedTimeTv);
        updatedTimeTv = findViewById(R.id.updatedTimeTv);
        bioTv = findViewById(R.id.bioTv);

        fotoFileIv = findViewById(R.id.fotoFileIv);

        carregandoDadosPorId();
    }
    private  void carregandoDadosPorId(){

        String SelecionaQuery = "SELECT * FROM "+Constants.TABELA_NAME + " WHERE " + Constants.C_ID + " =\"" + id + "\"";

        SQLiteDatabase db = dataBase.getWritableDatabase();
        Cursor cursor = db.rawQuery(SelecionaQuery, null);

        if (cursor.moveToFirst()){
            do {
                String image = "" + cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_FOTO));
                String nome ="" + cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_NOME));
                String telefone ="" + cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_NUMERO));
                String email = "" + cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_EMAIL));
                String bio = "" + cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_BIO));
                String addTime = "" + cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_ADICIONADO_TIMESTAMP));
                String updateTime = "" + cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_ATUALIZADO_TIMESTAMP));

//                Calendar calendar = Calendar.getInstance(Locale.getDefault());


                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(Long.parseLong(addTime));

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                String addtime = dateFormat.format(calendar.getTime());

                calendar.setTimeInMillis(Long.parseLong(updateTime));
                String timeUpdate = dateFormat.format(calendar.getTime());

                nomeVt.setText(nome);
                telefoneTv.setText(telefone);
                emailTv.setText(email);
                bioTv.setText(bio);
                addedTimeTv.setText(addtime);
                updatedTimeTv.setText(timeUpdate);



                if(image.equals("null")){
                    fotoFileIv.setImageResource(R.drawable.baseline_perm_identity_24);
                }else{
                    fotoFileIv.setImageURI(Uri.parse(image));
                }

            }while (cursor.moveToNext());
        }

    }
}