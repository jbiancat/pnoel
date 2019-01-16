package lpmiar.pnoel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
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

        final Enfant e = this.getItem(position);
        if (e != null){
            TextView textPrenom = (TextView) view.findViewById(R.id.text_prenom);
            textPrenom.setText(e.getPrenom());

            TextView textAge = (TextView) view.findViewById(R.id.text_age);
            textAge.setText(String.valueOf(e.getAge())+" ans");

            final ImageButton imgSage = (ImageButton) view.findViewById(R.id.img_sage);
            final ImageButton imgLettre = (ImageButton) view.findViewById(R.id.img_lettre);
            final ImageButton imgKdo = (ImageButton) view.findViewById(R.id.img_kdo);

            final TextView textSage = (TextView) view.findViewById(R.id.textSageImg);
            final TextView textLettre = (TextView) view.findViewById(R.id.textLettreImg);
            final TextView textKdo = (TextView) view.findViewById(R.id.textKdoImg);

            //on set les onClick
            imgSage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    e.setSage(!e.isSage());
                    loadSageImg(imgSage,textSage,e);
                }
            });
            imgLettre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    e.setLettreRecu(!e.isLettreRecu());
                    loadLettreImg(imgLettre, textLettre, e);
                }
            });
            imgKdo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    e.setCadeauLivre(!e.isCadeauLivre());
                    loadKdoImg(imgKdo, textKdo, e);
                }
            });


            //je charge tous les pictos au moins une fois avant de les afficher
            loadImage(Enfant.URL_IMG_SAGE, imgSage);
            loadImage(Enfant.URL_IMG_PAS_SAGE, imgSage);
            loadImage(Enfant.URL_IMG_LETTRE_RECU, imgLettre);
            loadImage(Enfant.URL_IMG_LETTRE_nRECU, imgLettre);
            loadImage(Enfant.URL_IMG_KDO_LIVRE, imgKdo);
            loadImage(Enfant.URL_IMG_KDO_nLIVRE, imgKdo);

            //on charge les images
            loadSageImg(imgSage,textSage,e);
            loadLettreImg(imgLettre, textLettre, e);
            loadKdoImg(imgKdo, textKdo, e);
        }

        return view;
    }

    private void loadImage(String URL, ImageButton button){
        Picasso.get()
                .load(URL)
                .resize(150,150)
                .into(button);
    }

    private void loadSageImg(ImageButton button, TextView textImg, Enfant e){
        if (e.isSage()){
            loadImage(Enfant.URL_IMG_SAGE, button);
            textImg.setText(Enfant.TEXT_SAGE);
        } else {
            loadImage(Enfant.URL_IMG_PAS_SAGE, button);
            textImg.setText(Enfant.TEXT_PAS_SAGE);
        }
    }

    private void loadLettreImg(ImageButton button, TextView textImg, Enfant e){
        if (e.isLettreRecu()){
            loadImage(Enfant.URL_IMG_LETTRE_RECU, button);
            textImg.setText(Enfant.TEXT_LETTRE_RECU);
        } else {
            loadImage(Enfant.URL_IMG_LETTRE_nRECU, button);
            textImg.setText(Enfant.TEXT_LETTRE_nRECU);
        }
    }

    private void loadKdoImg(ImageButton button, TextView textImg, Enfant e){
        if (e.isCadeauLivre()){
            loadImage(Enfant.URL_IMG_KDO_LIVRE, button);
            textImg.setText(Enfant.TEXT_KDO_LIVRE);
        } else {
            loadImage(Enfant.URL_IMG_KDO_nLIVRE, button);
            textImg.setText(Enfant.TEXT_KDO_nLIVRE);
        }
    }

}
