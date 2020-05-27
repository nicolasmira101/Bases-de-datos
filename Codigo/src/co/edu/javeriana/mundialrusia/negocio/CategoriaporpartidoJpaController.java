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
import co.edu.javeriana.mundialrusia.datos.Categoriaporpartido;
import co.edu.javeriana.mundialrusia.datos.CategoriaporpartidoPK;
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
public class CategoriaporpartidoJpaController implements Serializable {

    public CategoriaporpartidoJpaController() {
        this.emf = Persistence.createEntityManagerFactory("MundialRusiaPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Categoriaporpartido categoriaporpartido) throws PreexistingEntityException, Exception {
        if (categoriaporpartido.getCategoriaporpartidoPK() == null) {
            categoriaporpartido.setCategoriaporpartidoPK(new CategoriaporpartidoPK());
        }
        categoriaporpartido.getCategoriaporpartidoPK().setIdpartido(categoriaporpartido.getPartido().getIdpartido());
        categoriaporpartido.getCategoriaporpartidoPK().setIdcategoria(categoriaporpartido.getCategoria().getIdcategoria());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categoria categoria = categoriaporpartido.getCategoria();
            if (categoria != null) {
                categoria = em.getReference(categoria.getClass(), categoria.getIdcategoria());
                categoriaporpartido.setCategoria(categoria);
            }
            Partido partido = categoriaporpartido.getPartido();
            if (partido != null) {
                partido = em.getReference(partido.getClass(), partido.getIdpartido());
                categoriaporpartido.setPartido(partido);
            }
            em.persist(categoriaporpartido);
            if (categoria != null) {
                categoria.getCategoriaporpartidoList().add(categoriaporpartido);
                categoria = em.merge(categoria);
            }
            if (partido != null) {
                partido.getCategoriaporpartidoList().add(categoriaporpartido);
                partido = em.merge(partido);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCategoriaporpartido(categoriaporpartido.getCategoriaporpartidoPK()) != null) {
                throw new PreexistingEntityException("Categoriaporpartido " + categoriaporpartido + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Categoriaporpartido categoriaporpartido) throws NonexistentEntityException, Exception {
        categoriaporpartido.getCategoriaporpartidoPK().setIdpartido(categoriaporpartido.getPartido().getIdpartido());
        categoriaporpartido.getCategoriaporpartidoPK().setIdcategoria(categoriaporpartido.getCategoria().getIdcategoria());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categoriaporpartido persistentCategoriaporpartido = em.find(Categoriaporpartido.class, categoriaporpartido.getCategoriaporpartidoPK());
            Categoria categoriaOld = persistentCategoriaporpartido.getCategoria();
            Categoria categoriaNew = categoriaporpartido.getCategoria();
            Partido partidoOld = persistentCategoriaporpartido.getPartido();
            Partido partidoNew = categoriaporpartido.getPartido();
            if (categoriaNew != null) {
                categoriaNew = em.getReference(categoriaNew.getClass(), categoriaNew.getIdcategoria());
                categoriaporpartido.setCategoria(categoriaNew);
            }
            if (partidoNew != null) {
                partidoNew = em.getReference(partidoNew.getClass(), partidoNew.getIdpartido());
                categoriaporpartido.setPartido(partidoNew);
            }
            categoriaporpartido = em.merge(categoriaporpartido);
            if (categoriaOld != null && !categoriaOld.equals(categoriaNew)) {
                categoriaOld.getCategoriaporpartidoList().remove(categoriaporpartido);
                categoriaOld = em.merge(categoriaOld);
            }
            if (categoriaNew != null && !categoriaNew.equals(categoriaOld)) {
                categoriaNew.getCategoriaporpartidoList().add(categoriaporpartido);
                categoriaNew = em.merge(categoriaNew);
            }
            if (partidoOld != null && !partidoOld.equals(partidoNew)) {
                partidoOld.getCategoriaporpartidoList().remove(categoriaporpartido);
                partidoOld = em.merge(partidoOld);
            }
            if (partidoNew != null && !partidoNew.equals(partidoOld)) {
                partidoNew.getCategoriaporpartidoList().add(categoriaporpartido);
                partidoNew = em.merge(partidoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                CategoriaporpartidoPK id = categoriaporpartido.getCategoriaporpartidoPK();
                if (findCategoriaporpartido(id) == null) {
                    throw new NonexistentEntityException("The categoriaporpartido with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(CategoriaporpartidoPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categoriaporpartido categoriaporpartido;
            try {
                categoriaporpartido = em.getReference(Categoriaporpartido.class, id);
                categoriaporpartido.getCategoriaporpartidoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The categoriaporpartido with id " + id + " no longer exists.", enfe);
            }
            Categoria categoria = categoriaporpartido.getCategoria();
            if (categoria != null) {
                categoria.getCategoriaporpartidoList().remove(categoriaporpartido);
                categoria = em.merge(categoria);
            }
            Partido partido = categoriaporpartido.getPartido();
            if (partido != null) {
                partido.getCategoriaporpartidoList().remove(categoriaporpartido);
                partido = em.merge(partido);
            }
            em.remove(categoriaporpartido);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Categoriaporpartido> findCategoriaporpartidoEntities() {
        return findCategoriaporpartidoEntities(true, -1, -1);
    }

    public List<Categoriaporpartido> findCategoriaporpartidoEntities(int maxResults, int firstResult) {
        return findCategoriaporpartidoEntities(false, maxResults, firstResult);
    }

    private List<Categoriaporpartido> findCategoriaporpartidoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Categoriaporpartido.class));
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

    public Categoriaporpartido findCategoriaporpartido(CategoriaporpartidoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Categoriaporpartido.class, id);
        } finally {
            em.close();
        }
    }

    public int getCategoriaporpartidoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Categoriaporpartido> rt = cq.from(Categoriaporpartido.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
