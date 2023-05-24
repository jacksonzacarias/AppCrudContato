package com.example.conectapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton addButton;

    private RecyclerView contatoRView;

    private DataBase db;

    private ModeloContato modeloContato;

    private ControleContatos controleContatos;

    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //inicializando o db

        actionBar = getSupportActionBar();

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {



        getMenuInflater().inflate(R.menu.top_main_delete, menu);

        MenuItem item = menu.findItem(R.id.buscaContato);

        SearchView searchView = (SearchView) item.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query){
                buscarContato(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                buscarContato(newText);
                return false;
            }
        });
        return true;


    }

    private void buscarContato(String query) {
        controleContatos = new ControleContatos(this, db.getBuscaContato(query));
        contatoRView.setAdapter(controleContatos);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.deleteAlldelete:
                db.deleteAllContato();
                onResume();
                break;
        }
        return true;
    }
}