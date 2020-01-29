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
import co.edu.javeriana.mundialrusia.datos.Pais;
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
public class PaisJpaController implements Serializable {

    public PaisJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pais pais) throws PreexistingEntityException, Exception {
        if (pais.getJugadorList() == null) {
            pais.setJugadorList(new ArrayList<Jugador>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Jugador> attachedJugadorList = new ArrayList<Jugador>();
            for (Jugador jugadorListJugadorToAttach : pais.getJugadorList()) {
                jugadorListJugadorToAttach = em.getReference(jugadorListJugadorToAttach.getClass(), jugadorListJugadorToAttach.getIdjugador());
                attachedJugadorList.add(jugadorListJugadorToAttach);
            }
            pais.setJugadorList(attachedJugadorList);
            em.persist(pais);
            for (Jugador jugadorListJugador : pais.getJugadorList()) {
                Pais oldIdpaisOfJugadorListJugador = jugadorListJugador.getIdpais();
                jugadorListJugador.setIdpais(pais);
                jugadorListJugador = em.merge(jugadorListJugador);
                if (oldIdpaisOfJugadorListJugador != null) {
                    oldIdpaisOfJugadorListJugador.getJugadorList().remove(jugadorListJugador);
                    oldIdpaisOfJugadorListJugador = em.merge(oldIdpaisOfJugadorListJugador);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPais(pais.getIdpais()) != null) {
                throw new PreexistingEntityException("Pais " + pais + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pais pais) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pais persistentPais = em.find(Pais.class, pais.getIdpais());
            List<Jugador> jugadorListOld = persistentPais.getJugadorList();
            List<Jugador> jugadorListNew = pais.getJugadorList();
            List<String> illegalOrphanMessages = null;
            for (Jugador jugadorListOldJugador : jugadorListOld) {
                if (!jugadorListNew.contains(jugadorListOldJugador)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Jugador " + jugadorListOldJugador + " since its idpais field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Jugador> attachedJugadorListNew = new ArrayList<Jugador>();
            for (Jugador jugadorListNewJugadorToAttach : jugadorListNew) {
                jugadorListNewJugadorToAttach = em.getReference(jugadorListNewJugadorToAttach.getClass(), jugadorListNewJugadorToAttach.getIdjugador());
                attachedJugadorListNew.add(jugadorListNewJugadorToAttach);
            }
            jugadorListNew = attachedJugadorListNew;
            pais.setJugadorList(jugadorListNew);
            pais = em.merge(pais);
            for (Jugador jugadorListNewJugador : jugadorListNew) {
                if (!jugadorListOld.contains(jugadorListNewJugador)) {
                    Pais oldIdpaisOfJugadorListNewJugador = jugadorListNewJugador.getIdpais();
                    jugadorListNewJugador.setIdpais(pais);
                    jugadorListNewJugador = em.merge(jugadorListNewJugador);
                    if (oldIdpaisOfJugadorListNewJugador != null && !oldIdpaisOfJugadorListNewJugador.equals(pais)) {
                        oldIdpaisOfJugadorListNewJugador.getJugadorList().remove(jugadorListNewJugador);
                        oldIdpaisOfJugadorListNewJugador = em.merge(oldIdpaisOfJugadorListNewJugador);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Short id = pais.getIdpais();
                if (findPais(id) == null) {
                    throw new NonexistentEntityException("The pais with id " + id + " no longer exists.");
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
            Pais pais;
            try {
                pais = em.getReference(Pais.class, id);
                pais.getIdpais();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pais with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Jugador> jugadorListOrphanCheck = pais.getJugadorList();
            for (Jugador jugadorListOrphanCheckJugador : jugadorListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pais (" + pais + ") cannot be destroyed since the Jugador " + jugadorListOrphanCheckJugador + " in its jugadorList field has a non-nullable idpais field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(pais);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pais> findPaisEntities() {
        return findPaisEntities(true, -1, -1);
    }

    public List<Pais> findPaisEntities(int maxResults, int firstResult) {
        return findPaisEntities(false, maxResults, firstResult);
    }

    private List<Pais> findPaisEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pais.class));
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

    public Pais findPais(Short id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pais.class, id);
        } finally {
            em.close();
        }
    }

    public int getPaisCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pais> rt = cq.from(Pais.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
