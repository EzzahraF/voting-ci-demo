### Voting CI Demo – Refactoring & DevOps Pipeline
# Présentation du projet

Ce projet s’inscrit dans le cadre du module Ingénierie Logicielle à l’ENSA Marrakech. Il a pour objectif de transformer un code legacy de type spaghetti code en une application modulaire, testée et intégrée automatiquement dans une chaîne CI/CD.

Le système étudié est une application simple de votes en ligne, utilisée comme support pédagogique pour le refactoring, l’application de Design Patterns et la mise en place d’un pipeline DevOps.

# Objectifs du projet

Refactoriser un code monolithique difficilement maintenable

Appliquer des Design Patterns pour améliorer la qualité logicielle

Mettre en place des tests unitaires automatisés

Configurer une pipeline d’intégration continue avec Jenkins

Analyser la qualité du code avec SonarQube et JaCoCo

# Design Patterns utilisés

Factory Method : création dynamique des repositories sans couplage fort

Strategy : gestion de plusieurs algorithmes de comptage des votes

Observer : notification des événements lors de l’ajout d’un vote

Ces patterns permettent une meilleure séparation des responsabilités, une extensibilité accrue et une meilleure testabilité.

# Architecture du projet

L’application est structurée selon une architecture modulaire avec séparation claire des couches :

Model : entités métier (Vote, Candidate)

Repository (DAO) : persistance des données

Service : logique métier et orchestration

Strategy : algorithmes de dépouillement

Observer : notifications d’événements

App : point d’entrée CLI

# Technologies utilisées

Java

Maven

JUnit 5

JaCoCo

SonarQube

Jenkins

 Structure du projet
voting-ci-demo/
├─ pom.xml
├─ Jenkinsfile
├─ sonar-project.properties
├─ README.md
├─ src/
│  ├─ main/java/org/example/vote/
│  │  ├─ model/
│  │  ├─ repo/
│  │  ├─ factory/
│  │  ├─ service/
│  │  ├─ strategy/
│  │  ├─ observer/
│  │  └─ App.java
│  └─ test/java/org/example/vote/service/
│     └─ VoteServiceTest.java
└─ target/
 Exécution du projet
Compilation et tests
mvn clean install
Lancement de l’application (CLI)
java -jar target/voting-ci-demo.jar
# Tests unitaires

La couverture de code est générée automatiquement lors du build.

Rapport HTML : target/site/jacoco/index.html

Seuil minimal requis : > 60%

📸 Capture du rapport JaCoCo (à insérer ici)
![Uploading jacoco_test.png…]()


# Analyse de la qualité – SonarQube

L’analyse SonarQube permet de vérifier :

La qualité du code

Les duplications

Les bugs et code smells

Le respect du Quality Gate

Commande :

mvn sonar:sonar
![Uploading sonarqube_dashboard.png…]()


# Pipeline Jenkins

La pipeline Jenkins automatise les étapes suivantes :

Récupération du code source

Build Maven

Exécution des tests unitaires

Génération des rapports JaCoCo

Analyse SonarQube

Vérification du Quality Gate


# Livrables

Code source versionné (Git)

Jenkinsfile fonctionnel

Rapports JUnit, JaCoCo et SonarQube

Rapport de projet (PDF)

Présentation orale et démonstration

# Contexte académique

École : 

Module : Ingénierie Logicielle

Filière : Génie Informatique

✍️ Auteur

@Bahamd Imane
@Elkhlifi Yousra
@Fadyl Ezzahra
Année universitaire 2025/2026
