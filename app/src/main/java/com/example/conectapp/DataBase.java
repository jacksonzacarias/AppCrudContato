package com.example.conectapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DataBase extends SQLiteOpenHelper {


    public DataBase(@Nullable Context context) {
        super(context, Constants.DATABESE_NAME, null, Constants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(Constants.CREATE_TEBLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF  EXISTS " + Constants.TABELA_NAME);
        onCreate(db);

    }

    public long insertContato(String imagem, String nome, String numero, String email, String bio, String adicionadoTimeStamp, String atualizadoTimeStamp) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.C_FOTO, imagem);
        values.put(Constants.C_NOME, nome);
        values.put(Constants.C_NUMERO, numero);
        values.put(Constants.C_EMAIL, email);
        values.put(Constants.C_BIO, bio);
        values.put(Constants.C_ADICIONADO_TIMESTAMP, adicionadoTimeStamp);
        values.put(Constants.C_ATUALIZADO_TIMESTAMP, atualizadoTimeStamp);

        long id = db.insert(Constants.TABELA_NAME, null, values);
        db.close();
        return id;

    }

    public void updateContato(String id, String foto, String nome, String numero, String email, String bio, String adicionadoTimeStamp, String atualizadoTimeStamp) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.C_FOTO, foto);
        values.put(Constants.C_NOME, nome);
        values.put(Constants.C_NUMERO, numero);
        values.put(Constants.C_EMAIL, email);
        values.put(Constants.C_BIO, bio);
        values.put(Constants.C_ADICIONADO_TIMESTAMP, adicionadoTimeStamp);
        values.put(Constants.C_ATUALIZADO_TIMESTAMP, atualizadoTimeStamp);

        db.update(Constants.TABELA_NAME,values, Constants.C_ID + " = ?", new String[]{id});
        db.close();

    }

    public ArrayList<ModeloContato> getAllData() {

        ArrayList<ModeloContato> arrayList = new ArrayList<>();

        String SelecionaQuery = "SELECT * FROM " + Constants.TABELA_NAME;
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(SelecionaQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ModeloContato modeloContato = new ModeloContato(
                        "" + cursor.getInt(cursor.getColumnIndexOrThrow(Constants.C_ID)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_FOTO)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_NOME)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_NUMERO)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_EMAIL)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_BIO)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_ADICIONADO_TIMESTAMP)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_ATUALIZADO_TIMESTAMP))
                );

                arrayList.add(modeloContato);
            } while (cursor.moveToNext());
    }
        db.close();
        return arrayList;
        }
}
