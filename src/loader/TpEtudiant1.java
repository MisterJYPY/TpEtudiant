/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loader;

import controller.ClasseFormController;
import controller.EtudiantFormController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Smart Enigma Techno (NANOU KAKOU JEAN-PAUL)
 */
public class TpEtudiant1 extends Application {
    FXMLLoader loader = null;
    @Override
    public void start(Stage primaryStage) {
        
         try {
//            Image image = new Image(
//                    getClass().getResourceAsStream("image/icone.png")
//            );
            loader = new FXMLLoader();
           // loader.setLocation(getClass().getResource("/formuly/view/frontend/acceuille.fxml"));
           
           // loader.setLocation(getClass().getResource("/userInterface/classeForm.fxml"));
//            dem=new DemarrageAppController();
         //    loader.setController(ClasseFormController.class.newInstance());
                  
            loader.setLocation(getClass().getResource("/userInterface/etudiantForm.fxml"));
//            dem=new DemarrageAppController();
             loader.setController(EtudiantFormController.class.newInstance());
            Parent root = loader.load();
            primaryStage.setTitle("démarrage");
       //     primaryStage.getIcons().add(image);
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
            
        } catch (IOException ex) {        
         Logger.getLogger(TpEtudiant1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(TpEtudiant1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(TpEtudiant1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
