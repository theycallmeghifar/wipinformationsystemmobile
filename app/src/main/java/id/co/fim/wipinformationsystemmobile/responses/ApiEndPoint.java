package id.co.fim.wipinformationsystemmobile.responses;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiEndPoint {
    @FormUrlEncoded
    @POST("login.php")
    Call<UserResponse> loginProcess(@Field("username") String username,
                                      @Field("password") String password);

    @FormUrlEncoded
    @POST("getBoxInfo.php")
    Call<BoxInfoResponse> getBoxInfo(@Field("boxCode") String boxCode);

    @FormUrlEncoded
    @POST("getLocation.php")
    Call<LocationResponse> getLocation(@Field("locationId") int locationId);

    @FormUrlEncoded
    @POST("getItemInBox.php")
    Call<ItemInBoxResponse> getItemInBox(@Field("wipBoxId") int wipBoxId);

    @FormUrlEncoded
    @POST("getBoxOnSameStack.php")
    Call<StatusResponse> getBoxOnSameStack(@Field("locationId") int locationId,
                                     @Field("wipLineNumber") int wipLineNumber,
                                     @Field("stack") int stack);

    @FormUrlEncoded
    @POST("transferBox.php")
    Call<StatusResponse> transferBox(@Field("wipBoxId") int wipBoxId,
                                     @Field("locationId") int locationId,
                                     @Field("wipLineNumber") int wipLineNumber,
                                     @Field("stack") int stack,
                                     @Field("modifiedBy") String modifiedBy);
}
