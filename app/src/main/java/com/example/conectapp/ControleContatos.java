package com.example.conectapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ControleContatos  extends  RecyclerView.Adapter<ControleContatos.ContactViewHolder>{

    private Context context;
    private ArrayList<ModeloContato> contatoArrayList;

    //criando o construtor


    public ControleContatos(Context context, ArrayList<ModeloContato> contatoArrayList) {
        this.context = context;
        this.contatoArrayList = contatoArrayList;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_contato_item, parent, false);
        ContactViewHolder vhcontato = new ContactViewHolder(view);
        return vhcontato;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        ModeloContato modeloContato = contatoArrayList.get(position);

        String id = modeloContato.getId();
        String nome = modeloContato.getNome();
        String numero = modeloContato.getNumero();
        String foto = modeloContato.getFoto();
        String email = modeloContato.getEmail();
        String bio = modeloContato.getBio();
        String adicionadoTimeStamp = modeloContato.getAdicionadoTimeStamp();
        String atualizadoTimeStamp = modeloContato.getAtualizadoTimeStamp();

        holder.contatoNome.setText(nome);
        if (foto.equals("")){
            holder.contatoImage.setImageResource(R.drawable.baseline_perm_identity_24);
        }else {
            holder.contatoImage.setImageURI(Uri.parse(foto));
        }

        //handle click listener
        holder.contatoDial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetalheContato.class);
                intent.putExtra("ID", id);
                context.startActivity(intent);
            }
        });

        holder.contatoEdit.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View v){
                Toast.makeText(context, "Editar", Toast.LENGTH_SHORT).show();
            }
        });

        holder.contatoDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View v){
                Toast.makeText(context, "Deletar", Toast.LENGTH_SHORT).show();
            }
        });




    }

    @Override
    public int getItemCount() {
        return contatoArrayList.size();
    }

    class ContactViewHolder extends RecyclerView.ViewHolder{

        ImageView contatoImage, contatoDial;
        TextView contatoNome, contatoEdit, contatoDelete   ;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);

            contatoImage = itemView.findViewById(R.id.contato_image);
            contatoDial = itemView.findViewById(R.id.contato_telefone);
            contatoNome = itemView.findViewById(R.id.contato_nome);
            contatoEdit = itemView.findViewById(R.id.contact_edit);
            contatoDelete = itemView.findViewById(R.id.contact_delete);

        }
    }
}
