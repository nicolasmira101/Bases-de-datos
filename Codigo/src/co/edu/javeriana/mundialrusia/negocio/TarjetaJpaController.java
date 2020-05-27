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
import co.edu.javeriana.mundialrusia.datos.Jugador;
import co.edu.javeriana.mundialrusia.datos.Partido;
import co.edu.javeriana.mundialrusia.datos.Tarjeta;
import co.edu.javeriana.mundialrusia.negocio.exceptions.NonexistentEntityException;
import co.edu.javeriana.mundialrusia.negocio.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author nicolasmiranda
 */
public class TarjetaJpaController implements Serializable {

    public TarjetaJpaController() {
        this.emf = Persistence.createEntityManagerFactory("MundialRusiaPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tarjeta tarjeta) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Jugador idjugador = tarjeta.getIdjugador();
            if (idjugador != null) {
                idjugador = em.getReference(idjugador.getClass(), idjugador.getIdjugador());
                tarjeta.setIdjugador(idjugador);
            }
            Partido idpartido = tarjeta.getIdpartido();
            if (idpartido != null) {
                idpartido = em.getReference(idpartido.getClass(), idpartido.getIdpartido());
                tarjeta.setIdpartido(idpartido);
            }
            em.persist(tarjeta);
            if (idjugador != null) {
                idjugador.getTarjetaList().add(tarjeta);
                idjugador = em.merge(idjugador);
            }
            if (idpartido != null) {
                idpartido.getTarjetaList().add(tarjeta);
                idpartido = em.merge(idpartido);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTarjeta(tarjeta.getIdtarjeta()) != null) {
                throw new PreexistingEntityException("Tarjeta " + tarjeta + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tarjeta tarjeta) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tarjeta persistentTarjeta = em.find(Tarjeta.class, tarjeta.getIdtarjeta());
            Jugador idjugadorOld = persistentTarjeta.getIdjugador();
            Jugador idjugadorNew = tarjeta.getIdjugador();
            Partido idpartidoOld = persistentTarjeta.getIdpartido();
            Partido idpartidoNew = tarjeta.getIdpartido();
            if (idjugadorNew != null) {
                idjugadorNew = em.getReference(idjugadorNew.getClass(), idjugadorNew.getIdjugador());
                tarjeta.setIdjugador(idjugadorNew);
            }
            if (idpartidoNew != null) {
                idpartidoNew = em.getReference(idpartidoNew.getClass(), idpartidoNew.getIdpartido());
                tarjeta.setIdpartido(idpartidoNew);
            }
            tarjeta = em.merge(tarjeta);
            if (idjugadorOld != null && !idjugadorOld.equals(idjugadorNew)) {
                idjugadorOld.getTarjetaList().remove(tarjeta);
                idjugadorOld = em.merge(idjugadorOld);
            }
            if (idjugadorNew != null && !idjugadorNew.equals(idjugadorOld)) {
                idjugadorNew.getTarjetaList().add(tarjeta);
                idjugadorNew = em.merge(idjugadorNew);
            }
            if (idpartidoOld != null && !idpartidoOld.equals(idpartidoNew)) {
                idpartidoOld.getTarjetaList().remove(tarjeta);
                idpartidoOld = em.merge(idpartidoOld);
            }
            if (idpartidoNew != null && !idpartidoNew.equals(idpartidoOld)) {
                idpartidoNew.getTarjetaList().add(tarjeta);
                idpartidoNew = em.merge(idpartidoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Short id = tarjeta.getIdtarjeta();
                if (findTarjeta(id) == null) {
                    throw new NonexistentEntityException("The tarjeta with id " + id + " no longer exists.");
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
            Tarjeta tarjeta;
            try {
                tarjeta = em.getReference(Tarjeta.class, id);
                tarjeta.getIdtarjeta();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tarjeta with id " + id + " no longer exists.", enfe);
            }
            Jugador idjugador = tarjeta.getIdjugador();
            if (idjugador != null) {
                idjugador.getTarjetaList().remove(tarjeta);
                idjugador = em.merge(idjugador);
            }
            Partido idpartido = tarjeta.getIdpartido();
            if (idpartido != null) {
                idpartido.getTarjetaList().remove(tarjeta);
                idpartido = em.merge(idpartido);
            }
            em.remove(tarjeta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tarjeta> findTarjetaEntities() {
        return findTarjetaEntities(true, -1, -1);
    }

    public List<Tarjeta> findTarjetaEntities(int maxResults, int firstResult) {
        return findTarjetaEntities(false, maxResults, firstResult);
    }

    private List<Tarjeta> findTarjetaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tarjeta.class));
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

    public Tarjeta findTarjeta(Short id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tarjeta.class, id);
        } finally {
            em.close();
        }
    }

    public int getTarjetaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tarjeta> rt = cq.from(Tarjeta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
