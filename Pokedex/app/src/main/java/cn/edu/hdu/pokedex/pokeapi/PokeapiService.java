package cn.edu.hdu.pokedex.pokeapi;

import cn.edu.hdu.pokedex.models.PokemonResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PokeapiService {

    @GET("pokemon")
    Call<PokemonResponse> getListPokemon(@Query("limit") int limit, @Query("offset") int offset);

    @GET("pokemon/{id}")
    Call<PokemonResponse> getInfoPokemon(@Path("id") String id);

}
