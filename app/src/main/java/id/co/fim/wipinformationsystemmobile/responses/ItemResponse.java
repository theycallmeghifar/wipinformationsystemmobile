package id.co.fim.wipinformationsystemmobile.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.processing.Generated;

import id.co.fim.wipinformationsystemmobile.model.ItemInBox;

@Generated("jsonschema2pojo")
public class ItemResponse {
    @SerializedName("responses")
    @Expose
    private Boolean responses;

    @SerializedName("items")
    @Expose
    private List<ItemInBox> items;

    public Boolean getResponses() {
        return responses;
    }

    public void setResponses(Boolean responses) {
        this.responses = responses;
    }

    public List<ItemInBox> getItems() {
        return items;
    }

    public void setItems(List<ItemInBox> items) {
        this.items = items;
    }
}

