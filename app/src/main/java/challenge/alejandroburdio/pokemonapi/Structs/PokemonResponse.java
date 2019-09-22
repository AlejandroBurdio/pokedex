package challenge.alejandroburdio.pokemonapi.Structs;

import java.util.ArrayList;

/**
 * Created by Burdío on 22/09/2019.
 */

public class PokemonResponse {
    private ArrayList<Pokemon> results;

    public ArrayList<Pokemon> getResults() {
        return results;
    }

    public void setResults(ArrayList<Pokemon> results) {
        this.results = results;
    }
}
