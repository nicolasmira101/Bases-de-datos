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
import co.edu.javeriana.mundialrusia.datos.Categoria;
import co.edu.javeriana.mundialrusia.datos.Silla;
import co.edu.javeriana.mundialrusia.datos.SillaPK;
import co.edu.javeriana.mundialrusia.datos.Sillaporestadio;
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
public class SillaJpaController implements Serializable {

    public SillaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Silla silla) throws PreexistingEntityException, Exception {
        if (silla.getSillaPK() == null) {
            silla.setSillaPK(new SillaPK());
        }
        if (silla.getSillaporestadioList() == null) {
            silla.setSillaporestadioList(new ArrayList<Sillaporestadio>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categoria idcategoria = silla.getIdcategoria();
            if (idcategoria != null) {
                idcategoria = em.getReference(idcategoria.getClass(), idcategoria.getIdcategoria());
                silla.setIdcategoria(idcategoria);
            }
            List<Sillaporestadio> attachedSillaporestadioList = new ArrayList<Sillaporestadio>();
            for (Sillaporestadio sillaporestadioListSillaporestadioToAttach : silla.getSillaporestadioList()) {
                sillaporestadioListSillaporestadioToAttach = em.getReference(sillaporestadioListSillaporestadioToAttach.getClass(), sillaporestadioListSillaporestadioToAttach.getSillaporestadioPK());
                attachedSillaporestadioList.add(sillaporestadioListSillaporestadioToAttach);
            }
            silla.setSillaporestadioList(attachedSillaporestadioList);
            em.persist(silla);
            if (idcategoria != null) {
                idcategoria.getSillaList().add(silla);
                idcategoria = em.merge(idcategoria);
            }
            for (Sillaporestadio sillaporestadioListSillaporestadio : silla.getSillaporestadioList()) {
                Silla oldSillaOfSillaporestadioListSillaporestadio = sillaporestadioListSillaporestadio.getSilla();
                sillaporestadioListSillaporestadio.setSilla(silla);
                sillaporestadioListSillaporestadio = em.merge(sillaporestadioListSillaporestadio);
                if (oldSillaOfSillaporestadioListSillaporestadio != null) {
                    oldSillaOfSillaporestadioListSillaporestadio.getSillaporestadioList().remove(sillaporestadioListSillaporestadio);
                    oldSillaOfSillaporestadioListSillaporestadio = em.merge(oldSillaOfSillaporestadioListSillaporestadio);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSilla(silla.getSillaPK()) != null) {
                throw new PreexistingEntityException("Silla " + silla + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Silla silla) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Silla persistentSilla = em.find(Silla.class, silla.getSillaPK());
            Categoria idcategoriaOld = persistentSilla.getIdcategoria();
            Categoria idcategoriaNew = silla.getIdcategoria();
            List<Sillaporestadio> sillaporestadioListOld = persistentSilla.getSillaporestadioList();
            List<Sillaporestadio> sillaporestadioListNew = silla.getSillaporestadioList();
            List<String> illegalOrphanMessages = null;
            for (Sillaporestadio sillaporestadioListOldSillaporestadio : sillaporestadioListOld) {
                if (!sillaporestadioListNew.contains(sillaporestadioListOldSillaporestadio)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Sillaporestadio " + sillaporestadioListOldSillaporestadio + " since its silla field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idcategoriaNew != null) {
                idcategoriaNew = em.getReference(idcategoriaNew.getClass(), idcategoriaNew.getIdcategoria());
                silla.setIdcategoria(idcategoriaNew);
            }
            List<Sillaporestadio> attachedSillaporestadioListNew = new ArrayList<Sillaporestadio>();
            for (Sillaporestadio sillaporestadioListNewSillaporestadioToAttach : sillaporestadioListNew) {
                sillaporestadioListNewSillaporestadioToAttach = em.getReference(sillaporestadioListNewSillaporestadioToAttach.getClass(), sillaporestadioListNewSillaporestadioToAttach.getSillaporestadioPK());
                attachedSillaporestadioListNew.add(sillaporestadioListNewSillaporestadioToAttach);
            }
            sillaporestadioListNew = attachedSillaporestadioListNew;
            silla.setSillaporestadioList(sillaporestadioListNew);
            silla = em.merge(silla);
            if (idcategoriaOld != null && !idcategoriaOld.equals(idcategoriaNew)) {
                idcategoriaOld.getSillaList().remove(silla);
                idcategoriaOld = em.merge(idcategoriaOld);
            }
            if (idcategoriaNew != null && !idcategoriaNew.equals(idcategoriaOld)) {
                idcategoriaNew.getSillaList().add(silla);
                idcategoriaNew = em.merge(idcategoriaNew);
            }
            for (Sillaporestadio sillaporestadioListNewSillaporestadio : sillaporestadioListNew) {
                if (!sillaporestadioListOld.contains(sillaporestadioListNewSillaporestadio)) {
                    Silla oldSillaOfSillaporestadioListNewSillaporestadio = sillaporestadioListNewSillaporestadio.getSilla();
                    sillaporestadioListNewSillaporestadio.setSilla(silla);
                    sillaporestadioListNewSillaporestadio = em.merge(sillaporestadioListNewSillaporestadio);
                    if (oldSillaOfSillaporestadioListNewSillaporestadio != null && !oldSillaOfSillaporestadioListNewSillaporestadio.equals(silla)) {
                        oldSillaOfSillaporestadioListNewSillaporestadio.getSillaporestadioList().remove(sillaporestadioListNewSillaporestadio);
                        oldSillaOfSillaporestadioListNewSillaporestadio = em.merge(oldSillaOfSillaporestadioListNewSillaporestadio);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                SillaPK id = silla.getSillaPK();
                if (findSilla(id) == null) {
                    throw new NonexistentEntityException("The silla with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(SillaPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Silla silla;
            try {
                silla = em.getReference(Silla.class, id);
                silla.getSillaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The silla with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Sillaporestadio> sillaporestadioListOrphanCheck = silla.getSillaporestadioList();
            for (Sillaporestadio sillaporestadioListOrphanCheckSillaporestadio : sillaporestadioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Silla (" + silla + ") cannot be destroyed since the Sillaporestadio " + sillaporestadioListOrphanCheckSillaporestadio + " in its sillaporestadioList field has a non-nullable silla field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Categoria idcategoria = silla.getIdcategoria();
            if (idcategoria != null) {
                idcategoria.getSillaList().remove(silla);
                idcategoria = em.merge(idcategoria);
            }
            em.remove(silla);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Silla> findSillaEntities() {
        return findSillaEntities(true, -1, -1);
    }

    public List<Silla> findSillaEntities(int maxResults, int firstResult) {
        return findSillaEntities(false, maxResults, firstResult);
    }

    private List<Silla> findSillaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Silla.class));
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

    public Silla findSilla(SillaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Silla.class, id);
        } finally {
            em.close();
        }
    }

    public int getSillaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Silla> rt = cq.from(Silla.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
