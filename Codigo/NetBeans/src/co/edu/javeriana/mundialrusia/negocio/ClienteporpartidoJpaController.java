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
import co.edu.javeriana.mundialrusia.datos.Cliente;
import co.edu.javeriana.mundialrusia.datos.Clienteporpartido;
import co.edu.javeriana.mundialrusia.datos.ClienteporpartidoPK;
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
public class ClienteporpartidoJpaController implements Serializable {

    public ClienteporpartidoJpaController() {
        this.emf = Persistence.createEntityManagerFactory("MundialRusiaPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Clienteporpartido clienteporpartido) throws PreexistingEntityException, Exception {
        if (clienteporpartido.getClienteporpartidoPK() == null) {
            clienteporpartido.setClienteporpartidoPK(new ClienteporpartidoPK());
        }
        clienteporpartido.getClienteporpartidoPK().setIdcliente(clienteporpartido.getCliente().getIdcliente());
        clienteporpartido.getClienteporpartidoPK().setIdpartido(clienteporpartido.getPartido().getIdpartido());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente cliente = clienteporpartido.getCliente();
            if (cliente != null) {
                cliente = em.getReference(cliente.getClass(), cliente.getIdcliente());
                clienteporpartido.setCliente(cliente);
            }
            Partido partido = clienteporpartido.getPartido();
            if (partido != null) {
                partido = em.getReference(partido.getClass(), partido.getIdpartido());
                clienteporpartido.setPartido(partido);
            }
            em.persist(clienteporpartido);
            if (cliente != null) {
                cliente.getClienteporpartidoList().add(clienteporpartido);
                cliente = em.merge(cliente);
            }
            if (partido != null) {
                partido.getClienteporpartidoList().add(clienteporpartido);
                partido = em.merge(partido);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findClienteporpartido(clienteporpartido.getClienteporpartidoPK()) != null) {
                throw new PreexistingEntityException("Clienteporpartido " + clienteporpartido + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Clienteporpartido clienteporpartido) throws NonexistentEntityException, Exception {
        clienteporpartido.getClienteporpartidoPK().setIdcliente(clienteporpartido.getCliente().getIdcliente());
        clienteporpartido.getClienteporpartidoPK().setIdpartido(clienteporpartido.getPartido().getIdpartido());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clienteporpartido persistentClienteporpartido = em.find(Clienteporpartido.class, clienteporpartido.getClienteporpartidoPK());
            Cliente clienteOld = persistentClienteporpartido.getCliente();
            Cliente clienteNew = clienteporpartido.getCliente();
            Partido partidoOld = persistentClienteporpartido.getPartido();
            Partido partidoNew = clienteporpartido.getPartido();
            if (clienteNew != null) {
                clienteNew = em.getReference(clienteNew.getClass(), clienteNew.getIdcliente());
                clienteporpartido.setCliente(clienteNew);
            }
            if (partidoNew != null) {
                partidoNew = em.getReference(partidoNew.getClass(), partidoNew.getIdpartido());
                clienteporpartido.setPartido(partidoNew);
            }
            clienteporpartido = em.merge(clienteporpartido);
            if (clienteOld != null && !clienteOld.equals(clienteNew)) {
                clienteOld.getClienteporpartidoList().remove(clienteporpartido);
                clienteOld = em.merge(clienteOld);
            }
            if (clienteNew != null && !clienteNew.equals(clienteOld)) {
                clienteNew.getClienteporpartidoList().add(clienteporpartido);
                clienteNew = em.merge(clienteNew);
            }
            if (partidoOld != null && !partidoOld.equals(partidoNew)) {
                partidoOld.getClienteporpartidoList().remove(clienteporpartido);
                partidoOld = em.merge(partidoOld);
            }
            if (partidoNew != null && !partidoNew.equals(partidoOld)) {
                partidoNew.getClienteporpartidoList().add(clienteporpartido);
                partidoNew = em.merge(partidoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                ClienteporpartidoPK id = clienteporpartido.getClienteporpartidoPK();
                if (findClienteporpartido(id) == null) {
                    throw new NonexistentEntityException("The clienteporpartido with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ClienteporpartidoPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clienteporpartido clienteporpartido;
            try {
                clienteporpartido = em.getReference(Clienteporpartido.class, id);
                clienteporpartido.getClienteporpartidoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The clienteporpartido with id " + id + " no longer exists.", enfe);
            }
            Cliente cliente = clienteporpartido.getCliente();
            if (cliente != null) {
                cliente.getClienteporpartidoList().remove(clienteporpartido);
                cliente = em.merge(cliente);
            }
            Partido partido = clienteporpartido.getPartido();
            if (partido != null) {
                partido.getClienteporpartidoList().remove(clienteporpartido);
                partido = em.merge(partido);
            }
            em.remove(clienteporpartido);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Clienteporpartido> findClienteporpartidoEntities() {
        return findClienteporpartidoEntities(true, -1, -1);
    }

    public List<Clienteporpartido> findClienteporpartidoEntities(int maxResults, int firstResult) {
        return findClienteporpartidoEntities(false, maxResults, firstResult);
    }

    private List<Clienteporpartido> findClienteporpartidoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Clienteporpartido.class));
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

    public Clienteporpartido findClienteporpartido(ClienteporpartidoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Clienteporpartido.class, id);
        } finally {
            em.close();
        }
    }

    public int getClienteporpartidoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Clienteporpartido> rt = cq.from(Clienteporpartido.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
