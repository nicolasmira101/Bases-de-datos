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
import co.edu.javeriana.mundialrusia.datos.Categoriaporestadio;
import co.edu.javeriana.mundialrusia.datos.Estadio;
import java.util.ArrayList;
import java.util.List;
import co.edu.javeriana.mundialrusia.datos.Partido;
import co.edu.javeriana.mundialrusia.datos.Sillaporestadio;
import co.edu.javeriana.mundialrusia.negocio.exceptions.IllegalOrphanException;
import co.edu.javeriana.mundialrusia.negocio.exceptions.NonexistentEntityException;
import co.edu.javeriana.mundialrusia.negocio.exceptions.PreexistingEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author nicolasmiranda
 */
public class EstadioJpaController implements Serializable {

    public EstadioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Estadio estadio) throws PreexistingEntityException, Exception {
        if (estadio.getCategoriaporestadioList() == null) {
            estadio.setCategoriaporestadioList(new ArrayList<Categoriaporestadio>());
        }
        if (estadio.getPartidoList() == null) {
            estadio.setPartidoList(new ArrayList<Partido>());
        }
        if (estadio.getSillaporestadioList() == null) {
            estadio.setSillaporestadioList(new ArrayList<Sillaporestadio>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Categoriaporestadio> attachedCategoriaporestadioList = new ArrayList<Categoriaporestadio>();
            for (Categoriaporestadio categoriaporestadioListCategoriaporestadioToAttach : estadio.getCategoriaporestadioList()) {
                categoriaporestadioListCategoriaporestadioToAttach = em.getReference(categoriaporestadioListCategoriaporestadioToAttach.getClass(), categoriaporestadioListCategoriaporestadioToAttach.getCategoriaporestadioPK());
                attachedCategoriaporestadioList.add(categoriaporestadioListCategoriaporestadioToAttach);
            }
            estadio.setCategoriaporestadioList(attachedCategoriaporestadioList);
            List<Partido> attachedPartidoList = new ArrayList<Partido>();
            for (Partido partidoListPartidoToAttach : estadio.getPartidoList()) {
                partidoListPartidoToAttach = em.getReference(partidoListPartidoToAttach.getClass(), partidoListPartidoToAttach.getIdpartido());
                attachedPartidoList.add(partidoListPartidoToAttach);
            }
            estadio.setPartidoList(attachedPartidoList);
            List<Sillaporestadio> attachedSillaporestadioList = new ArrayList<Sillaporestadio>();
            for (Sillaporestadio sillaporestadioListSillaporestadioToAttach : estadio.getSillaporestadioList()) {
                sillaporestadioListSillaporestadioToAttach = em.getReference(sillaporestadioListSillaporestadioToAttach.getClass(), sillaporestadioListSillaporestadioToAttach.getSillaporestadioPK());
                attachedSillaporestadioList.add(sillaporestadioListSillaporestadioToAttach);
            }
            estadio.setSillaporestadioList(attachedSillaporestadioList);
            em.persist(estadio);
            for (Categoriaporestadio categoriaporestadioListCategoriaporestadio : estadio.getCategoriaporestadioList()) {
                Estadio oldEstadioOfCategoriaporestadioListCategoriaporestadio = categoriaporestadioListCategoriaporestadio.getEstadio();
                categoriaporestadioListCategoriaporestadio.setEstadio(estadio);
                categoriaporestadioListCategoriaporestadio = em.merge(categoriaporestadioListCategoriaporestadio);
                if (oldEstadioOfCategoriaporestadioListCategoriaporestadio != null) {
                    oldEstadioOfCategoriaporestadioListCategoriaporestadio.getCategoriaporestadioList().remove(categoriaporestadioListCategoriaporestadio);
                    oldEstadioOfCategoriaporestadioListCategoriaporestadio = em.merge(oldEstadioOfCategoriaporestadioListCategoriaporestadio);
                }
            }
            for (Partido partidoListPartido : estadio.getPartidoList()) {
                Estadio oldIdestadioOfPartidoListPartido = partidoListPartido.getIdestadio();
                partidoListPartido.setIdestadio(estadio);
                partidoListPartido = em.merge(partidoListPartido);
                if (oldIdestadioOfPartidoListPartido != null) {
                    oldIdestadioOfPartidoListPartido.getPartidoList().remove(partidoListPartido);
                    oldIdestadioOfPartidoListPartido = em.merge(oldIdestadioOfPartidoListPartido);
                }
            }
            for (Sillaporestadio sillaporestadioListSillaporestadio : estadio.getSillaporestadioList()) {
                Estadio oldEstadioOfSillaporestadioListSillaporestadio = sillaporestadioListSillaporestadio.getEstadio();
                sillaporestadioListSillaporestadio.setEstadio(estadio);
                sillaporestadioListSillaporestadio = em.merge(sillaporestadioListSillaporestadio);
                if (oldEstadioOfSillaporestadioListSillaporestadio != null) {
                    oldEstadioOfSillaporestadioListSillaporestadio.getSillaporestadioList().remove(sillaporestadioListSillaporestadio);
                    oldEstadioOfSillaporestadioListSillaporestadio = em.merge(oldEstadioOfSillaporestadioListSillaporestadio);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEstadio(estadio.getIdestadio()) != null) {
                throw new PreexistingEntityException("Estadio " + estadio + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Estadio estadio) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estadio persistentEstadio = em.find(Estadio.class, estadio.getIdestadio());
            List<Categoriaporestadio> categoriaporestadioListOld = persistentEstadio.getCategoriaporestadioList();
            List<Categoriaporestadio> categoriaporestadioListNew = estadio.getCategoriaporestadioList();
            List<Partido> partidoListOld = persistentEstadio.getPartidoList();
            List<Partido> partidoListNew = estadio.getPartidoList();
            List<Sillaporestadio> sillaporestadioListOld = persistentEstadio.getSillaporestadioList();
            List<Sillaporestadio> sillaporestadioListNew = estadio.getSillaporestadioList();
            List<String> illegalOrphanMessages = null;
            for (Categoriaporestadio categoriaporestadioListOldCategoriaporestadio : categoriaporestadioListOld) {
                if (!categoriaporestadioListNew.contains(categoriaporestadioListOldCategoriaporestadio)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Categoriaporestadio " + categoriaporestadioListOldCategoriaporestadio + " since its estadio field is not nullable.");
                }
            }
            for (Partido partidoListOldPartido : partidoListOld) {
                if (!partidoListNew.contains(partidoListOldPartido)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Partido " + partidoListOldPartido + " since its idestadio field is not nullable.");
                }
            }
            for (Sillaporestadio sillaporestadioListOldSillaporestadio : sillaporestadioListOld) {
                if (!sillaporestadioListNew.contains(sillaporestadioListOldSillaporestadio)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Sillaporestadio " + sillaporestadioListOldSillaporestadio + " since its estadio field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Categoriaporestadio> attachedCategoriaporestadioListNew = new ArrayList<Categoriaporestadio>();
            for (Categoriaporestadio categoriaporestadioListNewCategoriaporestadioToAttach : categoriaporestadioListNew) {
                categoriaporestadioListNewCategoriaporestadioToAttach = em.getReference(categoriaporestadioListNewCategoriaporestadioToAttach.getClass(), categoriaporestadioListNewCategoriaporestadioToAttach.getCategoriaporestadioPK());
                attachedCategoriaporestadioListNew.add(categoriaporestadioListNewCategoriaporestadioToAttach);
            }
            categoriaporestadioListNew = attachedCategoriaporestadioListNew;
            estadio.setCategoriaporestadioList(categoriaporestadioListNew);
            List<Partido> attachedPartidoListNew = new ArrayList<Partido>();
            for (Partido partidoListNewPartidoToAttach : partidoListNew) {
                partidoListNewPartidoToAttach = em.getReference(partidoListNewPartidoToAttach.getClass(), partidoListNewPartidoToAttach.getIdpartido());
                attachedPartidoListNew.add(partidoListNewPartidoToAttach);
            }
            partidoListNew = attachedPartidoListNew;
            estadio.setPartidoList(partidoListNew);
            List<Sillaporestadio> attachedSillaporestadioListNew = new ArrayList<Sillaporestadio>();
            for (Sillaporestadio sillaporestadioListNewSillaporestadioToAttach : sillaporestadioListNew) {
                sillaporestadioListNewSillaporestadioToAttach = em.getReference(sillaporestadioListNewSillaporestadioToAttach.getClass(), sillaporestadioListNewSillaporestadioToAttach.getSillaporestadioPK());
                attachedSillaporestadioListNew.add(sillaporestadioListNewSillaporestadioToAttach);
            }
            sillaporestadioListNew = attachedSillaporestadioListNew;
            estadio.setSillaporestadioList(sillaporestadioListNew);
            estadio = em.merge(estadio);
            for (Categoriaporestadio categoriaporestadioListNewCategoriaporestadio : categoriaporestadioListNew) {
                if (!categoriaporestadioListOld.contains(categoriaporestadioListNewCategoriaporestadio)) {
                    Estadio oldEstadioOfCategoriaporestadioListNewCategoriaporestadio = categoriaporestadioListNewCategoriaporestadio.getEstadio();
                    categoriaporestadioListNewCategoriaporestadio.setEstadio(estadio);
                    categoriaporestadioListNewCategoriaporestadio = em.merge(categoriaporestadioListNewCategoriaporestadio);
                    if (oldEstadioOfCategoriaporestadioListNewCategoriaporestadio != null && !oldEstadioOfCategoriaporestadioListNewCategoriaporestadio.equals(estadio)) {
                        oldEstadioOfCategoriaporestadioListNewCategoriaporestadio.getCategoriaporestadioList().remove(categoriaporestadioListNewCategoriaporestadio);
                        oldEstadioOfCategoriaporestadioListNewCategoriaporestadio = em.merge(oldEstadioOfCategoriaporestadioListNewCategoriaporestadio);
                    }
                }
            }
            for (Partido partidoListNewPartido : partidoListNew) {
                if (!partidoListOld.contains(partidoListNewPartido)) {
                    Estadio oldIdestadioOfPartidoListNewPartido = partidoListNewPartido.getIdestadio();
                    partidoListNewPartido.setIdestadio(estadio);
                    partidoListNewPartido = em.merge(partidoListNewPartido);
                    if (oldIdestadioOfPartidoListNewPartido != null && !oldIdestadioOfPartidoListNewPartido.equals(estadio)) {
                        oldIdestadioOfPartidoListNewPartido.getPartidoList().remove(partidoListNewPartido);
                        oldIdestadioOfPartidoListNewPartido = em.merge(oldIdestadioOfPartidoListNewPartido);
                    }
                }
            }
            for (Sillaporestadio sillaporestadioListNewSillaporestadio : sillaporestadioListNew) {
                if (!sillaporestadioListOld.contains(sillaporestadioListNewSillaporestadio)) {
                    Estadio oldEstadioOfSillaporestadioListNewSillaporestadio = sillaporestadioListNewSillaporestadio.getEstadio();
                    sillaporestadioListNewSillaporestadio.setEstadio(estadio);
                    sillaporestadioListNewSillaporestadio = em.merge(sillaporestadioListNewSillaporestadio);
                    if (oldEstadioOfSillaporestadioListNewSillaporestadio != null && !oldEstadioOfSillaporestadioListNewSillaporestadio.equals(estadio)) {
                        oldEstadioOfSillaporestadioListNewSillaporestadio.getSillaporestadioList().remove(sillaporestadioListNewSillaporestadio);
                        oldEstadioOfSillaporestadioListNewSillaporestadio = em.merge(oldEstadioOfSillaporestadioListNewSillaporestadio);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Short id = estadio.getIdestadio();
                if (findEstadio(id) == null) {
                    throw new NonexistentEntityException("The estadio with id " + id + " no longer exists.");
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
            Estadio estadio;
            try {
                estadio = em.getReference(Estadio.class, id);
                estadio.getIdestadio();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estadio with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Categoriaporestadio> categoriaporestadioListOrphanCheck = estadio.getCategoriaporestadioList();
            for (Categoriaporestadio categoriaporestadioListOrphanCheckCategoriaporestadio : categoriaporestadioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Estadio (" + estadio + ") cannot be destroyed since the Categoriaporestadio " + categoriaporestadioListOrphanCheckCategoriaporestadio + " in its categoriaporestadioList field has a non-nullable estadio field.");
            }
            List<Partido> partidoListOrphanCheck = estadio.getPartidoList();
            for (Partido partidoListOrphanCheckPartido : partidoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Estadio (" + estadio + ") cannot be destroyed since the Partido " + partidoListOrphanCheckPartido + " in its partidoList field has a non-nullable idestadio field.");
            }
            List<Sillaporestadio> sillaporestadioListOrphanCheck = estadio.getSillaporestadioList();
            for (Sillaporestadio sillaporestadioListOrphanCheckSillaporestadio : sillaporestadioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Estadio (" + estadio + ") cannot be destroyed since the Sillaporestadio " + sillaporestadioListOrphanCheckSillaporestadio + " in its sillaporestadioList field has a non-nullable estadio field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(estadio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Estadio> findEstadioEntities() {
        return findEstadioEntities(true, -1, -1);
    }

    public List<Estadio> findEstadioEntities(int maxResults, int firstResult) {
        return findEstadioEntities(false, maxResults, firstResult);
    }

    private List<Estadio> findEstadioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Estadio.class));
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

    public Estadio findEstadio(Short id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Estadio.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstadioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Estadio> rt = cq.from(Estadio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
