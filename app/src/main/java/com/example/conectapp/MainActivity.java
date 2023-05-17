package com.example.conectapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton addButton;

    private RecyclerView contatoRView;

    private DataBase db;

    private ModeloContato modeloContato;

    private ControleContatos controleContatos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //inicializando o db

        db = new DataBase(this);



//       Cria uma nova atividade para adicionar um novo contato
        addButton = findViewById(R.id.addButton);
        contatoRView = findViewById(R.id.contatoRView);
        contatoRView.setHasFixedSize(true);

        addButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditContato.class);
                intent.putExtra("modoDeEdicao", false);
                startActivity(intent);

            }
        });
        
        carregandoDados();

    }

    private void carregandoDados() {
        controleContatos = new ControleContatos(this, db.getAllData());
        contatoRView.setAdapter(controleContatos);

    }
    @Override
    protected void onResume() {
        super.onResume();
        carregandoDados(); //atualiza os dados
    }
}