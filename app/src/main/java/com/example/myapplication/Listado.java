package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Listado extends AppCompatActivity
{
    ListView listView;
    ArrayList<String> listado;

    @Override
    protected void onResume()
    {
        super.onResume();
        CargarListado();
    }

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);

        listView = (ListView) findViewById(R.id.lstDatos);
        CargarListado();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Toast.makeText(Listado.this, listado.get(position),Toast.LENGTH_SHORT).show();
                int clave = Integer.parseInt(listado.get(position).split(" ")[0]);
                String nombre = listado.get(position).split(" ")[1];
                String apellido = listado.get(position).split(" ")[2];
                String ciudad = listado.get(position).split(" ")[3];
                Intent intent = new Intent(Listado.this,Modificar.class);
                intent.putExtra("id",clave);
                intent.putExtra("Nombre",nombre);
                intent.putExtra("Apellido",apellido);
                intent.putExtra("Ciudad",ciudad);
                startActivity(intent);
            }
        });

        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
         if(item.getItemId()==android.R.id.home);
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void CargarListado()
     {
         listado = ListaPersonas();
         ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listado);
         listView.setAdapter(adapter);
     }

    private ArrayList<String> ListaPersonas()
    {
        ArrayList<String> datos = new ArrayList<String>();
        BaseHelper helper = new BaseHelper(this, "Demo", null, 1);
        SQLiteDatabase db = helper.getReadableDatabase();

        String sql = "SELECT * FROM PERSONAS";

        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst())
        {
            do
            {
                String linea = c.getInt(0) + " " + c.getString(1) + " " + c.getString(2)+" "+ c.getString(3);
                datos.add(linea);
            } while (c.moveToNext());
        }
        db.close();
        return datos;
    }
}












