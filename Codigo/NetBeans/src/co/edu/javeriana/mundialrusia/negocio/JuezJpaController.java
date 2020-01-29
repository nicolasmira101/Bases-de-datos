/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.javeriana.mundialrusia.negocio;

import co.edu.javeriana.mundialrusia.datos.Juez;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import co.edu.javeriana.mundialrusia.datos.Juezporpartido;
import co.edu.javeriana.mundialrusia.negocio.exceptions.IllegalOrphanException;
import co.edu.javeriana.mundialrusia.negocio.exceptions.NonexistentEntityException;
import co.edu.javeriana.mundialrusia.negocio.exceptions.PreexistingEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author nicolasmiranda
 */
public class JuezJpaController implements Serializable {

    public JuezJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Juez juez) throws PreexistingEntityException, Exception {
        if (juez.getJuezporpartidoList() == null) {
            juez.setJuezporpartidoList(new ArrayList<Juezporpartido>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Juezporpartido> attachedJuezporpartidoList = new ArrayList<Juezporpartido>();
            for (Juezporpartido juezporpartidoListJuezporpartidoToAttach : juez.getJuezporpartidoList()) {
                juezporpartidoListJuezporpartidoToAttach = em.getReference(juezporpartidoListJuezporpartidoToAttach.getClass(), juezporpartidoListJuezporpartidoToAttach.getJuezporpartidoPK());
                attachedJuezporpartidoList.add(juezporpartidoListJuezporpartidoToAttach);
            }
            juez.setJuezporpartidoList(attachedJuezporpartidoList);
            em.persist(juez);
            for (Juezporpartido juezporpartidoListJuezporpartido : juez.getJuezporpartidoList()) {
                Juez oldJuezOfJuezporpartidoListJuezporpartido = juezporpartidoListJuezporpartido.getJuez();
                juezporpartidoListJuezporpartido.setJuez(juez);
                juezporpartidoListJuezporpartido = em.merge(juezporpartidoListJuezporpartido);
                if (oldJuezOfJuezporpartidoListJuezporpartido != null) {
                    oldJuezOfJuezporpartidoListJuezporpartido.getJuezporpartidoList().remove(juezporpartidoListJuezporpartido);
                    oldJuezOfJuezporpartidoListJuezporpartido = em.merge(oldJuezOfJuezporpartidoListJuezporpartido);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findJuez(juez.getIdjuez()) != null) {
                throw new PreexistingEntityException("Juez " + juez + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Juez juez) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Juez persistentJuez = em.find(Juez.class, juez.getIdjuez());
            List<Juezporpartido> juezporpartidoListOld = persistentJuez.getJuezporpartidoList();
            List<Juezporpartido> juezporpartidoListNew = juez.getJuezporpartidoList();
            List<String> illegalOrphanMessages = null;
            for (Juezporpartido juezporpartidoListOldJuezporpartido : juezporpartidoListOld) {
                if (!juezporpartidoListNew.contains(juezporpartidoListOldJuezporpartido)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Juezporpartido " + juezporpartidoListOldJuezporpartido + " since its juez field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Juezporpartido> attachedJuezporpartidoListNew = new ArrayList<Juezporpartido>();
            for (Juezporpartido juezporpartidoListNewJuezporpartidoToAttach : juezporpartidoListNew) {
                juezporpartidoListNewJuezporpartidoToAttach = em.getReference(juezporpartidoListNewJuezporpartidoToAttach.getClass(), juezporpartidoListNewJuezporpartidoToAttach.getJuezporpartidoPK());
                attachedJuezporpartidoListNew.add(juezporpartidoListNewJuezporpartidoToAttach);
            }
            juezporpartidoListNew = attachedJuezporpartidoListNew;
            juez.setJuezporpartidoList(juezporpartidoListNew);
            juez = em.merge(juez);
            for (Juezporpartido juezporpartidoListNewJuezporpartido : juezporpartidoListNew) {
                if (!juezporpartidoListOld.contains(juezporpartidoListNewJuezporpartido)) {
                    Juez oldJuezOfJuezporpartidoListNewJuezporpartido = juezporpartidoListNewJuezporpartido.getJuez();
                    juezporpartidoListNewJuezporpartido.setJuez(juez);
                    juezporpartidoListNewJuezporpartido = em.merge(juezporpartidoListNewJuezporpartido);
                    if (oldJuezOfJuezporpartidoListNewJuezporpartido != null && !oldJuezOfJuezporpartidoListNewJuezporpartido.equals(juez)) {
                        oldJuezOfJuezporpartidoListNewJuezporpartido.getJuezporpartidoList().remove(juezporpartidoListNewJuezporpartido);
                        oldJuezOfJuezporpartidoListNewJuezporpartido = em.merge(oldJuezOfJuezporpartidoListNewJuezporpartido);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Short id = juez.getIdjuez();
                if (findJuez(id) == null) {
                    throw new NonexistentEntityException("The juez with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Short id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Juez juez;
            try {
                juez = em.getReference(Juez.class, id);
                juez.getIdjuez();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The juez with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Juezporpartido> juezporpartidoListOrphanCheck = juez.getJuezporpartidoList();
            for (Juezporpartido juezporpartidoListOrphanCheckJuezporpartido : juezporpartidoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Juez (" + juez + ") cannot be destroyed since the Juezporpartido " + juezporpartidoListOrphanCheckJuezporpartido + " in its juezporpartidoList field has a non-nullable juez field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(juez);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Juez> findJuezEntities() {
        return findJuezEntities(true, -1, -1);
    }

    public List<Juez> findJuezEntities(int maxResults, int firstResult) {
        return findJuezEntities(false, maxResults, firstResult);
    }

    private List<Juez> findJuezEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Juez.class));
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

    public Juez findJuez(Short id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Juez.class, id);
        } finally {
            em.close();
        }
    }

    public int getJuezCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Juez> rt = cq.from(Juez.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
