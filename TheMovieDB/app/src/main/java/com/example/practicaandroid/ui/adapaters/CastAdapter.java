package com.example.practicaandroid.ui.adapaters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.practicaandroid.R;
import com.example.practicaandroid.VistaActor;
import com.example.practicaandroid.ui.models.CreditsListed;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/*
    Defino un adaptador que hereda de RecyclerView.Adaptar y que definirá una clase aninada llamada castViewHolder
 */
public class CastAdapter extends RecyclerView.Adapter<CastAdapter.castViewHolder> {
    /*
    Atributos
    */
    public final Context context; //Almacena el contexto donde se ejecutará
    private ArrayList<CreditsListed> list; //Almacenará las películas a mostrar
    private CastAdapter.OnItemClickListener listener; //Listener para cuando se haga click

    //Defino un interface con el OnItemClickListener
    public interface OnItemClickListener {
        void onItemClick(CreditsListed movie);
    }

    /*
    Constructor
    */
    public CastAdapter(Context context) {
        this.list = new ArrayList<CreditsListed>();
        this.context = context;
        setListener();
    }

    /*
    Asocio al atributo listener el onItemClickListener correspondiente y sobreescribo el método onItemClick pasando como
    argumento una película
    */
    private void setListener () {
        this.listener = new CastAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CreditsListed movie) {
                Intent intent = new Intent(context, VistaActor.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                float id = movie.getId();

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
    public castViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_cast_item, parent, false);
        castViewHolder tvh = new castViewHolder(itemView);

        return tvh;
    }

    /*
    Sobreescribe onViewHolder, haciendo que muestre la película que hay en el arraylist list en la posición que pasamos como
    parámetro
     */
    @Override
    public void onBindViewHolder(castViewHolder holder, int position) {

        final CreditsListed movie = list.get(position);
        holder.bindMovie(movie, listener);
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
    public class castViewHolder extends RecyclerView.ViewHolder {
        /*
        Atributos
         */
        TextView tvName;
        ImageView image;

        /*
            Constructor, enlazo los atributos con los elementos del layout
         */
        public castViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            image = (ImageView) itemView.findViewById(R.id.imageView);

        }

        /*
        Defino un método que servirá para poner los datos de la película en los correspondientes textviews del layout e
        invocará al listener del adaptador cuando se haga click sobre la vista del viewHolder.
         */
        public void bindMovie(final CreditsListed actor, final CastAdapter.OnItemClickListener listener) {
            tvName.setText(actor.getName());

            if(actor.getProfile_path() != null){
                Picasso.get().load("https://image.tmdb.org/t/p/w500" + actor.getProfile_path()).into(image);
            } else {
                Picasso.get().load(R.mipmap.ic_launcher_prueba_foreground).into(image);
            }


            /*Coloco el Listener a la vista)*/
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(actor);
                }
            });
        }
    }

    /*
    Método que limpia el array list de contenidos, carga los nuevos contenidos que se le pasan por parámetro e invoca a
    notifyDataSetChanged para hacer que se refresque la vista del RecyclerView
     */
    public void swap(List<CreditsListed> newListMovies) {
        list.clear();
        list.addAll(newListMovies);
        notifyDataSetChanged();
    }
}