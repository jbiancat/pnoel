package lpmiar.pnoel;

import java.util.Calendar;

public class Enfant {
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

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(this.getPrenom() + " " + this.getAnneeNaissance() + " " + this.getSexe());
        return sb.toString();
    }
}
