public class BonusCard{
    private String nom;
    private String effet;

    BonusCard(String nom, String effet){
        this.nom = nom;
        this.effet = effet;
    }

    public String getEffet() {
        return this.effet;
    }

    public String getNom() {
        return this.nom;
    }

    public String toString(){
        return this.nom + " : "+this.effet;
    }
}
