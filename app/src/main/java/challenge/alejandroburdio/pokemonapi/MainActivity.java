package challenge.alejandroburdio.pokemonapi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import challenge.alejandroburdio.pokemonapi.Fragments.FragmentHandler;
import challenge.alejandroburdio.pokemonapi.Fragments.PokemonListFragment;

/**
 * Created by Burdío on 22/09/2019.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create and set Pokemon List Fragment.
        new FragmentHandler().replaceFragment(getSupportFragmentManager(), new PokemonListFragment());
    }
}
