package com.lgarcia.pokemon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    EditText edtPokemon;
    Button btnBuscar;
    TextView txtResultado;
    ImageView imgPokemon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtPokemon = findViewById(R.id.edtPokenon);
        btnBuscar = findViewById(R.id.btnBuscar);
        txtResultado = findViewById(R.id.txtResultado);
        imgPokemon = findViewById(R.id.imgPokemon);

    }
    public void BuscarPokemon(View v) {
        String Pokemon = edtPokemon.getText().toString();
        String url = "https://pokeapi.co/api/v2/pokemon/" + Pokemon;

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String name = jsonObject.getString("name");
                    JSONArray types = jsonObject.getJSONArray("types");

                    String typeList = "";

                    for (int i = 0; i < types.length(); i++) {
                        JSONObject type = types.getJSONObject(i);
                        JSONObject typeName = type.getJSONObject("type");
                        typeList += typeName.getString("name") + " ";
                    }

                    String result = "Name: " + name + "\nTypes: " + typeList;
                    txtResultado.setText(result);

                    String imageUrl = jsonObject.getJSONObject("sprites").getJSONObject("other").getJSONObject("official-artwork").getString("front_default");
                    Picasso.get().load(imageUrl).into(imgPokemon);

                } catch (JSONException e) {
                    Log.e("Api Info Pokemon", "Error parsing JSON", e);
                    Toast.makeText(MainActivity.this, "Pokemon no encontrado", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Api Info Pokemon", "Error in request", error);
                Toast.makeText(MainActivity.this, "Pokemon no encontrado", Toast.LENGTH_LONG).show();
            }
        });

        Volley.newRequestQueue(this).add(request);
    }

}