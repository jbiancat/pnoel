package lpmiar.pnoel;

import java.util.Calendar;

public class Enfant {
    //URL des pictos
    public final static String URL_IMG_SAGE = "https://cdn3.iconfinder.com/data/icons/lightly-icons/30/happy-480.png";
    public final static String URL_IMG_PAS_SAGE = "https://png2.kisspng.com/sh/c0c04814eed9a8880993f8091161e170/L0KzQYm3VcE3N6h0j5H0aYP2gLBuTfNwdaF6jNd7LXnmf7B6TfVud6Vue9H3LYPkdLBskCMue55uhNdELXPvecG0ggJ1NWZmftUBYUnoQIqAWMQ3NmU2TKsDMkezQYa5VsQ6OWk1TqI8OEixgLBu/kisspng-computer-icons-emoticon-sadness-smiley-clip-art-5afc6a9e097846.4149827015264918060388.png";
    public final static String URL_IMG_LETTRE_nRECU = "https://www.julielessard.com/lettre.png";
    public final static String URL_IMG_LETTRE_RECU = "https://static.thenounproject.com/png/466457-200.png";
    public final static String URL_IMG_KDO_LIVRE = "https://www.memo-cadeaux.com/images/picto/picto-offrir.png";
    public final static String URL_IMG_KDO_nLIVRE = "https://png2.kisspng.com/sh/ef776cdb640f63b47660fb35ce820c48/L0KzQYm3VMI4N6lnfZH0aYP2gLBuTfNwdaF6jNd7LXnmf7B6TfJwgF46edc8ZEnnRYKCVPM5QF88TqoCNEmzQ4K8UsQ5QGI9T6k6MEO5PsH1h5==/kisspng-computer-icons-box-5ae3d9d5194c88.7687490315248818771036.png";

    public final static String TEXT_SAGE = "Sage";
    public final static String TEXT_PAS_SAGE = "Pas sage";
    public final static String TEXT_LETTRE_RECU = "Reçue";
    public final static String TEXT_LETTRE_nRECU = "Envoyée";
    public final static String TEXT_KDO_LIVRE = "Livrés";
    public final static String TEXT_KDO_nLIVRE = "Non livrés";

    private int age;
    private int anneeNaissance;
    private String prenom;
    private String sexe;
    private boolean lettreRecu;
    private boolean sage;
    private boolean cadeauLivre;

    public Enfant(int aN, String p, String s){
        int now = Calendar.getInstance().get(Calendar.YEAR);
        this.anneeNaissance = aN;
        this.age = now - this.anneeNaissance;
        this.prenom = p;
        this.sexe = s;
        this.sage = true;
        this.lettreRecu = false;
        this.cadeauLivre = false;
    }

    public int getAge() { return age; }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAnneeNaissance() {
        return anneeNaissance;
    }

    public void setAnneeNaissance(int anneeNaissance) {
        this.anneeNaissance = anneeNaissance;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public boolean isLettreRecu() {
        return lettreRecu;
    }

    public void setLettreRecu(boolean lettreRecu) {
        this.lettreRecu = lettreRecu;
    }

    public boolean isSage() {
        return sage;
    }

    public void setSage(boolean sage) {
        this.sage = sage;
    }

    public boolean isCadeauLivre() {
        return cadeauLivre;
    }

    public void setCadeauLivre(boolean cadeauLivre) {
        this.cadeauLivre = cadeauLivre;
    }

    public String getInitiale(){
        return String.valueOf(getPrenom().charAt(0));
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(this.getPrenom() + " " + this.getAnneeNaissance() + " " + this.getSexe());
        return sb.toString();
    }
}
