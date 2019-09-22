package challenge.alejandroburdio.pokemonapi.PokeApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Burdío on 22/09/2019.
 */

public class PokeApiClient {

    public static final String POKEMON_API_BASE_URL_VERSION = "http://pokeapi.co/api/v2/";
    public static final String POKEMON_API_BASE_URL_IMAGES = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient(String url) {
        if(retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
