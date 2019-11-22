package cn.edu.hdu.pokedex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import cn.edu.hdu.pokedex.models.PokemonResponse;
import cn.edu.hdu.pokedex.models.Stat;
import cn.edu.hdu.pokedex.pokeapi.PokeapiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PokelineActivity extends AppCompatActivity {

    private static final String TAG = "POKEDEX";

    private Retrofit retrofit;
    private TextView name;
    private TextView speed;
    private TextView specialDefense;
    private TextView specialAttack;
    private TextView defense;
    private TextView attack;
    private TextView hp;

    private ImageView pokemonImg;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokeline);

        pokemonImg = findViewById(R.id.pokemonImg);

        Intent intent = getIntent();
        Log.e(TAG, "传入数据：" + intent.getIntExtra("id",0));
        int id = intent.getIntExtra("id", 0);

        Glide.with(this)
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + id + ".png")
                .into(pokemonImg);


        retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        getData(id);
    }
    private void getData(int id){

        name = findViewById(R.id.pokemonName);
        speed = findViewById(R.id.speed_data);
        specialDefense = findViewById(R.id.special_defense_data);
        specialAttack = findViewById(R.id.special_attack_data);
        defense = findViewById(R.id.defense_data);
        attack = findViewById(R.id.attack_data);
        hp = findViewById(R.id.hp_data);

        PokeapiService service = retrofit.create(PokeapiService.class);
        Call<PokemonResponse> pokemonResponseCall = service.getInfoPokemon(id+"");

        pokemonResponseCall.enqueue(new Callback<PokemonResponse>() {
            @Override
            public void onResponse(Call<PokemonResponse> call, Response<PokemonResponse> response) {
                if (response.isSuccessful()) {
                    PokemonResponse pokemonResponse = response.body();
                    String pokemonResponseName = pokemonResponse.getName();
                    ArrayList<Stat> pokemonResponseStats = pokemonResponse.getStats();
                    ArrayList pokemonResponseTypes = pokemonResponse.getTypes();

                    name.setText(pokemonResponseName);
                    speed.setText(pokemonResponseStats.get(0).getBase_stat()+"");
                    specialDefense.setText(pokemonResponseStats.get(1).getBase_stat()+"");
                    specialAttack.setText(pokemonResponseStats.get(2).getBase_stat()+"");
                    defense.setText(pokemonResponseStats.get(3).getBase_stat()+"");
                    attack.setText(pokemonResponseStats.get(4).getBase_stat()+"");
                    hp.setText(pokemonResponseStats.get(5).getBase_stat()+"");
                }
            }

            @Override
            public void onFailure(Call<PokemonResponse> call, Throwable t) {

            }
        });
    }
}
