package com.example.practicaandroid;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practicaandroid.ui.Videos.Result;
import com.example.practicaandroid.ui.Videos.Videos;
import com.example.practicaandroid.ui.adapaters.CastAdapter;
import com.example.practicaandroid.ui.adapaters.MovieAdapter;
import com.example.practicaandroid.ui.models.CreditsFeed;
import com.example.practicaandroid.ui.models.MovieDetail;
import com.example.practicaandroid.ui.models.MovieFeed;
import com.example.practicaandroid.ui.models.ProductionCompany;
import com.example.practicaandroid.ui.retrofit.MovieService;
import com.example.practicaandroid.ui.retrofit.RetrofitInstance;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class VistaPelicula extends AppCompatActivity {

    float id = 0;
    TextView title;
    TextView sinopsis;
    RatingBar ratingBar;
    ImageView movieImage;
    TextView released;
    TextView genre;
    TextView studio;
    Button trailerLink;
    RecyclerView recyclerView;
    RecyclerView recyclerViewCompanies;
    CheckBox favorite;

    MovieAdapter similar;

    CastAdapter movieAdapter;
    ProgressBar progressBar;

    Button back;


    List<Result> allVideos = new ArrayList<>();
    Result trailer = new Result();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_media);



        Intent intent = getIntent();

        id = intent.getFloatExtra("id", 0);
        title = findViewById(R.id.textView2);
        sinopsis = findViewById(R.id.textView8);

        ratingBar = (RatingBar) findViewById(R.id.ratingBar2);
        movieImage = (ImageView) findViewById(R.id.imageView3);
        movieImage.setVisibility(View.INVISIBLE);
        back = (Button) findViewById(R.id.buttonBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //Intent intent = new Intent(VistaPelicula.this, MainActivity.class);
                //startActivity(intent);
            }
        });


        released = (TextView) findViewById(R.id.release);
        genre = (TextView) findViewById(R.id.genre);
        studio = (TextView) findViewById(R.id.textView9);

        trailerLink = (Button) findViewById(R.id.button);
        favorite = (CheckBox) findViewById(R.id.checkBox2);



        /* Enlaza el RecyclerView definido en el layout con el objeto recyclerView */
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view2);
        recyclerViewCompanies = (RecyclerView) findViewById(R.id.recycler_viewCompanies);


        /* Establece que recyclerView tendrá un layout lineal, en concreto vertical*/
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewCompanies.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        /*  Indica que cada uno de los items va a tener un tamaño fijo*/
        recyclerView.setHasFixedSize(true);
        recyclerViewCompanies.setHasFixedSize(true);

        /* Establece la  decoración por defecto de cada uno de lo items: una línea de división*/
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerViewCompanies.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        /* Instancia un objeto de la clase MovieAdapter */
        movieAdapter = new CastAdapter(this);
        similar = new MovieAdapter(this);

        /* Establece el adaptador asociado a recyclerView */
        recyclerView.setAdapter(movieAdapter);
        recyclerViewCompanies.setAdapter(similar);



        /* Pone la animación por defecto de recyclerView */
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerViewCompanies.setItemAnimator(new DefaultItemAnimator());

        loadSimilar();


        loadCast();

        loadTrailer();

        loadSearch();

    }

    public void loadPelicula(final MovieDetail movie){

        title.setText(String.valueOf(movie.getTitle()));

        sinopsis.setText(String.valueOf(movie.getOverview()));

        if(movie.getPoster_path() != null){
            Picasso.get().load("https://image.tmdb.org/t/p/w500" + movie.getPoster_path()).into(movieImage);
        } else {
            Picasso.get().load(R.mipmap.ic_movie_without_photo).into(movieImage);
        }


        float punctuation = (movie.getVote_average()) / 2;
        ratingBar.setNumStars(5);
        ratingBar.setRating(punctuation);

        studio.setText("");
       if(movie.getProductionCompanies() != null && !movie.getProductionCompanies().isEmpty()) {
            for (int i = 0; i < movie.getProductionCompanies().size() - 1; i++) {
                studio.setText(studio.getText() + String.valueOf(movie.getProductionCompanies().get(i)).substring(String.valueOf(movie.getProductionCompanies().get(i)).indexOf("name") + 5, String.valueOf(movie.getProductionCompanies().get(i)).indexOf("origin_country") - 2) + ", ");

            }
            studio.setText(studio.getText() + String.valueOf(movie.getProductionCompanies().get(movie.getProductionCompanies().size() - 1)).substring(String.valueOf(movie.getProductionCompanies().get(movie.getProductionCompanies().size() - 1)).indexOf("name") + 5, String.valueOf(movie.getProductionCompanies().get(movie.getProductionCompanies().size() - 1)).indexOf("origin_country") - 2) + ".");
        }




        if(allVideos != null) {
            for (int i = 0; i < allVideos.size(); i++) {
                if (allVideos.get(i).getType().equals("Trailer")) {
                    trailer = allVideos.get(i);
                }
            }
        }

        trailerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(trailer != null) {
                    Uri uri = Uri.parse("https://www.youtube.com/watch?v=" + trailer.getKey()); // missing 'http://' will cause crashed
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                } else {
                    Toast.makeText(VistaPelicula.this, "We couldnt find the trailer...", Toast.LENGTH_SHORT).show();
                }
            }
        });

        genre.setText("");
        if(movie.getGenres() != null && !movie.getGenres().isEmpty()) {
            for (int i = 0; i < movie.getGenres().size() - 1; i++) {
                Log.i("GENEROS ", movie.getGenres().toString());
                genre.setText(genre.getText() + String.valueOf(movie.getGenres().get(i)).substring(String.valueOf(movie.getGenres().get(i)).indexOf("name") + 5, String.valueOf(movie.getGenres().get(i)).toString().length() - 1) + ", ");

            }
            genre.setText(genre.getText() + String.valueOf(movie.getGenres().get(movie.getGenres().size() - 1)).substring(String.valueOf(movie.getGenres().get(movie.getGenres().size() - 1)).indexOf("name") + 5, String.valueOf(movie.getGenres().get(movie.getGenres().size() - 1)).toString().length() - 1) + ".");
        }

        released.setText(String.valueOf(movie.getRelease_date()));


        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(this);
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        boolean encontradoEnFav = false;

        if(db != null){
            String[] projection = {
                    "_id",
                    "idFav"
            };

            String selection = "idFav = ?";
            String[] selectionArgs = { String.valueOf(movie.getId()) };

            Cursor pelisFav = db.query("favIds", projection, selection, selectionArgs, null, null, null );

            while(pelisFav.moveToNext()) {

                encontradoEnFav = true;
                favorite.setChecked(true);

            }
            pelisFav.close();
        }


        final boolean finalEncontradoEnFav = encontradoEnFav;
        favorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (db != null) {
                    if(isChecked){

                        if(finalEncontradoEnFav != true){
                            db.execSQL("INSERT INTO favIds (idFav) VALUES ('" + movie.getId() + "')");
                            favorite.setChecked(true);
                        } else {
                            favorite.setChecked(true);
                        }

                    } else if(isChecked == false) {
                        Log.i("movie id ",  String.valueOf(movie.getId()));
                        db.execSQL("DELETE FROM favIds WHERE idFav = '"+ String.valueOf(movie.getId()) + "';");
                    }
                }
            }
        });

    }

    public void loadSearch () {

        int idReal = (int) id;
        /* Crea la instanncia de retrofit */
        MovieService getMovie = RetrofitInstance.getRetrofitInstance().create(MovieService.class);
        /* Se definen los parámetros de la llamada a la función getTopRated */
        Call<MovieDetail> call = getMovie.getMovieByID(idReal, RetrofitInstance.getApiKey(), "en-US");
        /* Se hace una llamada asíncrona a la API */
        call.enqueue(new Callback<MovieDetail>() {
            @Override
            public void onResponse(Call<MovieDetail> call, Response<MovieDetail> response) {
                switch (response.code()) {
                    /* En caso de respuesta correcta */
                    case 200:
                        /* En el objeto data de la clase MovieFeed se almacena el JSON convertido a objetos*/
                        MovieDetail data = response.body();
                        loadPelicula(data);
                        movieImage.setVisibility(View.VISIBLE);
                        break;
                    case 401:
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onFailure(Call<MovieDetail> call, Throwable t) {
                Toast.makeText(VistaPelicula.this, "Error loading the movie...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadCast() {
        /* Crea la instanncia de retrofit */
        int idReal = (int) id;
        MovieService getMovie = RetrofitInstance.getRetrofitInstance().create(MovieService.class);
        /* Se definen los parámetros de la llamada a la función getTopRated */
        Call<CreditsFeed> call = getMovie.getMovieCast(idReal, RetrofitInstance.getApiKey(), "en-US");
        /* Se hace una llamada asíncrona a la API */
        call.enqueue(new Callback<CreditsFeed>() {
            @Override
            public void onResponse(Call<CreditsFeed> call, Response<CreditsFeed> response) {
                switch (response.code()) {
                    /* En caso de respuesta correcta */
                    case 200:
                        /* En el objeto data de la clase MovieFeed se almacena el JSON convertido a objetos*/
                        CreditsFeed data = response.body();
                        /* Se actualizan los datos contenidos en el adaptador */
                        movieAdapter.swap(data.getCast());
                        /* Se notifica un cambio de datos para que se refresque la vista */
                        movieAdapter.notifyDataSetChanged();
                        break;
                    case 401:
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onFailure(Call<CreditsFeed> call, Throwable t) {
                Toast.makeText(movieAdapter.context, "Error loading the cast...", Toast.LENGTH_SHORT).show();

            }

        });
    }

    private void loadTrailer() {
        /* Crea la instanncia de retrofit */
        int idReal = (int) id;
        MovieService getMovie = RetrofitInstance.getRetrofitInstance().create(MovieService.class);
        /* Se definen los parámetros de la llamada a la función getTopRated */
        Call<Videos> call = getMovie.getMovieTrailer(idReal, RetrofitInstance.getApiKey(), "en-US");
        /* Se hace una llamada asíncrona a la API */
        call.enqueue(new Callback<Videos>() {
            @Override
            public void onResponse(Call<Videos> call, Response<Videos> response) {
                Log.i("Response", String.valueOf(response));
                switch (response.code()) {
                    /* En caso de respuesta correcta */
                    case 200:
                        /* En el objeto data de la clase MovieFeed se almacena el JSON convertido a objetos*/
                        Videos data = response.body();
                        /* Se actualizan los datos contenidos en el adaptador */
                        for(Result result :data.getResults()){
                            allVideos.add(result);
                        }
                        Log.i("todos videos", String.valueOf(allVideos.size()));
                        break;
                    case 401:
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onFailure(Call<Videos> call, Throwable t) {
                Toast.makeText(movieAdapter.context, "Error loading the trailer...", Toast.LENGTH_SHORT).show();
            }

        });
    }

    public void loadSimilar(){
        /* Crea la instanncia de retrofit */
        int idReal = (int) id;
        MovieService getMovie = RetrofitInstance.getRetrofitInstance().create(MovieService.class);
        /* Se definen los parámetros de la llamada a la función getTopRated */
        Call<MovieFeed> call = getMovie.getSimilarMovies(idReal, RetrofitInstance.getApiKey(), "en-US");
        /* Se hace una llamada asíncrona a la API */
        call.enqueue(new Callback<MovieFeed>() {
            @Override
            public void onResponse(Call<MovieFeed> call, Response<MovieFeed> response) {
                Log.i("Response", String.valueOf(response));
                switch (response.code()) {
                    /* En caso de respuesta correcta */
                    case 200:
                        /* En el objeto data de la clase MovieFeed se almacena el JSON convertido a objetos*/
                        MovieFeed data = response.body();
                        /* Se actualizan los datos contenidos en el adaptador */
                        similar.swap(data.getResults());
                        similar.notifyDataSetChanged();
                        break;
                    case 401:
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onFailure(Call<MovieFeed> call, Throwable t) {
                Toast.makeText(movieAdapter.context, "Error loading the trailer...", Toast.LENGTH_SHORT).show();
            }

        });
    }
}
