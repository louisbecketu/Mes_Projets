import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Bataille extends Jeu{

    public Bataille(Joueur J1, Joueur J2, ArrayList<Card> pioche, ArrayList<BonusCard> bonusCard){
        super();
        this.J1 = J1;
        this.J2 = J2;
        this.pioche = pioche;
        this.bonusCard = bonusCard;
    }

        public void bataille(boolean estVide, Card c1, Card c2){
        if(!fini()){
            Scanner sc = new Scanner(System.in);
        if(J1.getCartes().size()>=2){
            System.out.println("Bataille ! Veuillez choisir une carte à mettre au milieu puis au dessus !");
            for(int y = 0; y < 2; y++){
                System.out.println("Tour de " + J1.getNom() + " :");
                ArrayList cartesEnMain = J1.Cartes(J1.getCartes());
                for(int i=0;i<cartesEnMain.size();i++){
                    System.out.print(cartesEnMain.get(i));
                }
                System.out.println("Saisir le numèro de la carte à jouer : ");
                String saisie1 = sc.nextLine();
                while (!bonneSaisieCard(saisie1, J1.getCartes())) {
                    System.out.println("Veuillez saisir un chiffre en 1 et " + J1.getCartes().size() + " !");
                    saisie1 = sc.nextLine();
                }
                c1 = this.J1.getCartes().get(Integer.parseInt(saisie1)-1);
                c1 = this.J1.getCartes().remove(Integer.parseInt(saisie1)-1);
            }
            System.out.print("\033\143");
            for(int y=0; y<2; y++){
                System.out.println("Tour de " + J2.getNom() + " :");
                ArrayList cartesEnMain = J2.Cartes(J2.getCartes());
                for(int i=0;i<cartesEnMain.size();i++){
                    System.out.print(cartesEnMain.get(i));
                }
                String saisie2 = sc.nextLine();
                while (!bonneSaisieCard(saisie2, J1.getCartes())) {
                    System.out.println("Veuillez saisir un chiffre en 1 et " + J2.getCartes().size() + " !");
                    saisie2 = sc.nextLine();
                }
                c2 = this.J2.getCartes().get(Integer.parseInt(saisie2)-1);
                c2 = this.J2.getCartes().remove(Integer.parseInt(saisie2)-1);
            }
            System.out.print("\033\143");
        }else{
            System.out.println("Bataille ! Il ne vous reste qu'une carte, elle a été joué automatiquement !");
            c1 = this.J1.getCartes().get(0);
            c1 = this.J1.getCartes().remove(0);
            c2 = this.J2.getCartes().get(0);
            c2 = this.J2.getCartes().remove(0);
        }

        if (!estVide)piocheBataille(pioche);
        System.out.println(J1.getNom() + " : " + c1 + "vs \n" + J2.getNom() + " :" + c2);
        if (c1.compareTo(c2)==1){
            System.out.println(J1.getNom() + " gagne la manche ! \n");
            J1.addScore(1);
        }else if (c1.compareTo(c2)==-1){
            System.out.println(J2.getNom() + " gagne la manche ! \n");
            J2.addScore(1);
        }else{
            System.out.println("Egalité \n");
            estVide = pioche.size()<=0;
            bataille(estVide, c1,c2);
        }
        }
    }

    public void piocheBataille(ArrayList<Card> pioche){
        Random r = new Random();
        if(pioche.size() < 4){
            int indice = r.nextInt(0, pioche.size());
            Card val = pioche.get(indice);
            this.J1.getCartes().add(val);
            pioche.remove(indice);

            int indice2 = r.nextInt(0, pioche.size());
            Card val2 = pioche.get(indice2);
            this.J2.getCartes().add(val2);
            pioche.remove(indice2);
        }else{
            for (int i=0;i<2;i++){
                int indice = r.nextInt(0, pioche.size());
                Card val = pioche.get(indice);
                this.J1.getCartes().add(val);
                pioche.remove(indice);

                int indice2 = r.nextInt(0, pioche.size());
                Card val2 = pioche.get(indice2);
                this.J2.getCartes().add(val2);
                pioche.remove(indice2);
            }
        }
    }

}
