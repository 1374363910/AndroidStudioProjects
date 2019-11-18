package cn.edu.hdu.pokedex;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import cn.edu.hdu.pokedex.models.Pokemon;

public class ListPokemonAdapter extends RecyclerView.Adapter<ListPokemonAdapter.ViewHolder> {

    private ArrayList<Pokemon> dataset;

    public ListPokemonAdapter(){
        dataset = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pokemon pokemon = dataset.get(position);
        holder.pokemonName.setText(pokemon.getName());
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void increaseListPokemon(ArrayList<Pokemon> listPokemon) {
        dataset.addAll(listPokemon);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView pokemonPicture;
        private TextView pokemonName;

        public ViewHolder(View itemView) {
            super(itemView);

            pokemonPicture = itemView.findViewById(R.id.pokemonPicture);
            pokemonName = itemView.findViewById(R.id.pokemonName);
        }
    }
}
