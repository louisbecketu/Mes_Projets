import extensions.CSVFile;

class EduFight extends Program {

    final String locaAff = "ressources/";                /*Localisation des fichiers d'affichage*/
    final String locaMenu = locaAff + "Menu/";                /*Localisation des fichiers du menu*/
    final String locaQuestion = locaMenu + "MenuQuestion/"; /*Localisation des fichiers après avoir cliqué sur questions dans le mainMenu (enseignant)*/
    final String locaPartie = locaMenu + "MenuPartie/";     /*Localisation des fichiers après avoir cliqué sur partie dans le mainMenu*/
    final String locaJeu = locaAff + "AffichagePartie/";
    final String locaMatiere = locaAff + "Matière/";
    final String AjoutduNav = fileAsString(locaMenu + "FooterNav.txt"); /*Extraction de la partie Nav*/
    Parametres P = newParametres();

    void algorithm(){ /*Fonction lancé au lancement du jeu*/
        stringAsFile(locaPartie + "Parametres.txt",fileAsString(locaPartie + "ParametresBase.txt"));
        String Affichage = fileAsString(locaAff + "Start.txt");
        int saisieRecu = 99;
        
        while(!saisieDemande(saisieRecu,-1)){
            println(Affichage);
            saisieRecu = readInt();
            if(saisieDemande(saisieRecu,0)){
                MainMenu();
            }
        }
    }

/*--------------------------------------------------------------------------------------------*/
/*NAVIGATION DANS LE MENU*/
    int NavigationDansLeMenu(String Affichage, int nmbMenuMax){
        int menuCocher = 0;
        int saisieRecu = 99;
        while(!saisieEntree(saisieRecu)){
            println(Affichage);
            saisieRecu = readInt();
            if(saisieDemande(saisieRecu,1) || saisieDemande(saisieRecu,2)){
                Affichage = changerCaseCocher(Affichage, saisieRecu);
                menuCocher = changementNombre(menuCocher,nmbMenuMax,saisieRecu);
            }
        }
        if(saisieDemande(saisieRecu,0)){
            return menuCocher;
        } else {
            return -1;
        }
    }

    void MainMenu(){ /*Affichage du menu principal du jeu*/
        String Affichage = fileAsString(locaMenu + "MainMenu.txt") + AjoutduNav;
        int nmbMenuMax = 3;
        int menuSelectionne = NavigationDansLeMenu(Affichage,nmbMenuMax);
        if(menuSelectionne == 0){
            MenuDeLaPartie();
        }
        if(menuSelectionne == 1){
            AfficherMenuQuestion(); 
        }
        if(menuSelectionne == 2){
            RulesDisplay();
        }
        if(menuSelectionne != -1){
            MainMenu(); 
        }
    }

    void RulesDisplay(){ /*Affichage de la page pour voir les règles du jeu*/
        String Affichage = fileAsString(locaMenu + "MenuRegles/Page1.txt");
        int saisieRecu = 99;
        while(!saisieDemande(saisieRecu,-1)){
            println(Affichage);
            saisieRecu = readInt();
        }
    }

/*--------------------------------------------*/
/*MENU QUESTION*/

    void AfficherMenuQuestion(){ /*Affichage du menu enseignant du jeu*/
        String Affichage = fileAsString(locaQuestion + "MenuQuestion.txt") + AjoutduNav;
        int nmbMenuMax = 6;
        int menuSelectionne = NavigationDansLeMenu(Affichage,nmbMenuMax);
        Matiere m = newMatiere();
        if(menuSelectionne < 6 && menuSelectionne >= 0){
            QuestionMenu(menuSelectionne, m);
        }
        if(menuSelectionne != -1){
            AfficherMenuQuestion();
        }
    }

    void QuestionMenu(int matiere, Matiere m){ /*Affichage des questions disponible dans cette matière (choix des matières sera disponible à la version finale)*/
        String[] tableauNomMatiere = new String[]{"Anglais     ","Français    ","Géographie  ","Histoire    ","Sciences    ","Personnalisé"};
        String affichageIntroQuestion = fileAsString(locaQuestion + "HeaderAddQuestion.txt");
        String affichageOutroQuestion = fileAsString(locaQuestion + "FooterAddQuestion.txt") + AjoutduNav;
        String nomMatiere = tableauNomMatiere[matiere];
        matiere = matiere*2;
        String texte = "| ";
        String txtFinal = "|                                                                                                                                        |\n";
            for(int iLigne=0;iLigne<length(m.matières[matiere].questions,1); iLigne=iLigne+1){
                if (iLigne>0){
                    texte = texte + iLigne + ") ";
                } else {
                    texte = texte + "   ";
                }
                for(int iColonne=0; iColonne<length(m.matières[matiere].questions,2); iColonne=iColonne+1){
                    texte = texte + m.matières[matiere].questions[iLigne][iColonne] + " | ";
                    
                }
                texte = substring(texte,0,length(texte)-3);
                while(length(texte)<137){
                    texte = texte + " ";
                }
                txtFinal = txtFinal + texte + "|\n";
                texte = "| ";
            }
            txtFinal = txtFinal + "|                                                                                                                                        |\n";
            for(int iLigne=0;iLigne<length(m.matières[matiere+1].questions,1); iLigne=iLigne+1){
                if (iLigne>0){
                    texte = texte + iLigne + ") ";
                } else {
                    texte = texte + "   ";
                }
                for(int iColonne=0; iColonne<length(m.matières[matiere+1].questions,2); iColonne=iColonne+1){
                    texte = texte + m.matières[matiere+1].questions[iLigne][iColonne] + " | ";
                    
                }
                texte = substring(texte,0,length(texte)-3);
                while(length(texte)<137){
                    texte = texte + " ";
                }
                txtFinal = txtFinal + texte + "|\n";
                texte = "| ";
            }
        String Affichage =  affichageIntroQuestion + 
                            "| Matière choisi : " + nomMatiere + "                                                                                                          |" +
                            '\n' + txtFinal + affichageOutroQuestion;
        int nmbMenuMax = 3;
        String[] tableauLocalisation = new String[]{"anglaisQD","anglaisQCM","françaisQD","françaisQCM","geographieQD","geographieQCM","histoireQD","histoireQCM","sciencesQD","sciencesQCM","personnaliseQD","personnaliseQCM"};
        IncrustationFichier(locaMatiere + tableauLocalisation[matiere] + ".csv", m.matières[matiere].questions);
        IncrustationFichier(locaMatiere + tableauLocalisation[matiere+1] + ".csv", m.matières[matiere+1].questions);
        int menuSelectionne = NavigationDansLeMenu(Affichage,nmbMenuMax);
        if(menuSelectionne == 0){
            AjoutQuestion(matiere, m);
        }
        if(menuSelectionne == 1){
            ModifierQuestion(matiere, m);
        }
        if(menuSelectionne == 2){
            RetirerQuestion(matiere, m);
        }
        if(menuSelectionne != -1){
            QuestionMenu(matiere/2, m);
        }
    }

    void AjoutQuestion(int matiere, Matiere m){ /*Ajouter une question dans les questions de cette matière*/
        int typequestion = -1;
        while(typequestion < 0 || typequestion > 1){
            println("Quel type de question voulez-vous ajouter ? (0) QD (1) QCM");
            typequestion = readInt();
        }
        String[][] nouveauTableau = new String[length(m.matières[matiere+typequestion].questions,1)+1][length(m.matières[matiere+typequestion].questions,2)];
        for(int iLigne=0;iLigne<length(m.matières[matiere+typequestion].questions,1);iLigne=iLigne+1){
            for(int iColonne=0;iColonne<length(m.matières[matiere+typequestion].questions,2);iColonne=iColonne+1){
                nouveauTableau[iLigne][iColonne] = m.matières[matiere+typequestion].questions[iLigne][iColonne];
            }
        }
        if(typequestion == 0){
            print("La question directe que vous voulez ajouter : ");
            String question = readString();
            print("La réponse directe que vous voulez ajouter : ");
            String réponse = readString();
            nouveauTableau[length(nouveauTableau)-1][0] = question;
            nouveauTableau[length(nouveauTableau)-1][1] = réponse;
        }
        if(typequestion == 1){
            print("La question directe que vous voulez ajouter : ");
            String question = readString();
            println("Saisissez 4 réponses (dont 1 correcte) :");
            String reponse1 = readString();
            String reponse2 = readString();
            String reponse3 = readString();
            String reponse4 = readString();
            int bonneReponse = 0;
            while(bonneReponse <= 0 || bonneReponse > 4){
                println("Quel est la bonne réponse ? 1/2/3/4 ");
                bonneReponse = readInt();
            }
            nouveauTableau[length(nouveauTableau)-1][0] = question;
            nouveauTableau[length(nouveauTableau)-1][1] = "" + bonneReponse;
            nouveauTableau[length(nouveauTableau)-1][2] = reponse1;
            nouveauTableau[length(nouveauTableau)-1][3] = reponse2;
            nouveauTableau[length(nouveauTableau)-1][4] = reponse3;
            nouveauTableau[length(nouveauTableau)-1][5] = reponse4;
        }
        m.matières[matiere+typequestion].questions = nouveauTableau;
    }
    void ModifierQuestion(int matiere, Matiere m){ /*Modifier une question dans les questions de cette matière*/
        int typequestion = -1;
        while(typequestion < 0 || typequestion > 1){
            println("Quel type de question voulez-vous modifiez ? (0) QD (1) QCM");
            typequestion = readInt();
        }
        int questionAModifier = 0;
        while(questionAModifier <= 0 || questionAModifier >= length(m.matières[matiere+typequestion].questions,1)){
            println("Quel question voulez-vous modifiez ?");
            questionAModifier = readInt();
        }
        if(typequestion == 0){
            println("La question directe que vous voulez modifier est : " + m.matières[matiere+typequestion].questions[questionAModifier][0]);
            print("La nouvelle question : ");
            m.matières[matiere+typequestion].questions[questionAModifier][0] = readString();
            println("La réponse que vous voulez modifier est : " + m.matières[matiere+typequestion].questions[questionAModifier][1]);
            print("La réponse que vous voulez est : ");
            m.matières[matiere+typequestion].questions[questionAModifier][1] = readString();
        } 
        if(typequestion == 1){
            println("La question QCM que vous voulez modifier est : " + m.matières[matiere+typequestion].questions[questionAModifier][0]);
            print("La nouvelle question : ");
            m.matières[matiere+typequestion].questions[questionAModifier][0] = readString();
            println("Saisissez 4 réponses (dont 1 correcte) :");
            m.matières[matiere+typequestion].questions[questionAModifier][2] = readString();
            m.matières[matiere+typequestion].questions[questionAModifier][3] = readString();
            m.matières[matiere+typequestion].questions[questionAModifier][4] = readString();
            m.matières[matiere+typequestion].questions[questionAModifier][5] = readString();
            int bonneReponse = 0;
            while(bonneReponse <= 0 || bonneReponse > 4){
                println("Quel est la bonne réponse ? 1/2/3/4 ");
                bonneReponse = readInt();
            }
            m.matières[matiere+typequestion].questions[questionAModifier][1] = "" + bonneReponse;
        }

    }
    void RetirerQuestion(int matiere, Matiere m){ /*Supprimer une question dans les questions de cette matière*/
        int typequestion = -1;
        while(typequestion < 0 || typequestion > 1){
            println("Quel type de question voulez-vous supprimez ? (0) QD (1) QCM");
            typequestion = readInt();
        }
        
        if(length(m.matières[matiere+typequestion].questions,1) > 2){
            int questionARetirer = 0;
            while(questionARetirer <= 0 || questionARetirer >= length(m.matières[matiere+typequestion].questions,1)){
                println("Quel question voulez-vous retirer ?");
                questionARetirer = readInt();
            }
            int indiceAjoutSiFichierTrouve = 0;
            String[][] nouveauTableau = new String[length(m.matières[matiere+typequestion].questions,1)-1][length(m.matières[matiere+typequestion].questions,2)];
            for(int iLigne=0;iLigne<length(nouveauTableau,1);iLigne=iLigne+1){
                if(iLigne == questionARetirer){
                    indiceAjoutSiFichierTrouve = 1;
                }
                for(int iColonne=0;iColonne<length(nouveauTableau,2);iColonne=iColonne+1){
                    nouveauTableau[iLigne][iColonne] = m.matières[matiere+typequestion].questions[iLigne+indiceAjoutSiFichierTrouve][iColonne];
                }
            }
            m.matières[matiere+typequestion].questions = nouveauTableau;
        } else {
            println("Il faut qu'il reste minimum 1 question pour éviter tout problème, merci de ne pas tout supprimer");
            println("Faites Entrer pour continuer");
            readString();
        }

    }

/*--------------------------------------------*/
/*MENU PARTIE*/

    void MenuDeLaPartie(){
        String sauvegarde = fileAsString(locaAff + "Sauvegarde.txt");
        if(length(sauvegarde) > 40){
            GameMenuSauvegarder(sauvegarde);
        } else {
            GameMenu();
        }
    }

    void GameMenu(){ /*Affichage du menu de la partie*/
        String Affichage = fileAsString(locaPartie + "Menu.txt") + AjoutduNav;
        int nmbMenuMax = 4;
        int menuSelectionne = NavigationDansLeMenu(Affichage,nmbMenuMax);
        if(menuSelectionne == 0){
            BotSettings();
        }
        if(menuSelectionne == 1){
            StartGame(0);
        }
        if(menuSelectionne == 2){
            Settings();
        }
        if(menuSelectionne == 3){
            Classement();
        }
        if(menuSelectionne != -1){
            MenuDeLaPartie(); 
        }
    }

    void GameMenuSauvegarder(String sauvegarde){ /*Affichage du menu de la partie*/
        String Affichage = fileAsString(locaPartie + "MenuAvecSauvegarde.txt") + AjoutduNav;
        int nmbMenuMax = 5;
        int menuSelectionne = NavigationDansLeMenu(Affichage,nmbMenuMax);
        if(menuSelectionne == 0){
            LancementJeu(newJeuSauvegarde(sauvegarde));
        }
        if(menuSelectionne == 1){
            BotSettings();
        }
        if(menuSelectionne == 2){
            StartGame(0);
        }
        if(menuSelectionne == 3){
            Settings();
        }
        if(menuSelectionne == 4){
            Classement();
        }
        if(menuSelectionne != -1){
            MenuDeLaPartie(); 
        }
    }

    void BotSettings(){ /*Paramètrage de la difficulté du bot si le joueur a choisi de jouer contre un ordinateur*/
        String Affichage = fileAsString(locaPartie + "Difficulté.txt") + AjoutduNav;
        int nmbMenuMax = 3;
        int menuSelectionne = NavigationDansLeMenu(Affichage,nmbMenuMax);
        if(menuSelectionne >= 0 && menuSelectionne <= 2){
            StartGame(menuSelectionne+1);
        } else if(menuSelectionne != -1){
            BotSettings(); 
        }
    }

/*--------------------------------------------------------------------------------------------*/
/*PARTIE*/

    void StartGame(int difficulté){ /*Lancement de la partie, initiation des paramètres de base*/
        Jeu g = newJeu(difficulté);
        println("Lancez la partie ? (\"0\" pour lancer la partie) / (\"1\" pour redémarrer ce menu) / (Autres chiffres pour quitter ce menu)");
        int reponse = readInt();
        if(reponse == 0){
            LancementJeu(g);
        }
        if(reponse == 1){
            StartGame(difficulté);
        }
    }
    Joueur newJoueur(int difficulté, int numeroJoueur, double attaque, int pv, double resistance){
        Joueur j = new Joueur();
        j.nom = "xxxxxxxxxxxxxxx";
        if(difficulté == 0){
            while(length(j.nom) > 14 || length(j.nom) <= 0 || equals(j.nom,"ORDINATEUR")){
                print("Nom du joueur " + numeroJoueur + " (14 caractères max): ");
                j.nom = readString();
            }
        } else {
            j.nom = "ORDINATEUR";
        }
        j.pv = pv;
        j.attaque = attaque;
        j.bouclier = 0;
        j.regen = 0;
        j.resistance = resistance;
        j.skip = 0;
        j.TourAcquisitionBonus = 0;
        return j;
    }

    Jeu newJeu(int difficulté){
        Jeu g = new Jeu();
        g.difficultéBot = difficulté;
        g.attaqueBase = P.attaqueBase;
        g.pvMax = P.pvBase;
        g.resiBase = P.resiBase;
        g.joueurs[0] = newJoueur(0,1,g.attaqueBase,g.pvMax,g.resiBase);
        g.joueurs[1] = newJoueur(g.difficultéBot,2,g.attaqueBase,g.pvMax,g.resiBase);
        g.roundMax = P.roundMax;
        g.tempsAcquisitionBonus = P.acquisitionBonus;
        g.chanceRoundSpe = P.chanceRoundSpe;
        g.roundActuel = 1;
        g.tourJoueur = 0;
        return g;
    }

    void LancementJeu(Jeu g){ /*Partie lancé, lancement des rounds, des changements de pvs et les déclarations de victoire*/
        sauvegarder(g);
        String Affichage = AffichageJeu(g);
        Matiere m = newMatiere();
        int nmbMenuMax = 3;
        int menuSelectionne = NavigationDansLeMenu(Affichage,nmbMenuMax);
        if(g.difficultéBot == 0 || g.tourJoueur == 0){
            if(menuSelectionne == 0){
                Attaqueur(g,m);
            }
            if(menuSelectionne == 1 && g.joueurs[g.tourJoueur].TourAcquisitionBonus <= 0){
                Bonus(g,m,0);
            } else if(menuSelectionne == 1 && g.joueurs[g.tourJoueur].TourAcquisitionBonus >= 0){
                println("Tour manquant pour tenter le bonus : " + g.joueurs[g.tourJoueur].TourAcquisitionBonus);
                readString();
            }
            if(menuSelectionne == 2){
                AffichageInformations(g);
            }
            if(menuSelectionne != -1){
                VerifJeu(g);
            }
        } else {
            tourBot(g,m);
            VerifJeu(g);
        }

    }
    
    void VerifJeu(Jeu g){
        if(g.joueurs[0].pv > 0 && g.joueurs[1].pv > 0 && g.roundActuel <= g.roundMax){
            LancementJeu(g);
        } else {
            stringAsFile(locaAff + "Sauvegarde.txt", "");
            finDuJeu(g);
        }
    }

    void finDuJeu(Jeu g){
        println(AffichageJeu(g));
        println();
        if(g.joueurs[0].pv > g.joueurs[1].pv){
            println("Victoire du joueur 1 : " + g.joueurs[0].nom);
            if(g.difficultéBot != 0){
                int points = g.joueurs[0].pv - g.joueurs[1].pv;
                if(g.difficultéBot == 2){
                    points = points * 2;
                }
                if(g.difficultéBot == 3){
                    points = points * 4;
                }
                SauvegardeDansLeClassement(g.joueurs[0].nom,points);
                println("Vous avez gagné " + points + " points dans votre classement.");
            }
        } else if (g.joueurs[0].pv < g.joueurs[1].pv){
            println("Victoire du joueur 2 : " + g.joueurs[1].nom);
        } else {
            println("EGALITE ?!");
        }
        readString();
    }

    String AffichageJeu(Jeu g){
       String Affichage = fileAsString(locaJeu + "Affichage.txt");
       String affichagepv1 = g.joueurs[0].pv + "/" + g.pvMax + " PV";
       String affichagepv2 = g.joueurs[1].pv + "/" + g.pvMax + " PV";
       String affichageRound = "ROUND " + g.roundActuel + " / " + g.roundMax;
       String nomJoueur = "";
       if(g.tourJoueur == 0){
        nomJoueur = g.joueurs[0].nom;
       } else {
        nomJoueur = g.joueurs[1].nom;
       }
       Affichage =  substring(Affichage,0,1392) + g.joueurs[0].nom + substring(Affichage,(1392+length(g.joueurs[0].nom)),1445) + affichageRound + 
                    substring(Affichage,(1445+length(affichageRound)),1510) + g.joueurs[1].nom + substring(Affichage,(1510+length(g.joueurs[1].nom)),1533) +
                    affichagepv1 + substring(Affichage,(1533+length(affichagepv1)),1595) + nomJoueur + substring(Affichage,(1595+length(nomJoueur)),1650) + affichagepv2 + substring(Affichage,(1650+length(affichagepv2)),length(Affichage));
        return Affichage;
    }

    void AffichageInformations(Jeu g){

        String Affichage = fileAsString(locaJeu + "Information.txt");
        String affichageAttaque = "x" + g.attaqueBase;
        String affichageResistance = "x" + g.resiBase;
        String affichageAttaqueJ1 = "x" + g.joueurs[0].attaque;
        String affichageAttaqueJ2 = "x" + g.joueurs[1].attaque;
        String affichageDefenseJ1 = "+" + g.joueurs[0].bouclier + " PV";
        String affichageDefenseJ2 = "+" + g.joueurs[1].bouclier + " PV";
        String affichageRegenJ1 = "+" + g.joueurs[0].regen + " PV / round";
        String affichageRegenJ2 = "+" + g.joueurs[1].regen + " PV / round";
        String affichageResiJ1 = "x" + g.joueurs[0].resistance;
        String affichageResiJ2 = "x" + g.joueurs[1].resistance;
        String affichageRoundActuel = "" + g.roundActuel;
        String affichageRoundMax = "" + g.roundMax;
        String affichagePvMax = "" + g.pvMax + " PV";
        String affichagePVJ1 = "" + g.joueurs[0].pv;      
        String affichagePVJ2 = "" + g.joueurs[1].pv;
        String affichageSkipJ1 = "" + g.joueurs[0].skip;      
        String affichageSkipJ2 = "" + g.joueurs[1].skip;
        String affichageChanceRS = "" + (g.chanceRoundSpe*100) + "%";

        Affichage = substring(Affichage,0,1267) + affichageRoundActuel + substring(Affichage,(1267+length(affichageRoundActuel)),1309) + affichageRoundMax + substring(Affichage,(1309+length(affichageRoundMax)),1366) + affichageChanceRS + substring(Affichage,(1366+length(affichageChanceRS)),1539) + affichagePvMax +
                    substring(Affichage,(1539+length(affichagePvMax)),1593) + affichageAttaque + substring(Affichage,(1593+length(affichageAttaque)),1642) + affichageResistance +
                    substring(Affichage,(1642+length(affichageResistance)),2285) + g.joueurs[0].nom + substring(Affichage,(2285+length(g.joueurs[0].nom)),2331) + g.joueurs[1].nom + substring(Affichage,(2331+length(g.joueurs[1].nom)),2567) +
                    affichagePVJ1 + substring(Affichage,(2567+length(affichagePVJ1)),2613) + affichagePVJ2 + substring(Affichage,(2613+length(affichagePVJ2)),2845) + affichageAttaqueJ1 + substring(Affichage,(2845+length(affichageAttaqueJ1)),2891) + affichageAttaqueJ2 + 
                    substring(Affichage,(2891+length(affichageAttaqueJ2)),3122) + affichageDefenseJ1 + substring(Affichage,(3122+length(affichageDefenseJ1)),3168) + affichageDefenseJ2 + substring(Affichage,(3168+length(affichageDefenseJ2)),3400) + 
                    affichageRegenJ1 + substring(Affichage,(3400+length(affichageRegenJ1)),3446) + affichageRegenJ2 + substring(Affichage,(3446+length(affichageRegenJ2)),3679) + affichageResiJ1 + 
                    substring(Affichage,(3679+length(affichageResiJ1)),3725) + affichageResiJ2 + substring(Affichage,(3725+length(affichageResiJ2)),3959) + affichageSkipJ1 + substring(Affichage,(3959+length(affichageSkipJ1)),4005) + affichageSkipJ2 + substring(Affichage,(4005+length(affichageSkipJ2)),length(Affichage));

        println(Affichage);
        readString();
    }

/*--------------------------------------------------------------------------------------------*/
/*FONCTIONS GENERALES*/

    boolean saisieEntree(int toucheAppuye){ /*(vu que pour la plupart des fonctions il faut les 2 saisies pour changer le menu) arrête la boucle de saisie quand la saisie est 0 ou -1 (annuler ou entrée)*/
        return (saisieDemande(toucheAppuye, 0) || saisieDemande(toucheAppuye, -1));
    }
    
    boolean saisieDemande(int toucheAppuye, int saisieAObtenir){ /*(vérifie si la saisie est égal à la saisie demandé )*/
        return (toucheAppuye == saisieAObtenir);
    }

    String changerCaseCocher(String texte, int toucheAppuye){ /*Change la case cochée, celle du bas devient la case coché si 2 est appuyé, celle du haut devient la case coché si 1 est appyé, si la bordure est atteinte, alors il cochera la case la plus proche depuis l'autre bordure */
        int indiceCaseCocher = -1;
        int indice = 0;
        char caseVide = '□';
        char caseCocher = '■';

        while(indice < length(texte)-1 && charAt(texte,indice) != caseCocher){
            indice = indice + 1;
        }
        if(charAt(texte,indice) == caseCocher){
            indiceCaseCocher = indice;
        }
        if(indiceCaseCocher >= 0){
            if(saisieDemande(toucheAppuye,1)){
                while(indice > 0 && charAt(texte,indice) != caseVide){
                    indice = indice - 1;
                }
                if(charAt(texte,indice) == caseVide){
                    texte = substring(texte,0,indice) + "■" + substring(texte,indice+1,indiceCaseCocher) + "□" + substring(texte,indiceCaseCocher+1,length(texte));
                } else if(indice == 0){
                    indice = length(texte)-1;
                    while(indice > indiceCaseCocher && charAt(texte,indice) != caseVide){
                        indice = indice - 1;
                    }
                    if(charAt(texte,indice) == caseVide){
                        texte = substring(texte,0,indiceCaseCocher) + "□" + substring(texte,indiceCaseCocher+1,indice) + "■" + substring(texte,indice+1,length(texte));
                    }
                }
            }
            if(saisieDemande(toucheAppuye,2)){
                while(indice < length(texte)-1 && charAt(texte,indice) != caseVide){
                    indice = indice + 1;
                }
                if(charAt(texte,indice) == caseVide){
                    texte = substring(texte,0,indiceCaseCocher) + "□" + substring(texte,indiceCaseCocher+1,indice) + "■" + substring(texte,indice+1,length(texte));
                } else if(indice == length(texte)-1){
                    indice = 0;
                    while(indice < indiceCaseCocher && charAt(texte,indice) != caseVide){
                        indice = indice + 1;
                    }
                    if(charAt(texte,indice) == caseVide){
                        texte = substring(texte,0,indice) + "■" + substring(texte,indice+1,indiceCaseCocher) + "□" + substring(texte,indiceCaseCocher+1,length(texte));
                    }
                }
            }
        }
        return texte;
    }

    int changementNombre(int caseCocherActuel,int nmbCasesMax,int toucheAppuye){ /*Si le nombre de la case cocher actuel n'est pas dans la norme, celle ci changera*/
        if(saisieDemande(toucheAppuye,1)){
            caseCocherActuel = caseCocherActuel-1;
        }
        if(saisieDemande(toucheAppuye,2)){
            caseCocherActuel = caseCocherActuel+1;
        }
        if(caseCocherActuel <= -1){
            caseCocherActuel = nmbCasesMax - 1;
        } else if(caseCocherActuel >= nmbCasesMax){
            caseCocherActuel = 0;
        }
        return caseCocherActuel;
    }

   Matiere newMatiere(){
    Matiere m = new Matiere();
    m.matières[0] = newQuestions(1, "anglais");
    m.matières[1] = newQuestions(0, "anglais");
    m.matières[2] = newQuestions(1, "français");
    m.matières[3] = newQuestions(0, "français");
    m.matières[4] = newQuestions(1, "geographie");
    m.matières[5] = newQuestions(0, "geographie");
    m.matières[6] = newQuestions(1, "histoire");
    m.matières[7] = newQuestions(0, "histoire");
    m.matières[8] = newQuestions(1, "sciences");
    m.matières[9] = newQuestions(0, "sciences");
    m.matières[10] = newQuestions(1, "personnalise");
    m.matières[11] = newQuestions(0, "personnalise");
    return m;
   }

   Questions newQuestions(int typeRecu, String nom){
    String[] tab = new String[]{"QCM","QD"};
    Questions q = new Questions();
    q.nom = nom;
    q.questions = ExtractionFichier(locaMatiere + nom + tab[typeRecu] + ".csv");
    return q;
   }

   String[][] ExtractionFichier(String localisationFichier){
    CSVFile fichierCSV = loadCSV(localisationFichier, '#');
    int nombreDeLignes = rowCount(fichierCSV);
    int nombreDeColonnes = columnCount(fichierCSV);
    String[][] tab = new String[nombreDeLignes][nombreDeColonnes];
    for(int iL=0;iL<nombreDeLignes;iL=iL+1){
        for(int iC=0;iC<nombreDeColonnes;iC=iC+1){
            tab[iL][iC] = getCell(fichierCSV,iL,iC);
        }
    }
    return tab;
   }

   void IncrustationFichier(String localisation, String[][] tab){
    saveCSV(tab, localisation, '#');
   }
    /*------------------------------------------------------------------*/ 

    void Attaqueur(Jeu g, Matiere m){
        if(random() < 0.3){
            AttaqueQD(g,m);
        } else {
            AttaqueQCM(g,m);
        }
        println(g.tourJoueur);
        if(g.tourJoueur == 0 && random() < g.chanceRoundSpe && g.roundActuel <= g.roundMax){
            roundSpe(g, m);
            sauvegarder(g);
        }
    }

    void AttaqueQD(Jeu g, Matiere m){
        String Affichage = fileAsString(locaJeu + "AttaquerDirecte.txt");
        String[] question = ChoixQuestion(0,m);
        Affichage = substring(Affichage,0,1530) + question[0] + substring(Affichage,(1530+length(question[0])),length(Affichage));
        println(Affichage);
        String reponse = readString();
        if(equals(reponse,question[1])){
            changementStat(g,0,reponse);
        } else {
            if(g.joueurs[g.tourJoueur].skip > 0){
                println("Mauvaise réponse, vous utilisez un Joker, pour retenter votre chance. Quel est votre nouvelle réponse ?");
                g.joueurs[g.tourJoueur].skip = g.joueurs[g.tourJoueur].skip - 1;
                reponse = readString();
                if(equals(reponse,question[1])){
                    changementStat(g,0,question[1]);
                } else {
                    changementStat(g,1,question[1]);
                }
            } else {
                changementStat(g,1,question[1]);
            }
        }
    }

    void AttaqueQCM(Jeu g, Matiere m){
        String Affichage = fileAsString(locaJeu + "AttaquerQCM.txt");
        String[] question = ChoixQuestion(1,m);
        Affichage = substring(Affichage,0,1530) + question[0] + substring(Affichage,(1530+length(question[0])),2505) + question[2] +
        substring(Affichage,(2505+length(question[2])),3061) + question[3] + substring(Affichage,(3061+length(question[3])),3617) +
        question[4] + substring(Affichage,(3617+length(question[4])),4173) + question[5] + substring(Affichage,(4173+length(question[5])),length(Affichage));
        int nmbMenuMax = 4;
        int reponseSelectionne = NavigationDansLeMenu(Affichage,nmbMenuMax);
        if(reponseSelectionne + 1 == stringToInt(question[1])){
            changementStat(g,0,question[stringToInt(question[1])+1]);
        } else {
            if(g.joueurs[g.tourJoueur].skip > 0){
                println("Mauvaise réponse, vous utilisez un Joker, pour retenter votre chance.");
                readString();
                g.joueurs[g.tourJoueur].skip = g.joueurs[g.tourJoueur].skip - 1;
                reponseSelectionne = NavigationDansLeMenu(Affichage,nmbMenuMax);
                if(reponseSelectionne + 1 == stringToInt(question[1])){
                    changementStat(g,0,question[stringToInt(question[1])+1]);
                } else {
                    changementStat(g,1,question[stringToInt(question[1])+1]);
                }
            } else {
                changementStat(g,1,question[stringToInt(question[1])+1]);
            }
            
        }
    }

    String[] ChoixQuestion(int TypeQuestion, Matiere m){
        boolean pastrouve = true;
        Questions q = m.matières[0];
        boolean autorise = false;
        int question = 13;
        while(pastrouve){
            autorise = false;
            while(!autorise){
                question = ((int)(random()*6));
                autorise = P.matiere[question];
            }
            question = question * 2;
            if(TypeQuestion == 1){
                question = question + 1;
            }
            q = m.matières[question];
            if(length(m.matières[question].questions,1) > 1){
                pastrouve = false;
            }
        }
        int choixQuestion = 1 + (int)(random()*(length(q.questions,1)-1));
        String[] questionChoisi = new String[length(q.questions,2)];
        for(int i=0;i<length(questionChoisi);i=i+1){
            questionChoisi[i] = q.questions[choixQuestion][i];
        }
        return questionChoisi;
    }

    void changementStat(Jeu g, int victoire, String reponse){
        String msgAfficher = "";
        int jAttaquant = g.tourJoueur;
        int jDefenseur = 0;
        if(g.tourJoueur == 0){
            jDefenseur = 1;
        } 
        double boost = random();
        if(victoire == 0){
            boost = boost + 1;
        }
        if(victoire == 0){
            msgAfficher = "trouvé la bonne réponse";
        } else {
            msgAfficher = "répondu la mauvaise réponse";
        }
        println();
        println(g.joueurs[jAttaquant].nom + " a " + msgAfficher);
        if(!equals(reponse,"3849975892")){
            println("La bonne réponse était " + reponse);
        }
        println();
        int degats = (int)(g.pvMax * boost *g.joueurs[jAttaquant].attaque*(1/g.joueurs[jDefenseur].resistance));
        if(g.joueurs[jDefenseur].bouclier > 0){
            g.joueurs[jDefenseur].bouclier = g.joueurs[jDefenseur].bouclier - degats;
            if(g.joueurs[jDefenseur].bouclier < 0){
                g.joueurs[jDefenseur].pv = g.joueurs[jDefenseur].pv + g.joueurs[jDefenseur].bouclier;
                g.joueurs[jDefenseur].bouclier = 0;
            }
            println("Le bouclier de " + g.joueurs[jDefenseur].nom + " lui a permis d'encaisser des dégâts.");
        } else {
            g.joueurs[jDefenseur].pv = g.joueurs[jDefenseur].pv - degats;
        }
        println(g.joueurs[jAttaquant].nom + " a réalisé -" + degats + "PV sur " + g.joueurs[jDefenseur].nom);

        if(g.joueurs[jAttaquant].regen > 0){
            println("La régénération de " + g.joueurs[jAttaquant].nom + " lui a fait gagner " + g.joueurs[jAttaquant].regen + " PVs");
            g.joueurs[jAttaquant].pv = g.joueurs[jAttaquant].pv + g.joueurs[jAttaquant].regen;
            if(g.joueurs[jAttaquant].pv > g.pvMax){
                g.joueurs[jAttaquant].pv = g.pvMax;
            }
        }
        g.tourJoueur = g.tourJoueur + 1;
        if(g.tourJoueur >= 2){
            g.tourJoueur = 0;
            g.roundActuel = g.roundActuel + 1;
            g.joueurs[0].TourAcquisitionBonus = g.joueurs[0].TourAcquisitionBonus - 1;
            g.joueurs[1].TourAcquisitionBonus = g.joueurs[1].TourAcquisitionBonus - 1;
        }
        println();
        println("C'est au tour de " + g.joueurs[jDefenseur].nom);
        println("Entrée pour continuer");
        readString();
    }

    void tourBot(Jeu g, Matiere m){
        double chance = P.reponseBot[g.difficultéBot-1];
        if(g.joueurs[g.tourJoueur].TourAcquisitionBonus <= 0){
            Bonus(g, m, 1);
        }
        if(g.tourJoueur == 1){
            if(random() <= chance){
                changementStat(g,0,"3849975892");
            } else{
                if(g.joueurs[g.tourJoueur].skip > 0){
                    println("l'ordinateur a répondu faux, mais il retente une chance grâce à son JOKER");
                    g.joueurs[g.tourJoueur].skip = g.joueurs[g.tourJoueur].skip - 1;
                    if(random() <= chance){
                    changementStat(g,0,"3849975892");
                    } else {
                        changementStat(g,1,"3849975892");
                    }
                } else {
                    changementStat(g,1,"3849975892");
                }
            }
        }
        if(random() < g.chanceRoundSpe && g.roundActuel <= g.roundMax){
            roundSpe(g, m);
            sauvegarder(g);
        }
    }

    void Bonus(Jeu g, Matiere m, int bot){
        String Affichage = fileAsString(locaJeu + "Bonus.txt");
        String[] question = ChoixQuestion(0,m);
        String[] typeBonus = new String[]{"   ATTAQUE   ","   BOUCLIER  "," REGENERATION","  RESISTANCE ","    JOKER    ","   MYSTERE   "};
        double random = random();
        int bonus = 5;
        if(random < P.bonusPourcentage[0]){
            bonus = 0;
        } else if(random < P.bonusPourcentage[0] + P.bonusPourcentage[1]){
            bonus = 1;
        } else if(random < P.bonusPourcentage[0] + P.bonusPourcentage[1] + P.bonusPourcentage[2]){
            bonus = 2;
        } else if(random < P.bonusPourcentage[0] + P.bonusPourcentage[1] + P.bonusPourcentage[2] + P.bonusPourcentage[3]){
            bonus = 3;
        } else if(random < P.bonusPourcentage[0] + P.bonusPourcentage[1] + P.bonusPourcentage[2] + P.bonusPourcentage[3] + P.bonusPourcentage[4]){
            bonus = 4;
        }
        Affichage = substring(Affichage,0,2007) + typeBonus[bonus] + substring(Affichage,(2007+length(typeBonus[bonus])),3615) + question[0] + substring(Affichage,(3615+length(question[0])),length(Affichage));
        println(Affichage);
        String reponse = "";
        if(bot == 0){
            reponse = readString();
            println();
        } else {
            double valeur = P.chanceBonusBot[g.difficultéBot-1];
            if(random() < valeur){
                reponse = question[1];
            } else {
                reponse = "Jsp";
            }
            println("La réponse du bot est : " + reponse);
        }
        g.joueurs[g.tourJoueur].TourAcquisitionBonus = g.tempsAcquisitionBonus;
        if(equals(reponse,question[1])){
            AjoutBonus(g, bonus);
        } else {
            println("Mauvaise réponse, dommage pour vous");
            g.tourJoueur = g.tourJoueur + 1;
            if(g.tourJoueur >= 2){
                g.tourJoueur = 0;
                g.roundActuel = g.roundActuel + 1;
            }
        }
        println("La bonne réponse était " + question[1]);
        readString();
    }

    void AjoutBonus(Jeu g, int bonus){
        if(bonus == 5){
            double random = random();
            if(random < P.bonusPourcentage[0]){
                bonus = 0;
            } else if(random < P.bonusPourcentage[0] + P.bonusPourcentage[1]){
                bonus = 1;
            } else if(random < P.bonusPourcentage[0] + P.bonusPourcentage[1] + P.bonusPourcentage[2]){
                bonus = 2;
            } else if(random < P.bonusPourcentage[0] + P.bonusPourcentage[1] + P.bonusPourcentage[2] + P.bonusPourcentage[3]){
                bonus = 3;
            } else if(random < P.bonusPourcentage[0] + P.bonusPourcentage[1] + P.bonusPourcentage[2] + P.bonusPourcentage[3] + P.bonusPourcentage[4]){
                bonus = 4;
            }
        }
        if(bonus == 0){
            g.joueurs[g.tourJoueur].attaque = g.joueurs[g.tourJoueur].attaque * 1.5;
            println("Bravo, vous avez gagné un bonus Attaque : votre attaque est passé maintenant à " + g.joueurs[g.tourJoueur].attaque);
        }
        if(bonus == 1){
            g.joueurs[g.tourJoueur].bouclier = g.joueurs[g.tourJoueur].bouclier + (int)(g.attaqueBase * 2 * g.pvMax);
            println("Bravo, vous avez gagné un bonus Bouclier : vous avez un bouclier de " + g.joueurs[g.tourJoueur].bouclier + " PV");
        }
        if(bonus == 2){
            g.joueurs[g.tourJoueur].regen = g.joueurs[g.tourJoueur].regen + (int)(g.pvMax * 0.05);
            println("Bravo, vous avez gagné un bonus Regeneration : vous vous régénérez de " + g.joueurs[g.tourJoueur].regen + " PV à chaque round");
        }
        if(bonus == 3){
            g.joueurs[g.tourJoueur].resistance = g.joueurs[g.tourJoueur].resistance * 1.3;
            println("Bravo, vous avez gagné un bonus Resistance : votre résistance est passé maintenant à " + g.joueurs[g.tourJoueur].resistance);
        }
        if(bonus == 4){
            g.joueurs[g.tourJoueur].skip = g.joueurs[g.tourJoueur].skip + 1;
            println("Bravo, vous avez gagné un bonus JOKER : vous pouvez recommencer une question " + g.joueurs[g.tourJoueur].skip + " fois");
        }
        if(bonus == 5){
            println("Bravo, vous avez gagné un droit de skip votre tour. Vous êtes tombé sur un MALUS bien joué.");
            g.tourJoueur = g.tourJoueur + 1;
            if(g.tourJoueur >= 2){
                g.tourJoueur = 0;
                g.roundActuel = g.roundActuel + 1;
            }
        }
    }

    Jeu newJeuSauvegarde(String fichierSauvegarde){
        Jeu g = new Jeu();
        String nom = "";
        String[] tab = new String[25];
        int indice = 0;
        for(int i=0;i<length(tab);i=i+1){
            while(charAt(fichierSauvegarde,indice) != '\n'){
                nom = nom + charAt(fichierSauvegarde,indice);
                indice = indice + 1;
            }
            indice = indice + 1;
            tab[i] = nom;
            nom = "";
        }
        g.joueurs[0] = newJoueurSauvegarde(tab, 0);
        g.joueurs[1] = newJoueurSauvegarde(tab, 1);
        g.attaqueBase = stringToDouble(tab[16]);
        g.pvMax = stringToInt(tab[17]);
        g.roundActuel = stringToInt(tab[18]);
        g.roundMax = stringToInt(tab[19]);
        g.resiBase = stringToDouble(tab[20]);
        g.tourJoueur = stringToInt(tab[21]);
        g.difficultéBot = stringToInt(tab[22]);
        g.tempsAcquisitionBonus = stringToInt(tab[23]);
        g.chanceRoundSpe = stringToDouble(tab[24]);
        return g;
    }

    Joueur newJoueurSauvegarde(String[] tab, int joueur){
        Joueur j = new Joueur();
        joueur = joueur*8;
        j.nom = tab[0+joueur];
        j.pv = stringToInt(tab[1+joueur]);
        j.attaque = stringToDouble(tab[2+joueur]);;
        j.bouclier = stringToInt(tab[3+joueur]);;
        j.regen = stringToInt(tab[4+joueur]);
        j.resistance = stringToDouble(tab[5+joueur]);;
        j.skip = stringToInt(tab[6+joueur]);;
        j.TourAcquisitionBonus = stringToInt(tab[7+joueur]);;
        return j;
    }

    void sauvegarder(Jeu g){
        String texte = "";
        String tab[] = new String[25];
        for(int i=0;i<2;i=i+1){
            tab[0+i*8] = g.joueurs[0+i].nom;
            tab[1+i*8] = "" + g.joueurs[0+i].pv;
            tab[2+i*8] = "" + g.joueurs[0+i].attaque;
            tab[3+i*8] = "" + g.joueurs[0+i].bouclier;
            tab[4+i*8] = "" + g.joueurs[0+i].regen;
            tab[5+i*8] = "" + g.joueurs[0+i].resistance;
            tab[6+i*8] = "" + g.joueurs[0+i].skip;
            tab[7+i*8] = "" + g.joueurs[0+i].TourAcquisitionBonus;
        }
        tab[16] = "" + g.attaqueBase;
        tab[17] = "" + g.pvMax;
        tab[18] = "" + g.roundActuel;
        tab[19] = "" + g.roundMax;
        tab[20] = "" + g.resiBase;
        tab[21] = "" + g.tourJoueur;  
        tab[22] = "" + g.difficultéBot;
        tab[23] = "" + g.tempsAcquisitionBonus;
        tab[24] = "" + g.chanceRoundSpe;
        for(int i=0;i<length(tab);i=i+1){
            texte = texte + tab[i] + '\n';
        }
        stringAsFile(locaAff + "Sauvegarde.txt", texte);
    }

    void roundSpe(Jeu g, Matiere m){
        String Affichage = fileAsString(locaJeu + "RoundSpe.txt");
        boolean mauvaiseRep = false;
        print(Affichage);
        String[] question;
        String reponse = "";
        int cycle = 5;
        while(!mauvaiseRep && cycle > 0){
            question = ChoixQuestion(0,m);
            Affichage = "|________________________________________________________________________________________________________________________________________|\n| JOUEUR : " + g.joueurs[g.tourJoueur].nom;
            for(int i=0;i+length(g.joueurs[g.tourJoueur].nom)<125;i=i+1){
                Affichage = Affichage + " ";
            }
            Affichage = Affichage + " |\n| Question : " + question[0];
            for(int i=0;i+length(question[0])<124;i=i+1){
                Affichage = Affichage + " ";
            }
            Affichage = Affichage + "|\n| Quel est votre réponse ?                                                                                                               |";
            println(Affichage);
            print("| ");
            if(g.tourJoueur == 0 || g.difficultéBot == 0){
                reponse = readString();
            } else {
                double valeur = (P.reponseBot[g.difficultéBot-1]+0.1);
                if(random() < valeur){
                    reponse = question[1];
                } else {
                    reponse = "Jsp";
                }
                println(reponse);

            }
            println("|                                                                                                                                        |");
            if(equals(reponse,question[1])){
                println("| Bonne réponse                                                                                                                          |");
            } else {
                println("| Mauvaise réponse                                                                                                                       |");
                mauvaiseRep = true;
            }
            readString();
            g.tourJoueur = g.tourJoueur + 1;
            if(g.tourJoueur > 1){
                g.tourJoueur = 0;
                cycle = cycle - 1;
            }
        }
        println("|________________________________________________________________________________________________________________________________________|\n");
        if(mauvaiseRep){
            println("Victoire du joueur " + g.joueurs[g.tourJoueur].nom);
            double random = random();
            int bonus = 5;
            if(random < P.bonusPourcentage[0]){
                bonus = 0;
            } else if(random < P.bonusPourcentage[0] + P.bonusPourcentage[1]){
                bonus = 1;
            } else if(random < P.bonusPourcentage[0] + P.bonusPourcentage[1] + P.bonusPourcentage[2]){
                bonus = 2;
            } else if(random < P.bonusPourcentage[0] + P.bonusPourcentage[1] + P.bonusPourcentage[2] + P.bonusPourcentage[3]){
                bonus = 3;
            } else if(random < P.bonusPourcentage[0] + P.bonusPourcentage[1] + P.bonusPourcentage[2] + P.bonusPourcentage[3] + P.bonusPourcentage[4]){
                bonus = 4;
            }
            AjoutBonus(g, bonus);
        } else {
            println("5 questions sont passés pour chaque joueur et aucun s'est départagé, vous avez tout les 2 gagné un bonus");
            for(int i=0;i<2;i=i+1){
                g.tourJoueur = i;
                println(g.joueurs[g.tourJoueur].nom + " :");
                double random = random();
                int bonus = 5;
                if(random < P.bonusPourcentage[0]){
                    bonus = 0;
                } else if(random < P.bonusPourcentage[0] + P.bonusPourcentage[1]){
                    bonus = 1;
                } else if(random < P.bonusPourcentage[0] + P.bonusPourcentage[1] + P.bonusPourcentage[2]){
                    bonus = 2;
                } else if(random < P.bonusPourcentage[0] + P.bonusPourcentage[1] + P.bonusPourcentage[2] + P.bonusPourcentage[3]){
                    bonus = 3;
                } else if(random < P.bonusPourcentage[0] + P.bonusPourcentage[1] + P.bonusPourcentage[2] + P.bonusPourcentage[3] + P.bonusPourcentage[4]){
                    bonus = 4;
                }
                AjoutBonus(g, bonus);
            }
        }
        g.tourJoueur = 0;
        readString();
    }

    void Classement(){
        String[][] tab = ExtractionFichier(locaAff + "Classement.csv");
        String classement = "";
        String affichageClassement = "";
        for(int i=1;i<length(tab,1);i=i+1){
         affichageClassement = "| " + tab[i][0] + ", " + tab[i][1] + " avec " + tab[i][2] + " points.";
         int lol = length(affichageClassement);
         for(int iL=0;iL+lol<137;iL=iL+1){
            affichageClassement = affichageClassement + " ";
         }
         classement = classement + affichageClassement + "|\n";
        }
        String Affichage = fileAsString(locaPartie + "Classement.txt") + classement + fileAsString(locaPartie + "ClassementEnd.txt");
        println(Affichage);
        int saisie = readInt();
        while(saisie != -1){
            saisie = readInt();
        }
    }

    void SauvegardeDansLeClassement(String nom, int points){
        String localisation = locaAff + "Classement.csv";
        String[][] tab = ExtractionFichier(localisation);
        int indice = 1;
        boolean Nomtrouve = false;
        while(indice < length(tab,1) && !Nomtrouve){
            if(equals(nom,tab[indice][1])){
                Nomtrouve = true;
            } else {
                indice = indice + 1;
            }
        }
        if(Nomtrouve){
            points = points + stringToInt(tab[indice][2]);
            tab[indice][2] = "" + points;
        } else {
            String[][] ancienTab = tab;
            tab = new String[length(ancienTab,1)+1][length(ancienTab,2)];
            for(int iL=0;iL<length(ancienTab,1);iL=iL+1){
                for(int iC=0;iC<length(ancienTab,2);iC=iC+1){
                    tab[iL][iC] = ancienTab[iL][iC];
                }
            }
            tab[length(tab,1)-1][0] = (length(tab,1)-1) + "ème";
            tab[length(tab,1)-1][1] = nom;
            tab[length(tab,1)-1][2] = "" + points;  
        }
        reconstructionClassement(tab);
        saveCSV(tab, localisation, '#');
    }

    void reconstructionClassement(String[][] tab){
        int indiceNouveau = length(tab,1) - 1;
        int indice = indiceNouveau-1;
        while(indice > 0 && stringToInt(tab[indice][2]) < stringToInt(tab[indiceNouveau][2])){
            indice = indice - 1;
        }
        indice = indice + 1;
        if(indiceNouveau > indice){
            String[] ancienneLigne = new String[]{tab[indiceNouveau][1],tab[indiceNouveau][2]};
            while(indiceNouveau != indice){
                tab[indiceNouveau][1] = tab[indiceNouveau-1][1];
                tab[indiceNouveau][2] = tab[indiceNouveau-1][2];
                indiceNouveau = indiceNouveau - 1;
            }
            tab[indice][1] = ancienneLigne[0];
            tab[indice][2] = ancienneLigne[1];
        }
    }

    void testReconstructionClassement() {
    String[][] tab = new String[][]{{"1er","nimp","9293"},
                                    {"2ème","oklm","249"},
                                    {"3ème","bonjour","247"},
                                    {"4ème","nouveauDansLeClassement","594"}};
    String[][] resultat = new String[][]   {{"1er","nimp","9293"},
                                            {"2ème","nouveauDansLeClassement","594"},
                                            {"3ème","oklm","249"},
                                            {"4ème","bonjour","247"}};
    reconstructionClassement(tab);
    assertArrayEquals(resultat, tab);
    }

    void Settings(){ /*Changement des paramètres (Version finale)*/
        int saisieRecu = 99;
        while(!saisieDemande(saisieRecu,-1)){
            saisieRecu = 99;
            println(fileAsString(locaPartie + "Parametres.txt"));
            while(saisieRecu > 10 || saisieRecu < -1 || saisieRecu == 0){
                saisieRecu = readInt();
            }
            if(saisieRecu <= 10 && saisieRecu >= 1){
               changementParametre(saisieRecu);
            }
        }
    }

    void changementParametre(int parametres){
        if(parametres == 1){
            P.attaqueBase = changementAttaqueBase(P.attaqueBase);
        }
        if(parametres == 2){
            P.pvBase = changementPVBase(P.pvBase);
        }
        if(parametres == 3){
            P.resiBase = changementResistanceBase(P.resiBase);
        }
        if(parametres == 4){
            P.roundMax = changementRoundMax(P.roundMax);
        }
        if(parametres == 5){
            P.chanceRoundSpe = changementChanceRoundSpe(P.chanceRoundSpe);
        }
        if(parametres == 6){
            P.acquisitionBonus = changementAcquisitionBonus(P.acquisitionBonus);
        }
        if(parametres == 7){
            P.chanceBonusBot = changementChanceBonusBot(P.chanceBonusBot);
        }
        if(parametres == 8){
            P.reponseBot = changementReponseBot(P.reponseBot);
        }
        if(parametres == 9){
            P.bonusPourcentage = changementBonusPourcentage(P.bonusPourcentage);
        }
        if(parametres == 10){
            P.matiere = changementMatiere(P.matiere);
        }
                
    }

    double changementAttaqueBase(double ancienneStat){
        double valeurMax = 0.5;
        double valeurMin = 0.05;
        println("Valeur de l'attaque de base actuelle : " + ancienneStat);
        println("Quelle est la nouvelle valeur que vous voulez pour l'attaque de base ? Valeur comprise entre 0.05 et 0.5 (Recommandé : 0.1)");
        double nouvelleStat = 1;
        while(nouvelleStat > valeurMax || nouvelleStat < valeurMin){
            nouvelleStat = readDouble();
        }
        changementAffichage(nouvelleStat, valeurMax, valeurMin, 1409);
        println("L'attaque de base est donc passé de " + ancienneStat + " à " + nouvelleStat);
        readString();
        return nouvelleStat;
    }
    int changementPVBase(int ancienneStat){
        int valeurMax = 1000;
        int valeurMin = 50;
        println("Valeur des PV de base actuelle : " + ancienneStat);
        println("Quelle est la nouvelle valeur que vous voulez pour les PV de base ? Valeur comprise entre 50 et 1000 (Recommandé : 100)");
        int nouvelleStat = 1;
        while(nouvelleStat > valeurMax || nouvelleStat < valeurMin){
            nouvelleStat = readInt();
        }
        changementAffichage((double)nouvelleStat, (double)valeurMax, (double)valeurMin, 1472);
        println("Les PVs de base est donc passé de " + ancienneStat + " à " + nouvelleStat);
        readString();
        return nouvelleStat;        
    }
    double changementResistanceBase(double ancienneStat){
        double valeurMax = 3;
        double valeurMin = 0.3;
        println("Valeur de la résistance de base actuelle : " + ancienneStat);
        println("Quelle est la nouvelle valeur que vous voulez pour la résistance de base ? Valeur comprise entre 0.3 et 3 (Recommandé : 1)");
        double nouvelleStat = 0;
        while(nouvelleStat > valeurMax || nouvelleStat < valeurMin){
            nouvelleStat = readDouble();
        }
        changementAffichage(nouvelleStat, valeurMax, valeurMin, 1829);
        println("La résistance de base est donc passé de " + ancienneStat + " à " + nouvelleStat);
        readString();
        return nouvelleStat;        
    }
    int changementRoundMax(int ancienneStat){
        int valeurMax = 50;
        int valeurMin = 3;
        println("Valeur du nombre de round maximum actuelle : " + ancienneStat);
        println("Quelle est la nouvelle valeur que vous voulez pour le nombre de round maximum ? Valeur comprise entre 3 et 50 (Recommandé : 10)");
        int nouvelleStat = 0;
        while(nouvelleStat > valeurMax || nouvelleStat < valeurMin){
            nouvelleStat = readInt();
        }
        changementAffichage((double)nouvelleStat, (double)valeurMax, (double)valeurMin, 1903);
        println("Le nombre de round max est donc passé de " + ancienneStat + " à " + nouvelleStat);
        readString();
        return nouvelleStat;  
    }
    double changementChanceRoundSpe(double ancienneStat){
        double valeurMax = 1;
        double valeurMin = 0;
        println("Valeur de la chance d'avoir un round spécial à chaque round : " + ancienneStat);
        println("Quelle est la nouvelle valeur que vous voulez pour le pourcentage de chance d'avoir un round spécial ? Valeur comprise entre 0 et 1 (Recommandé : 0.1)");
        double nouvelleStat = 2;
        while(nouvelleStat > valeurMax || nouvelleStat < valeurMin){
            nouvelleStat = readDouble();
        }
        changementAffichage(nouvelleStat, valeurMax, valeurMin, 2259);
        println("La chance d'avoir un round spécial à chaque round est passé de " + ancienneStat + " à " + nouvelleStat);
        readString();
        return nouvelleStat;        
    } 
    int changementAcquisitionBonus(int ancienneStat){
        double valeurMax = 10;
        double valeurMin = 0;
        println("Valeur du nombre de tours minimum entre chaque tentative de bonus : " + ancienneStat);
        println("Quelle est la nouvelle valeur que vous voulez pour le nombre de tours minimum ? Valeur comprise entre 1 et 10 (Recommandé : 3)");
        int nouvelleStat = 11;
        while(nouvelleStat > valeurMax || nouvelleStat < valeurMin){
            nouvelleStat = readInt();
        }
        changementAffichage((double)nouvelleStat, (double)valeurMax, (double)valeurMin, 2324);
        println("Le nombre de tours minimum entre chaque tentative de bonus est donc passé de " + ancienneStat + " à " + nouvelleStat);
        readString();
        return nouvelleStat;
    }
    double[] changementChanceBonusBot(double[] ancienneStat){
        println("Pourcentage de chance que l'ordinateur gagne un bonus : ");
        String[] nom = new String[]{"Facile","Moyen","Difficile"};
        for(int i=0;i<3;i=i+1){
            println(nom[i] + " : " + ancienneStat[i]);
        }
        println("Quelle est la nouvelle valeur que vous voulez pour chaque stat ? Valeur comprise entre 0 et 1 (Recommandé : 0.1 / 0.5 / 0.9)");
        double[] nouvelleStat = new double[]{2,2,2};
        for(int i=0;i<3;i=i+1){
            print(nom[i] + " : ");
            while(nouvelleStat[i] > 1 || nouvelleStat[i] < 0){
                nouvelleStat[i] = readDouble();
            }
        }
        changementAffichageBot(nouvelleStat, 2681);
        println("Voici les nouvelles valeurs pour le pourcentage de chance que l'ordinateur gagne un bonus : ");
        for(int i=0;i<3;i=i+1){
            println(nom[i] + " : " + ancienneStat[i] + " ---> " + nouvelleStat[i]);
        }
        readString();
        return nouvelleStat;
    }
    double[] changementReponseBot(double[] ancienneStat){
        println("Pourcentage de chance que l'ordinateur réponds correctement : ");
        String[] nom = new String[]{"Facile","Moyen","Difficile"};
        for(int i=0;i<3;i=i+1){
            println(nom[i] + " : " + ancienneStat[i]);
        }
        println("Quelle est la nouvelle valeur que vous voulez pour chaque stat ? Valeur comprise entre 0 et 1 (Recommandé : 0.3 / 0.5 / 0.7)");
        double[] nouvelleStat = new double[]{2,2,2};
        for(int i=0;i<3;i=i+1){
            print(nom[i] + " : ");
            while(nouvelleStat[i] > 1 || nouvelleStat[i] < 0){
                nouvelleStat[i] = readDouble();
            }
        }
        changementAffichageBot(nouvelleStat, 2754);
        println("Voici les nouvelles valeurs pour le pourcentage de chance que l'ordinateur réponds correctement : ");
        for(int i=0;i<3;i=i+1){
            println(nom[i] + " : " + ancienneStat[i] + " ---> " + nouvelleStat[i]);
        }
        readString();
        return nouvelleStat;
    }
    double[] changementBonusPourcentage(double[] ancienneStat){
        println("Pourcentage de chance d'avoir chaque bonus actuellement : ");
        String[] nom = new String[]{"Attaque","Bouclier","Regénération","Résistance","Joker","Mystère"};
        for(int i=0;i<6;i=i+1){
            println(nom[i] + " : " + ancienneStat[i]);
        }
        println("Quelle est la nouvelle valeur que vous voulez pour chaque bonus ? Valeur comprise entre 0 et 1 (Recommandé : 0.1 / 0.15 / 0.20 / 0.1 / 0.10 / 0.2 / 0.25)");
        double[] nouvelleStat = new double[]{0,0,0,0,0,0};
        int total = 0;
        while(total != 100){
            total = 0;
            for(int i=0;i<6;i=i+1){
                print("Pourcentage pour le bonus " + nom[i] + " : ");
                nouvelleStat[i] = 2;
                while(nouvelleStat[i] < 0 || nouvelleStat[i] > 1){
                    nouvelleStat[i] = readDouble();
                }
                total = total + (int)(nouvelleStat[i]*100);
            }
            if(total != 100){
                println("Recommencez ! Il le faut que les valeurs en s'additionnant donne 1");
            }
        }
        changementAffichageBonus(nouvelleStat, 3108);
        println("Voici les valeurs pour chaque bonus : ");
        for(int i=0;i<6;i=i+1){
            println(nom[i] + " : " + ancienneStat[i] + " --> " + nouvelleStat[i]);
        }
        readString();
        return nouvelleStat;
    }
    boolean[] changementMatiere(boolean[] ancienneStat){
        String[] nom = new String[]{"Anglais","Français","Géographie","Histoire","Sciences","Personnalisé"};        
        println("Les matières qui sont actuellement utilisés pour les questions : ");
        for(int i=0;i<6;i=i+1){
            if(ancienneStat[i]){
                println(nom[i] + " : " + "utilisé");
            } else {
                println(nom[i] + " : " + "non utilisé"); 
            }
        }
        String saisie = "";
        println("Quel matière voulait vous enlever / ajouter ? (pour terminer l'action faites \"Terminer\")");
        boolean incorrect = false;
        while(!equals(saisie,"Terminer") || incorrect){
            saisie = readString();
            int indice = 0;
            while(indice < length(nom) && !equals(saisie,nom[indice])){
                indice = indice + 1;
            }
            if(indice < length(nom)){
                if(equals(saisie,nom[indice])){
                    if(ancienneStat[indice]){
                        println("Voulez-vous supprimez " + nom[indice] + " des matières sélectionnés, tapez \"oui\" pour le supprimer");
                        if(equals("oui",readString())){
                            ancienneStat[indice] = false;
                            println("changement réalisé");
                        } else {
                            println("changement non réalisé");
                        }
                    } else {
                        println("Voulez-vous ajoutez " + nom[indice] + " dans les matières sélectionnés, tapez \"oui\" pour l'ajouter");
                        if(equals("oui",readString())){
                            ancienneStat[indice] = true;
                            println("changement réalisé");
                        } else {
                            println("changement non réalisé");
                        }
                    }
                }
            }
            println();
            println("Les matières qui sont actuellement utilisés pour les questions : ");
            for(int i=0;i<6;i=i+1){
                if(ancienneStat[i]){
                    println(nom[i] + " : " + "utilisé");
                } else {
                    println(nom[i] + " : " + "non utilisé"); 
                }
            }
            indice = 0;
            while(indice < length(ancienneStat) && !ancienneStat[indice]){
                indice = indice + 1;
            }
            if(indice >= length(ancienneStat)){
                incorrect = true;
                println("Vous ne pouvez pas supprimer toutes les matières");
            } else {
                incorrect = false;
            }
        }
        changementAffichageMatiere(ancienneStat, 3523);
        println("Vous avez quitté le menu");
        readString();
        return ancienneStat;
    }
    void changementAffichage(double stat, double max, double min, int localisation){
        String Affichage = fileAsString(locaPartie + "Parametres.txt");
        String texte = "";
        double valeur = min;
        while(stat >= valeur && length(texte) < 20){
            texte = texte + "■";
            valeur = valeur + ((max - min) / 20);
        }
        int txt = length(texte);
        for(int i=0;i+txt<20;i=i+1){
            texte = texte + "□";
        }
        if(stat == (int)stat){
            texte = texte + "   " + (int)stat + "     ";
        } else {
            texte = texte + "   " + stat + "     ";
        }

        Affichage = substring(Affichage,0,localisation) + texte + substring(Affichage,(localisation + length(texte)),length(Affichage));
        stringAsFile(locaPartie + "Parametres.txt", Affichage);
    }

    void changementAffichageBot(double[] affichageBot, int localisation){
        String Affichage = fileAsString(locaPartie + "Parametres.txt");
        for(int i=0;i<length(affichageBot);i=i+1){
            affichageBot[i] = (affichageBot[i]*100);
        }
        String texte = (int)affichageBot[0] + "     " + (int)affichageBot[1] + "     " + (int)affichageBot[2] + "    ";
        Affichage = substring(Affichage,0,localisation) + texte + substring(Affichage,(localisation + length(texte)),length(Affichage));
        stringAsFile(locaPartie + "Parametres.txt", Affichage);
    }

    void changementAffichageBonus(double[] affichageBonus, int localisation){
        String Affichage = fileAsString(locaPartie + "Parametres.txt");
        for(int i=0;i<length(affichageBonus);i=i+1){
            affichageBonus[i] = (affichageBonus[i]*100);
        }
        String texte = (int)affichageBonus[0] + "             " + (int)affichageBonus[1] + "             " + (int)affichageBonus[2] + "             " + (int)affichageBonus[3] + "             " + (int)affichageBonus[4] + "             " + (int)affichageBonus[5] + "       ";
        Affichage = substring(Affichage,0,localisation) + texte + substring(Affichage,(localisation + length(texte)),length(Affichage));
        stringAsFile(locaPartie + "Parametres.txt", Affichage);
    }

    void changementAffichageMatiere(boolean[] affichageMatiere, int localisation){
        String Affichage = fileAsString(locaPartie + "Parametres.txt");
        String texte = "";
        for(int i=0;i<6;i=i+1){
            if(affichageMatiere[i]){
                texte = texte + '■';
            } else {
                texte = texte + '□';
            }
            texte = texte + "             ";
        }
        Affichage = substring(Affichage,0,localisation) + texte + substring(Affichage,(localisation + length(texte)),length(Affichage));
        stringAsFile(locaPartie + "Parametres.txt", Affichage);
    }

    Parametres newParametres(){
        Parametres p = new Parametres();
        p.attaqueBase = 0.1;
        p.pvBase = 100;
        p.resiBase = 1;
        p.roundMax = 10;
        p.chanceRoundSpe = 0.1;
        p.acquisitionBonus = 3;
        p.chanceBonusBot[0] = 0.1;
        p.chanceBonusBot[1] = 0.5;
        p.chanceBonusBot[2] = 0.9;   
        p.reponseBot[0] = 0.3;
        p.reponseBot[1] = 0.5;
        p.reponseBot[2] = 0.7;     
        p.bonusPourcentage[0] = 0.1;
        p.bonusPourcentage[1] = 0.15;
        p.bonusPourcentage[2] = 0.2;
        p.bonusPourcentage[3] = 0.1;
        p.bonusPourcentage[4] = 0.2;
        p.bonusPourcentage[5] = 0.25;
        for(int i=0;i<6;i=i+1){
            p.matiere[i] = true;
        }
        return p;
    }
}

