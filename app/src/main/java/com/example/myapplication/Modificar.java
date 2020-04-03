package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class Modificar extends AppCompatActivity
{
    Button btnModificar, btnEliminar;
    EditText edtNombre,edtApellido,edtCiudad;
    int id;
    String nombre,apellido,ciudad,prueba;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar);

        Bundle b = getIntent().getExtras();
        if(b!=null)
        {
          id = b.getInt("id");
          nombre = b.getString("Nombre");
          apellido = b.getString("Apellido");
          ciudad = b.getString("Ciudad");
          prueba = b.getString("Prueba");

        }

        edtNombre = (EditText)findViewById(R.id.edtNombre);
        edtApellido = (EditText)findViewById(R.id.edtApellido);
        edtCiudad = (EditText)findViewById(R.id.edtCiudad);

        edtNombre.setText(nombre);
        edtApellido.setText(apellido);
        edtCiudad.setText(ciudad);

        btnModificar = (Button)findViewById(R.id.btnModificar);
        btnEliminar = (Button)findViewById(R.id.btnEliminar);

        //FUNCION EDITAR
        btnModificar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder alerta = new AlertDialog.Builder(Modificar.this);
                alerta.setMessage("¿Seguro de editar este registro?")
                     .setCancelable(false)
                     .setPositiveButton("Si", new DialogInterface.OnClickListener()
            {
                      @Override
                       public void onClick(DialogInterface dialog, int which)
                       {
                           Modificar(id,edtNombre.getText().toString(),edtApellido.getText().toString(),edtCiudad.getText().toString());
                           onBackPressed();
                       }
              })
                 .setNegativeButton("No", new DialogInterface.OnClickListener()
                {
                      @Override
                      public void onClick(DialogInterface dialog, int which)
                      {
                           dialog.cancel();
                      }
                });
                    AlertDialog  titulo = alerta.create();
                    titulo.setTitle("Editar"+" "+edtNombre.getText().toString()+" "+edtApellido.getText().toString());
                    titulo.show();
            }
        });

            //FUNCION ELIMINAR
        btnEliminar.setOnClickListener(new View.OnClickListener()
        {
                     @Override
            public void onClick(View v)
            {
                AlertDialog.Builder alerta = new AlertDialog.Builder(Modificar.this);
                alerta.setMessage("¿Esta seguro de eliminar?")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener()
             {
                      @Override
                      public void onClick(DialogInterface dialog, int which)
                      {
                        Eliminar(id);
                        onBackPressed();
                      }
             })
              .setNegativeButton("No", new DialogInterface.OnClickListener()
              {
                       @Override
                       public void onClick(DialogInterface dialog, int which)
                       {
                        dialog.cancel();
                       }
              });
                     AlertDialog  titulo = alerta.create();
                     titulo.setTitle("Esta apunto de eliminar"+" "+edtNombre.getText().toString()+" "+edtApellido.getText().toString());
                     titulo.show();
            }
        });

    }









    private void  Modificar(int id, String Nombre, String Apellido, String Ciudad)
    {
        BaseHelper helper = new BaseHelper(this,"Demo",null,1);
        SQLiteDatabase db = helper.getWritableDatabase();
        String  sql = "UPDATE personas SET NOMBRE ='"+Nombre+"',Apellido='"+Apellido+"',Ciudad='"+Ciudad+"' WHERE ID ="+id;
        db.execSQL(sql);
        db.close();
    }


    private void  Eliminar(int id)
    {
        BaseHelper helper = new BaseHelper(this,"Demo",null,1);
        SQLiteDatabase db = helper.getWritableDatabase();
        String  sql = "DELETE FROM PERSONAS WHERE ID ="+id;
        db.execSQL(sql);
        db.close();
    }
}
