package com.example.ejercicio3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ejercicio3.models.Pokemon;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Detalles extends AppCompatActivity implements Response.ErrorListener, Response.Listener<JSONObject>{

    Pokemon pokemon;
    TextView tvName;
    TextView tvExp;
    TextView tvWeight;
    TextView tvHeight;
    ImageView ivTipo1;
    ImageView ivTipo2;
    ImageView ivPokemon;

    CardView cvDetalles;
    LinearLayout expandible;
    MaterialButton btnMas;
    ProgressBar pbConexion;

    MediaPlayer regresar;
    RequestQueue queue;
    JsonObjectRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);

        //Inicialización de objeto de sonido
        regresar = MediaPlayer.create(this, R.raw.pokeball_caught);

        //Asociacion de elementos de la vista
        tvName = findViewById(R.id.tvName);
        tvExp = findViewById(R.id.tvExp);
        tvHeight = findViewById(R.id.tvHeight);
        tvWeight = findViewById(R.id.tvWeight);
        ivTipo1 = findViewById(R.id.ivTipo1);
        ivTipo2 = findViewById(R.id.ivTipo2);
        ivPokemon = findViewById(R.id.ivPokemon);
        cvDetalles = findViewById(R.id.cvDetalles);
        expandible = findViewById(R.id.expandible);
        btnMas = findViewById(R.id.btnMas);
        pbConexion = findViewById(R.id.pbConexion);

        //Recuperar Pokemon del Activity anterior
        Bundle bundle = new Bundle();
        bundle = getIntent().getExtras();

        //Verifica que el bundle no esté vacío
        if(bundle != null){
            pokemon = (Pokemon) bundle.getSerializable("Pokemon");
            conexion();
        }
    }

    //Metodo para realizar conexion al servicio
    public void conexion(){
        //Generar cola de conexiones
        queue = Volley.newRequestQueue(this);
        //Generación del request, almacena los datos de la respuesta
        request = new JsonObjectRequest(Request.Method.GET, pokemon.getUrl(), null, this, this);
        //Añade la conexión a la cola
        queue.add(request);
    }

    //Metodo para modificar la vista con información del pokemon
    public void setDetalles(){
        tvName.setText(pokemon.getNombre());
        tvExp.setText(Integer.toString(pokemon.getExpBase()));
        tvWeight.setText(Integer.toString(pokemon.getPeso()));
        tvHeight.setText(Integer.toString(pokemon.getAltura()));

        //Asigna la imagen del pokemon a través de su URL
        Picasso.with(this).load(pokemon.getUrlImg())
                .placeholder(getDrawable(R.drawable.pokemon_carga))         //Imagen momentanea mientras se realiza la descarga
                .error(getDrawable(R.drawable.pokemon_default))             //Imagen default para error en descarga
                .into(ivPokemon);

        //Asigna la imagen de los tipos
        asignarTipo(ivTipo1, pokemon.getTipo(0));
        if(pokemon.getTipos() > 1){
            asignarTipo(ivTipo2, pokemon.getTipo(1));
        }

    }

    //Metodo para asignar el tipo de Pokémon
    public void asignarTipo(ImageView iv, String tipo){
        if(tipo.equals(getString(R.string.tBug))){
            iv.setImageResource(R.drawable.bug);
        }else if(tipo.equals(getString(R.string.tDark))){
            iv.setImageResource(R.drawable.dark);
        }else if(tipo.equals(getString(R.string.tDragon))){
            iv.setImageResource(R.drawable.dragon);
        }else if(tipo.equals(getString(R.string.tElectric))){
            iv.setImageResource(R.drawable.electric);
        }else if(tipo.equals(getString(R.string.tFairy))){
            iv.setImageResource(R.drawable.fairy);
        }else if(tipo.equals(getString(R.string.tFighting))){
            iv.setImageResource(R.drawable.fighting);
        }else if(tipo.equals(getString(R.string.tFire))){
            iv.setImageResource(R.drawable.fire);
        }else if(tipo.equals(getString(R.string.tFlying))){
            iv.setImageResource(R.drawable.flying);
        }else if(tipo.equals(getString(R.string.tGhost))){
            iv.setImageResource(R.drawable.ghost);
        }else if(tipo.equals(getString(R.string.tGrass))){
            iv.setImageResource(R.drawable.grass);
        }else if(tipo.equals(getString(R.string.tGround))){
            iv.setImageResource(R.drawable.ground);
        }else if(tipo.equals(getString(R.string.tIce))){
            iv.setImageResource(R.drawable.ice);
        }else if(tipo.equals(getString(R.string.tNormal))){
            iv.setImageResource(R.drawable.normal);
        }else if(tipo.equals(getString(R.string.tPoison))){
            iv.setImageResource(R.drawable.poison);
        }else if(tipo.equals(getString(R.string.tPsychic))){
            iv.setImageResource(R.drawable.psychic);
        }else if(tipo.equals(getString(R.string.tRock))){
            iv.setImageResource(R.drawable.rock);
        }else if(tipo.equals(getString(R.string.tSteel))){
            iv.setImageResource(R.drawable.steel);
        }else if(tipo.equals(getString(R.string.tWater))){
            iv.setImageResource(R.drawable.water);
        }
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

    @Override
    public void onErrorResponse(VolleyError error) {
        //Muestra un error de conexión
        //Creacion de un nuevo dialogo
        new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.error))
                .setMessage(getResources().getString(R.string.errorConexion))
                .setPositiveButton(getResources().getString(R.string.btnTry), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        conexion();
                    }
                })
                .setNegativeButton(getResources().getString(R.string.btnCancelar), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .create().show();
    }

    @Override
    public void onResponse(JSONObject response) {
        //Quitar ProgressBar
        pbConexion.setVisibility(View.GONE);

        //Obtiene el objeto JSON de la respuesta
        JSONObject json = response;
        JSONArray jsonTipos;

        try {
            //Asigna la experiencia base al pokemon recuperado
            pokemon.setExpBase(Integer.parseInt(json.getString(getResources().getString(R.string.rqExp))));
            //Asigancion del peso
            pokemon.setPeso(Integer.parseInt(json.getString(getResources().getString(R.string.rqWeight))));
            //Asignacion de la altura
            pokemon.setAltura(Integer.parseInt(json.getString(getResources().getString(R.string.rqHeight))));
            //Asignacion del URL de la imagen
            pokemon.setUrlImg(json.optJSONObject("sprites")
                    .optJSONObject("other")
                    .optJSONObject("official-artwork").getString("front_default"));
            //Obtencion del array de tipos
            jsonTipos = json.optJSONArray(getResources().getString(R.string.rqTypes));
            //Iteracion sobre el Array
            for(int i = 0; i<jsonTipos.length(); i++){
                JSONObject jsonTmp = jsonTipos.getJSONObject(i);
                //Agrega los tipos asociados al pokemon
                pokemon.addTipo(jsonTmp.optJSONObject("type").getString("name"));
            }
            //Asigna detalles a la vista
            setDetalles();
        } catch (JSONException e) {
            //Creación de un nuevo dialogo
            new AlertDialog.Builder(this)
                    .setTitle(getResources().getString(R.string.error))
                    .setMessage(getResources().getString(R.string.errorProcesamiento))
                    .setPositiveButton(getResources().getString(R.string.btnAceptar), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).create().show();
            e.printStackTrace();
        }
    }

    public void home(View view) {
        regresar.start();
        //Regresa al catalogo de pokemon
        super.onBackPressed();
    }
}