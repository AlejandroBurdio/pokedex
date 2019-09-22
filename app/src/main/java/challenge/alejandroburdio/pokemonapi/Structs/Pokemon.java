package challenge.alejandroburdio.pokemonapi.Structs;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Burdío on 22/09/2019.
 */

public class Pokemon {

    @SerializedName("name")
    private String name;

    private String url;

    @SerializedName("height")
    private String height;

    @SerializedName("weight")
    private String weight;

    public Pokemon(){

    }

    //Getters
    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getNumber() {
        String[] urlPartes = url.split("/");
        return urlPartes[urlPartes.length - 1];
    }

    public String getHeight() {
        return height;
    }

    public String getWeight() {
        return weight;
    }


    //Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
