package sio.devoir1.model;

import java.util.ArrayList;

public class Equipe
{
    private String nom;
    private ArrayList<Integer> scores;

    public Equipe(String nom) {
        this.nom = nom;
        this.scores = new ArrayList<>();
    }

    public String getNom() {
        return nom;
    }

    public ArrayList<Integer> getScores() {
        return scores;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getTotalPoints()
    {
        int total = 0;
        for (int score : scores) {
            total += score;
        }
        return total;
    }

    public void vider()
    {
        scores.clear();
    }
}
