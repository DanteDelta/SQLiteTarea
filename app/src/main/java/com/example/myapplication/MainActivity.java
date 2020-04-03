package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
 EditText Nombre,Apellido,Ciudad ;
 Button btnAgregar,btnVermas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Nombre = (EditText)findViewById(R.id.edtNombre);
        Apellido = (EditText)findViewById(R.id.edtApellido);
        Ciudad = (EditText)findViewById(R.id.edtCiudad);

        btnAgregar = (Button)findViewById(R.id.btnCrear);
        btnVermas = (Button)findViewById(R.id.btnConsultar);

        btnAgregar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this);
                alerta.setMessage("Estas apunto de agregar a"+" "+Nombre.getText().toString()+" "+Apellido.getText().toString())
                        .setCancelable(false)
                        .setPositiveButton("Registrar", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                Guardar(Nombre.getText().toString().trim(),Apellido.getText().toString().trim(),Ciudad.getText().toString().trim());

                                //Limpia el EditText
                                Nombre.setText("");
                                Apellido.setText("");
                                Ciudad.setText("");

                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                dialog.cancel();
                            }
                        });

                        AlertDialog  titulo = alerta.create();
                        titulo.setTitle("Registrar");
                        titulo.show();
            }
        });

        btnVermas.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity( new Intent(MainActivity.this,Listado.class));
            }
        });
    }

        private  void  Guardar(String Nombre,String Apellido, String Ciudad)
        {
            BaseHelper helper = new BaseHelper(this,"Demo",null,1);
            SQLiteDatabase db = helper.getWritableDatabase();

            try
            {
                ContentValues c = new ContentValues();
                c.put("Nombre",Nombre);
                c.put("Apellido",Apellido);
                c.put("Ciudad",Ciudad);
                db.insert("PERSONAS",null,c);
                db.close();
                Toast.makeText(this, "Registro insertado",Toast.LENGTH_SHORT).show();;
            } catch ( Exception e){
                Toast.makeText(this, "Registro no insertado"+e.getMessage(),Toast.LENGTH_SHORT).show();;
            }
        }

}
