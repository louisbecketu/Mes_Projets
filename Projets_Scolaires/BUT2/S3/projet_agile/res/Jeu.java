import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Jeu{

    Joueur J1;
    Joueur J2;
    ArrayList<Card> pioche;
    ArrayList<BonusCard> bonusCard;

    public Jeu(){
        this.J1 = new Joueur();
        this.J2 = new Joueur();
        this.pioche = new ArrayList<Card>();
        this.bonusCard = new ArrayList<BonusCard>();
    }

    public void save(){
        try (FileWriter fw = new FileWriter("res/save.csv", true); PrintWriter pw = new PrintWriter(fw)) {
            pw.write("Nom : "+J1.getNom() + " VS "+ J2.getNom() + "\n");
            pw.write("Score j1 : "+J1.getScore() + " Score j2 : "+ J2.getScore() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
       
    public static void historique(String csv){
        ArrayList<String[]> tp = new ArrayList<String[]>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(csv))) {
            String delimiter = ";";
            String line = br.readLine();
            while (line != null) {
                tp.add(line.split(delimiter));
                line = br.readLine(); 
            }
        }catch(FileNotFoundException fnfe){
            System.out.println("File not found: "); fnfe.printStackTrace(); 
        }catch (IOException ex) {
            System.out.println("Reading error: " + ex.getMessage());
            ex.printStackTrace();
        }
        for(String[] t : tp){
            for(int i = 0; i < t.length; i++){
                System.out.println(t[i]);
            }
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("\nAppuyez sur Entrée pour continuer...");
        sc.nextLine();
    }

    public ArrayList<Card> genererPacket(){
        ArrayList<Card> packet = new ArrayList<Card>();
        for(int i=0;i<4;i++){
            String color;
            if (i==0) color = "PIQUE";
            else if (i==1) color = "TREFLE";
            else if (i==2) color = "CARREAU";
            else color = "COEUR";
            for (int j=2;j<15;j++){
                String value = "DEUX";
                if (j == 3) value = "TROIS";
                if (j == 4) value = "QUATRE";
                if (j == 5) value = "CINQ";
                if (j == 6) value = "SIX";
                if (j == 7) value = "SEPT";
                if (j == 8) value = "HUIT";
                if (j == 9) value = "NEUF";
                if (j == 10) value = "DIX";
                if (j == 11) value = "VALET";
                if (j == 12) value = "DAME";
                if (j == 13) value = "ROI";
                if (j == 14) value = "ACE";
                Card c = new Card(value, color);
                packet.add(c);
            }
        }
        return packet;
    }

    public void initializationBonusCard(){
        BonusCard bc1= new BonusCard("carteU", "Oblige au joueur d'utiliser une carte");
        bonusCard.add(bc1);
    }
       
    public int utilisationBonus(BonusCard bc){
        int idx = 0;
        Scanner sc = new Scanner(System.in);
        if(bc.getNom().equalsIgnoreCase("carteU")){
            System.out.println("Quelle carte voulez-vous que votre adversaire utilise");
            idx = sc.nextInt();
        }
        return idx;
    }

    public void distribuerCarte(ArrayList<Card> packet){
        Random r = new Random();
        for (int i = 0;i<5;i++){
            int indice = r.nextInt(0, packet.size());
            Card val = packet.get(indice);
            this.J1.getCartes().add(val);
            packet.remove(indice);
        }

        for (int i = 0;i<5;i++){
            int indice = r.nextInt(0, packet.size());
            Card val = packet.get(indice);
            this.J2.getCartes().add(val);
            packet.remove(indice);
        }

        this.pioche = packet;
    }

    /*public void distribuerCarte(ArrayList<Card> packet, int nbManche){
        int nbCartes = nbManche*2;
        Random r = new Random();
        if (nbManche>=5){
            for (int i = 0;i<5;i++){
                int indice = r.nextInt(0, packet.size());
                Card val = packet.get(indice);
                this.J1.getCartes().add(val);
                packet.remove(indice);
            }

            for (int i = 0;i<5;i++){
                int indice = r.nextInt(0, packet.size());
                Card val = packet.get(indice);
                this.J2.getCartes().add(val);
                packet.remove(indice);
            }
        }else if (nbManche<5){
            for (int i = 0;i<nbManche;i++){
                int indice = r.nextInt(0, packet.size());
                Card val = packet.get(indice);
                this.J1.getCartes().add(val);
                packet.remove(indice);
            }

            for (int i = 0;i<nbManche;i++){
                int indice = r.nextInt(0, packet.size());
                Card val = packet.get(indice);
                this.J2.getCartes().add(val);
                packet.remove(indice);
            }
        }
        for (int i = 0;i<nbCartes-J1.getCartes().size()-J2.getCartes().size();i++){
            int indice = r.nextInt(0, packet.size());
            Card val = packet.get(indice);
            this.pioche.add(val);
            packet.remove(indice);
        }
    }*/

    public static String affichageTitre(){
        String res = "";
        Scanner title = null;
        try {
            title = new Scanner(new File("res"+File.separator+"titre.txt"));
            while (title.hasNextLine()) {
                res += title.nextLine()+"\n";
            }
            return res;
        } catch (FileNotFoundException e) {
            return("An error occurred.");
        } catch (IOException e) {
            return("An error occurred.");
        } catch (Exception e) {
            return("An error occurred.");
        }
        finally {
            if (title != null) {
                title.close();
            }
        }
    }

    public static String affichageFin(){
        String res = "";
        Scanner title = null;
        try {
            title = new Scanner(new File("res"+File.separator+"fin.txt"));
            while (title.hasNextLine()) {
                res += title.nextLine()+"\n";
            }
            return res;
        } catch (FileNotFoundException e) {
            return("An error occurred.");
        } catch (IOException e) {
            return("An error occurred.");
        } catch (Exception e) {
            return("An error occurred.");
        }
        finally {
            if (title != null) {
                title.close();
            }
        }
    }

    public boolean bonneSaisie(String saisie, ArrayList<Card> main){
        try{
            int i = Integer.parseInt(saisie);
            if(i>=1 && i<=main.size())return true;
        }catch (NumberFormatException e){
            return false;
        }
        return false;
    }

    public void jouer(){
        String tp = "";
        String saisie1 = "1";
        String saisie2 = "1";

        boolean fini2 = false;
        boolean fini3 = false;

        Random r = new Random();
        boolean piocheEstVide = false;
        Scanner sc = new Scanner(System.in);
        System.out.println("Joueur 1 saisissez votre nom :");
        String nom = sc.nextLine();
        J1.setNom(nom); 
        System.out.println("Joueur 2 saisissez votre nom :");
        String nom2 = sc.nextLine();
        J2.setNom(nom2); 
        ArrayList<Card> packet = genererPacket();
        System.out.println(packet.size());
        Card j1;
        Card j2;
        distribuerCarte(packet);
        System.out.println(this.pioche.size());
        boolean fini = false;
        /*while (!fini()){
            fini2 = false;
            fini3 = false;
            System.out.println("Score de " + J1.getNom() + " : " + J1.getScore());
            System.out.println("Score de " + J2.getNom() + " : " + J2.getScore());
            if(pioche.isEmpty())piocheEstVide=true;
            System.out.println("Tour de " + J1.getNom() + " :");
            int saisie = 0;
            while(!fini2){
                System.out.println("1 : Jouer une carte spéciale ");
                System.out.println("2 : Jouer une carte normal ");
                tp = sc.next();
                if(tp.equals("1")){
                    if(!bonusCard.isEmpty()){
                        for(int i = 0; i < J1.getBonusCard().size(); i++){
                            System.out.println(i + " : " +bonusCard.get(i));
                        }
                        saisie = sc.nextInt();
                        if(saisie >= J1.getBonusCard().size()){
                            utilisationBonus(bonusCard.get(saisie-1)); 
                        }
                    }else{
                        System.out.println("Vous n'avez pas de bonus");
                    }
                }else if(tp.equals("2")){
                    for (int i=0;i<this.J1.getCartes().size();i++){
                        System.out.println(i+1+" : " + this.J1.getCartes().get(i));
                    }
                    System.out.println("Saisir le numèro de la carte à jouer : ");
                    saisie1 = sc.nextLine();
                    while(!bonneSaisie(saisie1, J1.getCartes())){
                        System.out.println("Veuillez saisir un chiffre entre 1 et " + J1.getCartes().size() + " :");
                        saisie1 = sc.nextLine();
                    }
                    j1 = this.J1.getCartes().get(Integer.parseInt(saisie1)-1);
                    fini2=true;
                    System.out.print("\033\143");
                }
            }
            System.out.println("Tour de " + J2.getNom() + " :");
            tp ="";
            int saisie3 = 0;
            while(!fini3){
                System.out.println("1 : Jouer une carte spéciale ");
                System.out.println("2 : Jouer une carte normal ");
                tp = sc.next();
                if(tp.equals("1")){
                    if(!bonusCard.isEmpty()){
                        for(int i = 0; i < J2.getBonusCard().size(); i++){
                             System.out.println(i + " : " +bonusCard.get(i));
                        }
                        saisie3 = sc.nextInt();
                        if(saisie3 >= J2.getBonusCard().size()){
                            utilisationBonus(bonusCard.get(saisie3-1)); 
                        }
                    }else{
                        System.out.println("Vous n'avez pas de bonus");
                    }
                }else if(tp.equals("2")){
                    for (int i=0;i<this.J2.getCartes().size();i++){
                        System.out.println(i+1+" : " + this.J2.getCartes().get(i));
                    }
                    System.out.println("Saisir le numèro de la carte à jouer : ");
                    saisie2 = sc.nextLine();
                    while(!bonneSaisie(saisie2, J2.getCartes())){
                        System.out.println("Veuillez saisir un chiffre entre 1 et " + J2.getCartes().size() + " :");
                        saisie2 = sc.nextLine();
                    }
                    j2 = this.J2.getCartes().get(Integer.parseInt(saisie2)-1);
                    fini3 = true;
                    System.out.print("\033\143");
                }
            }
            System.out.println(J1.getNom() + " : " + J1.getCartes().get(Integer.parseInt(saisie1)-1) + "vs \n" + J2.getNom() + " :" + J2.getCartes().get(Integer.parseInt(saisie2)-1));

            j1 = this.J1.getCartes().remove(Integer.parseInt(saisie1)-1);
            j2 = this.J2.getCartes().remove(Integer.parseInt(saisie2)-1);

            if(!piocheEstVide){
                int indice = r.nextInt(0, pioche.size());
                Card val = pioche.get(indice);
                this.J1.getCartes().add(val);
                pioche.remove(indice);

                int indice2 = r.nextInt(0, pioche.size());
                Card val2 = pioche.get(indice2);
                this.J2.getCartes().add(val2);
                pioche.remove(indice2);
            }

            if (j1.compareTo(j2)==1){
                System.out.println(J1.getNom() + " gagne la manche ! \n");
                J1.addScore(1);
            }else if (j1.compareTo(j2)==-1){
                System.out.println(J2.getNom() + " gagne la manche ! \n");
                J2.addScore(1);
            }else{
                System.out.println("Egalité \n");
                bataille(piocheEstVide, j1,j2);
            }
            fini = fini();
        }*/
        J1.setScore(5);
        J2.setScore(5);
        System.out.println("Fin de la partie : \n" + this.J1.getNom() + " Score = " + this.J1.getScore() + "\n" + this.J2.getNom() + " Score = " + this.J2.getScore());
        if(this.J1.getScore() > this.J2.getScore()){
            System.out.println(J1.getNom() + " gagne la partie !");save();
        }else if(this.J1.getScore() < this.J2.getScore()){
            System.out.println(J2.getNom() + " gagne la partie !");save();
        }else{
            String saisieEgalité;
            boolean egalite = false;
            System.out.println("Vous avez fini à égalité \n" + "Souhaitez-vous relancer une partie de quelques manches ? Si oui, tapez le nombre de manches. Sinon tapez N/Non");
            saisieEgalité = sc.nextLine();
            while(!egalite){
                if(saisieEgalité.equalsIgnoreCase("non")|| saisieEgalité.equalsIgnoreCase("N")){
                    System.out.println("Egalité des deux joueurs ");save();
                    egalite=true;
                }else{
                    try {
                        int nbManche = Integer.parseInt(saisieEgalité);
                        if(nbManche>=1 && nbManche<=10)jouer(nbManche);
                        egalite=true;
                    } catch (Exception e) {
                        System.out.println("Veuillez saisir un chiffre entre 1 et 10 ou non.");
                        saisieEgalité = sc.nextLine();
                    }
                }
            }
        }   
    }

    public void jouer(int nbManche){
        String tp = "";
        String saisie1 = "1";
        String saisie2 = "1";
        boolean fini2 = false;
        boolean fini3 = false;
        Random r = new Random();
        boolean piocheEstVide = false;
        Scanner sc = new Scanner(System.in);
        ArrayList<Card> packet = genererPacket();
        Card j1;
        Card j2;
        distribuerCarte(packet);
        System.out.println(this.pioche.size());
        boolean fini = false;
        while (nbManche>0){
            fini2 = false;
            fini3 = false;
            System.out.println("Score de " + J1.getNom() + " : " + J1.getScore());
            System.out.println("Score de " + J2.getNom() + " : " + J2.getScore());
            if(pioche.isEmpty())piocheEstVide=true;
            System.out.println("Tour de " + J1.getNom() + " :");
            int saisie = 0;
            while(!fini2){
                System.out.println("1 : Jouer une carte spéciale ");
                System.out.println("2 : Jouer une carte normal ");
                tp = sc.next();
                if(tp.equals("1")){
                    if(!bonusCard.isEmpty()){
                        for(int i = 0; i < J1.getBonusCard().size(); i++){
                            System.out.println(i + " : " +bonusCard.get(i));
                        }
                        saisie = sc.nextInt();
                        if(saisie >= J1.getBonusCard().size()){
                            utilisationBonus(bonusCard.get(saisie-1)); 
                        }
                    }else{
                        System.out.println("Vous n'avez pas de bonus");
                    }
                }else if(tp.equals("2")){
                    for (int i=0;i<this.J1.getCartes().size();i++){
                        System.out.println(i+1+" : " + this.J1.getCartes().get(i));
                    }
                    System.out.println("Saisir le numèro de la carte à jouer : ");
                    saisie1 = sc.nextLine();
                    while(!bonneSaisie(saisie1, J1.getCartes())){
                        System.out.println("Veuillez saisir un chiffre entre 1 et " + J1.getCartes().size() + " :");
                        saisie1 = sc.nextLine();
                    }
                    j1 = this.J1.getCartes().get(Integer.parseInt(saisie1)-1);
                    fini2=true;
                    System.out.print("\033\143");
                }
            }
            System.out.println("Tour de " + J2.getNom() + " :");
            tp ="";
            int saisie3 = 0;
            while(!fini3){
                System.out.println("1 : Jouer une carte spéciale ");
                System.out.println("2 : Jouer une carte normal ");
                tp = sc.next();
                if(tp.equals("1")){
                    if(!bonusCard.isEmpty()){
                        for(int i = 0; i < J2.getBonusCard().size(); i++){
                             System.out.println(i + " : " +bonusCard.get(i));
                        }
                        saisie3 = sc.nextInt();
                        if(saisie3 >= J2.getBonusCard().size()){
                            utilisationBonus(bonusCard.get(saisie3-1)); 
                        }
                    }else{
                        System.out.println("Vous n'avez pas de bonus");
                    }
                }else if(tp.equals("2")){
                    for (int i=0;i<this.J2.getCartes().size();i++){
                        System.out.println(i+1+" : " + this.J2.getCartes().get(i));
                    }
                    System.out.println("Saisir le numèro de la carte à jouer : ");
                    saisie2 = sc.nextLine();
                    while(!bonneSaisie(saisie2, J2.getCartes())){
                        System.out.println("Veuillez saisir un chiffre entre 1 et " + J2.getCartes().size() + " :");
                        saisie2 = sc.nextLine();
                    }
                    j2 = this.J2.getCartes().get(Integer.parseInt(saisie2)-1);
                    fini3 = true;
                    System.out.print("\033\143");
                }
            }
            System.out.println(J1.getNom() + " : " + J1.getCartes().get(Integer.parseInt(saisie1)-1) + "vs \n" + J2.getNom() + " :" + J2.getCartes().get(Integer.parseInt(saisie2)-1));

            j1 = this.J1.getCartes().remove(Integer.parseInt(saisie1)-1);
            j2 = this.J2.getCartes().remove(Integer.parseInt(saisie2)-1);

            if(!piocheEstVide){
                int indice = r.nextInt(0, pioche.size());
                Card val = pioche.get(indice);
                this.J1.getCartes().add(val);
                pioche.remove(indice);

                int indice2 = r.nextInt(0, pioche.size());
                Card val2 = pioche.get(indice2);
                this.J2.getCartes().add(val2);
                pioche.remove(indice2);
            }

            if (j1.compareTo(j2)==1){
                System.out.println(J1.getNom() + " gagne la manche ! \n");
                J1.addScore(1);
            }else if (j1.compareTo(j2)==-1){
                System.out.println(J2.getNom() + " gagne la manche ! \n");
                J2.addScore(1);
            }else{
                System.out.println("Egalité \n");
                bataille(piocheEstVide, j1,j2);
            }
            fini = fini();
            nbManche--;
        }
        System.out.println("Fin de la partie : \n" + this.J1.getNom() + " Score = " + this.J1.getScore() + "\n" + this.J2.getNom() + " Score = " + this.J2.getScore());
        if(this.J1.getScore() > this.J2.getScore()){
            System.out.println(J1.getNom() + " gagne la partie !");save();
        }if(this.J1.getScore() < this.J2.getScore()){
            System.out.println(J2.getNom() + " gagne la partie !");save();
        }else{
            String saisieEgalité;
            boolean egalite = false;
            System.out.println("Vous avez fini à égalité \n" + "Souhaitez-vous relancer une partie de quelques manches ? Si oui, tapez le nombre de manches. Sinon tapez N/Non");
            saisieEgalité = sc.nextLine();
            while(!egalite){
                if(saisieEgalité.equalsIgnoreCase("non")|| saisieEgalité.equalsIgnoreCase("N")){
                    System.out.println("Egalité des deux joueurs ");save();
                    egalite=true;
                }else{
                    try {
                        nbManche = Integer.parseInt(saisieEgalité);
                        if(nbManche>=1 && nbManche<=4)jouer(nbManche);egalite=true;
                    } catch (Exception e) {
                        System.out.println("Veuillez saisir un chiffre entre 1 et 10 ou non.");
                    }
                }
            }
        }   
    }

    public boolean fini(){
        if(this.J1.getCartes().size() <= 0) return true;
        if(this.J2.getCartes().size() <= 0) return true;

        return false;
    }

    public void bataille(boolean estVide, Card c1, Card c2){
        if(!fini()){
            Scanner sc = new Scanner(System.in);
        if(J1.getCartes().size()>=2){
            System.out.println("Bataille ! Veuillez choisir une carte à mettre au milieu puis au dessus !");
            for(int y = 0; y < 2; y++){
                System.out.println("Tour de " + J1.getNom() + " :");
                for (int i=0;i<this.J1.getCartes().size();i++){
                    System.out.println(i+1+" : " + this.J1.getCartes().get(i));
                }
                System.out.println("Saisir le numèro de la carte à jouer : ");
                int saisie1 = sc.nextInt();
                c1 = this.J1.getCartes().get(saisie1-1);
                c1 = this.J1.getCartes().remove(saisie1-1);
            }
            System.out.print("\033\143");
            for(int y=0; y<2; y++){
                System.out.println("Tour de " + J2.getNom() + " :");
                for (int i=0;i<this.J2.getCartes().size();i++){
                    System.out.println(i+1+" : " + this.J2.getCartes().get(i));
                }
                int saisie2 = sc.nextInt();
                c2 = this.J2.getCartes().get(saisie2-1);
                c2 = this.J2.getCartes().remove(saisie2-1);
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

    public static boolean bonneSaisieMenu(String saisie){
        try {
            int i = Integer.parseInt(saisie);
            if(i>=1 && i<=4)return true;
        }catch (NumberFormatException e){
            return false;
        }
        return false;
    }

    public static void menu(){
        
        Scanner scanner = new Scanner(System.in);
        String choice;
        int choix = 0;

        do {
            System.out.println(affichageTitre());
            System.out.print("Choisissez une option :");
            choice = scanner.nextLine();
            while (!bonneSaisieMenu(choice)) {
                System.out.println("Veuillez saisir un nombre entre 1 et 4 :");
                choice = scanner.nextLine();
            }
            choix = Integer.parseInt(choice);
                switch (choix) {
                    case 1:
                        System.out.println("Démarrage d'un nouveau jeu...");
                        // Ajouter le code pour démarrer un nouveau jeu
                        Jeu j = new Jeu();
                        j.jouer();
                        break;
                    case 2:
                        System.out.println("Chargement de l'historique ...");
                        historique("./res/save.csv");
                        break;
                    case 3:
                        System.out.println(" Voici les règles de World of cards\n\n SOMMAIRE:\n1.. Les Cartes\n2.. Les Bonus\n\n 1 LES CARTES\n Lors d'une bataille il n'y a qu'une seule règle, la plus forte des cartes et celles qui rapportera un point au joueur, alors réflechissez avant de jouer.\n 2 LES BONUS\n\n STOOOOOOOOOOP: Cette carte permet d'empecher son adversaire de jouer une carte bonus\n Pas plus: permet au joueur d'empecher l'autre de jouer une carte supérieur à une certaine valeur.\n Le Monde à l'envers: cette carte permet de renverser la règle principale du jeu où la carte avec la plus petite valeur gagne.3");
                        Scanner sc = new Scanner(System.in);
                        System.out.println("\nAppuyez sur Entrée pour continuer...");
                        sc.nextLine();
                        break;
                    case 4:
                        System.out.println(affichageFin());
                        break;
                    default:
                        System.out.println("Option invalide. Veuillez réessayer.");
                }
        } while (choix != 4);

        scanner.close();
    }

    public static void main(String[] args){        
        menu();
    }
}