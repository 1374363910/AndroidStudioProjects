package cn.edu.hdu.pokedex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Types;
import java.util.ArrayList;

import cn.edu.hdu.pokedex.models.PokemonResponse;
import cn.edu.hdu.pokedex.models.Stat;
import cn.edu.hdu.pokedex.models.Type;
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

    private TextView type1;
    private TextView type2;

    private Button button;

    private int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokeline);

        pokemonImg = findViewById(R.id.pokemonImg);

        button = findViewById(R.id.button);

        Intent intent = getIntent();
        Log.e(TAG, "传入数据：" + intent.getIntExtra("id",0));
        id = intent.getIntExtra("id", 0);

        Glide.with(this)
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + id + ".png")
                .into(pokemonImg);


        retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        getData(id);

    }

    //查看宝可梦的正面或背面
    public void loadImage(View view) {
        if (button.getText().equals("SEE THE ASS")) {
            String url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/" + id + ".png";
            Glide.with(this).load(url).into(pokemonImg);
            button.setText("SEE THE FACE");
        } else {
            String url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + id + ".png";
            Glide.with(this).load(url).into(pokemonImg);
            button.setText("SEE THE ASS");
        }

    }

    //获取宝可梦的详细信息
    private void getData(int id){

        name = findViewById(R.id.pokemonName);
        speed = findViewById(R.id.speed_data);
        specialDefense = findViewById(R.id.special_defense_data);
        specialAttack = findViewById(R.id.special_attack_data);
        defense = findViewById(R.id.defense_data);
        attack = findViewById(R.id.attack_data);
        hp = findViewById(R.id.hp_data);

        type1 = findViewById(R.id.type1_data);
        type2 = findViewById(R.id.type2_data);


        PokeapiService service = retrofit.create(PokeapiService.class);
        Call<PokemonResponse> pokemonResponseCall = service.getInfoPokemon(id+"");

        pokemonResponseCall.enqueue(new Callback<PokemonResponse>() {
            @Override
            public void onResponse(Call<PokemonResponse> call, Response<PokemonResponse> response) {
                if (response.isSuccessful()) {
                    PokemonResponse pokemonResponse = response.body();
                    String pokemonResponseName = pokemonResponse.getName();
                    ArrayList<Stat> pokemonResponseStats = pokemonResponse.getStats();
                    ArrayList<Type> pokemonResponseTypes = pokemonResponse.getTypes();

                    name.setText(pokemonResponseName);
                    speed.setText(pokemonResponseStats.get(0).getBase_stat()+"");
                    specialDefense.setText(pokemonResponseStats.get(1).getBase_stat()+"");
                    specialAttack.setText(pokemonResponseStats.get(2).getBase_stat()+"");
                    defense.setText(pokemonResponseStats.get(3).getBase_stat()+"");
                    attack.setText(pokemonResponseStats.get(4).getBase_stat()+"");
                    hp.setText(pokemonResponseStats.get(5).getBase_stat()+"");

                    type1.setText(pokemonResponseTypes.get(0).getType().getName());
                    if (pokemonResponseTypes.size() > 1) {
                        type2.setText(pokemonResponseTypes.get(1).getType().getName());
                    }
                }
            }

            @Override
            public void onFailure(Call<PokemonResponse> call, Throwable t) {

            }
        });
    }
}
