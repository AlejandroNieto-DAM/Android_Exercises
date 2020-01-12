package com.example.practicaandroid.ui.Favorites;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practicaandroid.FeedReaderDbHelper;
import com.example.practicaandroid.ui.adapaters.MovieDetailAdapter;
import com.example.practicaandroid.R;
import com.example.practicaandroid.ui.adapaters.SerieDetailAdapter;
import com.example.practicaandroid.ui.models.MovieDetail;
import com.example.practicaandroid.ui.models.TVShowDetail;
import com.example.practicaandroid.ui.retrofit.MovieService;
import com.example.practicaandroid.ui.retrofit.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavouritesFragment extends Fragment {

    private FavouritesViewModel favouritesViewModel;
    RecyclerView recyclerView;
    RecyclerView recyclerView2;
    MovieDetailAdapter movieAdapter;
    SerieDetailAdapter serieAdapter;

    ConstraintLayout capa;

    float id = 0;

    List<MovieDetail> peliculasFavoritas = new ArrayList<>();
    List<TVShowDetail> seriesFavoritas = new ArrayList<>();



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        favouritesViewModel =
               ViewModelProviders.of(this).get(FavouritesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_favoritos, container, false);



        /* Enlaza el RecyclerView definido en el layout con el objeto recyclerView */
        recyclerView = (RecyclerView) root.findViewById(R.id.recycler_view);
        recyclerView2 = (RecyclerView) root.findViewById(R.id.recycler_view2);

        /* Establece que recyclerView tendrá un layout lineal, en concreto vertical*/
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView2.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false));

        /*  Indica que cada uno de los items va a tener un tamaño fijo*/
        recyclerView.setHasFixedSize(true);
        recyclerView2.setHasFixedSize(true);

        /* Establece la  decoración por defecto de cada uno de lo items: una línea de división*/
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView2.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        /* Instancia un objeto de la clase MovieAdapter */
        movieAdapter = new MovieDetailAdapter(this.getContext());
        serieAdapter = new SerieDetailAdapter(this.getContext());

        /* Establece el adaptador asociado a recyclerView */
        recyclerView.setAdapter(movieAdapter);
        recyclerView2.setAdapter(serieAdapter);


        /* Pone la animación por defecto de recyclerView */
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView2.setItemAnimator(new DefaultItemAnimator());

        capa = (ConstraintLayout) root.findViewById(R.id.capaNegra);

        loadFavs();

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        movieAdapter.notifyDataSetChanged();
        serieAdapter.notifyDataSetChanged();
        //loadFavs();
    }

    public void loadFavs(){
        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(this.getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if(db != null) {
            String[] projection = {
                    "_id",
                    "idFav"
            };
            Cursor pelisFav = db.query("favIds", projection, null, null, null, null, null);

            while (pelisFav.moveToNext()) {
                long itemId = pelisFav.getLong(
                        pelisFav.getColumnIndexOrThrow("idFav"));
                id = itemId;
                loadSearch();
                //capa.setVisibility(View.VISIBLE);


            }

            Cursor seriesFav = db.query("series", projection, null, null, null, null, null );

            while(seriesFav.moveToNext()) {
                long itemId = seriesFav.getLong(
                        seriesFav.getColumnIndexOrThrow("idFav"));
                id = itemId;
                loadSearchSeries();
                //capa.setVisibility(View.VISIBLE);
            }



            pelisFav.close();

        }
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
                        peliculasFavoritas.add(data);
                        movieAdapter.swap(peliculasFavoritas);
                        movieAdapter.notifyDataSetChanged();
                        break;
                    case 401:
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onFailure(Call<MovieDetail> call, Throwable t) {
                Toast.makeText(movieAdapter.context, "Error cargando películas", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void loadSearchSeries () {


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
                        seriesFavoritas.add(data);
                        serieAdapter.swap(seriesFavoritas);
                        serieAdapter.notifyDataSetChanged();
                        break;
                    case 401:
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onFailure(Call<TVShowDetail> call, Throwable t) {
                Toast.makeText(serieAdapter.context, "Error cargando películas", Toast.LENGTH_SHORT).show();
            }
        });

    }


}