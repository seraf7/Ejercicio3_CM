package com.example.ejercicio3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.ejercicio3.models.Pokemon;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvListado;
    GridLayoutManager glm;
    PokemonAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Asociación de elementos de la vista
        rvListado = findViewById(R.id.rvListado);

        //Instancia de GridLayourManager con 2 columnas
        glm = new GridLayoutManager(this, 2);
        //Uso de RecyclerView con dos columnas
        rvListado.setLayoutManager(glm);
        //Configuracion del Adapter
        adapter = new PokemonAdapter(llenado());
        rvListado.setAdapter(adapter);
    }

    private ArrayList<Pokemon> llenado(){
        ArrayList<Pokemon> datos = new ArrayList<>();
        for(int i = 0; i<30; i++){
            Pokemon pokemon = new Pokemon(i, getResources().getString(R.string.pokemon)+i, "URL");
            datos.add(pokemon);
        }
        return datos;
    }
}