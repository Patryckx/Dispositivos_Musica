package com.example.pia_musica;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class SongAdapter extends ArrayAdapter<Song> {

    public SongAdapter(Context context, List<Song> songs) {
        super(context, 0, songs);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtener el objeto Song para esta posición
        Song song = getItem(position);

        // Reutilizar la vista si está disponible, de lo contrario, inflar una nueva
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.song_item, parent, false);
        }

        // Obtener referencias a las vistas en el diseño del elemento de la canción
        TextView titleTextView = convertView.findViewById(R.id.titleTextView);
        TextView bandTextView = convertView.findViewById(R.id.bandTextView);
        TextView yearTextView = convertView.findViewById(R.id.yearTextView);

        // Configurar las vistas con los detalles de la canción actual
        titleTextView.setText(song.getNombreCancion());
        bandTextView.setText(song.getBanda());
        yearTextView.setText(String.valueOf(song.getAno()));

        return convertView;
    }
}
