/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Smart Enigma Techno
 */
@Entity
@Table(name = "Inscription")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Inscription.findAll", query = "SELECT i FROM Inscription i")
    , @NamedQuery(name = "Inscription.findById", query = "SELECT i FROM Inscription i WHERE i.id = :id")
    , @NamedQuery(name = "Inscription.findByAnnee", query = "SELECT i FROM Inscription i WHERE i.annee = :annee")
    , @NamedQuery(name = "Inscription.findByDerniereModif", query = "SELECT i FROM Inscription i WHERE i.derniereModif = :derniereModif")})
public class Inscription implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "annee")
    private String annee;
    @Column(name = "derniereModif")
    @Temporal(TemporalType.TIMESTAMP)
    private Date derniereModif;
    @JoinColumn(name = "classe", referencedColumnName = "id")
    @ManyToOne
    private Classe classe;
    @JoinColumn(name = "etudiant", referencedColumnName = "id")
    @ManyToOne
    private Etudiant etudiant;

    public Inscription() {
    }

    public Inscription(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAnnee() {
        return annee;
    }

    public void setAnnee(String annee) {
        this.annee = annee;
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

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
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
        if (!(object instanceof Inscription)) {
            return false;
        }
        Inscription other = (Inscription) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Inscription[ id=" + id + " ]";
    }
    
}
