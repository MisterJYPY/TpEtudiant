/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Entities.Classe;
import controller.jpa.ClasseJpaController;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * FXML Controller class
 *
 * @author Smart Enigma Techno
 */
public class ClasseFormController implements Initializable {

    /**
     * Tous les elements ci-dessous font parti du binding entre les 
     * elements de la vue et du controller. le symbole fxml permet de specifier que les elements 
     * proviennent d'un fichier fxml
     */
    
    @FXML
    private TableColumn<?, ?> table_numero;

    @FXML
    private TableColumn<?, ?> table_intitule;

    @FXML
    private TableColumn<?, ?> table_description;

    @FXML
    private TableColumn<?, ?> table_modif;

    @FXML
    private TableColumn<?, ?> table_delete;

    @FXML
    private TextField intitule;

    @FXML
    private TextArea description;

    @FXML
    private Button valider;
    /**
     * Initializes the controller class.
     */
    /**
     * le manager qui va permettre a notre classe d'avoir une instance de notre classe de persistence
     * assuré par JPA
     */
    EntityManagerFactory  entityManagerFactory;
    /**
     * le controller JPA creer par netbeans qui permettra d'appeler les methodes de crud
     * liées aux actions de la classe
     */
    ClasseJpaController JpaCtr;
    
    /**
     * c'est cette methode qui est la premiere à s'executer lorsque l'on desire afficher une vue 
     * qui est geré par un controller; (dans notre cas c'est la classe Classe)
     * @param url
     * @param rb 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        /**
         * recupération de l'instance de persistence en spécifiant le nom de l'unité de persistence
         */
         entityManagerFactory=Persistence.createEntityManagerFactory("TpEtudiant1PU");
         /**
          * nous mettons une action sur le bouton valider 
          * cela permettra de recuperer les informations saisies et l'enregistrer dans la base de données
          */
         valider.setOnAction(e->{
             //recupération des informations
           String intitule=this.intitule.getText();
           String description=this.description.getText();
           // fin  recupération des elements
           //recuperation de notre instance de notre controller
           
           JpaCtr=new ClasseJpaController(entityManagerFactory);
           /**
            * creation de l'instance de l'entités
            */
           Classe nvocls=new Classe();
           nvocls.setDerniereModif(new Timestamp(new Date().getTime()));
           nvocls.setDescription(description);
           nvocls.setLibelle(intitule);
           //fin de la mise a jour de notre entites
           /**
            * persistence dans la base de données par la methode create definit ds le controller 
            * ou il est chargé d'appeler la methode persist
            */
           JpaCtr.create(nvocls);
           
        });
    }    
    
}
