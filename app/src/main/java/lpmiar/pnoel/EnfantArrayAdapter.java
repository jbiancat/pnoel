package lpmiar.pnoel;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class EnfantArrayAdapter extends ArrayAdapter<Enfant> {

    public EnfantArrayAdapter(Context context, int viewRessourceId, int textViewId, List<Enfant> enfants) {
        super(context,viewRessourceId,textViewId, enfants);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View view = super.getView(position, convertView, parent);
        TextView text1 = (TextView) view.findViewById(R.id.text1);
        text1.setText(getItem(position).getPrenom());
        ImageView img = (ImageView) view.findViewById(R.id.img);
        String imageURL = "http://i.imgur.com/DvpvklR.png";
        Picasso.get()
                .load(imageURL)
                .resize(200,200)
                .into(img);

        return view;
    }
}
