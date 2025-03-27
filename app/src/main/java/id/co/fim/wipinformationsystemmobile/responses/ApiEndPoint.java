package id.co.fim.wipinformationsystemmobile.responses;

import id.co.fim.wipinformationsystemmobile.request.EditWipBoxDetail;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiEndPoint {
    @GET("checkConnection.php")
    Call<StatusResponse> checkConnection();

    @FormUrlEncoded
    @POST("login.php")
    Call<UserResponse> loginProcess(@Field("username") String username,
                                      @Field("password") String password);

    @GET("getAllItem.php")
    Call<ItemResponse> getAllItem();

    @FormUrlEncoded
    @POST("getBoxInfo.php")
    Call<BoxInfoResponse> getBoxInfo(@Field("boxCode") String boxCode);

    @FormUrlEncoded
    @POST("getLocation.php")
    Call<LocationResponse> getLocation(@Field("locationId") int locationId);

    @FormUrlEncoded
    @POST("getPendingLocation.php")
    Call<LocationResponse> getPendingLocation(@Field("line") String line);

    @FormUrlEncoded
    @POST("getFinishingLocation.php")
    Call<LocationResponse> getFinishingLocation(@Field("area") String area);

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
                                     @Field("modifiedBy") String modifiedBy,
                                     @Field("status") int status);

    @FormUrlEncoded
    @POST("changeType.php")
    Call<StatusResponse> changeType(@Field("wipBoxId") int wipBoxId,
                                       @Field("locationId") int locationId,
                                       @Field("wipLineNumber") int wipLineNumber,
                                       @Field("stack") int stack,
                                       @Field("modifiedBy") String modifiedBy,
                                       @Field("status") int status);

    @POST("editWipBoxDetail.php")
    Call<StatusResponse> editWipBoxDetail(@Body EditWipBoxDetail request);

    @FormUrlEncoded
    @POST("pending.php")
    Call<StatusResponse> pending(@Field("wipBoxId") int wipBoxId,
                                    @Field("locationId") int locationId,
                                    @Field("wipLineNumber") int wipLineNumber,
                                    @Field("stack") int stack,
                                    @Field("modifiedBy") String modifiedBy,
                                    @Field("status") int status);

    @FormUrlEncoded
    @POST("clear.php")
    Call<StatusResponse> clear(@Field("wipBoxId") int wipBoxId,
                                 @Field("locationId") int locationId,
                                 @Field("wipLineNumber") int wipLineNumber,
                                 @Field("stack") int stack,
                                 @Field("modifiedBy") String modifiedBy,
                                 @Field("status") int status);
}
