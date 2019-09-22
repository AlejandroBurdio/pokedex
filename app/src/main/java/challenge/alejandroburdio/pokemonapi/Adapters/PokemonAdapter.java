package challenge.alejandroburdio.pokemonapi.Adapters;

/**
 * Created by Burdío on 22/09/2019.
 */
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import challenge.alejandroburdio.pokemonapi.Listeners.PokemonClickListener;
import challenge.alejandroburdio.pokemonapi.PokeApi.PokeApiClient;
import challenge.alejandroburdio.pokemonapi.R;
import challenge.alejandroburdio.pokemonapi.Structs.Pokemon;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.ViewHolder> implements Filterable {

    private ArrayList<Pokemon> pokemonList;
    private ArrayList<Pokemon> pokemonCopyList;
    private final PokemonClickListener listener;

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView pokemonImageView;
        private TextView nombreTextView;
        private TextView numberTextView;

        ViewHolder(View itemView) {
            super(itemView);

            pokemonImageView = (ImageView) itemView.findViewById(R.id.idImagen);
            nombreTextView = (TextView) itemView.findViewById(R.id.idName);
            numberTextView = (TextView) itemView.findViewById(R.id.idNumber);
        }
    }

    public PokemonAdapter(ArrayList<Pokemon> pokemonList, PokemonClickListener listener) {
        this.pokemonList = pokemonList;
        this.pokemonCopyList = pokemonList;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.pokemon_item, parent, false);
        final ViewHolder customViewHolder = new ViewHolder(itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(view, Integer.parseInt(String.valueOf(customViewHolder.numberTextView.getText()))-1);
            }
        });

        return customViewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Pokemon pokemon = pokemonCopyList.get(position);
        holder.nombreTextView.setText(pokemon.getName());
        holder.numberTextView.setText(pokemon.getNumber());

        Glide.with(holder.pokemonImageView.getContext())
                .load(PokeApiClient.POKEMON_API_BASE_URL_IMAGES+ pokemon.getNumber() + ".png")
                .centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.pokemonImageView);
    }

    @Override
    public int getItemCount() {
        return pokemonCopyList.size();
    }

    /**
     * Add all pokemon from Api to custom list
     * @param list
     */
    public void add(ArrayList<Pokemon> list){
        pokemonList.addAll(list);
        notifyDataSetChanged();
    }

    /**
     * @return result from search toolbar
     */
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    pokemonCopyList = pokemonList;
                } else {
                    ArrayList<Pokemon> filteredList = new ArrayList<>();
                    for (Pokemon row : pokemonList) {
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    pokemonCopyList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = pokemonCopyList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                pokemonCopyList = (ArrayList<Pokemon>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}