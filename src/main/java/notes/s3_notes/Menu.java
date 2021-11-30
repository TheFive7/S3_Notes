package notes.s3_notes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.*;

public class Menu extends Pane {
    static String user = "user";
    static ObservableList<String> users;

    public Menu() {

        users = FXCollections.observableArrayList();

        // Ajouter tous les utilisateurs
        File repertoire = new File("src/main/resources/notes/s3_notes");
        String[] liste = repertoire.list();

        if (liste != null) {
            for (String s : liste) {
                final String SEPARATEUR = "\\.";
                String[] mots = s.split(SEPARATEUR);
                users.add(mots[0]);
            }
        }

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

        // Users
        ChoiceBox<String> choiceUser = new ChoiceBox<>(users); choiceUser.setLayoutX(650); choiceUser.setLayoutY(200);
        choiceUser.setOnAction(actionEvent -> chooseUser(choiceUser,textes,moyenne,total,labelMoyenne,coefficients));
        choiceUser.setValue(users.get(0));

        Button buttonMoyenne = new Button("> Calculer ma moyenne <"); buttonMoyenne.setLayoutY(350); buttonMoyenne.setLayoutX(600);
        buttonMoyenne.setOnAction(actionEvent -> calculerMoyenne(textes,moyenne,total,labelMoyenne,coefficients));

        // Create User
        Button buttonCreateUser = new Button("Create User"); buttonCreateUser.setLayoutX(650); buttonCreateUser.setLayoutY(100);
        buttonCreateUser.setOnAction(actionEvent -> createUser());

        getChildren().addAll(labelMoyenne,buttonMoyenne,buttonCreateUser,choiceUser);
    }

    /**
     * Créé un nouveau user.
     */
    public static void createUser(){
        Stage stage = new Stage();
        Pane pane = new Pane();
        Label label = new Label("User name : "); label.setLayoutY(100); label.setLayoutX(50);
        TextArea textAreaUser = new TextArea();
        textAreaUser.setLayoutY(100); textAreaUser.setLayoutX(150); textAreaUser.setPrefWidth(200); textAreaUser.setPrefHeight(20);
        Button button = new Button("Submit"); button.setLayoutY(150); button.setLayoutX(200);
        button.setOnAction(actionEvent -> {
            TextArea[] textes = new TextArea[15]; for(int i = 0; i < textes.length; i++){textes[i] = new TextArea();}
            save("src/main/resources/notes/s3_notes/"+ textAreaUser.getText() +".txt",textes);
            users.add(textAreaUser.getText());
            stage.close();
        });
        pane.getChildren().addAll(label,textAreaUser,button);
        Scene scene = new Scene(pane, 400, 200);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Permet de load un user.
     * @param choiceBox : ChoiceBox
     * @param textes : Notes
     */
    public static void chooseUser(ChoiceBox choiceBox,TextArea[] textes,double moyenne,double total,Label label,double[] coefficients){
        save("src/main/resources/notes/s3_notes/"+ user +".txt",textes);
        user = (String) choiceBox.getValue();
        load("src/main/resources/notes/s3_notes/"+ user +".txt",textes);
        calculerMoyenne(textes,moyenne,total,label,coefficients);
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
     * Permet de charger les notes sauvegardées dans ele fichier.
     * @param chemin : Chemin menant à la sauvegarde
     * @param textes : Les textes à changer
     */
    public static void load(String chemin, TextArea[] textes) {
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
