package com.example.ejercicio3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ejercicio3.models.Pokemon;
import java.util.ArrayList;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>{

    private ArrayList<Pokemon> datos;
    private ItemClickListener clickListener;

    //Interfaz para reconocer eventos Clic
    interface ItemClickListener{
        void OnItemCLick(int position);
    }

    public PokemonAdapter(ArrayList<Pokemon> datos, ItemClickListener onClickListener){
        this.datos = datos;
        this.clickListener = onClickListener;
    }

    //Crea una vista PokemonViewHolder y la asocia a los elementos de item_pokemon
    @Override
    public PokemonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Instancia de vista item_pokemon
        return new PokemonViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemon, parent, false));
    }

    //Inyecta la informacion de los elementos de la colección a cada vista
    @Override
    public void onBindViewHolder(PokemonViewHolder holder, int position) {
        //Obtiene elemento del arreglo
        Pokemon tmpPokemon = datos.get(position);
        //Establece contenido de etiquetas
        holder.tvNombre.setText(tmpPokemon.getNombre());
        holder.tvNumero.setText(String.valueOf(tmpPokemon.getId()));
    }

    //Devuelve la cantidad de elementos de la colección
    @Override
    public int getItemCount() {
        return datos.size();
    }

    //Clase para representar cada vista de la coleccion
    class PokemonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        //Atributos que contiene cada elemento
        ImageView ivCard;
        TextView tvNumero;
        TextView tvNombre;

        //Constructor de la clase
        public PokemonViewHolder(View itemView) {
            super(itemView);
            //Asociacion de elementos de la vista
            ivCard = itemView.findViewById(R.id.ivCard);
            tvNumero = itemView.findViewById(R.id.tvNumero);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            //Asociacion del listener
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //Recupera la posicion del elemento seleccionado
            int position = getAdapterPosition();
            //Invoca el metodo OnItemClick con la posicion del elemento
            clickListener.OnItemCLick(position);
        }
    }
}
