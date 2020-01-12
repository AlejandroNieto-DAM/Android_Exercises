package com.example.practicaandroid.ui.adapaters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.practicaandroid.R;
import com.example.practicaandroid.VistaSerie;
import com.example.practicaandroid.ui.models.TVShowDetail;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/*
    Defino un adaptador que hereda de RecyclerView.Adaptar y que definirá una clase aninada llamada castViewHolder
 */
public class SerieDetailAdapter extends RecyclerView.Adapter<SerieDetailAdapter.seriesDetailViewHolder> {
    /*
    Atributos
    */
    public final Context context; //Almacena el contexto donde se ejecutará
    private ArrayList<TVShowDetail> list; //Almacenará las películas a mostrar
    private SerieDetailAdapter.OnItemClickListener listener; //Listener para cuando se haga click

    //Defino un interface con el OnItemClickListener
    public interface OnItemClickListener {
        void onItemClick(TVShowDetail movie);
    }

    /*
    Constructor
    */
    public SerieDetailAdapter(Context context) {
        this.list = new ArrayList<TVShowDetail>();
        this.context = context;
        setListener();
    }

    /*
    Asocio al atributo listener el onItemClickListener correspondiente y sobreescribo el método onItemClick pasando como
    argumento una película
    */
    private void setListener () {
        this.listener = new SerieDetailAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(TVShowDetail serie) {
                Intent intent = new Intent(context, VistaSerie.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                String message = serie.getName();
                float id = serie.getId();


                intent.putExtra("id", id);

                context.startActivity(intent);
            }


        };
    }

    /*
    Sobreescribo onCreateViewHolder, donde  "inflo" la vista de cada item  y devuelve el viewholder que se crea pasándole la vista
    como parámetro
    */
    @Override
    public seriesDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_movie_item_roundedcorners, parent, false);
        seriesDetailViewHolder tvh = new seriesDetailViewHolder(itemView);

        return tvh;
    }

    /*
    Sobreescribe onViewHolder, haciendo que muestre la película que hay en el arraylist list en la posición que pasamos como
    parámetro
     */
    @Override
    public void onBindViewHolder(seriesDetailViewHolder holder, int position) {

        final TVShowDetail serie = list.get(position);
        Log.i("Id_peli:",serie.getName()+" "+serie.getId());


        holder.bindSerie(serie, listener);
    }

    /*
    Sobreescribe getItemCount devolviendo el número de películas que tenemos en el arraylist list.
     */
    @Override
    public int getItemCount() {
        return list.size();
    }

    /*
    Defino el viewHolder anidado que hereda de Recycler.ViewHolder necesario para que funcione el adaptador
     */
    public class seriesDetailViewHolder extends RecyclerView.ViewHolder {
        /*
        Atributos
         */
        TextView tvName;
        RatingBar ratingBar;
        ImageView image;

        /*
            Constructor, enlazo los atributos con los elementos del layout
         */
        public seriesDetailViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);
            image = (ImageView) itemView.findViewById(R.id.imageView);

        }

        /*
        Defino un método que servirá para poner los datos de la película en los correspondientes textviews del layout e
        invocará al listener del adaptador cuando se haga click sobre la vista del viewHolder.
         */
        public void bindSerie(final TVShowDetail serie, final SerieDetailAdapter.OnItemClickListener listener) {
            tvName.setText(serie.getName());
            float puntuacion =  (serie.getVote_average()) / 2;
            ratingBar.setNumStars(5);
            ratingBar.setRating(puntuacion);
            if( serie.getPoster_path() != null){
                Picasso.get().load("https://image.tmdb.org/t/p/w500" + serie.getPoster_path()).into(image);
            } else {
                Picasso.get().load(R.mipmap.ic_launcher_serie_foreground).into(image);
            }

            /*Coloco el Listener a la vista)*/
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(serie);
                }
            });
        }
    }

    /*
    Método que limpia el array list de contenidos, carga los nuevos contenidos que se le pasan por parámetro e invoca a
    notifyDataSetChanged para hacer que se refresque la vista del RecyclerView
     */
    public void swap(List<TVShowDetail> newListSeries) {
        list.clear();
        list.addAll(newListSeries);
        notifyDataSetChanged();
    }
}