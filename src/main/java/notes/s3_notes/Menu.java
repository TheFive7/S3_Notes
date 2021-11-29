package notes.s3_notes;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Menu extends Pane {
    public Menu() {
        String[] moyennes = {
                "M3101 - C",
                "M3102 - Réseaux",
                "M3103 - Algo Avancé",
                "M3104 - Programmation Web",
                "M3105 - Conception Objet",
                "M3106 - Bases de données",
                "M3201 - Probabilités",
                "M3202 - Maths",
                "M3203 - Droit",
                "M3204 - Gestion des systèmes",
                "M3205 - Expression",
                "M3206 - Anglais",
                "M3301 - Production application",
                "M3302 - Projet tutoré",
                "M3303 - PPP"
        };

        double[] coefficients = {
            2.5,1.5,1.5,2.5,2.5,1.5,2.5,1.5,1.5,2.5,1.5,2.5,3,2,1
        };

        TextArea[] textes = new TextArea[15];

        // Moyenne
        double moyenne = 0;
        double total = 0;

        int compteur = 0;
        for (String s : moyennes){
            // Pane
            Pane pane = new Pane(); pane.setLayoutY(compteur * 50);

            // Texte
            TextArea textArea = new TextArea(); textArea.setPrefHeight(20); textArea.setPrefWidth(50); textArea.setLayoutX(300);
            textes[compteur] = textArea;

            // Label
            Label labelNote = new Label("Note :"); labelNote.setLayoutX(250);
            Label labelCoeff = new Label("Coefficient : " + coefficients[compteur]); labelCoeff.setLayoutX(400);

            // Ajout
            pane.getChildren().addAll(new Label(s),labelNote,textArea,labelCoeff);
            getChildren().add(pane);
            compteur ++;
        }

        // Moyenne
        Label labelMoyenne = new Label("Moyenne Générale : " + moyenne); labelMoyenne.setLayoutY(300); labelMoyenne.setLayoutX(600);

        Button buttonMoyenne = new Button("> Calculer ma moyenne <"); buttonMoyenne.setLayoutY(350); buttonMoyenne.setLayoutX(600);
        buttonMoyenne.setOnAction(actionEvent -> calculerMoyenne(textes,moyenne,total,labelMoyenne,coefficients));

        // Save
        Button buttonSave = new Button("Save notes"); buttonSave.setLayoutX(600);
        buttonSave.setOnAction(actionEvent -> save("src/main/resources/notes/s3_notes/save.txt",textes));

        // Load
        Button buttonLoad = new Button("Load notes"); buttonLoad.setLayoutX(700);
        buttonLoad.setOnAction(actionEvent -> load("src/main/resources/notes/s3_notes/save.txt",textes));

        getChildren().addAll(labelMoyenne,buttonMoyenne,buttonSave,buttonLoad);
    }

    /**
     * Calcule la moyenne et l'affiche.
     * @param textes : Textes pour avoir les notes
     * @param moyenne : La moyenne
     * @param total : Le total des coefficients
     * @param label : Le label à changer
     * @param coefficients : Les coefficients
     */
    public static void calculerMoyenne(TextArea[] textes,double moyenne,double total,Label label,double[] coefficients){
        int compteur = 0;
        for(TextArea t : textes){
            if (!t.getText().equals("")) {
                moyenne += Double.parseDouble(t.getText()) * coefficients[compteur];
                total += coefficients[compteur];
            }
            compteur ++;
        }
        moyenne /= total;
        label.setText("Moyenne Générale : " + Math.round(moyenne * 1000.0) / 1000.0);
    }

    /**
     * Permet de sauvegarder les notes de l'utilisateur.
     * @param chemin : Chemin du fichier
     * @param textes :Les textes à changer
     */
    public static void save(String chemin,TextArea[] textes){
        // Sauvegarde
        String[] notes = new String[15];
        int compteur = 0;
        for(TextArea textArea : textes){
            notes[compteur] = textArea.getText();
            compteur ++;
        }

        try {
            FileOutputStream file = new FileOutputStream(chemin);
            ObjectOutputStream output = new ObjectOutputStream(file);
            output.writeObject(notes);
            System.out.println("Fichier sauvegardé.");
        } catch (Exception e) {
            System.err.println("Erreur avec la sauvegarde du fichier.");
        }
    }

    /**
     * Permet de charger les notes sauvegardées dans le fichier.
     * @param chemin : Chemin menant à la sauvegarde
     * @param textes : Les textes à changer
     */
    public void load(String chemin, TextArea[] textes) {
        try {
            FileInputStream fileStream = new FileInputStream(chemin);
            ObjectInputStream input = new ObjectInputStream(fileStream);
            String[] notes = (String[]) input.readObject();

            // Load
            int compteur = 0;
            for(TextArea t : textes){
                t.setText(notes[compteur]);
                compteur ++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
