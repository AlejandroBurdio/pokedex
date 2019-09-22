package challenge.alejandroburdio.pokemonapi.PokeApi;

/**
 * Created by Burdío on 22/09/2019.
 */

import challenge.alejandroburdio.pokemonapi.Structs.Pokemon;
import challenge.alejandroburdio.pokemonapi.Structs.PokemonResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PokeApiService {

    //Get by default 20 pokemons ()
    @GET("pokemon")
    Call<PokemonResponse> getPokemonList();

    //Get pokemons until limit (First generation 151)
    @GET("pokemon")
    Call<PokemonResponse> getPokemonList(@Query("limit") int limit, @Query("offset") int offset);

    //Get pokemon by id
    @GET("pokemon/{id}")
    Call<Pokemon> getPokemon(@Path("id") String id);
}
