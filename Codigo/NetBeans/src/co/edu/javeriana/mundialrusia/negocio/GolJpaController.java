/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.javeriana.mundialrusia.negocio;

import co.edu.javeriana.mundialrusia.datos.Gol;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import co.edu.javeriana.mundialrusia.datos.Jugador;
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
public class GolJpaController implements Serializable {

    public GolJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Gol gol) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Jugador idjugador = gol.getIdjugador();
            if (idjugador != null) {
                idjugador = em.getReference(idjugador.getClass(), idjugador.getIdjugador());
                gol.setIdjugador(idjugador);
            }
            Partido idpartido = gol.getIdpartido();
            if (idpartido != null) {
                idpartido = em.getReference(idpartido.getClass(), idpartido.getIdpartido());
                gol.setIdpartido(idpartido);
            }
            em.persist(gol);
            if (idjugador != null) {
                idjugador.getGolList().add(gol);
                idjugador = em.merge(idjugador);
            }
            if (idpartido != null) {
                idpartido.getGolList().add(gol);
                idpartido = em.merge(idpartido);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findGol(gol.getIdgol()) != null) {
                throw new PreexistingEntityException("Gol " + gol + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Gol gol) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Gol persistentGol = em.find(Gol.class, gol.getIdgol());
            Jugador idjugadorOld = persistentGol.getIdjugador();
            Jugador idjugadorNew = gol.getIdjugador();
            Partido idpartidoOld = persistentGol.getIdpartido();
            Partido idpartidoNew = gol.getIdpartido();
            if (idjugadorNew != null) {
                idjugadorNew = em.getReference(idjugadorNew.getClass(), idjugadorNew.getIdjugador());
                gol.setIdjugador(idjugadorNew);
            }
            if (idpartidoNew != null) {
                idpartidoNew = em.getReference(idpartidoNew.getClass(), idpartidoNew.getIdpartido());
                gol.setIdpartido(idpartidoNew);
            }
            gol = em.merge(gol);
            if (idjugadorOld != null && !idjugadorOld.equals(idjugadorNew)) {
                idjugadorOld.getGolList().remove(gol);
                idjugadorOld = em.merge(idjugadorOld);
            }
            if (idjugadorNew != null && !idjugadorNew.equals(idjugadorOld)) {
                idjugadorNew.getGolList().add(gol);
                idjugadorNew = em.merge(idjugadorNew);
            }
            if (idpartidoOld != null && !idpartidoOld.equals(idpartidoNew)) {
                idpartidoOld.getGolList().remove(gol);
                idpartidoOld = em.merge(idpartidoOld);
            }
            if (idpartidoNew != null && !idpartidoNew.equals(idpartidoOld)) {
                idpartidoNew.getGolList().add(gol);
                idpartidoNew = em.merge(idpartidoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Short id = gol.getIdgol();
                if (findGol(id) == null) {
                    throw new NonexistentEntityException("The gol with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Short id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Gol gol;
            try {
                gol = em.getReference(Gol.class, id);
                gol.getIdgol();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The gol with id " + id + " no longer exists.", enfe);
            }
            Jugador idjugador = gol.getIdjugador();
            if (idjugador != null) {
                idjugador.getGolList().remove(gol);
                idjugador = em.merge(idjugador);
            }
            Partido idpartido = gol.getIdpartido();
            if (idpartido != null) {
                idpartido.getGolList().remove(gol);
                idpartido = em.merge(idpartido);
            }
            em.remove(gol);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Gol> findGolEntities() {
        return findGolEntities(true, -1, -1);
    }

    public List<Gol> findGolEntities(int maxResults, int firstResult) {
        return findGolEntities(false, maxResults, firstResult);
    }

    private List<Gol> findGolEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Gol.class));
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

    public Gol findGol(Short id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Gol.class, id);
        } finally {
            em.close();
        }
    }

    public int getGolCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Gol> rt = cq.from(Gol.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
