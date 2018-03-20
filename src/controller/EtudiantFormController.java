/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Entities.Classe;
import Entities.Etudiant;
import Model.ClasseJpaController;
import Model.EtudiantJpaController;
import Model.classTableModel;
import Model.etudiantFormModel;
import controller.jpa.exceptions.NonexistentEntityException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
public class EtudiantFormController implements Initializable {

    
        @FXML
    private TableView<etudiantFormModel> table;

    @FXML
    private TableColumn<etudiantFormModel,Integer> table_numero;

    @FXML
    private TableColumn<etudiantFormModel, String> table_nom;

    @FXML
    private TableColumn<etudiantFormModel, String> table_prenom;

    @FXML
    private TableColumn<etudiantFormModel, String> table_matricule;

    @FXML
    private TableColumn<etudiantFormModel, Date> table_dateN;

    @FXML
    private TableColumn<etudiantFormModel, String> table_lieuN;

    @FXML
    private TableColumn<etudiantFormModel, String> table_modif;

    @FXML
    private TableColumn<etudiantFormModel, String> table_del;

    @FXML
    private TextField nom;

    @FXML
    private TextField prenom;

    @FXML
    private TextField matricule;

    @FXML
    private DatePicker dateN;

    @FXML
    private TextField lieuN;

    @FXML
    private ComboBox<Classe> classe;

    @FXML
    private Label annee;

    @FXML
    private Button valider;

    @FXML
    private Label alertM;
    
     EntityManagerFactory  entityManagerFactory;
    /**
     * le controller JPA creer par netbeans qui permettra d'appeler les methodes de crud
     * liées aux actions de la classe
     */
    EtudiantJpaController JpaCtr;
    ClasseJpaController JpaCtrClasse;
     ObservableList<etudiantFormModel> list;
     ObservableList<etudiantFormModel> listTemp;

     
    /**
     * Initializes the controller class.
     */
    
    public EtudiantFormController() {
         entityManagerFactory=Persistence.createEntityManagerFactory("TpEtudiant1PU"); 
            // fin  recupération des elements
        
           JpaCtr=new EtudiantJpaController(entityManagerFactory);  
           JpaCtrClasse=new ClasseJpaController(entityManagerFactory);  
               list=JpaCtr.listTableModel();
           //recuperation de notre instance de notre controller
            listTemp=FXCollections.observableArrayList();
    }    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        initialisationTable();
        valider.setOnAction((ActionEvent e)->{
            
            /**
             * recuperation des informations
             */
            String nom=this.nom.getText();
            String prenom=this.prenom.getText();
            String lieuN=this.lieuN.getText();
            String matricule=this.matricule.getText();      
             Date dateN= java.sql.Date.valueOf( this.dateN.getValue() );
             /**
              * creation de l'etudaint et assignation des proprietes
              */
            Etudiant etu=new Etudiant();
            etu.setDateNaissance(dateN);
            etu.setNom(nom);
            etu.setPrenom(prenom);
            etu.setMatricule(matricule);
            etu.setDerniereModif(new Timestamp(new Date().getTime()));
            etu.setLieuNaissance(lieuN);
            /**
             * rendre persistent cet etudiant (ds la bdd)
             */
            JpaCtr.create(etu);
            
            /**
             * preparation pour la mise a jour ds le tableau
             */
            etudiantFormModel modelEt=new etudiantFormModel();
            modelEt.setNumero(list.size()+1);
            modelEt.setNom(etu.getNom());
            modelEt.setPrenoms(etu.getPrenom());
            modelEt.setLieuN(etu.getLieuNaissance());
            modelEt.setDateN(etu.getDateNaissance());
            modelEt.setEtudiant(etu);
            modelEt.setMatricule(etu.getMatricule());
            /**
             * ajout à la liste et mise a jour 
             */
            list.add(modelEt);
         
        });
    }    
     private void initialisationTable()
    {
     placerBouton(1);
          placerBouton("");

      table_numero.setCellValueFactory(new PropertyValueFactory<>("numero")); 
      table_nom.setCellValueFactory(new PropertyValueFactory<>("nom")); 
      table_prenom.setCellValueFactory(new PropertyValueFactory<>("prenoms")); 
      table_dateN.setCellValueFactory(new PropertyValueFactory<>("dateN"));
      table_lieuN.setCellValueFactory(new PropertyValueFactory<>("lieuN")); 
      table_matricule.setCellValueFactory(new PropertyValueFactory<>("matricule")); 
      table_modif.setCellValueFactory(new PropertyValueFactory<>("modif")); 
      table_del.setCellValueFactory(new PropertyValueFactory<>("del")); 
      //placerBouton();
      table.setItems(list);
    }
     
  public void placerBouton(int n)
    {
     table_del.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));
      Callback<TableColumn<etudiantFormModel, String>, TableCell<etudiantFormModel, String>> cellFactory = new Callback<TableColumn<etudiantFormModel, String>, TableCell<etudiantFormModel, String>>() {      
                    @Override
            public TableCell call(final TableColumn<etudiantFormModel, String> param) {
                final TableCell<etudiantFormModel, String> cell = new TableCell<etudiantFormModel, String>() {

                    final Button btn = new Button("del");
                    final CheckBox chk=new CheckBox();

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
//                         
                            chk.setOnAction(e->{
                             if(chk.isSelected())
                                 {
                                 etudiantFormModel element = getTableView().getItems().get(getIndex());
                                  listTemp.add(element);
                                     System.out.println("nbre Element ajjj : "+listTemp.size());
                                 }
                                 else{
                                 etudiantFormModel element = getTableView().getItems().get(getIndex());
                                   listTemp.remove(element);
                                    System.out.println("element decliquer : "+element.getNom());
                                    System.out.println("nbre Element supp : "+listTemp.size());
                                 }
                            
                            });
                            
                            setGraphic(chk);
                            
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        };
         table_del.setCellFactory(cellFactory);

    }
   public void placerBouton(String n)
    {
     table_modif.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));
      Callback<TableColumn<etudiantFormModel, String>, TableCell<etudiantFormModel, String>> cellFactory = new Callback<TableColumn<etudiantFormModel, String>, TableCell<etudiantFormModel, String>>() {      
                    @Override
            public TableCell call(final TableColumn<etudiantFormModel, String> param) {
                final TableCell<etudiantFormModel, String> cell = new TableCell<etudiantFormModel, String>() {

                    final Button btn = new Button("del");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
//                         
                            btn.setOnAction(e->{
                            etudiantFormModel element = getTableView().getItems().get(getIndex());
                                Etudiant Etu=element.getEtudiant();
                                try {
                                    JpaCtr.destroy(Etu.getId());
                                     list.remove(getIndex());
                                     if(!listTemp.isEmpty())
                                     {
                                     listTemp.remove(element);
                                     }
                                } catch (NonexistentEntityException ex) {
                                    Logger.getLogger(EtudiantFormController.class.getName()).log(Level.SEVERE, null, ex);
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
         table_modif.setCellFactory(cellFactory);

    }
}
