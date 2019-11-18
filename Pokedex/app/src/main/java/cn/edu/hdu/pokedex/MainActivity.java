package cn.edu.hdu.pokedex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import cn.edu.hdu.pokedex.models.Pokemon;
import cn.edu.hdu.pokedex.models.PokemonResponse;
import cn.edu.hdu.pokedex.pokeapi.PokeapiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "POKEDEX";
    private Retrofit retrofit;

    private RecyclerView recyclerView;
    private ListPokemonAdapter listPokemonAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        listPokemonAdapter = new ListPokemonAdapter();
        recyclerView.setAdapter(listPokemonAdapter);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        getData();
    }

    private void getData(){
        PokeapiService service = retrofit.create(PokeapiService.class);
        Call<PokemonResponse> pokemonResponseCall = service.getListPokemon();

        pokemonResponseCall.enqueue(new Callback<PokemonResponse>() {

            @Override
            public void onResponse(Call<PokemonResponse> call, Response<PokemonResponse> response) {
                if (response.isSuccessful()) {
                    PokemonResponse pokemonResponse = response.body();
                    ArrayList<Pokemon> listPokemon = pokemonResponse.getResults();

                    listPokemonAdapter.increaseListPokemon(listPokemon);
                } else {
                    Log.e(TAG, "onResponse:" + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<PokemonResponse> call, Throwable t) {
                Log.e(TAG, "onFailure:" + t.getMessage());
            }
        });
    }
}
