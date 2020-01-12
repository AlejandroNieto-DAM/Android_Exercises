package com.example.practicaandroid.ui.retrofit;

import com.example.practicaandroid.ui.CastClassesToMovie.CastMovieCredits;
import com.example.practicaandroid.ui.Videos.Videos;
import com.example.practicaandroid.ui.models.CreditsDetail;
import com.example.practicaandroid.ui.models.CreditsFeed;
import com.example.practicaandroid.ui.models.MovieDetail;
import com.example.practicaandroid.ui.models.MovieFeed;
import com.example.practicaandroid.ui.models.TVShowDetail;
import com.example.practicaandroid.ui.models.TVShowFeed;
import com.example.practicaandroid.ui.models.TVShowListed;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieService {


    //Movie calls

    @GET("movie/top_rated")
    Call<MovieFeed> getTopRated(@Query("api_key") String apiKey, @Query("language") String language);

    @GET("/movie/{movie_id}/credits")
    Call<CreditsFeed> getCast(@Path("id") int id, @Query("api_key") String apiKey, @Query("language") String language);

    @GET("movie/popular")
    Call<MovieFeed> getMovies(@Query("api_key") String apiKey, @Query("language") String language);

    @GET("movie/{id}")
    Call<MovieDetail> getMovieByID(@Path("id") int id, @Query("api_key") String apiKey, @Query("language") String language);

    @GET("movie/{id}/credits")
    Call<CreditsFeed> getMovieCast(@Path("id") int id, @Query("api_key") String apiKey, @Query("language") String language);

    @GET("/3/search/movie/")
    Call<MovieFeed> getMovieByName(@Query("api_key") String apiKey, @Query("language") String language, @Query("query") String query);

    @GET("/3/movie/{id}/videos")
    Call<Videos> getMovieTrailer(@Path("id") int id, @Query("api_key") String apiKey, @Query("language") String language);

    @GET("/3/movie/{id}/similar")
    Call<MovieFeed> getSimilarMovies(@Path("id") int id, @Query("api_key") String apiKey, @Query("language") String language);


    //Tv calls

    @GET("tv/popular")
    Call<TVShowFeed> getSeriesPopular(@Query("api_key") String apiKey, @Query("language") String language);

    @GET("tv/{id}")
    Call<TVShowDetail> getSerieByID(@Path("id") int id, @Query("api_key") String apiKey, @Query("language") String language);

    @GET("tv/top_rated")
    Call<TVShowFeed> getSeriesTopRated(@Query("api_key") String apiKey, @Query("language") String language);

    @GET("tv/{id}/credits")
    Call<CreditsFeed> getSeriesCast(@Path("id") int id, @Query("api_key") String apiKey, @Query("language") String language);

    @GET("/3/search/tv/")
    Call<TVShowFeed> getSerieByName(@Query("api_key") String apiKey, @Query("language") String language, @Query("query") String query);

    @GET("/3/tv/{id}/videos")
    Call<Videos> getSerieTrailer(@Path("id") int id, @Query("api_key") String apiKey, @Query("language") String language);

    @GET("/3/tv/{id}/similar")
    Call<TVShowFeed> getSimilarSeries(@Path("id") int id, @Query("api_key") String apiKey, @Query("language") String language);


    //Cast calls

    @GET("/3/person/{id}")
    Call<CreditsDetail> getActorByID(@Path("id") int id, @Query("api_key") String apiKey, @Query("language") String language);


    @GET("/3/person/{id}/movie_credits")
    Call<CastMovieCredits> getActorCredits(@Path("id") int id, @Query("api_key") String apiKey, @Query("language") String language);

    @GET("/3/person/{id}/tv_credits")
    Call<CastMovieCredits> getActorSeriesCredits(@Path("id") int id, @Query("api_key") String apiKey, @Query("language") String language);








}