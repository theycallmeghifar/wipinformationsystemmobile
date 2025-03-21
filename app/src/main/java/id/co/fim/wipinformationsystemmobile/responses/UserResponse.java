package id.co.fim.wipinformationsystemmobile.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class UserResponse {
    @SerializedName("responses")
    @Expose
    private Boolean responses;

    @SerializedName("role")
    @Expose
    private int role;

    public Boolean getResponses() {
        return responses;
    }

    public void setResponses(Boolean responses) {
        this.responses = responses;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}