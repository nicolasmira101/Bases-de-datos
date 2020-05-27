/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.javeriana.mundialrusia.negocio;

import co.edu.javeriana.mundialrusia.datos.Calendario;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import co.edu.javeriana.mundialrusia.datos.Partido;
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
public class CalendarioJpaController implements Serializable {

    public CalendarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Calendario calendario) throws PreexistingEntityException, Exception {
        if (calendario.getPartidoList() == null) {
            calendario.setPartidoList(new ArrayList<Partido>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Partido> attachedPartidoList = new ArrayList<Partido>();
            for (Partido partidoListPartidoToAttach : calendario.getPartidoList()) {
                partidoListPartidoToAttach = em.getReference(partidoListPartidoToAttach.getClass(), partidoListPartidoToAttach.getIdpartido());
                attachedPartidoList.add(partidoListPartidoToAttach);
            }
            calendario.setPartidoList(attachedPartidoList);
            em.persist(calendario);
            for (Partido partidoListPartido : calendario.getPartidoList()) {
                Calendario oldIdcalendarioOfPartidoListPartido = partidoListPartido.getIdcalendario();
                partidoListPartido.setIdcalendario(calendario);
                partidoListPartido = em.merge(partidoListPartido);
                if (oldIdcalendarioOfPartidoListPartido != null) {
                    oldIdcalendarioOfPartidoListPartido.getPartidoList().remove(partidoListPartido);
                    oldIdcalendarioOfPartidoListPartido = em.merge(oldIdcalendarioOfPartidoListPartido);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCalendario(calendario.getIdcalendario()) != null) {
                throw new PreexistingEntityException("Calendario " + calendario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Calendario calendario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Calendario persistentCalendario = em.find(Calendario.class, calendario.getIdcalendario());
            List<Partido> partidoListOld = persistentCalendario.getPartidoList();
            List<Partido> partidoListNew = calendario.getPartidoList();
            List<String> illegalOrphanMessages = null;
            for (Partido partidoListOldPartido : partidoListOld) {
                if (!partidoListNew.contains(partidoListOldPartido)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Partido " + partidoListOldPartido + " since its idcalendario field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Partido> attachedPartidoListNew = new ArrayList<Partido>();
            for (Partido partidoListNewPartidoToAttach : partidoListNew) {
                partidoListNewPartidoToAttach = em.getReference(partidoListNewPartidoToAttach.getClass(), partidoListNewPartidoToAttach.getIdpartido());
                attachedPartidoListNew.add(partidoListNewPartidoToAttach);
            }
            partidoListNew = attachedPartidoListNew;
            calendario.setPartidoList(partidoListNew);
            calendario = em.merge(calendario);
            for (Partido partidoListNewPartido : partidoListNew) {
                if (!partidoListOld.contains(partidoListNewPartido)) {
                    Calendario oldIdcalendarioOfPartidoListNewPartido = partidoListNewPartido.getIdcalendario();
                    partidoListNewPartido.setIdcalendario(calendario);
                    partidoListNewPartido = em.merge(partidoListNewPartido);
                    if (oldIdcalendarioOfPartidoListNewPartido != null && !oldIdcalendarioOfPartidoListNewPartido.equals(calendario)) {
                        oldIdcalendarioOfPartidoListNewPartido.getPartidoList().remove(partidoListNewPartido);
                        oldIdcalendarioOfPartidoListNewPartido = em.merge(oldIdcalendarioOfPartidoListNewPartido);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Short id = calendario.getIdcalendario();
                if (findCalendario(id) == null) {
                    throw new NonexistentEntityException("The calendario with id " + id + " no longer exists.");
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
            Calendario calendario;
            try {
                calendario = em.getReference(Calendario.class, id);
                calendario.getIdcalendario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The calendario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Partido> partidoListOrphanCheck = calendario.getPartidoList();
            for (Partido partidoListOrphanCheckPartido : partidoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Calendario (" + calendario + ") cannot be destroyed since the Partido " + partidoListOrphanCheckPartido + " in its partidoList field has a non-nullable idcalendario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(calendario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Calendario> findCalendarioEntities() {
        return findCalendarioEntities(true, -1, -1);
    }

    public List<Calendario> findCalendarioEntities(int maxResults, int firstResult) {
        return findCalendarioEntities(false, maxResults, firstResult);
    }

    private List<Calendario> findCalendarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Calendario.class));
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

    public Calendario findCalendario(Short id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Calendario.class, id);
        } finally {
            em.close();
        }
    }

    public int getCalendarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Calendario> rt = cq.from(Calendario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
