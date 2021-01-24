package com.example.ejercicio3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ejercicio3.models.Pokemon;
import com.google.android.material.button.MaterialButton;

public class Detalles extends AppCompatActivity {

    Pokemon pokemon;
    TextView tvName;
    CardView cvDetalles;
    LinearLayout expandible;
    MaterialButton btnMas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);

        //Asociacion de elementos de la vista
        tvName = findViewById(R.id.tvName);
        cvDetalles = findViewById(R.id.cvDetalles);
        expandible = findViewById(R.id.expandible);
        btnMas = findViewById(R.id.btnMas);

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

    public void expandir(View view) {
        //Valida si la vista es visible
        if(expandible.getVisibility() == View.VISIBLE){
            //Creacion de una transición
            TransitionManager.beginDelayedTransition(cvDetalles, new AutoTransition());
            //Oculta la vista de detalles
            expandible.setVisibility(View.GONE);
            //Cambia el icono del boton
            btnMas.setIcon(getResources().getDrawable(R.drawable.menos));
        }
        else{   //Los detalles no son visibles
            //Crea la transición
            TransitionManager.beginDelayedTransition(cvDetalles, new AutoTransition());
            //Hace visible la vista
            expandible.setVisibility(View.VISIBLE);
            //Cambia el icono del boton
            btnMas.setIcon(getResources().getDrawable(R.drawable.mas));
        }
    }
}