/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.javeriana.mundialrusia.negocio;

import co.edu.javeriana.mundialrusia.datos.Categoria;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import co.edu.javeriana.mundialrusia.datos.Categoriaporestadio;
import java.util.ArrayList;
import java.util.List;
import co.edu.javeriana.mundialrusia.datos.Categoriaporpartido;
import co.edu.javeriana.mundialrusia.datos.Silla;
import co.edu.javeriana.mundialrusia.negocio.exceptions.IllegalOrphanException;
import co.edu.javeriana.mundialrusia.negocio.exceptions.NonexistentEntityException;
import co.edu.javeriana.mundialrusia.negocio.exceptions.PreexistingEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author nicolasmiranda
 */
public class CategoriaJpaController implements Serializable {

    public CategoriaJpaController() {
        this.emf = Persistence.createEntityManagerFactory("MundialRusiaPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Categoria categoria) throws PreexistingEntityException, Exception {
        if (categoria.getCategoriaporestadioList() == null) {
            categoria.setCategoriaporestadioList(new ArrayList<Categoriaporestadio>());
        }
        if (categoria.getCategoriaporpartidoList() == null) {
            categoria.setCategoriaporpartidoList(new ArrayList<Categoriaporpartido>());
        }
        if (categoria.getSillaList() == null) {
            categoria.setSillaList(new ArrayList<Silla>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Categoriaporestadio> attachedCategoriaporestadioList = new ArrayList<Categoriaporestadio>();
            for (Categoriaporestadio categoriaporestadioListCategoriaporestadioToAttach : categoria.getCategoriaporestadioList()) {
                categoriaporestadioListCategoriaporestadioToAttach = em.getReference(categoriaporestadioListCategoriaporestadioToAttach.getClass(), categoriaporestadioListCategoriaporestadioToAttach.getCategoriaporestadioPK());
                attachedCategoriaporestadioList.add(categoriaporestadioListCategoriaporestadioToAttach);
            }
            categoria.setCategoriaporestadioList(attachedCategoriaporestadioList);
            List<Categoriaporpartido> attachedCategoriaporpartidoList = new ArrayList<Categoriaporpartido>();
            for (Categoriaporpartido categoriaporpartidoListCategoriaporpartidoToAttach : categoria.getCategoriaporpartidoList()) {
                categoriaporpartidoListCategoriaporpartidoToAttach = em.getReference(categoriaporpartidoListCategoriaporpartidoToAttach.getClass(), categoriaporpartidoListCategoriaporpartidoToAttach.getCategoriaporpartidoPK());
                attachedCategoriaporpartidoList.add(categoriaporpartidoListCategoriaporpartidoToAttach);
            }
            categoria.setCategoriaporpartidoList(attachedCategoriaporpartidoList);
            List<Silla> attachedSillaList = new ArrayList<Silla>();
            for (Silla sillaListSillaToAttach : categoria.getSillaList()) {
                sillaListSillaToAttach = em.getReference(sillaListSillaToAttach.getClass(), sillaListSillaToAttach.getSillaPK());
                attachedSillaList.add(sillaListSillaToAttach);
            }
            categoria.setSillaList(attachedSillaList);
            em.persist(categoria);
            for (Categoriaporestadio categoriaporestadioListCategoriaporestadio : categoria.getCategoriaporestadioList()) {
                Categoria oldCategoriaOfCategoriaporestadioListCategoriaporestadio = categoriaporestadioListCategoriaporestadio.getCategoria();
                categoriaporestadioListCategoriaporestadio.setCategoria(categoria);
                categoriaporestadioListCategoriaporestadio = em.merge(categoriaporestadioListCategoriaporestadio);
                if (oldCategoriaOfCategoriaporestadioListCategoriaporestadio != null) {
                    oldCategoriaOfCategoriaporestadioListCategoriaporestadio.getCategoriaporestadioList().remove(categoriaporestadioListCategoriaporestadio);
                    oldCategoriaOfCategoriaporestadioListCategoriaporestadio = em.merge(oldCategoriaOfCategoriaporestadioListCategoriaporestadio);
                }
            }
            for (Categoriaporpartido categoriaporpartidoListCategoriaporpartido : categoria.getCategoriaporpartidoList()) {
                Categoria oldCategoriaOfCategoriaporpartidoListCategoriaporpartido = categoriaporpartidoListCategoriaporpartido.getCategoria();
                categoriaporpartidoListCategoriaporpartido.setCategoria(categoria);
                categoriaporpartidoListCategoriaporpartido = em.merge(categoriaporpartidoListCategoriaporpartido);
                if (oldCategoriaOfCategoriaporpartidoListCategoriaporpartido != null) {
                    oldCategoriaOfCategoriaporpartidoListCategoriaporpartido.getCategoriaporpartidoList().remove(categoriaporpartidoListCategoriaporpartido);
                    oldCategoriaOfCategoriaporpartidoListCategoriaporpartido = em.merge(oldCategoriaOfCategoriaporpartidoListCategoriaporpartido);
                }
            }
            for (Silla sillaListSilla : categoria.getSillaList()) {
                Categoria oldIdcategoriaOfSillaListSilla = sillaListSilla.getIdcategoria();
                sillaListSilla.setIdcategoria(categoria);
                sillaListSilla = em.merge(sillaListSilla);
                if (oldIdcategoriaOfSillaListSilla != null) {
                    oldIdcategoriaOfSillaListSilla.getSillaList().remove(sillaListSilla);
                    oldIdcategoriaOfSillaListSilla = em.merge(oldIdcategoriaOfSillaListSilla);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCategoria(categoria.getIdcategoria()) != null) {
                throw new PreexistingEntityException("Categoria " + categoria + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Categoria categoria) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categoria persistentCategoria = em.find(Categoria.class, categoria.getIdcategoria());
            List<Categoriaporestadio> categoriaporestadioListOld = persistentCategoria.getCategoriaporestadioList();
            List<Categoriaporestadio> categoriaporestadioListNew = categoria.getCategoriaporestadioList();
            List<Categoriaporpartido> categoriaporpartidoListOld = persistentCategoria.getCategoriaporpartidoList();
            List<Categoriaporpartido> categoriaporpartidoListNew = categoria.getCategoriaporpartidoList();
            List<Silla> sillaListOld = persistentCategoria.getSillaList();
            List<Silla> sillaListNew = categoria.getSillaList();
            List<String> illegalOrphanMessages = null;
            for (Categoriaporestadio categoriaporestadioListOldCategoriaporestadio : categoriaporestadioListOld) {
                if (!categoriaporestadioListNew.contains(categoriaporestadioListOldCategoriaporestadio)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Categoriaporestadio " + categoriaporestadioListOldCategoriaporestadio + " since its categoria field is not nullable.");
                }
            }
            for (Categoriaporpartido categoriaporpartidoListOldCategoriaporpartido : categoriaporpartidoListOld) {
                if (!categoriaporpartidoListNew.contains(categoriaporpartidoListOldCategoriaporpartido)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Categoriaporpartido " + categoriaporpartidoListOldCategoriaporpartido + " since its categoria field is not nullable.");
                }
            }
            for (Silla sillaListOldSilla : sillaListOld) {
                if (!sillaListNew.contains(sillaListOldSilla)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Silla " + sillaListOldSilla + " since its idcategoria field is not nullable.");
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
            categoria.setCategoriaporestadioList(categoriaporestadioListNew);
            List<Categoriaporpartido> attachedCategoriaporpartidoListNew = new ArrayList<Categoriaporpartido>();
            for (Categoriaporpartido categoriaporpartidoListNewCategoriaporpartidoToAttach : categoriaporpartidoListNew) {
                categoriaporpartidoListNewCategoriaporpartidoToAttach = em.getReference(categoriaporpartidoListNewCategoriaporpartidoToAttach.getClass(), categoriaporpartidoListNewCategoriaporpartidoToAttach.getCategoriaporpartidoPK());
                attachedCategoriaporpartidoListNew.add(categoriaporpartidoListNewCategoriaporpartidoToAttach);
            }
            categoriaporpartidoListNew = attachedCategoriaporpartidoListNew;
            categoria.setCategoriaporpartidoList(categoriaporpartidoListNew);
            List<Silla> attachedSillaListNew = new ArrayList<Silla>();
            for (Silla sillaListNewSillaToAttach : sillaListNew) {
                sillaListNewSillaToAttach = em.getReference(sillaListNewSillaToAttach.getClass(), sillaListNewSillaToAttach.getSillaPK());
                attachedSillaListNew.add(sillaListNewSillaToAttach);
            }
            sillaListNew = attachedSillaListNew;
            categoria.setSillaList(sillaListNew);
            categoria = em.merge(categoria);
            for (Categoriaporestadio categoriaporestadioListNewCategoriaporestadio : categoriaporestadioListNew) {
                if (!categoriaporestadioListOld.contains(categoriaporestadioListNewCategoriaporestadio)) {
                    Categoria oldCategoriaOfCategoriaporestadioListNewCategoriaporestadio = categoriaporestadioListNewCategoriaporestadio.getCategoria();
                    categoriaporestadioListNewCategoriaporestadio.setCategoria(categoria);
                    categoriaporestadioListNewCategoriaporestadio = em.merge(categoriaporestadioListNewCategoriaporestadio);
                    if (oldCategoriaOfCategoriaporestadioListNewCategoriaporestadio != null && !oldCategoriaOfCategoriaporestadioListNewCategoriaporestadio.equals(categoria)) {
                        oldCategoriaOfCategoriaporestadioListNewCategoriaporestadio.getCategoriaporestadioList().remove(categoriaporestadioListNewCategoriaporestadio);
                        oldCategoriaOfCategoriaporestadioListNewCategoriaporestadio = em.merge(oldCategoriaOfCategoriaporestadioListNewCategoriaporestadio);
                    }
                }
            }
            for (Categoriaporpartido categoriaporpartidoListNewCategoriaporpartido : categoriaporpartidoListNew) {
                if (!categoriaporpartidoListOld.contains(categoriaporpartidoListNewCategoriaporpartido)) {
                    Categoria oldCategoriaOfCategoriaporpartidoListNewCategoriaporpartido = categoriaporpartidoListNewCategoriaporpartido.getCategoria();
                    categoriaporpartidoListNewCategoriaporpartido.setCategoria(categoria);
                    categoriaporpartidoListNewCategoriaporpartido = em.merge(categoriaporpartidoListNewCategoriaporpartido);
                    if (oldCategoriaOfCategoriaporpartidoListNewCategoriaporpartido != null && !oldCategoriaOfCategoriaporpartidoListNewCategoriaporpartido.equals(categoria)) {
                        oldCategoriaOfCategoriaporpartidoListNewCategoriaporpartido.getCategoriaporpartidoList().remove(categoriaporpartidoListNewCategoriaporpartido);
                        oldCategoriaOfCategoriaporpartidoListNewCategoriaporpartido = em.merge(oldCategoriaOfCategoriaporpartidoListNewCategoriaporpartido);
                    }
                }
            }
            for (Silla sillaListNewSilla : sillaListNew) {
                if (!sillaListOld.contains(sillaListNewSilla)) {
                    Categoria oldIdcategoriaOfSillaListNewSilla = sillaListNewSilla.getIdcategoria();
                    sillaListNewSilla.setIdcategoria(categoria);
                    sillaListNewSilla = em.merge(sillaListNewSilla);
                    if (oldIdcategoriaOfSillaListNewSilla != null && !oldIdcategoriaOfSillaListNewSilla.equals(categoria)) {
                        oldIdcategoriaOfSillaListNewSilla.getSillaList().remove(sillaListNewSilla);
                        oldIdcategoriaOfSillaListNewSilla = em.merge(oldIdcategoriaOfSillaListNewSilla);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Short id = categoria.getIdcategoria();
                if (findCategoria(id) == null) {
                    throw new NonexistentEntityException("The categoria with id " + id + " no longer exists.");
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
            Categoria categoria;
            try {
                categoria = em.getReference(Categoria.class, id);
                categoria.getIdcategoria();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The categoria with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Categoriaporestadio> categoriaporestadioListOrphanCheck = categoria.getCategoriaporestadioList();
            for (Categoriaporestadio categoriaporestadioListOrphanCheckCategoriaporestadio : categoriaporestadioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Categoria (" + categoria + ") cannot be destroyed since the Categoriaporestadio " + categoriaporestadioListOrphanCheckCategoriaporestadio + " in its categoriaporestadioList field has a non-nullable categoria field.");
            }
            List<Categoriaporpartido> categoriaporpartidoListOrphanCheck = categoria.getCategoriaporpartidoList();
            for (Categoriaporpartido categoriaporpartidoListOrphanCheckCategoriaporpartido : categoriaporpartidoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Categoria (" + categoria + ") cannot be destroyed since the Categoriaporpartido " + categoriaporpartidoListOrphanCheckCategoriaporpartido + " in its categoriaporpartidoList field has a non-nullable categoria field.");
            }
            List<Silla> sillaListOrphanCheck = categoria.getSillaList();
            for (Silla sillaListOrphanCheckSilla : sillaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Categoria (" + categoria + ") cannot be destroyed since the Silla " + sillaListOrphanCheckSilla + " in its sillaList field has a non-nullable idcategoria field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(categoria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Categoria> findCategoriaEntities() {
        return findCategoriaEntities(true, -1, -1);
    }

    public List<Categoria> findCategoriaEntities(int maxResults, int firstResult) {
        return findCategoriaEntities(false, maxResults, firstResult);
    }

    private List<Categoria> findCategoriaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Categoria.class));
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

    public Categoria findCategoria(Short id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Categoria.class, id);
        } finally {
            em.close();
        }
    }

    public int getCategoriaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Categoria> rt = cq.from(Categoria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
