package lpmiar.pnoel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class EnfantArrayAdapter extends ArrayAdapter<Enfant> {

    private int myItemLayout;
    private LayoutInflater li;

    public EnfantArrayAdapter(Context context, int viewRessourceId) {
        super(context,viewRessourceId);
        this.myItemLayout = viewRessourceId;
        this.li = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View view = convertView;

        if (view == null) view = this.li.inflate(this.myItemLayout, null);

        Enfant e = this.getItem(position);
        if (e != null){
            TextView textPrenom = (TextView) view.findViewById(R.id.text_prenom);
            textPrenom.setText(e.getPrenom());

            TextView textAge = (TextView) view.findViewById(R.id.text_age);
            textAge.setText(String.valueOf(e.getAge())+" ans");

            ImageView imgSage = (ImageView) view.findViewById(R.id.img_sage);
            ImageView imgLettre = (ImageView) view.findViewById(R.id.img_lettre);
            ImageView imgKdo = (ImageView) view.findViewById(R.id.img_kdo);

            String imageSageURL = "https://cdn3.iconfinder.com/data/icons/lightly-icons/30/happy-480.png";
            String imageLettreURL = "https://www.julielessard.com/lettre.png";
            String imageKdoURL = "https://www.memo-cadeaux.com/images/picto/picto-offrir.png";
            Picasso.get()
                    .load(imageSageURL)
                    .resize(150,150)
                    .into(imgSage);
            Picasso.get()
                    .load(imageLettreURL)
                    .resize(150,150)
                    .into(imgLettre);
            Picasso.get()
                    .load(imageKdoURL)
                    .resize(150,150)
                    .into(imgKdo);
        }

        return view;
    }
}
