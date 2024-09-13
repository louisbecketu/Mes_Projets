--Exercice 3
--Q1

select n56, (n57 +n58 +n59) as nombres_n56 
from import;

--Q2

select count(*) as lignes_totales, (select count(*) as lignes_recalcul_n56 from import 
where n56 = (n57+n58+n59)) from import;

/*Cela permet de compter le nombre de lignes où la colonne 56 est égale à la somme des colonnes 57,58 et 59. Si le résultat
est égale au nombre de lignes totale alors on a trouvé le bon calcul */

--Q3

select n74, round(n51 /n47 *100) as pourcentages_74 
from import where n74 <> 0;

--Q4 

select count(*) as lignes_totales, (select count(*) as lignes_recalcul_n74 from import where n74 <>0 
and round(n51/n47 *100) = n74 ) 
from import where n74 <> 0;

/*Il y a 3 de différences entre les lignes totales et les lignes recalculés, cela est normal puisque
certaines valeurs sont mis en "57,49999..." et sont donc arrondies à 57 au lieu de 58.*/

--Q5 

select n76, round(n53/n47 *100) as pourcentage_76 
from import where n76 <> 0;

-- A partir de 14 décimales, les données deviennent exactes.

--Q6

select pct_finpp, round(admis_finpp/admis_total *100) as recalcul_finpp
from admissions where pct_finpp <> 0 and admis_finpp <> 0 and admis_total <> 0;

/*
Pour vérifier :

select count(*) as pct_finpp, (select count(*) as lignes_recalcul_finpp from admissions 
where admis_finpp <> 0 and admis_total <> 0 and round(admis_finpp/admis_total *100) = pct_finpp) 
from admissions where pct_finpp <> 0 ;

10 qui manque dans la table recalculée, même erreur que la Question 4 qui arrondie à un chiffre de moins.
*/

--Q7

select n81, round(n55*100/n56) as recalcul_n81
from import where n81 <> 0;

--à partir de 16 décimales

--Q8

select pct_brs, round(admis_brs*100/admis_bac) as recalcul_brs
from info_admis where pct_brs <> 0 and admis_bac <> 0;

/*
Pour vérifier :

select count(*) as pct_brs, (select count(*) as lignes_recalcul_brs from info_admis 
where admis_brs <> 0 and admis_bac <> 0 and (round(admis_brs*100/admis_bac)=pct_brs or round(admis_brs*100/admis_bac) = pct_brs-1))
from info_admis where pct_brs <> 0 ;

Dû au problème d'arrondie, on accepte les valeurs égales ou inférieur de 1 de la valeur pourcentage, toutes les valeurs sont retournées.
*/