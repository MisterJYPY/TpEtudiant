/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Entities.Classe;
import Entities.Etudiant;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entities.Inscription;
import controller.jpa.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Smart Enigma Techno
 */
public class EtudiantJpaController implements Serializable {

    public EtudiantJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Etudiant etudiant) {
        if (etudiant.getInscriptionCollection() == null) {
            etudiant.setInscriptionCollection(new ArrayList<Inscription>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Inscription> attachedInscriptionCollection = new ArrayList<Inscription>();
            for (Inscription inscriptionCollectionInscriptionToAttach : etudiant.getInscriptionCollection()) {
                inscriptionCollectionInscriptionToAttach = em.getReference(inscriptionCollectionInscriptionToAttach.getClass(), inscriptionCollectionInscriptionToAttach.getId());
                attachedInscriptionCollection.add(inscriptionCollectionInscriptionToAttach);
            }
            etudiant.setInscriptionCollection(attachedInscriptionCollection);
            em.persist(etudiant);
            for (Inscription inscriptionCollectionInscription : etudiant.getInscriptionCollection()) {
                Etudiant oldEtudiantOfInscriptionCollectionInscription = inscriptionCollectionInscription.getEtudiant();
                inscriptionCollectionInscription.setEtudiant(etudiant);
                inscriptionCollectionInscription = em.merge(inscriptionCollectionInscription);
                if (oldEtudiantOfInscriptionCollectionInscription != null) {
                    oldEtudiantOfInscriptionCollectionInscription.getInscriptionCollection().remove(inscriptionCollectionInscription);
                    oldEtudiantOfInscriptionCollectionInscription = em.merge(oldEtudiantOfInscriptionCollectionInscription);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Etudiant etudiant) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Etudiant persistentEtudiant = em.find(Etudiant.class, etudiant.getId());
            Collection<Inscription> inscriptionCollectionOld = persistentEtudiant.getInscriptionCollection();
            Collection<Inscription> inscriptionCollectionNew = etudiant.getInscriptionCollection();
            Collection<Inscription> attachedInscriptionCollectionNew = new ArrayList<Inscription>();
            for (Inscription inscriptionCollectionNewInscriptionToAttach : inscriptionCollectionNew) {
                inscriptionCollectionNewInscriptionToAttach = em.getReference(inscriptionCollectionNewInscriptionToAttach.getClass(), inscriptionCollectionNewInscriptionToAttach.getId());
                attachedInscriptionCollectionNew.add(inscriptionCollectionNewInscriptionToAttach);
            }
            inscriptionCollectionNew = attachedInscriptionCollectionNew;
            etudiant.setInscriptionCollection(inscriptionCollectionNew);
            etudiant = em.merge(etudiant);
            for (Inscription inscriptionCollectionOldInscription : inscriptionCollectionOld) {
                if (!inscriptionCollectionNew.contains(inscriptionCollectionOldInscription)) {
                    inscriptionCollectionOldInscription.setEtudiant(null);
                    inscriptionCollectionOldInscription = em.merge(inscriptionCollectionOldInscription);
                }
            }
            for (Inscription inscriptionCollectionNewInscription : inscriptionCollectionNew) {
                if (!inscriptionCollectionOld.contains(inscriptionCollectionNewInscription)) {
                    Etudiant oldEtudiantOfInscriptionCollectionNewInscription = inscriptionCollectionNewInscription.getEtudiant();
                    inscriptionCollectionNewInscription.setEtudiant(etudiant);
                    inscriptionCollectionNewInscription = em.merge(inscriptionCollectionNewInscription);
                    if (oldEtudiantOfInscriptionCollectionNewInscription != null && !oldEtudiantOfInscriptionCollectionNewInscription.equals(etudiant)) {
                        oldEtudiantOfInscriptionCollectionNewInscription.getInscriptionCollection().remove(inscriptionCollectionNewInscription);
                        oldEtudiantOfInscriptionCollectionNewInscription = em.merge(oldEtudiantOfInscriptionCollectionNewInscription);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = etudiant.getId();
                if (findEtudiant(id) == null) {
                    throw new NonexistentEntityException("The etudiant with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Etudiant etudiant;
            try {
                etudiant = em.getReference(Etudiant.class, id);
                etudiant.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The etudiant with id " + id + " no longer exists.", enfe);
            }
            Collection<Inscription> inscriptionCollection = etudiant.getInscriptionCollection();
            for (Inscription inscriptionCollectionInscription : inscriptionCollection) {
                inscriptionCollectionInscription.setEtudiant(null);
                inscriptionCollectionInscription = em.merge(inscriptionCollectionInscription);
            }
            em.remove(etudiant);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Etudiant> findEtudiantEntities() {
        return findEtudiantEntities(true, -1, -1);
    }

    public List<Etudiant> findEtudiantEntities(int maxResults, int firstResult) {
        return findEtudiantEntities(false, maxResults, firstResult);
    }
      public ObservableList<etudiantFormModel> listTableModel()
    {
        List<Etudiant> list=findEtudiantEntities();
        ObservableList<etudiantFormModel> listObs=FXCollections.observableArrayList();
       int cpt=1;
        for (Iterator<Etudiant> iterator = list.iterator(); iterator.hasNext();) {
              Etudiant next = iterator.next();
              etudiantFormModel mdl=new etudiantFormModel();
              mdl.setNumero(cpt);
              mdl.setMatricule(next.getMatricule());
              mdl.setDateN(next.getDateNaissance());
              mdl.setLieuN(next.getLieuNaissance());
              mdl.setPrenoms(next.getPrenom());
              mdl.setNom(next.getNom());
              mdl.setDerniereModif(next.getDerniereModif());
              mdl.setEtudiant(next);
              listObs.add(mdl);
//             mdl.setC
//             listObs.add(mdl);
               cpt++;
        }
      return listObs;
    }
    private List<Etudiant> findEtudiantEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Etudiant.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Etudiant findEtudiant(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Etudiant.class, id);
        } finally {
            em.close();
        }
    }

    public int getEtudiantCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Etudiant> rt = cq.from(Etudiant.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
