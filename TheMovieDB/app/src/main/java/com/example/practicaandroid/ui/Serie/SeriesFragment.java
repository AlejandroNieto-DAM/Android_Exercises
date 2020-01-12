package com.example.practicaandroid.ui.Serie;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practicaandroid.ui.adapaters.FindSerieAdapter;
import com.example.practicaandroid.R;
import com.example.practicaandroid.ui.adapaters.SerieAdapter;
import com.example.practicaandroid.ui.models.TVShowFeed;
import com.example.practicaandroid.ui.retrofit.MovieService;
import com.example.practicaandroid.ui.retrofit.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeriesFragment extends Fragment {

    private SeriesViewModel seriesViewModel;
    RecyclerView recyclerView;
    RecyclerView recyclerView3;
    SerieAdapter movieAdapter;
    SerieAdapter movieAdapter2;
    RecyclerView recyclerView2;
    ProgressBar progressBar;

    EditText editText;

    FindSerieAdapter movieAdapter3;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        seriesViewModel =
                ViewModelProviders.of(this).get(SeriesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_media_final, container, false);
        /* Enlaza el RecyclerView definido en el layout con el objeto recyclerView */
        recyclerView = (RecyclerView) root.findViewById(R.id.recycler_view);
        recyclerView2 = (RecyclerView) root.findViewById(R.id.recycler_view2);
        recyclerView3 = (RecyclerView) root.findViewById(R.id.recycler_view_busqueda);
        recyclerView3.setVisibility(View.INVISIBLE);

        /* Establece que recyclerView tendrá un layout lineal, en concreto vertical*/
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView2.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView3.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));

        /*  Indica que cada uno de los items va a tener un tamaño fijo*/
        recyclerView.setHasFixedSize(true);
        recyclerView2.setHasFixedSize(true);
        recyclerView3.setHasFixedSize(true);

        /* Establece la  decoración por defecto de cada uno de lo items: una línea de división*/
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView2.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView3.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        /* Instancia un objeto de la clase MovieAdapter */
        movieAdapter = new SerieAdapter(this.getContext());
        movieAdapter2 = new SerieAdapter(this.getContext());
        movieAdapter3 = new FindSerieAdapter(this.getContext());

        /* Establece el adaptador asociado a recyclerView */
        recyclerView.setAdapter(movieAdapter);
        recyclerView2.setAdapter(movieAdapter2);
        recyclerView3.setAdapter(movieAdapter3);



        /* Pone la animación por defecto de recyclerView */
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView2.setItemAnimator(new DefaultItemAnimator());
        recyclerView3.setItemAnimator(new DefaultItemAnimator());

        editText = (EditText) root.findViewById(R.id.editText);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() > 0){
                    recyclerView3.setVisibility(View.VISIBLE);
                    loadBusqueda(s);
                } else if(s.length() == 0){
                    recyclerView3.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        progressBar = (ProgressBar) root.findViewById(R.id.progressBar);

        loadSearch();

        return root;
    }

    public void loadSearch () {
        /* Crea la instanncia de retrofit */
        MovieService getMovie = RetrofitInstance.getRetrofitInstance().create(MovieService.class);
        /* Se definen los parámetros de la llamada a la función getTopRated */
        Call<TVShowFeed> call2 = getMovie.getSeriesPopular(RetrofitInstance.getApiKey(), "en-US");
        Call<TVShowFeed> call = getMovie.getSeriesTopRated(RetrofitInstance.getApiKey(), "en-US");

        /* Se hace una llamada asíncrona a la API */
        call.enqueue(new Callback<TVShowFeed>() {
            @Override
            public void onResponse(Call<TVShowFeed> call, Response<TVShowFeed> response) {
                switch (response.code()) {
                    /* En caso de respuesta correcta */
                    case 200:
                        TVShowFeed data = response.body();
                        movieAdapter.swap(data.getResults());
                        movieAdapter.notifyDataSetChanged();
                        break;
                    case 401:
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onFailure(Call<TVShowFeed> call, Throwable t) {
                Toast.makeText(movieAdapter.context, "Error loading top rated series...", Toast.LENGTH_SHORT).show();

            }
        });

        /* Se hace una llamada asíncrona a la API */
        call2.enqueue(new Callback<TVShowFeed>() {
            @Override
            public void onResponse(Call<TVShowFeed> call, Response<TVShowFeed> response) {
                switch (response.code()) {
                    /* En caso de respuesta correcta */
                    case 200:
                        TVShowFeed data = response.body();
                        movieAdapter2.swap(data.getResults());
                        progressBar.setVisibility(View.INVISIBLE);
                        movieAdapter2.notifyDataSetChanged();
                        break;
                    case 401:
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onFailure(Call<TVShowFeed> call, Throwable t) {
                Toast.makeText(movieAdapter2.context, "Error loading popular series...", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void loadBusqueda(CharSequence s){

        String nombre = s.toString();
        MovieService getMovie = RetrofitInstance.getRetrofitInstance().create(MovieService.class);
        Call<TVShowFeed> call = getMovie.getSerieByName(RetrofitInstance.getApiKey(), "en-US", s.toString());

        call.enqueue(new Callback<TVShowFeed>() {
            @Override
            public void onResponse(Call<TVShowFeed> call, Response<TVShowFeed> response) {
                switch (response.code()) {
                    case 200:
                        TVShowFeed data = response.body();
                        movieAdapter3.swap(data.getResults());
                        movieAdapter3.notifyDataSetChanged();
                        break;
                    case 401:
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onFailure(Call<TVShowFeed> call, Throwable t) {
                Toast.makeText(movieAdapter3.context, "Error finding...", Toast.LENGTH_SHORT).show();

            }
        });
    }

}