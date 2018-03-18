/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.jpa;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entities.Classe;
import Entities.Etudiant;
import Entities.Inscription;
import controller.jpa.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Smart Enigma Techno
 */
public class InscriptionJpaController implements Serializable {

    public InscriptionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Inscription inscription) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Classe classe = inscription.getClasse();
            if (classe != null) {
                classe = em.getReference(classe.getClass(), classe.getId());
                inscription.setClasse(classe);
            }
            Etudiant etudiant = inscription.getEtudiant();
            if (etudiant != null) {
                etudiant = em.getReference(etudiant.getClass(), etudiant.getId());
                inscription.setEtudiant(etudiant);
            }
            em.persist(inscription);
            if (classe != null) {
                classe.getInscriptionCollection().add(inscription);
                classe = em.merge(classe);
            }
            if (etudiant != null) {
                etudiant.getInscriptionCollection().add(inscription);
                etudiant = em.merge(etudiant);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Inscription inscription) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Inscription persistentInscription = em.find(Inscription.class, inscription.getId());
            Classe classeOld = persistentInscription.getClasse();
            Classe classeNew = inscription.getClasse();
            Etudiant etudiantOld = persistentInscription.getEtudiant();
            Etudiant etudiantNew = inscription.getEtudiant();
            if (classeNew != null) {
                classeNew = em.getReference(classeNew.getClass(), classeNew.getId());
                inscription.setClasse(classeNew);
            }
            if (etudiantNew != null) {
                etudiantNew = em.getReference(etudiantNew.getClass(), etudiantNew.getId());
                inscription.setEtudiant(etudiantNew);
            }
            inscription = em.merge(inscription);
            if (classeOld != null && !classeOld.equals(classeNew)) {
                classeOld.getInscriptionCollection().remove(inscription);
                classeOld = em.merge(classeOld);
            }
            if (classeNew != null && !classeNew.equals(classeOld)) {
                classeNew.getInscriptionCollection().add(inscription);
                classeNew = em.merge(classeNew);
            }
            if (etudiantOld != null && !etudiantOld.equals(etudiantNew)) {
                etudiantOld.getInscriptionCollection().remove(inscription);
                etudiantOld = em.merge(etudiantOld);
            }
            if (etudiantNew != null && !etudiantNew.equals(etudiantOld)) {
                etudiantNew.getInscriptionCollection().add(inscription);
                etudiantNew = em.merge(etudiantNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = inscription.getId();
                if (findInscription(id) == null) {
                    throw new NonexistentEntityException("The inscription with id " + id + " no longer exists.");
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
            Inscription inscription;
            try {
                inscription = em.getReference(Inscription.class, id);
                inscription.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The inscription with id " + id + " no longer exists.", enfe);
            }
            Classe classe = inscription.getClasse();
            if (classe != null) {
                classe.getInscriptionCollection().remove(inscription);
                classe = em.merge(classe);
            }
            Etudiant etudiant = inscription.getEtudiant();
            if (etudiant != null) {
                etudiant.getInscriptionCollection().remove(inscription);
                etudiant = em.merge(etudiant);
            }
            em.remove(inscription);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Inscription> findInscriptionEntities() {
        return findInscriptionEntities(true, -1, -1);
    }

    public List<Inscription> findInscriptionEntities(int maxResults, int firstResult) {
        return findInscriptionEntities(false, maxResults, firstResult);
    }

    private List<Inscription> findInscriptionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Inscription.class));
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

    public Inscription findInscription(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Inscription.class, id);
        } finally {
            em.close();
        }
    }

    public int getInscriptionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Inscription> rt = cq.from(Inscription.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
