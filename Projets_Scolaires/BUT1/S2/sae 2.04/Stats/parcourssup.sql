-- \! wget https://data.enseignementsup-recherche.gouv.fr/api/explore/v2.1/catalog/datasets/fr-esr-parcoursup/exports/csv?lang=fr&timezone=Europe%2FBerlin&use_labels=true&delimiter=%3B

drop table if exists etablissements, localisation, formations, admissions, info_admis cascade;

CREATE temp TABLE import (
n1 INT, n2 VARCHAR(33), n3 CHAR(8), n4 TEXT, n5 VARCHAR(3), n6 VARCHAR(23), n7 VARCHAR(27), n8 VARCHAR(21), n9 VARCHAR(29), 
n10 TEXT, n11 VARCHAR(25), n12 VARCHAR(18), n13 TEXT, n14 TEXT, n15 TEXT, n16 TEXT, n17 VARCHAR(21), 
n18 INT, n19 INT, n20 INT, n21 INT, n22 TEXT, n23 INT, n24 INT, n25 INT, 
n26 INT, n27 INT, n28 INT, n29 INT, n30 INT, n31 INT, n32 INT, n33 INT, 
n34 INT, n35 INT, n36 INT, n37 TEXT, n38 TEXT, n39 INT, n40 INT, n41 INT, 
n42 INT, n43 INT, n44 INT, n45 INT, n46 INT, n47 INT, n48 INT, n49 INT,
n50 INT, n51 FLOAT, n52 FLOAT, n53 FLOAT, n54 TEXT, n55 INT, n56 INT,
n57 INT, n58 INT, n59 INT, n60 INT, n61 INT, n62 INT, n63 INT,
n64 INT, n65 INT, n66 FLOAT, n67 INT, n68 INT, n69 INT, n70 TEXT,
n71 TEXT, n72 INT, n73 INT, n74 FLOAT, n75 FLOAT, n76 FLOAT, n77 FLOAT,
n78 FLOAT, n79 FLOAT, n80 FLOAT, n81 FLOAT, n82 FLOAT, n83 FLOAT, n84 FLOAT,
n85 FLOAT, n86 FLOAT, n87 FLOAT, n88 FLOAT, n89 FLOAT, n90 FLOAT, n91 FLOAT,
n92 FLOAT, n93 FLOAT, n94 FLOAT, n95 FLOAT, n96 FLOAT, n97 FLOAT, n98 FLOAT,
n99 FLOAT, n100 FLOAT, n101 FLOAT, n102 VARCHAR(39), n103 TEXT, n104 TEXT, n105 TEXT, 
n106 TEXT, n107 TEXT, n108 VARCHAR(45), n109 VARCHAR(19), n110 VARCHAR(5), n111 TEXT, n112 TEXT, 
n113 TEXT, n114 FLOAT, n115 FLOAT, n116 FLOAT, n117 CHAR(5), n118 CHAR(5));

\copy import from fr-esr-parcoursup.csv with(FORMAT CSV, NULL 'null_string', delimiter ';', HEADER)

CREATE TABLE etablissements(uai,statut,nom_etab)
AS SELECT DISTINCT n3,n2,n4 from import;

CREATE TABLE localisation(uai,ville_etab,acad_etab,code_dep,nom_dep,region)
AS SELECT DISTINCT n3,n9,n8,n5,n6,n7 from import;

CREATE TABLE formations(form_code,uai,form_nom,form_data,form_selec,form_agreg,form_capa,form_taux,form_eprv)
AS SELECT n110,n3,n10,n13,n11,n12,n18,n113,n111 from import;

CREATE TABLE candidats(form_code, bacg_princ, bact_princ)
AS SELECT n110,n23,n25 from import;

CREATE TABLE admissions(form_code,admis_total,admis_princ,admis_compl,admis_debpp,admis_avbac,admis_finpp,pct_finpp)
AS SELECT n110,n47,n49,n50,n51,n52,n53,n76 from import;

CREATE TABLE info_admis(form_code,admis_fem_total,admis_inter,admis_brs,admis_bac,admis_bacg,admis_bact,admis_bacp,admis_autre,admis_ment_unk,admis_ment_non,admis_ment_ab,admis_ment_b,admis_ment_tb,admis_ment_feli,pct_brs)
AS SELECT n110,n48,n54,n55,n56,n57,n58,n59,n60,n61,n62,n63,n64,n65,n66,n81 from import;

ALTER TABLE etablissements ADD CONSTRAINT pk_etablissements PRIMARY KEY(uai);
ALTER TABLE formations ADD CONSTRAINT pk_formations PRIMARY KEY(form_code);
ALTER TABLE localisation ADD CONSTRAINT fk_etablissements FOREIGN KEY(uai) REFERENCES etablissements(uai);
ALTER TABLE formations ADD CONSTRAINT fk_etablissements FOREIGN KEY(uai) REFERENCES etablissements(uai);
ALTER TABLE admissions ADD CONSTRAINT fk_formations FOREIGN KEY(form_code) REFERENCES formations(form_code);
ALTER TABLE info_admis ADD CONSTRAINT fk_formations FOREIGN KEY(form_code) REFERENCES formations(form_code);

SELECT pg_total_relation_size('import') as taille_octet_import;
SELECT pg_total_relation_size('etablissements')+pg_total_relation_size('formations')+pg_total_relation_size('localisation')+pg_total_relation_size('admissions')+pg_total_relation_size('info_admis') as taille_octet_import;