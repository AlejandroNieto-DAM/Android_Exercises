package com.example.practicaandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.practicaandroid.ui.CastClassesToMovie.CastMovieCredits;
import com.example.practicaandroid.ui.adapaters.CastAdapter;
import com.example.practicaandroid.ui.adapaters.CastAdapterMovie;
import com.example.practicaandroid.ui.adapaters.CastAdapterSerie;
import com.example.practicaandroid.ui.models.CreditsDetail;
import com.example.practicaandroid.ui.models.MovieDetail;
import com.example.practicaandroid.ui.retrofit.MovieService;
import com.example.practicaandroid.ui.retrofit.RetrofitInstance;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VistaActor extends AppCompatActivity {

    float id;
    TextView name;
    TextView biography;
    TextView birthday;
    ImageView photo;
    TextView place_of_birth;
    TextView popularity;
    TextView knownFor;
    TextView knownAs;

    RecyclerView recyclerView;
    RecyclerView recyclerView2;

    CastAdapterMovie movieAdapter;
    CastAdapterSerie serieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_actor);

        Intent intent = getIntent();

        id = intent.getFloatExtra("id", 0);
        name = (TextView) findViewById(R.id.textView2);
        biography = (TextView) findViewById(R.id.textView9);
        birthday = (TextView) findViewById(R.id.textView8);
        photo = (ImageView) findViewById(R.id.imageView3);
        place_of_birth = (TextView) findViewById(R.id.textView12);
        popularity = (TextView) findViewById(R.id.textView14);
        knownFor = (TextView) findViewById(R.id.textView16);
        knownAs = (TextView) findViewById(R.id.textView18);

        /* Enlaza el RecyclerView definido en el layout con el objeto recyclerView */
        recyclerView = (RecyclerView) findViewById(R.id.moviesActors);
        recyclerView2 = (RecyclerView) findViewById(R.id.seriesActors);

        /* Establece que recyclerView tendrá un layout lineal, en concreto vertical*/
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        /*  Indica que cada uno de los items va a tener un tamaño fijo*/
        recyclerView.setHasFixedSize(true);
        recyclerView.setHasFixedSize(true);


        /* Establece la  decoración por defecto de cada uno de lo items: una línea de división*/
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        /* Instancia un objeto de la clase MovieAdapter */
        movieAdapter = new CastAdapterMovie(this);
        serieAdapter = new CastAdapterSerie(this);

        /* Establece el adaptador asociado a recyclerView */
        recyclerView.setAdapter(movieAdapter);
        recyclerView2.setAdapter(serieAdapter);


        /* Pone la animación por defecto de recyclerView */
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView2.setItemAnimator(new DefaultItemAnimator());

        loadRecycler2();
        loadRecycler();
        loadSearch();
    }

    public void loadRecycler(){
        int idReal = (int) id;
        /* Crea la instanncia de retrofit */
        MovieService getMovie = RetrofitInstance.getRetrofitInstance().create(MovieService.class);
        /* Se definen los parámetros de la llamada a la función getTopRated */
        Call<CastMovieCredits> call = getMovie.getActorCredits(idReal, RetrofitInstance.getApiKey(), "en");
        /* Se hace una llamada asíncrona a la API */
        call.enqueue(new Callback<CastMovieCredits>() {
            @Override
            public void onResponse(Call<CastMovieCredits> call, Response<CastMovieCredits> response) {
                Log.i("Response", String.valueOf(response));
                switch (response.code()) {
                    /* En caso de respuesta correcta */
                    case 200:
                        /* En el objeto data de la clase MovieFeed se almacena el JSON convertido a objetos*/
                        CastMovieCredits data = response.body();
                        movieAdapter.swap(data.getCast());
                        movieAdapter.notifyDataSetChanged();
                        break;
                    case 401:
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onFailure(Call<CastMovieCredits> call, Throwable t) {
                Toast.makeText(VistaActor.this, "Error cargando películas", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void loadRecycler2(){
        int idReal = (int) id;
        /* Crea la instanncia de retrofit */
        MovieService getMovie = RetrofitInstance.getRetrofitInstance().create(MovieService.class);
        /* Se definen los parámetros de la llamada a la función getTopRated */
        Call<CastMovieCredits> call = getMovie.getActorSeriesCredits(idReal, RetrofitInstance.getApiKey(), "en");
        /* Se hace una llamada asíncrona a la API */
        call.enqueue(new Callback<CastMovieCredits>() {
            @Override
            public void onResponse(Call<CastMovieCredits> call, Response<CastMovieCredits> response) {
                switch (response.code()) {
                    /* En caso de respuesta correcta */
                    case 200:
                        /* En el objeto data de la clase MovieFeed se almacena el JSON convertido a objetos*/
                        CastMovieCredits data = response.body();
                        serieAdapter.swap(data.getCast());
                        serieAdapter.notifyDataSetChanged();
                        break;
                    case 401:
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onFailure(Call<CastMovieCredits> call, Throwable t) {
                Toast.makeText(VistaActor.this, "Error cargando películas", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void loadSearch () {

        int idReal = (int) id;
        /* Crea la instanncia de retrofit */
        MovieService getMovie = RetrofitInstance.getRetrofitInstance().create(MovieService.class);
        /* Se definen los parámetros de la llamada a la función getTopRated */
        Call<CreditsDetail> call = getMovie.getActorByID(idReal, RetrofitInstance.getApiKey(), "en");
        /* Se hace una llamada asíncrona a la API */
        call.enqueue(new Callback<CreditsDetail>() {
            @Override
            public void onResponse(Call<CreditsDetail> call, Response<CreditsDetail> response) {
                Log.i("Response", String.valueOf(response));
                switch (response.code()) {
                    /* En caso de respuesta correcta */
                    case 200:
                        /* En el objeto data de la clase MovieFeed se almacena el JSON convertido a objetos*/
                        CreditsDetail data = response.body();
                        loadActor(data);
                        break;
                    case 401:
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onFailure(Call<CreditsDetail> call, Throwable t) {
                Toast.makeText(VistaActor.this, "Error cargando películas", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadActor(CreditsDetail actor){

        name.setText(actor.getName());
        birthday.setText(actor.getBirthday());
        biography.setText(actor.getBiography());
        Picasso.get().load("https://image.tmdb.org/t/p/w500" + actor.getProfilePath()).into(photo);
        place_of_birth.setText(actor.getPlaceOfBirth());
        popularity.setText(String.valueOf(actor.getPopularity()));
        knownFor.setText(actor.getKnownForDepartment());

        if(actor.getAlsoKnownAs().toString().length() != 0) {
            knownAs.setText(String.valueOf(actor.getAlsoKnownAs().toString().substring(1, actor.getAlsoKnownAs().toString().length() - 1)));
            knownAs.setText(knownAs.getText() + ".");
        }

    }
}
