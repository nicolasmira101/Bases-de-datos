/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.javeriana.mundialrusia.negocio;

import co.edu.javeriana.mundialrusia.datos.Cliente;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import co.edu.javeriana.mundialrusia.datos.Clienteporpartido;
import co.edu.javeriana.mundialrusia.negocio.exceptions.IllegalOrphanException;
import co.edu.javeriana.mundialrusia.negocio.exceptions.NonexistentEntityException;
import co.edu.javeriana.mundialrusia.negocio.exceptions.PreexistingEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author nicolasmiranda
 */
public class ClienteJpaController implements Serializable {

    public ClienteJpaController() {
        this.emf = Persistence.createEntityManagerFactory("MundialRusiaPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cliente cliente) throws PreexistingEntityException, Exception {
        if (cliente.getClienteporpartidoList() == null) {
            cliente.setClienteporpartidoList(new ArrayList<Clienteporpartido>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Clienteporpartido> attachedClienteporpartidoList = new ArrayList<Clienteporpartido>();
            for (Clienteporpartido clienteporpartidoListClienteporpartidoToAttach : cliente.getClienteporpartidoList()) {
                clienteporpartidoListClienteporpartidoToAttach = em.getReference(clienteporpartidoListClienteporpartidoToAttach.getClass(), clienteporpartidoListClienteporpartidoToAttach.getClienteporpartidoPK());
                attachedClienteporpartidoList.add(clienteporpartidoListClienteporpartidoToAttach);
            }
            cliente.setClienteporpartidoList(attachedClienteporpartidoList);
            em.persist(cliente);
            for (Clienteporpartido clienteporpartidoListClienteporpartido : cliente.getClienteporpartidoList()) {
                Cliente oldClienteOfClienteporpartidoListClienteporpartido = clienteporpartidoListClienteporpartido.getCliente();
                clienteporpartidoListClienteporpartido.setCliente(cliente);
                clienteporpartidoListClienteporpartido = em.merge(clienteporpartidoListClienteporpartido);
                if (oldClienteOfClienteporpartidoListClienteporpartido != null) {
                    oldClienteOfClienteporpartidoListClienteporpartido.getClienteporpartidoList().remove(clienteporpartidoListClienteporpartido);
                    oldClienteOfClienteporpartidoListClienteporpartido = em.merge(oldClienteOfClienteporpartidoListClienteporpartido);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCliente(cliente.getIdcliente()) != null) {
                throw new PreexistingEntityException("Cliente " + cliente + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cliente cliente) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente persistentCliente = em.find(Cliente.class, cliente.getIdcliente());
            List<Clienteporpartido> clienteporpartidoListOld = persistentCliente.getClienteporpartidoList();
            List<Clienteporpartido> clienteporpartidoListNew = cliente.getClienteporpartidoList();
            List<String> illegalOrphanMessages = null;
            for (Clienteporpartido clienteporpartidoListOldClienteporpartido : clienteporpartidoListOld) {
                if (!clienteporpartidoListNew.contains(clienteporpartidoListOldClienteporpartido)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Clienteporpartido " + clienteporpartidoListOldClienteporpartido + " since its cliente field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Clienteporpartido> attachedClienteporpartidoListNew = new ArrayList<Clienteporpartido>();
            for (Clienteporpartido clienteporpartidoListNewClienteporpartidoToAttach : clienteporpartidoListNew) {
                clienteporpartidoListNewClienteporpartidoToAttach = em.getReference(clienteporpartidoListNewClienteporpartidoToAttach.getClass(), clienteporpartidoListNewClienteporpartidoToAttach.getClienteporpartidoPK());
                attachedClienteporpartidoListNew.add(clienteporpartidoListNewClienteporpartidoToAttach);
            }
            clienteporpartidoListNew = attachedClienteporpartidoListNew;
            cliente.setClienteporpartidoList(clienteporpartidoListNew);
            cliente = em.merge(cliente);
            for (Clienteporpartido clienteporpartidoListNewClienteporpartido : clienteporpartidoListNew) {
                if (!clienteporpartidoListOld.contains(clienteporpartidoListNewClienteporpartido)) {
                    Cliente oldClienteOfClienteporpartidoListNewClienteporpartido = clienteporpartidoListNewClienteporpartido.getCliente();
                    clienteporpartidoListNewClienteporpartido.setCliente(cliente);
                    clienteporpartidoListNewClienteporpartido = em.merge(clienteporpartidoListNewClienteporpartido);
                    if (oldClienteOfClienteporpartidoListNewClienteporpartido != null && !oldClienteOfClienteporpartidoListNewClienteporpartido.equals(cliente)) {
                        oldClienteOfClienteporpartidoListNewClienteporpartido.getClienteporpartidoList().remove(clienteporpartidoListNewClienteporpartido);
                        oldClienteOfClienteporpartidoListNewClienteporpartido = em.merge(oldClienteOfClienteporpartidoListNewClienteporpartido);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Short id = cliente.getIdcliente();
                if (findCliente(id) == null) {
                    throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.");
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
            Cliente cliente;
            try {
                cliente = em.getReference(Cliente.class, id);
                cliente.getIdcliente();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Clienteporpartido> clienteporpartidoListOrphanCheck = cliente.getClienteporpartidoList();
            for (Clienteporpartido clienteporpartidoListOrphanCheckClienteporpartido : clienteporpartidoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cliente (" + cliente + ") cannot be destroyed since the Clienteporpartido " + clienteporpartidoListOrphanCheckClienteporpartido + " in its clienteporpartidoList field has a non-nullable cliente field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(cliente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cliente> findClienteEntities() {
        return findClienteEntities(true, -1, -1);
    }

    public List<Cliente> findClienteEntities(int maxResults, int firstResult) {
        return findClienteEntities(false, maxResults, firstResult);
    }

    private List<Cliente> findClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cliente.class));
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

    public Cliente findCliente(Short id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cliente.class, id);
        } finally {
            em.close();
        }
    }

    public int getClienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cliente> rt = cq.from(Cliente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
