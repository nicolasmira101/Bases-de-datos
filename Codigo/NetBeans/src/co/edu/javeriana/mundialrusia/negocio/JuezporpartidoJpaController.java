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
import co.edu.javeriana.mundialrusia.datos.Juez;
import co.edu.javeriana.mundialrusia.datos.Juezporpartido;
import co.edu.javeriana.mundialrusia.datos.JuezporpartidoPK;
import co.edu.javeriana.mundialrusia.datos.Partido;
import co.edu.javeriana.mundialrusia.negocio.exceptions.NonexistentEntityException;
import co.edu.javeriana.mundialrusia.negocio.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author nicolasmiranda
 */
public class JuezporpartidoJpaController implements Serializable {

    public JuezporpartidoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Juezporpartido juezporpartido) throws PreexistingEntityException, Exception {
        if (juezporpartido.getJuezporpartidoPK() == null) {
            juezporpartido.setJuezporpartidoPK(new JuezporpartidoPK());
        }
        juezporpartido.getJuezporpartidoPK().setIdjuez(juezporpartido.getJuez().getIdjuez());
        juezporpartido.getJuezporpartidoPK().setIdpartido(juezporpartido.getPartido().getIdpartido());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Juez juez = juezporpartido.getJuez();
            if (juez != null) {
                juez = em.getReference(juez.getClass(), juez.getIdjuez());
                juezporpartido.setJuez(juez);
            }
            Partido partido = juezporpartido.getPartido();
            if (partido != null) {
                partido = em.getReference(partido.getClass(), partido.getIdpartido());
                juezporpartido.setPartido(partido);
            }
            em.persist(juezporpartido);
            if (juez != null) {
                juez.getJuezporpartidoList().add(juezporpartido);
                juez = em.merge(juez);
            }
            if (partido != null) {
                partido.getJuezporpartidoList().add(juezporpartido);
                partido = em.merge(partido);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findJuezporpartido(juezporpartido.getJuezporpartidoPK()) != null) {
                throw new PreexistingEntityException("Juezporpartido " + juezporpartido + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Juezporpartido juezporpartido) throws NonexistentEntityException, Exception {
        juezporpartido.getJuezporpartidoPK().setIdjuez(juezporpartido.getJuez().getIdjuez());
        juezporpartido.getJuezporpartidoPK().setIdpartido(juezporpartido.getPartido().getIdpartido());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Juezporpartido persistentJuezporpartido = em.find(Juezporpartido.class, juezporpartido.getJuezporpartidoPK());
            Juez juezOld = persistentJuezporpartido.getJuez();
            Juez juezNew = juezporpartido.getJuez();
            Partido partidoOld = persistentJuezporpartido.getPartido();
            Partido partidoNew = juezporpartido.getPartido();
            if (juezNew != null) {
                juezNew = em.getReference(juezNew.getClass(), juezNew.getIdjuez());
                juezporpartido.setJuez(juezNew);
            }
            if (partidoNew != null) {
                partidoNew = em.getReference(partidoNew.getClass(), partidoNew.getIdpartido());
                juezporpartido.setPartido(partidoNew);
            }
            juezporpartido = em.merge(juezporpartido);
            if (juezOld != null && !juezOld.equals(juezNew)) {
                juezOld.getJuezporpartidoList().remove(juezporpartido);
                juezOld = em.merge(juezOld);
            }
            if (juezNew != null && !juezNew.equals(juezOld)) {
                juezNew.getJuezporpartidoList().add(juezporpartido);
                juezNew = em.merge(juezNew);
            }
            if (partidoOld != null && !partidoOld.equals(partidoNew)) {
                partidoOld.getJuezporpartidoList().remove(juezporpartido);
                partidoOld = em.merge(partidoOld);
            }
            if (partidoNew != null && !partidoNew.equals(partidoOld)) {
                partidoNew.getJuezporpartidoList().add(juezporpartido);
                partidoNew = em.merge(partidoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                JuezporpartidoPK id = juezporpartido.getJuezporpartidoPK();
                if (findJuezporpartido(id) == null) {
                    throw new NonexistentEntityException("The juezporpartido with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(JuezporpartidoPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Juezporpartido juezporpartido;
            try {
                juezporpartido = em.getReference(Juezporpartido.class, id);
                juezporpartido.getJuezporpartidoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The juezporpartido with id " + id + " no longer exists.", enfe);
            }
            Juez juez = juezporpartido.getJuez();
            if (juez != null) {
                juez.getJuezporpartidoList().remove(juezporpartido);
                juez = em.merge(juez);
            }
            Partido partido = juezporpartido.getPartido();
            if (partido != null) {
                partido.getJuezporpartidoList().remove(juezporpartido);
                partido = em.merge(partido);
            }
            em.remove(juezporpartido);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Juezporpartido> findJuezporpartidoEntities() {
        return findJuezporpartidoEntities(true, -1, -1);
    }

    public List<Juezporpartido> findJuezporpartidoEntities(int maxResults, int firstResult) {
        return findJuezporpartidoEntities(false, maxResults, firstResult);
    }

    private List<Juezporpartido> findJuezporpartidoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Juezporpartido.class));
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

    public Juezporpartido findJuezporpartido(JuezporpartidoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Juezporpartido.class, id);
        } finally {
            em.close();
        }
    }

    public int getJuezporpartidoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Juezporpartido> rt = cq.from(Juezporpartido.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
