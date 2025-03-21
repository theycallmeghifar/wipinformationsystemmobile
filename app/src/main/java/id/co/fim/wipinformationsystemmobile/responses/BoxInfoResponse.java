package id.co.fim.wipinformationsystemmobile.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class BoxInfoResponse {
    @SerializedName("responses")
    @Expose
    private Boolean responses;
    @SerializedName("wipBoxId")
    @Expose
    private int wipBoxId;
    @SerializedName("wipBoxDetailId")
    @Expose
    private int wipBoxDetailId;
    @SerializedName("boxCode")
    @Expose
    private String boxCode;
    @SerializedName("locationId")
    @Expose
    private int locationId;
    @SerializedName("wipLineNumber")
    @Expose
    private int wipLineNumber;
    @SerializedName("stack")
    @Expose
    private int stack;
    @SerializedName("status")
    @Expose
    private int status;

    public Boolean getResponses() {
        return responses;
    }

    public void setResponses(Boolean responses) {
        this.responses = responses;
    }

    public int getWipBoxId() {
        return wipBoxId;
    }

    public void setWipBoxId(int wipBoxId) {
        this.wipBoxId = wipBoxId;
    }

    public int getWipBoxDetailId() {
        return wipBoxDetailId;
    }

    public void setWipBoxDetailId(int wipBoxDetailId) {
        this.wipBoxDetailId = wipBoxDetailId;
    }

    public String getBoxCode() {
        return boxCode;
    }

    public void setBoxCode(String boxCode) {
        this.boxCode = boxCode;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public int getWipLineNumber() {
        return wipLineNumber;
    }

    public void setWipLineNumber(int wipLineNumber) {
        this.wipLineNumber = wipLineNumber;
    }

    public int getStack() {
        return stack;
    }

    public void setStack(int stack) {
        this.stack = stack;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
