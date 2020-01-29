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
import co.edu.javeriana.mundialrusia.datos.Categoriaporestadio;
import co.edu.javeriana.mundialrusia.datos.CategoriaporestadioPK;
import co.edu.javeriana.mundialrusia.datos.Estadio;
import co.edu.javeriana.mundialrusia.negocio.exceptions.NonexistentEntityException;
import co.edu.javeriana.mundialrusia.negocio.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author nicolasmiranda
 */
public class CategoriaporestadioJpaController implements Serializable {

    public CategoriaporestadioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Categoriaporestadio categoriaporestadio) throws PreexistingEntityException, Exception {
        if (categoriaporestadio.getCategoriaporestadioPK() == null) {
            categoriaporestadio.setCategoriaporestadioPK(new CategoriaporestadioPK());
        }
        categoriaporestadio.getCategoriaporestadioPK().setIdestadio(categoriaporestadio.getEstadio().getIdestadio());
        categoriaporestadio.getCategoriaporestadioPK().setIdcategoria(categoriaporestadio.getCategoria().getIdcategoria());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categoria categoria = categoriaporestadio.getCategoria();
            if (categoria != null) {
                categoria = em.getReference(categoria.getClass(), categoria.getIdcategoria());
                categoriaporestadio.setCategoria(categoria);
            }
            Estadio estadio = categoriaporestadio.getEstadio();
            if (estadio != null) {
                estadio = em.getReference(estadio.getClass(), estadio.getIdestadio());
                categoriaporestadio.setEstadio(estadio);
            }
            em.persist(categoriaporestadio);
            if (categoria != null) {
                categoria.getCategoriaporestadioList().add(categoriaporestadio);
                categoria = em.merge(categoria);
            }
            if (estadio != null) {
                estadio.getCategoriaporestadioList().add(categoriaporestadio);
                estadio = em.merge(estadio);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCategoriaporestadio(categoriaporestadio.getCategoriaporestadioPK()) != null) {
                throw new PreexistingEntityException("Categoriaporestadio " + categoriaporestadio + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Categoriaporestadio categoriaporestadio) throws NonexistentEntityException, Exception {
        categoriaporestadio.getCategoriaporestadioPK().setIdestadio(categoriaporestadio.getEstadio().getIdestadio());
        categoriaporestadio.getCategoriaporestadioPK().setIdcategoria(categoriaporestadio.getCategoria().getIdcategoria());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categoriaporestadio persistentCategoriaporestadio = em.find(Categoriaporestadio.class, categoriaporestadio.getCategoriaporestadioPK());
            Categoria categoriaOld = persistentCategoriaporestadio.getCategoria();
            Categoria categoriaNew = categoriaporestadio.getCategoria();
            Estadio estadioOld = persistentCategoriaporestadio.getEstadio();
            Estadio estadioNew = categoriaporestadio.getEstadio();
            if (categoriaNew != null) {
                categoriaNew = em.getReference(categoriaNew.getClass(), categoriaNew.getIdcategoria());
                categoriaporestadio.setCategoria(categoriaNew);
            }
            if (estadioNew != null) {
                estadioNew = em.getReference(estadioNew.getClass(), estadioNew.getIdestadio());
                categoriaporestadio.setEstadio(estadioNew);
            }
            categoriaporestadio = em.merge(categoriaporestadio);
            if (categoriaOld != null && !categoriaOld.equals(categoriaNew)) {
                categoriaOld.getCategoriaporestadioList().remove(categoriaporestadio);
                categoriaOld = em.merge(categoriaOld);
            }
            if (categoriaNew != null && !categoriaNew.equals(categoriaOld)) {
                categoriaNew.getCategoriaporestadioList().add(categoriaporestadio);
                categoriaNew = em.merge(categoriaNew);
            }
            if (estadioOld != null && !estadioOld.equals(estadioNew)) {
                estadioOld.getCategoriaporestadioList().remove(categoriaporestadio);
                estadioOld = em.merge(estadioOld);
            }
            if (estadioNew != null && !estadioNew.equals(estadioOld)) {
                estadioNew.getCategoriaporestadioList().add(categoriaporestadio);
                estadioNew = em.merge(estadioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                CategoriaporestadioPK id = categoriaporestadio.getCategoriaporestadioPK();
                if (findCategoriaporestadio(id) == null) {
                    throw new NonexistentEntityException("The categoriaporestadio with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(CategoriaporestadioPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categoriaporestadio categoriaporestadio;
            try {
                categoriaporestadio = em.getReference(Categoriaporestadio.class, id);
                categoriaporestadio.getCategoriaporestadioPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The categoriaporestadio with id " + id + " no longer exists.", enfe);
            }
            Categoria categoria = categoriaporestadio.getCategoria();
            if (categoria != null) {
                categoria.getCategoriaporestadioList().remove(categoriaporestadio);
                categoria = em.merge(categoria);
            }
            Estadio estadio = categoriaporestadio.getEstadio();
            if (estadio != null) {
                estadio.getCategoriaporestadioList().remove(categoriaporestadio);
                estadio = em.merge(estadio);
            }
            em.remove(categoriaporestadio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Categoriaporestadio> findCategoriaporestadioEntities() {
        return findCategoriaporestadioEntities(true, -1, -1);
    }

    public List<Categoriaporestadio> findCategoriaporestadioEntities(int maxResults, int firstResult) {
        return findCategoriaporestadioEntities(false, maxResults, firstResult);
    }

    private List<Categoriaporestadio> findCategoriaporestadioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Categoriaporestadio.class));
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

    public Categoriaporestadio findCategoriaporestadio(CategoriaporestadioPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Categoriaporestadio.class, id);
        } finally {
            em.close();
        }
    }

    public int getCategoriaporestadioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Categoriaporestadio> rt = cq.from(Categoriaporestadio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
