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

import com.example.practicaandroid.ui.Serie.SeriesFragment;
import com.example.practicaandroid.ui.Videos.Result;
import com.example.practicaandroid.ui.Videos.Videos;
import com.example.practicaandroid.ui.adapaters.CastAdapter;
import com.example.practicaandroid.ui.adapaters.SerieAdapter;
import com.example.practicaandroid.ui.models.CreditsFeed;
import com.example.practicaandroid.ui.models.MovieFeed;
import com.example.practicaandroid.ui.models.TVShowDetail;
import com.example.practicaandroid.ui.models.TVShowFeed;
import com.example.practicaandroid.ui.retrofit.MovieService;
import com.example.practicaandroid.ui.retrofit.RetrofitInstance;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VistaSerie extends AppCompatActivity {
    float id = 0;
    TextView title;
    TextView sinopsis;
    RatingBar ratingBar;
    ImageView movieImage;
    ProgressBar bar;
    TextView released;
    TextView genre;
    TextView studio;
    TextView numberSeasons;
    TextView numberEpisodes;
    TextView status;
    Button trailerLink;
    RecyclerView recyclerView;
    RecyclerView recyclerViewCompanies;
    CheckBox favorite;

    CastAdapter serieAdapter;
    SerieAdapter similar;
    Button back;

    List<Result> allVideos = new ArrayList<>();
    Result trailer = new Result();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_media_serie);

        Intent intent = getIntent();
        id = intent.getFloatExtra("id", 0);



        bar = (ProgressBar) findViewById(R.id.progressLoading);

        title = findViewById(R.id.textView2);
        sinopsis = findViewById(R.id.textView8);

        back = (Button) findViewById(R.id.buttonBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //Intent intent = new Intent(VistaSerie.this, MainActivity.class);
                //startActivity(intent);
            }
        });

        ratingBar = (RatingBar) findViewById(R.id.ratingBar2);
        movieImage = (ImageView) findViewById(R.id.imageView3);


        released = (TextView) findViewById(R.id.release);
        genre = (TextView) findViewById(R.id.genre);
        studio = (TextView) findViewById(R.id.textView9);

        numberEpisodes = (TextView) findViewById(R.id.textView27);
        numberSeasons = (TextView) findViewById(R.id.textView25);
        status = (TextView) findViewById(R.id.textView29);


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
        serieAdapter = new CastAdapter(this);
        similar = new SerieAdapter(this);

        /* Establece el adaptador asociado a recyclerView */
        recyclerView.setAdapter(serieAdapter);
        recyclerViewCompanies.setAdapter(similar);


        /* Pone la animación por defecto de recyclerView */
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerViewCompanies.setItemAnimator(new DefaultItemAnimator());


        loadSimilar();

        loadCast();

        loadTrailer();

        loadSearch();
    }

    public void loadSerie(final TVShowDetail serie){

        title.setText(String.valueOf(serie.getName()));

        sinopsis.setText(String.valueOf(serie.getOverview()));

        numberEpisodes.setText(String.valueOf(serie.getNumber_of_episodes()).substring(0, String.valueOf(serie.getNumber_of_episodes()).length() - 2 ));

        numberSeasons.setText(String.valueOf(serie.getNumber_of_seasons()).substring(0, String.valueOf(serie.getNumber_of_seasons()).length() - 2 ));

        status.setText(String.valueOf(serie.getStatus()));

        if(serie.getPoster_path() != null){
            Picasso.get().load("https://image.tmdb.org/t/p/w500" + serie.getPoster_path()).into(movieImage);
        } else {
            Picasso.get().load(R.mipmap.ic_serie_without_photo).into(movieImage);
        }

        float punctuation = (serie.getVote_average()) / 2;
        ratingBar.setNumStars(5);
        ratingBar.setRating(punctuation);

        studio.setText("");
        if(serie.getProductionCompanies() != null && !serie.getProductionCompanies().isEmpty()) {
            for (int i = 0; i < serie.getProductionCompanies().size() - 1; i++) {
                studio.setText(studio.getText() + String.valueOf(serie.getProductionCompanies().get(i)).substring(String.valueOf(serie.getProductionCompanies().get(i)).indexOf("name") + 5, String.valueOf(serie.getProductionCompanies().get(i)).indexOf("origin_country") - 2) + ", ");

            }
            studio.setText(studio.getText() + String.valueOf(serie.getProductionCompanies().get(serie.getProductionCompanies().size() - 1)).substring(String.valueOf(serie.getProductionCompanies().get(serie.getProductionCompanies().size() - 1)).indexOf("name") + 5, String.valueOf(serie.getProductionCompanies().get(serie.getProductionCompanies().size() - 1)).indexOf("origin_country") - 2) + ".");
        }

        final float movieId = serie.getId();

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
                    Toast.makeText(VistaSerie.this, "We couldnt find the trailer...", Toast.LENGTH_SHORT).show();
                }
            }
        });

        genre.setText("");
        if(serie.getGenres() != null && !serie.getGenres().isEmpty()) {
            for (int i = 0; i < serie.getGenres().size() - 1; i++) {
                genre.setText(genre.getText() + String.valueOf(serie.getGenres().get(i)).substring(String.valueOf(serie.getGenres().get(i)).indexOf("name") + 5, String.valueOf(serie.getGenres().get(i)).toString().length() - 1) + ", ");

            }
            genre.setText(genre.getText() + String.valueOf(serie.getGenres().get(serie.getGenres().size() - 1)).substring(String.valueOf(serie.getGenres().get(serie.getGenres().size() - 1)).indexOf("name") + 5, String.valueOf(serie.getGenres().get(serie.getGenres().size() - 1)).toString().length() - 1) + ".");
        }

        released.setText(String.valueOf(serie.getLast_air_date()));


        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(this);
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        //dbHelper.onUpgrade(db, 0, 0);
        boolean encontradoEnFav = false;

        if(db != null){
            String[] projection = {
                    "_id",
                    "idFav"
            };

            String selection = "idFav = ?";
            String[] selectionArgs = { String.valueOf(serie.getId()) };

            Cursor pelisFav = db.query("series", projection, selection, selectionArgs, null, null, null );

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
                            db.execSQL("INSERT INTO series (idFav) VALUES ('" + String.valueOf(serie.getId()) + "')");
                            favorite.setChecked(true);
                        } else {
                            favorite.setChecked(true);
                        }

                    } else if(isChecked == false) {
                        Log.i("movie id ",  String.valueOf(serie.getId()));
                        db.execSQL("DELETE FROM series WHERE idFav = '"+ String.valueOf(serie.getId()) + "';");
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
        Call<TVShowDetail> call = getMovie.getSerieByID(idReal, RetrofitInstance.getApiKey(), "en-US");
        /* Se hace una llamada asíncrona a la API */
        call.enqueue(new Callback<TVShowDetail>() {
            @Override
            public void onResponse(Call<TVShowDetail> call, Response<TVShowDetail> response) {
                switch (response.code()) {
                    /* En caso de respuesta correcta */
                    case 200:
                        /* En el objeto data de la clase MovieFeed se almacena el JSON convertido a objetos*/
                        TVShowDetail data = response.body();
                        loadSerie(data);
                        bar.setVisibility(View.INVISIBLE);
                        break;
                    case 401:
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onFailure(Call<TVShowDetail> call, Throwable t) {
                Toast.makeText(VistaSerie.this, "Error loading the serie...", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void loadCast() {
        /* Crea la instanncia de retrofit */
        int idReal = (int) id;

        MovieService getMovie = RetrofitInstance.getRetrofitInstance().create(MovieService.class);
        /* Se definen los parámetros de la llamada a la función getTopRated */
        Call<CreditsFeed> call = getMovie.getSeriesCast(idReal, RetrofitInstance.getApiKey(), "en-US");

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
                        serieAdapter.swap(data.getCast());
                        /* Se notifica un cambio de datos para que se refresque la vista */
                        serieAdapter.notifyDataSetChanged();
                        break;
                    case 401:
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onFailure(Call<CreditsFeed> call, Throwable t) {
                Toast.makeText(serieAdapter.context, "Error loading the cast...", Toast.LENGTH_SHORT).show();

            }

        });
    }

    private void loadTrailer() {
        /* Crea la instanncia de retrofit */
        int idReal = (int) id;
        MovieService getMovie = RetrofitInstance.getRetrofitInstance().create(MovieService.class);
        /* Se definen los parámetros de la llamada a la función getTopRated */
        Call<Videos> call = getMovie.getSerieTrailer(idReal, RetrofitInstance.getApiKey(), "en-US");
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
                        break;
                    case 401:
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onFailure(Call<Videos> call, Throwable t) {
                Toast.makeText(serieAdapter.context, "Error loading the trailer...", Toast.LENGTH_SHORT).show();
            }

        });
    }

    public void loadSimilar() {
        /* Crea la instanncia de retrofit */
        int idReal = (int) id;
        MovieService getMovie = RetrofitInstance.getRetrofitInstance().create(MovieService.class);
        /* Se definen los parámetros de la llamada a la función getTopRated */
        Call<TVShowFeed> call = getMovie.getSimilarSeries(idReal, RetrofitInstance.getApiKey(), "en-US");
        /* Se hace una llamada asíncrona a la API */
        call.enqueue(new Callback<TVShowFeed>() {
            @Override
            public void onResponse(Call<TVShowFeed> call, Response<TVShowFeed> response) {
                Log.i("Response", String.valueOf(response));
                switch (response.code()) {
                    /* En caso de respuesta correcta */
                    case 200:
                        /* En el objeto data de la clase MovieFeed se almacena el JSON convertido a objetos*/
                        TVShowFeed data = response.body();
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
            public void onFailure(Call<TVShowFeed> call, Throwable t) {
                Toast.makeText(serieAdapter.context, "Error loading the trailer...", Toast.LENGTH_SHORT).show();
            }

        });
    }
}
