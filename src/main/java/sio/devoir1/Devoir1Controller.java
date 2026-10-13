package sio.devoir1;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import sio.devoir1.model.Equipe;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.TreeMap;

public class Devoir1Controller implements Initializable
{
    // Clé = Nom du tournoi
    // Value = Une TreeMap qui stocke :
    // Clé = nom de l'équipe
    // Value = l'objet EQUIPE avec ses scores
    private HashMap<String, TreeMap<String, Equipe>> tournois;

    @javafx.fxml.FXML
    private TextField txtScore;
    @javafx.fxml.FXML
    private Label lblResultat;
    @javafx.fxml.FXML
    private TextField txtEquipe;
    @javafx.fxml.FXML
    private TreeView tvTournois;
    @javafx.fxml.FXML
    private ComboBox cbTournois;
    @javafx.fxml.FXML
    private ComboBox cbEquipes;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        tournois = new HashMap<>();
        chargerDonnees();
        chargerTreeView();
        cbTournois.getItems().addAll(tournois.keySet());
    }

    private void chargerDonnees()
    {
        // Voici au départ le jeu d'essais

        // 2 tournois : Robotique et Cyber
        tournois.put("Robotique", new TreeMap<>());
        tournois.put("Cyber", new TreeMap<>());

        // Les 3 équipes du tournoi "Robotique"
        Equipe e1 = new Equipe("Team Eclipse");
        e1.getScores().add(50);
        e1.getScores().add(40);
        e1.getScores().add(30);

        Equipe e2 = new Equipe("Team Alpha");
        e2.getScores().add(20);
        e2.getScores().add(25);
        e2.getScores().add(35);

        Equipe e3 = new Equipe("Team Nova");
        e3.getScores().add(60);
        e3.getScores().add(10);

        tournois.get("Robotique").put(e1.getNom(), e1);
        tournois.get("Robotique").put(e2.getNom(), e2);
        tournois.get("Robotique").put(e3.getNom(), e3);

        // Les 2 équipes du tournoi "Cyber"
        Equipe e4 = new Equipe("Phoenix");
        e4.getScores().add(35);
        e4.getScores().add(45);

        Equipe e5 = new Equipe("Titans");
        e5.getScores().add(50);
        e5.getScores().add(40);

        tournois.get("Cyber").put(e4.getNom(), e4);
        tournois.get("Cyber").put(e5.getNom(), e5);
    }

    private void chargerTreeView()
    {
        TreeItem<String> racine = new TreeItem<>("Tournois");
        racine.setExpanded(true);

        for (String nomTournoi : tournois.keySet()) {
            TreeItem<String> tournoiItem = new TreeItem<>(nomTournoi);
            tournoiItem.setExpanded(true);

            TreeMap<String, Equipe> equipes = tournois.get(nomTournoi);
            for (String nomEquipe : equipes.keySet()) {
                Equipe equipe = equipes.get(nomEquipe);
                TreeItem<String> equipeItem = new TreeItem<>(nomEquipe + " - " + equipe.getTotalPoints() + " pts");
                tournoiItem.getChildren().add(equipeItem);
            }

            racine.getChildren().add(tournoiItem);
        }

        tvTournois.setRoot(racine);
    }

    private void chargerEquipes()
    {
        cbEquipes.getItems().clear();
        String nomTournoi =  cbTournois.getSelectionModel().getSelectedItem().toString();
        if (nomTournoi != null) {
            TreeMap<String, Equipe> equipes = tournois.get(nomTournoi);
            for (String nomEquipe : equipes.keySet()) {
                cbEquipes.getItems().add(nomEquipe);
            }
        }
    }

    @javafx.fxml.FXML
    public void ajouterEquipe(ActionEvent actionEvent)
    {
        lblResultat.setText("");
        String nomEquipe = txtEquipe.getText().trim();
        String nomTournoi = cbTournois.getSelectionModel().getSelectedItem().toString();

        if (nomEquipe.isEmpty()) {
            lblResultat.setText("Le nom de l'équipe est obligatoire");
            return;
        }

        if (nomTournoi == null) {
            lblResultat.setText("Veuillez sélectionner un tournoi");
            return;
        }

        TreeMap<String, Equipe> equipes = tournois.get(nomTournoi);
        if (equipes.containsKey(nomEquipe)) {
            lblResultat.setText("Cette équipe existe déjà dans ce tournoi");
            return;
        }

        Equipe nouvelleEquipe = new Equipe(nomEquipe);
        equipes.put(nomEquipe, nouvelleEquipe);

        chargerTreeView();
        chargerEquipes();
        txtEquipe.clear();
        lblResultat.setText("Équipe ajoutée avec succès");
    }

    @javafx.fxml.FXML
    public void ajouterScore(ActionEvent actionEvent)
    {
        lblResultat.setText("");
        String nomTournoi = cbTournois.getSelectionModel().getSelectedItem().toString();
        String nomEquipe = cbEquipes.getSelectionModel().getSelectedItem().toString();
        String scoreStr = txtScore.getText().trim();

        if (nomEquipe == null) {


            lblResultat.setText("Veuillez sélectionner une équipe");

        }

        if (scoreStr.isEmpty()) {
            lblResultat.setText("Le score est obligatoire");

        }

        try {
            int score = Integer.parseInt(scoreStr);
            Equipe equipe = tournois.get(nomTournoi).get(nomEquipe);
            equipe.getScores().add(score);

            chargerTreeView();
            txtScore.clear();
            lblResultat.setText("Score ajouté avec succès");
        } catch (NumberFormatException e) {
            lblResultat.setText("Le score doit êtree  un nombre");
        }
    }

    @javafx.fxml.FXML
    public void calculTotalTournoi(ActionEvent actionEvent)
    {
        lblResultat.setText("");
        String nomTournoi = cbTournois.getSelectionModel().getSelectedItem().toString();

        if (nomTournoi == null) {
            lblResultat.setText("Veuillez sélèctionner un tournoi");
            return;
        }

        int ttlTournoi = 0;
        TreeMap<String, Equipe> equipes = tournois.get(nomTournoi);
        for (Equipe equipe : equipes.values()) {
            ttlTournoi += equipe.getTotalPoints();
        }

        lblResultat.setText("Total du tournpi " + nomTournoi + " : " + ttlTournoi + " ptss");
    }

    @javafx.fxml.FXML
    public void meilleureEquipe(ActionEvent actionEvent)
    {
        lblResultat.setText("");
        String nomTournoi =  cbTournois.getSelectionModel().getSelectedItem().toString();


        if (nomTournoi == null) {
            lblResultat.setText("Veuillez sélectionner un tournoi");
            return;
        }


        TreeMap<String, Equipe> equipes = tournois.get(nomTournoi);


        if (equipes.size() == 0) {
            lblResultat.setText("Aucune équipe dans ce tournoi");
            return;
        }


        Equipe meilleureEquipe = null;
        int meilleurScore = 0;


        for (Equipe equipe : equipes.values()) {
            int scoreEquipe = equipe.getTotalPoints();
            if (scoreEquipe > meilleurScore) {
                meilleurScore = scoreEquipe;
                meilleureEquipe = equipe;
            }
        }
        lblResultat.setText("Meilleure 2quipe : " + meilleureEquipe.getNom() + " avec " + meilleurScore + " pts");
    }

    @javafx.fxml.FXML
    public void renommerEquipe(ActionEvent actionEvent)
    {
        // PS : je ne parviens pas a corriger mes erreurs je prèfére laisser de coté pour me concentrer sur les autres methodes

//        // A vous de jouer
//        String nomEquipe = (String) cbEquipes.getSelectionModel().getSelectedItem();
//        String nomTournoi = (String) cbTournois.getSelectionModel().getSelectedItem();
//        boolean validationNomEquipe = false;
//        boolean validationTxtEquipe = false;
//        boolean validationUnique = false;
//
//        if (nomEquipe == null) {
//            lblResultat.setText("Vous devez séléctionner une équipe .");
//        }else {
//            validationNomEquipe = true;
//        }
//        if (txtEquipe.getText().isEmpty()) {
//            lblResultat.setText("Vous devez saisir un nom pour renommer votre équipe");
//        }else {
//            validationTxtEquipe = true;
//        }
//
//        for(String nom : tournois.get(nomTournoi).keySet()) {
//            if (nomEquipe.toString() == nom) {
//                lblResultat.setText("Votre nom doit etre unique.");
//            }else {
//                validationUnique = true;
//            }
//        }
//
//        if (validationNomEquipe == true && validationTxtEquipe == true && validationUnique == true) {
//            Equipe equipe = tournois.get(nomTournoi).get(nomEquipe);
//            equipe.getScores()
//            tournois.get(nomTournoi).put(txtEquipe.getText(), equipe.getScores());
//            chargerTreeView();
//            chargerEquipes();
//    }

    }

    @javafx.fxml.FXML
    public void chargerEquipes(ActionEvent actionEvent)
    {
        chargerEquipes();
    }
}
