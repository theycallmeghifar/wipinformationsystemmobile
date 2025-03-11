package id.co.fim.wipinformationsystemmobile.responses;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiEndPoint {
    @FormUrlEncoded
    @POST("login.php")
    Call<StatusResponse> loginProcess(@Field("username") String username,
                                      @Field("password") String password);
}
