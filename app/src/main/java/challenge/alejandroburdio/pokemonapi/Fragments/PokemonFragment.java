package challenge.alejandroburdio.pokemonapi.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import challenge.alejandroburdio.pokemonapi.PokeApi.PokeApiClient;
import challenge.alejandroburdio.pokemonapi.PokeApi.PokeApiService;
import challenge.alejandroburdio.pokemonapi.R;
import challenge.alejandroburdio.pokemonapi.Structs.Pokemon;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Burdío on 22/09/2019.
 */

public class PokemonFragment extends Fragment {

    private TextView textViewName, textViewHeight, textViewWeight;
    private ImageView imageViewPokemon;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); //Enabled onOptionsItemSelected
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pokemon_details, container, false);

        //Set back button to action Bar
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textViewName = (TextView) view.findViewById(R.id.idName);
        textViewHeight = (TextView) view.findViewById(R.id.idHeight);
        textViewWeight = (TextView) view.findViewById(R.id.idWeight);
        imageViewPokemon = (ImageView) view.findViewById(R.id.idImagen);
        getDataPokemon(getArguments().getString("pokemonId"));

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //Back to the pokemon list
            case android.R.id.home:
                item.setVisible(false);
                new FragmentHandler().replaceFragment(getFragmentManager(), new PokemonListFragment());
                return true;
        }
        return false;
    }

    /**
     * Get pokemon attributtes from API
     * @param id
     */
    private void getDataPokemon(final String id){
        final PokeApiService apiService = PokeApiClient.getClient(PokeApiClient.POKEMON_API_BASE_URL_VERSION).create(PokeApiService.class);

        Call<Pokemon> call = apiService.getPokemon(id);
        call.enqueue(new Callback<Pokemon>() {
            @Override
            public void onResponse(Call<Pokemon> call, Response<Pokemon> response){
                if(response.isSuccessful()) {
                    Pokemon pokemon = response.body();

                    textViewName.setText(pokemon.getName());
                    ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(pokemon.getName());
                    textViewHeight.setText(pokemon.getHeight());
                    textViewWeight.setText(pokemon.getWeight());

                    Glide.with(imageViewPokemon.getContext())
                            .load(PokeApiClient.POKEMON_API_BASE_URL_IMAGES+ id + ".png")
                            .centerCrop()
                            .crossFade()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(imageViewPokemon);
                }
            }

            @Override
            public void onFailure(Call<Pokemon> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
