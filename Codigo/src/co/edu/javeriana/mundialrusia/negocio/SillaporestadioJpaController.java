/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.javeriana.mundialrusia.negocio;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import co.edu.javeriana.mundialrusia.datos.Estadio;
import co.edu.javeriana.mundialrusia.datos.Silla;
import co.edu.javeriana.mundialrusia.datos.Sillaporestadio;
import co.edu.javeriana.mundialrusia.datos.SillaporestadioPK;
import co.edu.javeriana.mundialrusia.negocio.exceptions.NonexistentEntityException;
import co.edu.javeriana.mundialrusia.negocio.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author nicolasmiranda
 */
public class SillaporestadioJpaController implements Serializable {

    public SillaporestadioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sillaporestadio sillaporestadio) throws PreexistingEntityException, Exception {
        if (sillaporestadio.getSillaporestadioPK() == null) {
            sillaporestadio.setSillaporestadioPK(new SillaporestadioPK());
        }
        sillaporestadio.getSillaporestadioPK().setFila(sillaporestadio.getSilla().getSillaPK().getFila());
        sillaporestadio.getSillaporestadioPK().setIdestadio(sillaporestadio.getEstadio().getIdestadio());
        sillaporestadio.getSillaporestadioPK().setIdsilla(sillaporestadio.getSilla().getSillaPK().getIdsilla());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estadio estadio = sillaporestadio.getEstadio();
            if (estadio != null) {
                estadio = em.getReference(estadio.getClass(), estadio.getIdestadio());
                sillaporestadio.setEstadio(estadio);
            }
            Silla silla = sillaporestadio.getSilla();
            if (silla != null) {
                silla = em.getReference(silla.getClass(), silla.getSillaPK());
                sillaporestadio.setSilla(silla);
            }
            em.persist(sillaporestadio);
            if (estadio != null) {
                estadio.getSillaporestadioList().add(sillaporestadio);
                estadio = em.merge(estadio);
            }
            if (silla != null) {
                silla.getSillaporestadioList().add(sillaporestadio);
                silla = em.merge(silla);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSillaporestadio(sillaporestadio.getSillaporestadioPK()) != null) {
                throw new PreexistingEntityException("Sillaporestadio " + sillaporestadio + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Sillaporestadio sillaporestadio) throws NonexistentEntityException, Exception {
        sillaporestadio.getSillaporestadioPK().setFila(sillaporestadio.getSilla().getSillaPK().getFila());
        sillaporestadio.getSillaporestadioPK().setIdestadio(sillaporestadio.getEstadio().getIdestadio());
        sillaporestadio.getSillaporestadioPK().setIdsilla(sillaporestadio.getSilla().getSillaPK().getIdsilla());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sillaporestadio persistentSillaporestadio = em.find(Sillaporestadio.class, sillaporestadio.getSillaporestadioPK());
            Estadio estadioOld = persistentSillaporestadio.getEstadio();
            Estadio estadioNew = sillaporestadio.getEstadio();
            Silla sillaOld = persistentSillaporestadio.getSilla();
            Silla sillaNew = sillaporestadio.getSilla();
            if (estadioNew != null) {
                estadioNew = em.getReference(estadioNew.getClass(), estadioNew.getIdestadio());
                sillaporestadio.setEstadio(estadioNew);
            }
            if (sillaNew != null) {
                sillaNew = em.getReference(sillaNew.getClass(), sillaNew.getSillaPK());
                sillaporestadio.setSilla(sillaNew);
            }
            sillaporestadio = em.merge(sillaporestadio);
            if (estadioOld != null && !estadioOld.equals(estadioNew)) {
                estadioOld.getSillaporestadioList().remove(sillaporestadio);
                estadioOld = em.merge(estadioOld);
            }
            if (estadioNew != null && !estadioNew.equals(estadioOld)) {
                estadioNew.getSillaporestadioList().add(sillaporestadio);
                estadioNew = em.merge(estadioNew);
            }
            if (sillaOld != null && !sillaOld.equals(sillaNew)) {
                sillaOld.getSillaporestadioList().remove(sillaporestadio);
                sillaOld = em.merge(sillaOld);
            }
            if (sillaNew != null && !sillaNew.equals(sillaOld)) {
                sillaNew.getSillaporestadioList().add(sillaporestadio);
                sillaNew = em.merge(sillaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                SillaporestadioPK id = sillaporestadio.getSillaporestadioPK();
                if (findSillaporestadio(id) == null) {
                    throw new NonexistentEntityException("The sillaporestadio with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(SillaporestadioPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sillaporestadio sillaporestadio;
            try {
                sillaporestadio = em.getReference(Sillaporestadio.class, id);
                sillaporestadio.getSillaporestadioPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sillaporestadio with id " + id + " no longer exists.", enfe);
            }
            Estadio estadio = sillaporestadio.getEstadio();
            if (estadio != null) {
                estadio.getSillaporestadioList().remove(sillaporestadio);
                estadio = em.merge(estadio);
            }
            Silla silla = sillaporestadio.getSilla();
            if (silla != null) {
                silla.getSillaporestadioList().remove(sillaporestadio);
                silla = em.merge(silla);
            }
            em.remove(sillaporestadio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Sillaporestadio> findSillaporestadioEntities() {
        return findSillaporestadioEntities(true, -1, -1);
    }

    public List<Sillaporestadio> findSillaporestadioEntities(int maxResults, int firstResult) {
        return findSillaporestadioEntities(false, maxResults, firstResult);
    }

    private List<Sillaporestadio> findSillaporestadioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sillaporestadio.class));
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

    public Sillaporestadio findSillaporestadio(SillaporestadioPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sillaporestadio.class, id);
        } finally {
            em.close();
        }
    }

    public int getSillaporestadioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sillaporestadio> rt = cq.from(Sillaporestadio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
