package id.co.fim.wipinformationsystemmobile.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.processing.Generated;

@Generated("jsonschema2pojo")
public class LocationResponse {
    @SerializedName("responses")
    @Expose
    private Boolean responses;
    @SerializedName("locationId")
    @Expose
    private int locationId;
    @SerializedName("area")
    @Expose
    private String area;
    @SerializedName("line")
    @Expose
    private String line;

    public Boolean getResponses() {
        return responses;
    }

    public void setResponses(Boolean responses) {
        this.responses = responses;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }
}
