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
import co.edu.javeriana.mundialrusia.datos.Jugadorporpartido;
import co.edu.javeriana.mundialrusia.datos.JugadorporpartidoPK;
import co.edu.javeriana.mundialrusia.datos.Partido;
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
public class JugadorporpartidoJpaController implements Serializable {

    public JugadorporpartidoJpaController() {
        this.emf = Persistence.createEntityManagerFactory("MundialRusiaPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Jugadorporpartido jugadorporpartido) throws PreexistingEntityException, Exception {
        if (jugadorporpartido.getJugadorporpartidoPK() == null) {
            jugadorporpartido.setJugadorporpartidoPK(new JugadorporpartidoPK());
        }
        jugadorporpartido.getJugadorporpartidoPK().setIdjugador(jugadorporpartido.getJugador().getIdjugador());
        jugadorporpartido.getJugadorporpartidoPK().setIdpartido(jugadorporpartido.getPartido().getIdpartido());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Jugador jugador = jugadorporpartido.getJugador();
            if (jugador != null) {
                jugador = em.getReference(jugador.getClass(), jugador.getIdjugador());
                jugadorporpartido.setJugador(jugador);
            }
            Partido partido = jugadorporpartido.getPartido();
            if (partido != null) {
                partido = em.getReference(partido.getClass(), partido.getIdpartido());
                jugadorporpartido.setPartido(partido);
            }
            em.persist(jugadorporpartido);
            if (jugador != null) {
                jugador.getJugadorporpartidoList().add(jugadorporpartido);
                jugador = em.merge(jugador);
            }
            if (partido != null) {
                partido.getJugadorporpartidoList().add(jugadorporpartido);
                partido = em.merge(partido);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findJugadorporpartido(jugadorporpartido.getJugadorporpartidoPK()) != null) {
                throw new PreexistingEntityException("Jugadorporpartido " + jugadorporpartido + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Jugadorporpartido jugadorporpartido) throws NonexistentEntityException, Exception {
        jugadorporpartido.getJugadorporpartidoPK().setIdjugador(jugadorporpartido.getJugador().getIdjugador());
        jugadorporpartido.getJugadorporpartidoPK().setIdpartido(jugadorporpartido.getPartido().getIdpartido());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Jugadorporpartido persistentJugadorporpartido = em.find(Jugadorporpartido.class, jugadorporpartido.getJugadorporpartidoPK());
            Jugador jugadorOld = persistentJugadorporpartido.getJugador();
            Jugador jugadorNew = jugadorporpartido.getJugador();
            Partido partidoOld = persistentJugadorporpartido.getPartido();
            Partido partidoNew = jugadorporpartido.getPartido();
            if (jugadorNew != null) {
                jugadorNew = em.getReference(jugadorNew.getClass(), jugadorNew.getIdjugador());
                jugadorporpartido.setJugador(jugadorNew);
            }
            if (partidoNew != null) {
                partidoNew = em.getReference(partidoNew.getClass(), partidoNew.getIdpartido());
                jugadorporpartido.setPartido(partidoNew);
            }
            jugadorporpartido = em.merge(jugadorporpartido);
            if (jugadorOld != null && !jugadorOld.equals(jugadorNew)) {
                jugadorOld.getJugadorporpartidoList().remove(jugadorporpartido);
                jugadorOld = em.merge(jugadorOld);
            }
            if (jugadorNew != null && !jugadorNew.equals(jugadorOld)) {
                jugadorNew.getJugadorporpartidoList().add(jugadorporpartido);
                jugadorNew = em.merge(jugadorNew);
            }
            if (partidoOld != null && !partidoOld.equals(partidoNew)) {
                partidoOld.getJugadorporpartidoList().remove(jugadorporpartido);
                partidoOld = em.merge(partidoOld);
            }
            if (partidoNew != null && !partidoNew.equals(partidoOld)) {
                partidoNew.getJugadorporpartidoList().add(jugadorporpartido);
                partidoNew = em.merge(partidoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                JugadorporpartidoPK id = jugadorporpartido.getJugadorporpartidoPK();
                if (findJugadorporpartido(id) == null) {
                    throw new NonexistentEntityException("The jugadorporpartido with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(JugadorporpartidoPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Jugadorporpartido jugadorporpartido;
            try {
                jugadorporpartido = em.getReference(Jugadorporpartido.class, id);
                jugadorporpartido.getJugadorporpartidoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The jugadorporpartido with id " + id + " no longer exists.", enfe);
            }
            Jugador jugador = jugadorporpartido.getJugador();
            if (jugador != null) {
                jugador.getJugadorporpartidoList().remove(jugadorporpartido);
                jugador = em.merge(jugador);
            }
            Partido partido = jugadorporpartido.getPartido();
            if (partido != null) {
                partido.getJugadorporpartidoList().remove(jugadorporpartido);
                partido = em.merge(partido);
            }
            em.remove(jugadorporpartido);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Jugadorporpartido> findJugadorporpartidoEntities() {
        return findJugadorporpartidoEntities(true, -1, -1);
    }

    public List<Jugadorporpartido> findJugadorporpartidoEntities(int maxResults, int firstResult) {
        return findJugadorporpartidoEntities(false, maxResults, firstResult);
    }

    private List<Jugadorporpartido> findJugadorporpartidoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Jugadorporpartido.class));
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

    public Jugadorporpartido findJugadorporpartido(JugadorporpartidoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Jugadorporpartido.class, id);
        } finally {
            em.close();
        }
    }

    public int getJugadorporpartidoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Jugadorporpartido> rt = cq.from(Jugadorporpartido.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
