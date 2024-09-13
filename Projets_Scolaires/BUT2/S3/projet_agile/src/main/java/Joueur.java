import java.util.ArrayList;

public class Joueur {
    private String nom;
    private int score;
    private ArrayList<Card> deck;
    private ArrayList<BonusCard> bonusCard;

    public Joueur(){
        this.nom = "";
        this.score = 0;
        this.deck = new ArrayList<Card>();
        this.bonusCard = new ArrayList<BonusCard>();
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom){
        this.nom = nom;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public ArrayList<Card> getCartes() {
        return deck;
    }

    public void addScore(int score){
        this.score += score;
    }

    public String toString(){
        String s = "";
        s += this.nom + " a un score de " + this.score;
        s += " et possède les cartes suivantes : ";
        for (Card c : this.deck){
            s += c.toString() + " ";
        }
        return s;
    }

    public ArrayList<BonusCard> getBonusCard() {
        return bonusCard;
    }

    public ArrayList<String> Cartes(ArrayList<Card> liste){
        ArrayList<String> deckJoueur = new ArrayList<>();
        String l0 = "";
        String l1 = "\n";
        String tp = "\n";
        String l3 = "\n";
        String l4 = "\n";
        String l5 = "\n";
        for(int i=0;i<liste.size();i++){
            l0 += i+1 + ":      \t";
        }deckJoueur.add(l0);
        for(int i=0;i<liste.size();i++){
            l1 += " ______\t\t";
        }deckJoueur.add(l1);
        for(int i=0;i<liste.size();i++){
            if(liste.get(i).value.getValeur() == 11){
                tp += "|V";
            }else if(liste.get(i).value.getValeur() == 12){
                tp += "|D";
            }else if(liste.get(i).value.getValeur() == 13){
                tp += "|R";
            }else if(liste.get(i).value.getValeur() == 14){
                tp += "|A";
            }else{
                tp += "|" + liste.get(i).value.getValeur();
            }
            if(liste.get(i).value.getValeur() == 10){
                tp += "    | \t";
            }else{
                tp += "     | \t";
            }
        }deckJoueur.add(tp);
        for(int i=0;i<liste.size();i++){
            if(liste.get(i).color.equals(Color.CARREAU)){
                l3 += "|  ♦";
                }else if(liste.get(i).color.equals(Color.COEUR)){
                l3 += "|  ♥";
                }else if(liste.get(i).color.equals(Color.PIQUE)){
                l3 += "|  ♠";
                }else if(liste.get(i).color.equals(Color.TREFLE)){
                l3 += "|  ♣";
                }
                l3 += "   | \t";
        }deckJoueur.add(l3);
        for(int i=0;i<liste.size();i++){
            if(liste.get(i).value.getValeur() == 10){
                l4 += "|    ";
            }else{
                l4 += "|     ";
            }
            if(liste.get(i).value.getValeur() == 11){
                l4 += "V|";
            }else if(liste.get(i).value.getValeur() == 12){
                l4 += "D|";
            }else if(liste.get(i).value.getValeur() == 13){
                l4 += "R|";
            }else if(liste.get(i).value.getValeur() == 14){
                l4 += "A|";
            }else{
                l4 += liste.get(i).value.getValeur() + "|";
            }
            l4 += " \t";
        }deckJoueur.add(l4);
        for(int i=0;i<liste.size();i++){
            l5 += " ------ \t";
        }deckJoueur.add(l5);
        deckJoueur.add("\n");
        return deckJoueur;
    }
}