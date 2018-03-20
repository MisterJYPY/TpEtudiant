/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Entities.Classe;
import java.util.Date;



/**
 *
 * @author Smart Enigma Techno
 */
public class classTableModel {
    
    private Integer numero;
    private String libelle;
    private String description;
    private Date derniereModif;
    private Classe classe;
    private String delete;

    public classTableModel() {
    }

    public classTableModel(Integer numero, String libelle, String description, Date derniereModif, Classe classe) {
        this.numero = numero;
        this.libelle = libelle;
        this.description = description;
        this.derniereModif = derniereModif;
        this.classe = classe;
        delete="";
    }

    public classTableModel(Integer numero, String libelle, String description) {
        this.numero = numero;
        this.libelle = libelle;
        this.description = description;
        delete="";
    }

    
    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDerniereModif() {
        return derniereModif;
    }

    public void setDerniereModif(Date derniereModif) {
        this.derniereModif = derniereModif;
    }

    public Classe getClasse() {
        return classe;
    }

    public void setClasse(Classe classe) {
        this.classe = classe;
    }
    
    
    
}
