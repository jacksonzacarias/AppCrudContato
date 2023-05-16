package com.example.conectapp;

import android.Manifest;
import android.Manifest.permission;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;



import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class AddEditContato extends AppCompatActivity {

    private ImageView fotoFile;
    private EditText nomeEt, numeroEt, emailEt, bioEt;
    private FloatingActionButton addButton;

    //   private ActionBar actionBar;
//
//
//
    String nome, numero, email, bio;
    private static final int CAMERA_PERMISSAO_CODE = 100;
    private static final int STORAGE_PERMISSAO_CODE = 200;
    private static final int IMAGE_DA_GALERIA_CODE = 300;
    private static final int IMAGE_DA_CAMERA_CODE = 400;


    private String[] storagePermission;


    private String[] cameraPermissions;

    private Uri image_uri;

    ActionBar actionBar;

    private DataBase dataBase;
//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_contato);

        dataBase = new DataBase(this);

        //iniciando as permisao

        cameraPermissions = new String[]{permission.CAMERA, permission.WRITE_EXTERNAL_STORAGE};
        storagePermission = new String[]{permission.WRITE_EXTERNAL_STORAGE};

        actionBar = getSupportActionBar();
        actionBar.setTitle("Adicionar Contato");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
//
//
//// inicilizacao view
        fotoFile = findViewById(R.id.fotoFile);
        nomeEt = findViewById(R.id.nomeEt);
        numeroEt = findViewById(R.id.numeroEt);
        emailEt = findViewById(R.id.emailEt);
        bioEt = findViewById(R.id.bioEt);
        addButton = findViewById(R.id.addButton);
//
//        // Adicionando evento de clique no botão
//
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();

            }
        });
        fotoFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // mostrar dialogo de imagem
                showImagePickerDialog();

            }
        });

    }

    private void showImagePickerDialog() {
        String optins[] = {"Camera", "Galeria"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Escolha uma Opção");
        builder.setItems(optins, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int escolha) {
                //
                if (escolha == 0) {

                    // escolheu camera

                    if (!checkCameraPermission()) {
                        requestCameraPermission();
                    } else {
                        escolha_camera();
                    }

                } else if (escolha == 1) {
                    // escolheu galeria
                    if (!checkStoragePermission()) {
                        requestStoragePermission();
                    } else {
                        escolha_galeria();
                    }


                }

                // escolheu galeria


            }
        }).create().show();


    }

    private void escolha_galeria() {
        Intent galeriaIntent = new Intent(Intent.ACTION_PICK);
        galeriaIntent.setType("image/*");
//        startActivityForResult(galeriaIntent,IMAGE_DA_GALERIA_CODE);

        PackageManager packageManager = getPackageManager();
        if (galeriaIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(galeriaIntent, IMAGE_DA_GALERIA_CODE);
        } else {
            Toast.makeText(this, "Galeria não encontrada", Toast.LENGTH_SHORT).show();
        }

    }

    private void escolha_camera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "IMAGE_TITLE"); // titulo da imagem
        values.put(MediaStore.Images.Media.DESCRIPTION, "IMAGE_DESCRICAO"); // descrição da imagem
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);

        startActivityForResult(cameraIntent, IMAGE_DA_CAMERA_CODE);
        // TRANTAR ESSA FALHA COM PRINT SE NAO FUNCIONAR


    }


    //
    private void saveData() {
        nome = nomeEt.getText().toString();
        numero = numeroEt.getText().toString();
        email = emailEt.getText().toString();
        bio = bioEt.getText().toString();

        String timeStamp = "" +System.currentTimeMillis();

        if (!nome.isEmpty() || !numero.isEmpty() || !email.isEmpty() || !bio.isEmpty()) {
            // salvar dados

       long id = dataBase.insertContato(
               ""+image_uri,
               ""+nome,
               ""+numero,
               ""+email,
               ""+bio,
               ""+timeStamp,
               ""+timeStamp
       );
         Toast.makeText(getApplicationContext(),  "Contato adicionado com sucesso"+id, Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getApplicationContext(), "Todos os campos são obrigatórios", Toast.LENGTH_SHORT).show();

        }



    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    // checar permissao da camera

    private boolean checkCameraPermission() {
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);


        return result && result1;
    }

    private void requestCameraPermission() {

        ActivityCompat.requestPermissions(this, cameraPermissions, CAMERA_PERMISSAO_CODE); // lidar com permissão de resquet no método override
    }

    private boolean checkStoragePermission() {
        boolean result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result1;
    }

    private void requestStoragePermission() {

        ActivityCompat.requestPermissions(this, storagePermission, STORAGE_PERMISSAO_CODE); // solicita permissao da Storage (GALERIA)

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case CAMERA_PERMISSAO_CODE:
                if (grantResults.length > 0) {
                    boolean cameraAceita = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAceita = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAceita && storageAceita) {
                        escolha_camera();
                    } else {
                        Toast.makeText(this, "Camera e Galeria de fotos são necessárias", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case STORAGE_PERMISSAO_CODE:
                if (grantResults.length > 0) {
                    boolean storageAceita = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (storageAceita) {
                        escolha_galeria();
                    } else {
                        Toast.makeText(this, "Galeria de fotos é necessária", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_DA_GALERIA_CODE) {
                CropImage.activity(data.getData()).setGuidelines(CropImageView.Guidelines.ON).setAspectRatio(1, 1).start(this);

            } else if (requestCode == IMAGE_DA_CAMERA_CODE) {
                CropImage.activity(image_uri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1)  // cortar imagem 1:1
                        .start(this);
            } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

                CropImage.ActivityResult result = CropImage.getActivityResult(data);
//                if (resultCode == RESULT_OK){
                image_uri = result.getUri(); // uri da imagem cortada
                fotoFile.setImageURI(image_uri);

            }
            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Algo deu errado", Toast.LENGTH_SHORT).show();
            }

                }
            }
        }












