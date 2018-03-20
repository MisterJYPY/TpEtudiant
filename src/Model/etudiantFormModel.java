/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Entities.Etudiant;
import java.util.Date;

/**
 *
 * @author Smart Enigma Techno
 */
public class etudiantFormModel {
    
    private Integer numero;
    private String nom;
    private String prenoms;
    private Date dateN;
    private String lieuN;
    private Date derniereModif;
    private String contact;
    private String adresse;
    private String matricule;
    private String modif;
    private String del;
    private Etudiant etudiant;
    
    public etudiantFormModel(Integer numero, String nom, String prenoms, Date dateN, String lieuN, Date derniereModif, String contact, String adresse) {
        this.numero = numero;
        this.nom = nom;
        this.prenoms = prenoms;
        this.dateN = dateN;
        this.lieuN = lieuN;
        this.derniereModif = derniereModif;
        this.contact = contact;
        this.adresse = adresse;
    }

    public etudiantFormModel(Integer numero, String nom, String prenoms, Date dateN, String lieuN, String adresse) {
        this.numero = numero;
        this.nom = nom;
        this.prenoms = prenoms;
        this.dateN = dateN;
        this.lieuN = lieuN;
        this.adresse = adresse;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public String getModif() {
        return modif;
    }

    public void setModif(String modif) {
        this.modif = modif;
    }

    public String getDel() {
        return del;
    }

    public void setDel(String del) {
        this.del = del;
    }

    
    public etudiantFormModel() {
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }
    
    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenoms() {
        return prenoms;
    }

    public void setPrenoms(String prenoms) {
        this.prenoms = prenoms;
    }

    public Date getDateN() {
        return dateN;
    }

    public void setDateN(Date dateN) {
        this.dateN = dateN;
    }

    public String getLieuN() {
        return lieuN;
    }

    public void setLieuN(String lieuN) {
        this.lieuN = lieuN;
    }

    public Date getDerniereModif() {
        return derniereModif;
    }

    public void setDerniereModif(Date derniereModif) {
        this.derniereModif = derniereModif;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
    
    
    
}
