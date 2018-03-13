/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Smart Enigma Techno
 */
@Entity
@Table(name = "etudiant")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Etudiant.findAll", query = "SELECT e FROM Etudiant e")
    , @NamedQuery(name = "Etudiant.findById", query = "SELECT e FROM Etudiant e WHERE e.id = :id")
    , @NamedQuery(name = "Etudiant.findByNom", query = "SELECT e FROM Etudiant e WHERE e.nom = :nom")
    , @NamedQuery(name = "Etudiant.findByPrenom", query = "SELECT e FROM Etudiant e WHERE e.prenom = :prenom")
    , @NamedQuery(name = "Etudiant.findByMatricule", query = "SELECT e FROM Etudiant e WHERE e.matricule = :matricule")
    , @NamedQuery(name = "Etudiant.findByDateNaissance", query = "SELECT e FROM Etudiant e WHERE e.dateNaissance = :dateNaissance")
    , @NamedQuery(name = "Etudiant.findByLieuNaissance", query = "SELECT e FROM Etudiant e WHERE e.lieuNaissance = :lieuNaissance")
    , @NamedQuery(name = "Etudiant.findByDerniereModif", query = "SELECT e FROM Etudiant e WHERE e.derniereModif = :derniereModif")})
public class Etudiant implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nom")
    private String nom;
    @Column(name = "prenom")
    private String prenom;
    @Column(name = "matricule")
    private String matricule;
    @Column(name = "dateNaissance")
    @Temporal(TemporalType.DATE)
    private Date dateNaissance;
    @Column(name = "lieuNaissance")
    private String lieuNaissance;
    @Column(name = "derniereModif")
    @Temporal(TemporalType.TIME)
    private Date derniereModif;
    @OneToMany(mappedBy = "etudiant")
    private Collection<Inscription> inscriptionCollection;

    public Etudiant() {
    }

    public Etudiant(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getLieuNaissance() {
        return lieuNaissance;
    }

    public void setLieuNaissance(String lieuNaissance) {
        this.lieuNaissance = lieuNaissance;
    }

    public Date getDerniereModif() {
        return derniereModif;
    }

    public void setDerniereModif(Date derniereModif) {
        this.derniereModif = derniereModif;
    }

    @XmlTransient
    public Collection<Inscription> getInscriptionCollection() {
        return inscriptionCollection;
    }

    public void setInscriptionCollection(Collection<Inscription> inscriptionCollection) {
        this.inscriptionCollection = inscriptionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Etudiant)) {
            return false;
        }
        Etudiant other = (Etudiant) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Etudiant[ id=" + id + " ]";
    }
    
}
