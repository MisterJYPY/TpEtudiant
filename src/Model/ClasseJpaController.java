/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Entities.Classe;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entities.Inscription;
import controller.jpa.exceptions.NonexistentEntityException;
import java.sql.Date;
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
public class ClasseJpaController implements Serializable {

    public ClasseJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Classe classe) {
        if (classe.getInscriptionCollection() == null) {
            classe.setInscriptionCollection(new ArrayList<Inscription>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Inscription> attachedInscriptionCollection = new ArrayList<Inscription>();
            for (Inscription inscriptionCollectionInscriptionToAttach : classe.getInscriptionCollection()) {
                inscriptionCollectionInscriptionToAttach = em.getReference(inscriptionCollectionInscriptionToAttach.getClass(), inscriptionCollectionInscriptionToAttach.getId());
                attachedInscriptionCollection.add(inscriptionCollectionInscriptionToAttach);
            }
            classe.setInscriptionCollection(attachedInscriptionCollection);
            em.persist(classe);
            for (Inscription inscriptionCollectionInscription : classe.getInscriptionCollection()) {
                Classe oldClasseOfInscriptionCollectionInscription = inscriptionCollectionInscription.getClasse();
                inscriptionCollectionInscription.setClasse(classe);
                inscriptionCollectionInscription = em.merge(inscriptionCollectionInscription);
                if (oldClasseOfInscriptionCollectionInscription != null) {
                    oldClasseOfInscriptionCollectionInscription.getInscriptionCollection().remove(inscriptionCollectionInscription);
                    oldClasseOfInscriptionCollectionInscription = em.merge(oldClasseOfInscriptionCollectionInscription);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Classe classe) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Classe persistentClasse = em.find(Classe.class, classe.getId());
            Collection<Inscription> inscriptionCollectionOld = persistentClasse.getInscriptionCollection();
            Collection<Inscription> inscriptionCollectionNew = classe.getInscriptionCollection();
            Collection<Inscription> attachedInscriptionCollectionNew = new ArrayList<Inscription>();
            for (Inscription inscriptionCollectionNewInscriptionToAttach : inscriptionCollectionNew) {
                inscriptionCollectionNewInscriptionToAttach = em.getReference(inscriptionCollectionNewInscriptionToAttach.getClass(), inscriptionCollectionNewInscriptionToAttach.getId());
                attachedInscriptionCollectionNew.add(inscriptionCollectionNewInscriptionToAttach);
            }
            inscriptionCollectionNew = attachedInscriptionCollectionNew;
            classe.setInscriptionCollection(inscriptionCollectionNew);
            classe = em.merge(classe);
            for (Inscription inscriptionCollectionOldInscription : inscriptionCollectionOld) {
                if (!inscriptionCollectionNew.contains(inscriptionCollectionOldInscription)) {
                    inscriptionCollectionOldInscription.setClasse(null);
                    inscriptionCollectionOldInscription = em.merge(inscriptionCollectionOldInscription);
                }
            }
            for (Inscription inscriptionCollectionNewInscription : inscriptionCollectionNew) {
                if (!inscriptionCollectionOld.contains(inscriptionCollectionNewInscription)) {
                    Classe oldClasseOfInscriptionCollectionNewInscription = inscriptionCollectionNewInscription.getClasse();
                    inscriptionCollectionNewInscription.setClasse(classe);
                    inscriptionCollectionNewInscription = em.merge(inscriptionCollectionNewInscription);
                    if (oldClasseOfInscriptionCollectionNewInscription != null && !oldClasseOfInscriptionCollectionNewInscription.equals(classe)) {
                        oldClasseOfInscriptionCollectionNewInscription.getInscriptionCollection().remove(inscriptionCollectionNewInscription);
                        oldClasseOfInscriptionCollectionNewInscription = em.merge(oldClasseOfInscriptionCollectionNewInscription);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = classe.getId();
                if (findClasse(id) == null) {
                    throw new NonexistentEntityException("The classe with id " + id + " no longer exists.");
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
            Classe classe;
            try {
                classe = em.getReference(Classe.class, id);
                classe.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The classe with id " + id + " no longer exists.", enfe);
            }
            Collection<Inscription> inscriptionCollection = classe.getInscriptionCollection();
            for (Inscription inscriptionCollectionInscription : inscriptionCollection) {
                inscriptionCollectionInscription.setClasse(null);
                inscriptionCollectionInscription = em.merge(inscriptionCollectionInscription);
            }
            em.remove(classe);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Classe> findClasseEntities() {
        return findClasseEntities(true, -1, -1);
    }
 
    public ObservableList<classTableModel> listTableModel()
    {
        List<Classe> list=findClasseEntities();
        ObservableList<classTableModel> listObs=FXCollections.observableArrayList();
       int cpt=1;
        for (Iterator<Classe> iterator = list.iterator(); iterator.hasNext();) {
              Classe next = iterator.next();
               classTableModel mdl=new classTableModel(cpt,next.getLibelle(),next.getDescription(),next.getDerniereModif(),next);
             mdl.setClasse(next);
             listObs.add(mdl);
               cpt++;
        }
      return listObs;
    }
    public List<Classe> findClasseEntities(int maxResults, int firstResult) {
        return findClasseEntities(false, maxResults, firstResult);
    }

    private List<Classe> findClasseEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Classe.class));
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

    public Classe findClasse(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Classe.class, id);
        } finally {
            em.close();
        }
    }

    public int getClasseCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Classe> rt = cq.from(Classe.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
