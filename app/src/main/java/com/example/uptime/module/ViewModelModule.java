package com.example.uptime.module;

import static com.example.uptime.misc.BetteruptimeConstants.BASE_URL;

import com.example.uptime.network.BetteruptimeApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ViewModelComponent;
import dagger.hilt.components.SingletonComponent;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Implementations for ViewModel dependencies here
 * 11/18/21 Jose Salazar
 * **/

// move to app and make singleton?
@Module
@InstallIn(SingletonComponent.class)
public class ViewModelModule {
    /**
     * If different url is needed than BASE_URL then either:
     * delete the below and create a Retrofitfactory or
     * create qualifiers and create implementations
     * **/
    @Provides
    public static Retrofit provideRetrofit(){
        // Create Retrofit instance with our base url
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    @Provides
    public static BetteruptimeApi provideBetteruptimeApi(Retrofit retrofit){
        // Retrofit implements our api
        return retrofit.create(BetteruptimeApi.class);
    }
}
