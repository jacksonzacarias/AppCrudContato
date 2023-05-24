package com.example.conectapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;

import java.util.ArrayList;

public class ControleContatos  extends  RecyclerView.Adapter<ControleContatos.ContactViewHolder>{

    private Context context;
    private ArrayList<ModeloContato> listContato;

    private DataBase dataBase;

    //criando o construtor


    public ControleContatos(Context context, ArrayList<ModeloContato> listContato) {
        this.context = context;
        this.listContato = listContato;
        dataBase = new DataBase(context);
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
        ModeloContato modeloContato = listContato.get(position);

        String id = modeloContato.getId();
        String nome = modeloContato.getNome();
        String numero = modeloContato.getNumero();
        String imagem = modeloContato.getFoto();
        String email = modeloContato.getEmail();
        String bio = modeloContato.getBio();
        String adicionadoTimeStamp = modeloContato.getAdicionadoTimeStamp();
        String atualizadoTimeStamp = modeloContato.getAtualizadoTimeStamp();

        holder.contatoNome.setText(nome);
        if (imagem.equals("")){
            holder.contatoImage.setImageResource(R.drawable.baseline_perm_identity_24);
        }else {
            holder.contatoImage.setImageURI(Uri.parse(imagem));
        }

        //handle click listener
        holder.contatoDial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
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
                Intent intent = new Intent(context, AddEditContato.class);
                intent.putExtra("ID", id);
                intent.putExtra("NOME", nome);
                intent.putExtra("NUMERO", numero);
                intent.putExtra("IMAGEM", imagem);
                intent.putExtra("E-MAIL", email);
                intent.putExtra("BIO", bio);
                intent.putExtra("ADICIONADO_EM", adicionadoTimeStamp);
                intent.putExtra("ATUALIZADO_EM", atualizadoTimeStamp);

                intent.putExtra("modoDeEdicao", true);

                context.startActivity(intent);


                Toast.makeText(context, "Editar", Toast.LENGTH_SHORT).show();
            }
        });

        holder.contatoDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View v){
                dataBase.deleteContato(id);

                ((MainActivity)context).onResume();

                Toast.makeText(context, "Deletar", Toast.LENGTH_SHORT).show();
            }
        });




    }

    @Override
    public int getItemCount() {
        return listContato.size();
    }

    class ContactViewHolder extends RecyclerView.ViewHolder{

        ImageView contatoImage, contatoDial;
        TextView contatoNome, contatoEdit, contatoDelete   ;
        RelativeLayout relativeLayout;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);

            contatoImage = itemView.findViewById(R.id.contato_image);
            contatoDial = itemView.findViewById(R.id.contato_telefone);
            contatoNome = itemView.findViewById(R.id.contato_nome);
            contatoEdit = itemView.findViewById(R.id.contact_edit);
            contatoDelete = itemView.findViewById(R.id.contact_delete);
            relativeLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
