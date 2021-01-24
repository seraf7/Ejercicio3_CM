package com.example.ejercicio3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ejercicio3.models.Pokemon;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

//Se indica que se recibiran arreglos de objetos JSON
public class MainActivity extends AppCompatActivity implements PokemonAdapter.ItemClickListener, Response.ErrorListener, Response.Listener<JSONObject>{

    ProgressBar pbConexion;
    RecyclerView rvListado;
    GridLayoutManager glm;
    PokemonAdapter adapter;
    ArrayList<Pokemon> pokemons;

    String url;
    RequestQueue queue;
    JsonObjectRequest request;

    MediaPlayer elegir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inicialización de objeto de audio
        elegir = MediaPlayer.create(this, R.raw.pokeball_opening);

        //Asociación de elementos de la vista
        pbConexion = findViewById(R.id.pbConexion);
        rvListado = findViewById(R.id.rvListado);
        pokemons = new ArrayList<>();

        conexion();
    }

    public void conexion(){
        //Generacion de la cola de conexiones
        queue = Volley.newRequestQueue(this);
        //Generaion de la solicitud, se guardan los datos de la respuesta
        request = new JsonObjectRequest(Request.Method.GET, getResources().getString(R.string.peticionInicial), null, this, this);
        //Realiza la conexión
        queue.add(request);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
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
        //Obtiene el Array de pokemons del objeto JSON
        JSONArray resultado = response.optJSONArray(getResources().getString(R.string.rqResultados));

        try{
            //Iteracion sobre el arreglo JSON para instanciar pokemons
            for(int i=0; i<resultado.length(); i++){
                //Recupera datos del objeto JSON para generar objeto Pokemon
                String tmpNombre = resultado.getJSONObject(i).getString(getResources().getString(R.string.rqNombre));
                String tmpUrl = resultado.getJSONObject(i).getString(getResources().getString(R.string.rqUrl));
                //Añade al Array de Pokemon el objeto creado
                pokemons.add(new Pokemon(i+1, tmpNombre, tmpUrl));
            }

            //Quitar ProgressBar
            pbConexion.setVisibility(View.GONE);

            //Instancia de GridLayourManager con 2 columnas
            glm = new GridLayoutManager(this, 2);
            //Uso de RecyclerView con dos columnas
            rvListado.setLayoutManager(glm);
            //Configuracion del Adapter
            adapter = new PokemonAdapter(pokemons, this);
            rvListado.setAdapter(adapter);
        }
        //Ocurrión un error en el procesamiento de la informacón
        catch (Exception e){
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
        }
    }

    //Metodo para reconocer clic en cada elemento del RecyclerView
    @Override
    public void OnItemCLick(int position) {
        //Intent para indicar el siguiente Activity
        Intent intent = new Intent(this, Detalles.class);
        //Bundle para almacenar el Pokemón seleccionado
        Bundle bundle = new Bundle();
        bundle.putSerializable("Pokemon", pokemons.get(position));
        //Guardar información en el Intent
        intent.putExtras(bundle);

        //Reproducción de sonido
        elegir.start();

        //Inicia el Activity de Detalles
        startActivity(intent);
    }
}