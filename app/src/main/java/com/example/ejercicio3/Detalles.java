package com.example.ejercicio3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.ejercicio3.models.Pokemon;

public class Detalles extends AppCompatActivity {

    Pokemon pokemon;
    TextView tvName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);

        //Asociacion de elementos de la vista
        tvName = findViewById(R.id.tvName);

        //Recuperar Pokemon del Activity anterior
        Bundle bundle = new Bundle();
        bundle = getIntent().getExtras();
        pokemon = (Pokemon) bundle.getSerializable("Pokemon");

        setDetalles();
    }

    //Metodo para modificar la vista con información del pokemon
    public void setDetalles(){
        tvName.setText(pokemon.getNombre());
    }
}