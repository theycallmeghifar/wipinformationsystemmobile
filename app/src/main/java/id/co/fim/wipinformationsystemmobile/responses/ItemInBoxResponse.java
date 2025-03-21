package id.co.fim.wipinformationsystemmobile.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.processing.Generated;

import id.co.fim.wipinformationsystemmobile.model.ItemInBox;

@Generated("jsonschema2pojo")
public class ItemInBoxResponse {
    @SerializedName("responses")
    @Expose
    private Boolean responses;
    @SerializedName("data")
    @Expose
    private List<ItemInBox> data;

    public Boolean getResponses() {
        return responses;
    }

    public void setResponses(Boolean responses) {
        this.responses = responses;
    }

    public List<ItemInBox> getData() {
        return data;
    }

    public void setData(List<ItemInBox> data) {
        this.data = data;
    }
}
