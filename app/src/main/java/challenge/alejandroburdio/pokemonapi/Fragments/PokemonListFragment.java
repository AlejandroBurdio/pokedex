package challenge.alejandroburdio.pokemonapi.Fragments;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import challenge.alejandroburdio.pokemonapi.Adapters.PokemonAdapter;
import challenge.alejandroburdio.pokemonapi.Listeners.PokemonClickListener;
import challenge.alejandroburdio.pokemonapi.PokeApi.PokeApiClient;
import challenge.alejandroburdio.pokemonapi.PokeApi.PokeApiService;
import challenge.alejandroburdio.pokemonapi.R;
import challenge.alejandroburdio.pokemonapi.Structs.Pokemon;
import challenge.alejandroburdio.pokemonapi.Structs.PokemonResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Burdío on 22/09/2019.
 */

public class PokemonListFragment extends Fragment {

    ArrayList<Pokemon> pokemonList = new ArrayList<>();
    PokemonAdapter pokemonAdapter;
    RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); //Enabled onOptionsItemSelected
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pokemon_list, container, false);

        //Set title to action bar
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.app_name);
        //Hide back button on action Bar
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_pokemons);
        pokemonAdapter = new PokemonAdapter(pokemonList, new PokemonClickListener(){
            @Override
            public void onItemClick(View v, int position) {
                //sending data to second fragment
                Fragment fragment = new PokemonFragment();
                Bundle bundle = new Bundle();
                bundle.putString("pokemonId", pokemonList.get(position).getNumber());
                fragment.setArguments(bundle);
                new FragmentHandler().replaceFragment(getFragmentManager(), fragment);
            }
        });

        RecyclerView.LayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(pokemonAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener());

        getDataPokemonList();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_pokemon, menu);

        // Associate searchable configuration with the SearchView - Documentation: https://developer.android.com/training/search/setup.html#java
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                pokemonAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                pokemonAdapter.getFilter().filter(query);
                return false;
            }
        });

        super.onCreateOptionsMenu(menu,inflater);
    }

    /**
     * Get Pokemons from API (20 by default, if u want more use getPokemonList(limit, offset))
     */
    private void getDataPokemonList() {
        PokeApiService apiService = PokeApiClient.getClient(PokeApiClient.POKEMON_API_BASE_URL_VERSION).create(PokeApiService.class);
        Call<PokemonResponse> call = apiService.getPokemonList();
        call.enqueue(new Callback<PokemonResponse>() {
            @Override
            public void onResponse(Call<PokemonResponse> call, Response<PokemonResponse> response) {
                if(response.isSuccessful()){
                    PokemonResponse pokemon = response.body();
                    ArrayList<Pokemon> listaPokemon = pokemon.getResults();
                    pokemonAdapter.add(listaPokemon);
                }
            }
            @Override
            public void onFailure(Call<PokemonResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
