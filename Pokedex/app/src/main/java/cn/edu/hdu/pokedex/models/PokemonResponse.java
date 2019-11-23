package cn.edu.hdu.pokedex.models;

import java.util.ArrayList;

public class PokemonResponse {
    private ArrayList<Pokemon> results;

    private String name;
    private ArrayList<Stat> stats;
    private ArrayList<Type> types;

    public ArrayList<Pokemon> getResults() {
        return results;
    }

    public void setResults(ArrayList<Pokemon> results) {
        this.results = results;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Stat> getStats() {
        return stats;
    }

    public void setStats(ArrayList<Stat> stats) {
        this.stats = stats;
    }


    public ArrayList<Type> getTypes() {
        return types;
    }

    public void setTypes(ArrayList<Type> types) {
        this.types = types;
    }
}
