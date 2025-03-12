package id.co.fim.wipinformationsystemmobile.services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    //public static final String BASE_URL="http://www.fim.co.id:9080/WarehousingSystemMobile/API/";
    public static final String BASE_URL = "http://192.168.81.16:80/wipinformationsystemmobile/API/";
    private static Retrofit retrofit=null;

    public static Retrofit getClient() {
        if (retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}