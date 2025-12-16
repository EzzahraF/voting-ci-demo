# Voting CI Demo — Refactoring & CI/CD Project

Ce projet a été réalisé dans le cadre du module **Ingénierie des Composants – Refactoring**.  
L’objectif principal est de transformer une application initialement peu structurée en une **architecture propre, modulaire, testable et intégrée dans un pipeline CI/CD**.

Le projet met l’accent sur :
- l’application de **design patterns**
- la **qualité du code**
- la **testabilité**
- l’intégration continue (**Jenkins, JaCoCo, SonarQube**)

---

## Objectifs du projet

- Refactoriser un code existant vers une architecture maintenable
- Appliquer des **patterns de conception** (Factory, Strategy, Observer)
- Séparer clairement les responsabilités (SOLID)
- Ajouter des **tests unitaires**
- Mesurer la **couverture de tests**
- Mettre en place une **pipeline CI/CD**

---

## Architecture globale

Le projet est organisé selon une architecture en couches claire :



src/main/java/org/example/vote/
├── model/ # Modèle métier (Vote, Candidate)
├── repo/ # Accès aux données (Repository Pattern)
├── factory/ # Création des repositories (Factory Method)
├── strategy/ # Algorithmes de comptage (Strategy)
├── observer/ # Notification d’événements (Observer)
├── service/ # Logique métier centrale
└── App.java # Interface Console (CLI)


Cette organisation améliore :
- la lisibilité du code
- la maintenabilité
- l’extensibilité (ajout de nouvelles stratégies ou implémentations)

---

## Design Patterns utilisés

### 🔹 Factory Method
Le pattern **Factory** est utilisé pour instancier les repositories sans coupler le service à une implémentation spécifique.

Exemple :
```java
VoteRepository repo = RepositoryFactory.createRepo("memory");


UML :


🔹 Strategy

Le pattern Strategy permet de changer dynamiquement l’algorithme de comptage des votes sans modifier le service.

Implémentations :

PluralityCountingStrategy

RankedChoiceCountingStrategy (optionnelle)

 Exemple :

service.count(new PluralityCountingStrategy());


UML :


🔹 Observer

Le pattern Observer permet de notifier des événements lors de l’ajout d’un vote (ex : logging).

 Exemple :

service.addListener(new LoggingVoteListener());


📷 UML :


Tests unitaires

Des tests unitaires ont été implémentés afin de valider :

le comptage des votes

le reset du système

les différentes stratégies de comptage

Framework utilisé : JUnit 5

📌 Commande :

mvn clean verify


📷 Résultats des tests :


📊 Couverture de tests — JaCoCo

La couverture de tests est mesurée avec JaCoCo.

📈 Résumé global :

Instructions : 97 %

Branches : 91 %

Classes : 100 %

📷 Rapport JaCoCo :


📌 Rapport HTML :

target/site/jacoco/index.html

🔄 CI/CD — Jenkins & SonarQube

Une pipeline CI/CD complète est définie via Jenkinsfile :

Étapes :

Checkout du code

Build Maven

Tests unitaires

Génération JaCoCo

Analyse SonarQube

Quality Gate

📷 Jenkins Pipeline :


📷 SonarQube Dashboard :


▶ Exécution de l’application

L’application est une interface console (CLI), choix volontaire afin de se concentrer sur :

la logique métier

la qualité du code

la refactorisation

Lancement :

mvn exec:java


Fonctionnalités :

Ajouter un vote

Compter les votes

Réinitialiser

Quitter

📌 Choix techniques

Java 17

Maven

JUnit 5

JaCoCo

Jenkins

SonarQube

Le choix d’une application CLI permet de respecter les objectifs du module IC-Refactoring, sans ajouter une complexité inutile liée à une interface graphique.

✍ Auteur

Projet réalisé par Ezzahra
Module : Ingénierie des Composants – Refactoring & CI/CD


---

# ✅ COMMENT AJOUTER LES IMAGES (TRÈS IMPORTANT)

## 📁 Étape 1 — Créer les dossiers

À la racine du projet :



docs/
├── uml/
├── tests/
└── ci/


---

## 📷 Étape 2 — Ajouter les images

### 🔹 UML (exportées depuis PlantUML)
- `docs/uml/factory-pattern.png`
- `docs/uml/strategy-pattern.png`
- `docs/uml/observer-pattern.png`

### 🔹 Tests & couverture
- `docs/tests/tests-success.png`
- `docs/tests/jacoco-report.png`

### 🔹 CI/CD
- `docs/ci/jenkins-pipeline.png`
- `docs/ci/sonar-dashboard.png`

📌 **Astuce** :  
- JaCoCo → ouvre `target/site/jacoco/index.html` → screenshot  
- SonarQube → screenshot dashboard  
- Jenkins → screenshot pipeline verte  

---

## 🧪 Étape 3 — Commit Git

```bash
git add README.md docs/
git commit -m "Add professional README with UML, tests and CI screenshots"
git push

🏆 Résultat final

Avec cette README :

✅ très professionnelle

✅ claire et structurée

✅ alignée avec IC-Refactoring

✅ prête pour GitHub / soutenance

👉 Tu montres clairement que tu maîtrises :

le refactoring

les patterns

la qualité logicielle

la CI/CD