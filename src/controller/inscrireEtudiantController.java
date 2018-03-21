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
import Model.etudiantFormModel;
import controller.jpa.exceptions.NonexistentEntityException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Smart Enigma Techno
 */
public class inscrireEtudiantController implements Initializable {
    
    @FXML
    private TableView<etudiantFormModel> table_recap;

    @FXML
    private TableColumn<etudiantFormModel, Integer> tabler_numero;

    @FXML
    private TableColumn<etudiantFormModel, String> tabler_nom;

    @FXML
    private TableColumn<etudiantFormModel, String> tabler_prenom;

    @FXML
    private TableColumn<etudiantFormModel, String> tabler_matricule;

    @FXML
    private TableColumn<etudiantFormModel, String> tabler_delete;

    @FXML
    private ComboBox<Classe> classe;

    @FXML
    private Label annee;

    @FXML
    private Button valider;

    @FXML
    private TableView<?> table_inscrit;

    @FXML
    private TableColumn<?, ?> tablei_numero;

    @FXML
    private TableColumn<?, ?> tablei_nom;

    @FXML
    private TableColumn<?, ?> tablei_prenom;

    @FXML
    private TableColumn<?, ?> tablei_matricule;

    @FXML
    private TableColumn<?, ?> tablei_classe;

    @FXML
    private TableColumn<?, ?> tablei_delete;
    
    private ObservableList<etudiantFormModel> listRecap;
    private ObservableList<etudiantFormModel> listInscrit;
      EntityManagerFactory  entityManagerFactory;
    /**
     * le controller JPA creer par netbeans qui permettra d'appeler les methodes de crud
     * liées aux actions de la classe
     */
    EtudiantJpaController JpaCtr;
    ClasseJpaController JpaCtrClasse;
     FXMLLoader loader = null;

    public inscrireEtudiantController(ObservableList<etudiantFormModel> listRecap) {
        this.listRecap = listRecap;
           entityManagerFactory=Persistence.createEntityManagerFactory("TpEtudiant1PU"); 
            // fin  recupération des elements
           JpaCtrClasse=new ClasseJpaController(entityManagerFactory); 
    }
    
     private void intialiserCombo()
    {
  //  classe.setValue();
     classe.getItems().clear();
  //   classe.setItems(FXCollections.observableList(JpaCtrClasse.findClasseEntities()));
    }
      public void contenuClassePresenter()
    {
      classe.setItems(FXCollections.observableList(JpaCtrClasse.findClasseEntities()));
          classe.getSelectionModel().selectFirst();
          classe.setCellFactory(new Callback<ListView<Classe>,ListCell<Classe>>(){
            @Override
            public ListCell<Classe> call(ListView<Classe> l){
                return new ListCell<Classe>(){
                    @Override
                    protected void updateItem(Classe item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setGraphic(null);
                        } else {
                         setText(item.getLibelle());
                        }
                    }
                } ;
            }
         });
           classe.setConverter(new StringConverter<Classe>() {
              @Override
              public String toString(Classe user) {
                if (user == null){
                  return null;
                } else {
                  return user.getLibelle();
                }
              }

            @Override
            public Classe fromString(String userId) {
                return null;
            }
        });
        
    }
     
      @Override
    public void initialize(URL url, ResourceBundle rb) {
     initialisationTable();
     contenuClassePresenter();
    }
     private void initialisationTable()
    {
      tabler_numero.setCellValueFactory(new PropertyValueFactory<>("numero")); 
      tabler_nom.setCellValueFactory(new PropertyValueFactory<>("nom")); 
      tabler_prenom.setCellValueFactory(new PropertyValueFactory<>("prenoms")); 
    
      tabler_matricule.setCellValueFactory(new PropertyValueFactory<>("matricule")); 
      tabler_delete.setCellValueFactory(new PropertyValueFactory<>("del")); 
      //placerBouton();
      miseAjourNumeroList(listRecap);
       placerBouton();
       table_recap.setItems(listRecap);
    }
     /**
      * mise a jour des numero de la liste pour mettre
      * les numero dans l'ordre croissant
      * @param listRecap 
      */
     private void miseAjourNumeroList(ObservableList<etudiantFormModel> listRecap)
     {
         int cmpt=1;
       
         for(int i=0;i<listRecap.size();i++)
         {
         listRecap.get(i).setNumero(cmpt);
         cmpt++;
         }
     }
        public void placerBouton()
    {
     tabler_delete.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));
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
                                //  JpaCtr.destroy(Etu.getId());
                                listRecap.remove(getIndex());
                            });
                            
                            setGraphic(btn);
                            
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        };
         tabler_delete.setCellFactory(cellFactory);

    }
}
