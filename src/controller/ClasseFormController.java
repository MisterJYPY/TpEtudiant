/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Entities.Classe;
import Model.ClasseJpaController;
import Model.classTableModel;
import controller.jpa.exceptions.NonexistentEntityException;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
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
    private TableColumn<classTableModel,Integer> table_numero;

    @FXML
    private TableColumn<classTableModel, String> table_intitule;

    @FXML
    private TableColumn<classTableModel, String> table_description;

    @FXML
    private TableColumn<classTableModel, Date> table_modif;

    @FXML
    private TableColumn<classTableModel, String> table_delete;

    @FXML
    private TextField intitule;

    @FXML
    private TextArea description;

    @FXML
    private Button valider;
    
    @FXML
     private TableView table;
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
     ObservableList<classTableModel> list;

    public ClasseFormController() {
         // TODO
        /**
         * recupération de l'instance de persistence en spécifiant le nom de l'unité de persistence
         */
         entityManagerFactory=Persistence.createEntityManagerFactory("TpEtudiant1PU"); 
            // fin  recupération des elements
           //recuperation de notre instance de notre controller
           
           JpaCtr=new ClasseJpaController(entityManagerFactory);  
            list=JpaCtr.listTableModel();
           
    }
    
    
    /**
     * c'est cette methode qui est la premiere à s'executer lorsque l'on desire afficher une vue 
     * qui est geré par un controller; (dans notre cas c'est la classe Classe)
     * @param url
     * @param rb 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
         /**
          * nous mettons une action sur le bouton valider 
          * cela permettra de recuperer les informations saisies et l'enregistrer dans la base de données
          */
             initialisationTable();

         valider.setOnAction(e->{
             //recupération des informations
           String intitule=this.intitule.getText();
           String description=this.description.getText();
        
           // fin  recupération des elements
           //recuperation de notre instance de notre controller
           

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
           classTableModel mdl=new classTableModel(list.size()+1,nvocls.getLibelle(),nvocls.getDescription());
           mdl.setDerniereModif(nvocls.getDerniereModif());
           mdl.setClasse(nvocls);
           list.add(mdl);
           
        });
    }    
    private void initialisationTable()
    {
     
      table_numero.setCellValueFactory(new PropertyValueFactory<>("numero")); 
      table_intitule.setCellValueFactory(new PropertyValueFactory<>("libelle")); 
      table_description.setCellValueFactory(new PropertyValueFactory<>("description")); 
      table_modif.setCellValueFactory(new PropertyValueFactory<>("derniereModif"));
      table_delete.setCellValueFactory(new PropertyValueFactory<>("delete")); 
      placerBouton();
       table.setItems(list);
    }
      public void placerBouton()
    {
     table_delete.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));
      Callback<TableColumn<classTableModel, String>, TableCell<classTableModel, String>> cellFactory = new Callback<TableColumn<classTableModel, String>, TableCell<classTableModel, String>>() {      
                    @Override
            public TableCell call(final TableColumn<classTableModel, String> param) {
                final TableCell<classTableModel, String> cell = new TableCell<classTableModel, String>() {

                    final Button btn = new Button("del");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setOnAction(event -> {
                                classTableModel element = getTableView().getItems().get(getIndex());
                                Classe clasEc=element.getClasse();
                                try {
                                    JpaCtr.destroy(clasEc.getId());
                                     list.remove(getIndex());
                                } catch (NonexistentEntityException ex) {
                                    Logger.getLogger(ClasseFormController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                               
                            });
                            setGraphic(btn);
                            
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        };
         table_delete.setCellFactory(cellFactory);

    }
}
