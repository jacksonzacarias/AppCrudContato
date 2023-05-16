package com.example.conectapp;

public class Constants {

    public static final String DATABESE_NAME = "CONTATO_DB";

    public static final int DATABASE_VERSION = 1;

    public static final String TABELA_NAME = "CONTATO_TABELA";

    public static final String C_ID = "ID";
    public static final String C_FOTO = "FOTO";
    public static final String C_NOME = "NOME";
    public static final String C_NUMERO = "NUMERO";
    public static final String C_EMAIL = "EMAIL";
    public static final String C_BIO = "BIO";
    public static final String C_ADICIONADO_TIMESTAMP = "TIMESTAMP";
    public static final String C_ATUALIZADO_TIMESTAMP = "ATUALIZADO_TIMESTAMP";


    public static final String CREATE_TEBLE = "CREATE TABLE " + TABELA_NAME + "("
            + C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + C_FOTO + " TEXT,"
            + C_NOME + " TEXT,"
            + C_NUMERO + " TEXT,"
            + C_EMAIL + " TEXT,"
            + C_BIO + " TEXT,"
            + C_ADICIONADO_TIMESTAMP + " TEXT,"
            + C_ATUALIZADO_TIMESTAMP + " TEXT);";

}
