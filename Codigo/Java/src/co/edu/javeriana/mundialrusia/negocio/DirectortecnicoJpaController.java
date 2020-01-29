/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.javeriana.mundialrusia.negocio;

import co.edu.javeriana.mundialrusia.datos.Directortecnico;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import co.edu.javeriana.mundialrusia.datos.Equipo;
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
public class DirectortecnicoJpaController implements Serializable {

    public DirectortecnicoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Directortecnico directortecnico) throws PreexistingEntityException, Exception {
        if (directortecnico.getEquipoList() == null) {
            directortecnico.setEquipoList(new ArrayList<Equipo>());
        }
        if (directortecnico.getEquipoList1() == null) {
            directortecnico.setEquipoList1(new ArrayList<Equipo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Equipo> attachedEquipoList = new ArrayList<Equipo>();
            for (Equipo equipoListEquipoToAttach : directortecnico.getEquipoList()) {
                equipoListEquipoToAttach = em.getReference(equipoListEquipoToAttach.getClass(), equipoListEquipoToAttach.getIdequipo());
                attachedEquipoList.add(equipoListEquipoToAttach);
            }
            directortecnico.setEquipoList(attachedEquipoList);
            List<Equipo> attachedEquipoList1 = new ArrayList<Equipo>();
            for (Equipo equipoList1EquipoToAttach : directortecnico.getEquipoList1()) {
                equipoList1EquipoToAttach = em.getReference(equipoList1EquipoToAttach.getClass(), equipoList1EquipoToAttach.getIdequipo());
                attachedEquipoList1.add(equipoList1EquipoToAttach);
            }
            directortecnico.setEquipoList1(attachedEquipoList1);
            em.persist(directortecnico);
            for (Equipo equipoListEquipo : directortecnico.getEquipoList()) {
                Directortecnico oldIddirectortecnicoOfEquipoListEquipo = equipoListEquipo.getIddirectortecnico();
                equipoListEquipo.setIddirectortecnico(directortecnico);
                equipoListEquipo = em.merge(equipoListEquipo);
                if (oldIddirectortecnicoOfEquipoListEquipo != null) {
                    oldIddirectortecnicoOfEquipoListEquipo.getEquipoList().remove(equipoListEquipo);
                    oldIddirectortecnicoOfEquipoListEquipo = em.merge(oldIddirectortecnicoOfEquipoListEquipo);
                }
            }
            for (Equipo equipoList1Equipo : directortecnico.getEquipoList1()) {
                Directortecnico oldIdasistentetecnicoOfEquipoList1Equipo = equipoList1Equipo.getIdasistentetecnico();
                equipoList1Equipo.setIdasistentetecnico(directortecnico);
                equipoList1Equipo = em.merge(equipoList1Equipo);
                if (oldIdasistentetecnicoOfEquipoList1Equipo != null) {
                    oldIdasistentetecnicoOfEquipoList1Equipo.getEquipoList1().remove(equipoList1Equipo);
                    oldIdasistentetecnicoOfEquipoList1Equipo = em.merge(oldIdasistentetecnicoOfEquipoList1Equipo);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDirectortecnico(directortecnico.getIddirectortecnico()) != null) {
                throw new PreexistingEntityException("Directortecnico " + directortecnico + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Directortecnico directortecnico) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Directortecnico persistentDirectortecnico = em.find(Directortecnico.class, directortecnico.getIddirectortecnico());
            List<Equipo> equipoListOld = persistentDirectortecnico.getEquipoList();
            List<Equipo> equipoListNew = directortecnico.getEquipoList();
            List<Equipo> equipoList1Old = persistentDirectortecnico.getEquipoList1();
            List<Equipo> equipoList1New = directortecnico.getEquipoList1();
            List<String> illegalOrphanMessages = null;
            for (Equipo equipoListOldEquipo : equipoListOld) {
                if (!equipoListNew.contains(equipoListOldEquipo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Equipo " + equipoListOldEquipo + " since its iddirectortecnico field is not nullable.");
                }
            }
            for (Equipo equipoList1OldEquipo : equipoList1Old) {
                if (!equipoList1New.contains(equipoList1OldEquipo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Equipo " + equipoList1OldEquipo + " since its idasistentetecnico field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Equipo> attachedEquipoListNew = new ArrayList<Equipo>();
            for (Equipo equipoListNewEquipoToAttach : equipoListNew) {
                equipoListNewEquipoToAttach = em.getReference(equipoListNewEquipoToAttach.getClass(), equipoListNewEquipoToAttach.getIdequipo());
                attachedEquipoListNew.add(equipoListNewEquipoToAttach);
            }
            equipoListNew = attachedEquipoListNew;
            directortecnico.setEquipoList(equipoListNew);
            List<Equipo> attachedEquipoList1New = new ArrayList<Equipo>();
            for (Equipo equipoList1NewEquipoToAttach : equipoList1New) {
                equipoList1NewEquipoToAttach = em.getReference(equipoList1NewEquipoToAttach.getClass(), equipoList1NewEquipoToAttach.getIdequipo());
                attachedEquipoList1New.add(equipoList1NewEquipoToAttach);
            }
            equipoList1New = attachedEquipoList1New;
            directortecnico.setEquipoList1(equipoList1New);
            directortecnico = em.merge(directortecnico);
            for (Equipo equipoListNewEquipo : equipoListNew) {
                if (!equipoListOld.contains(equipoListNewEquipo)) {
                    Directortecnico oldIddirectortecnicoOfEquipoListNewEquipo = equipoListNewEquipo.getIddirectortecnico();
                    equipoListNewEquipo.setIddirectortecnico(directortecnico);
                    equipoListNewEquipo = em.merge(equipoListNewEquipo);
                    if (oldIddirectortecnicoOfEquipoListNewEquipo != null && !oldIddirectortecnicoOfEquipoListNewEquipo.equals(directortecnico)) {
                        oldIddirectortecnicoOfEquipoListNewEquipo.getEquipoList().remove(equipoListNewEquipo);
                        oldIddirectortecnicoOfEquipoListNewEquipo = em.merge(oldIddirectortecnicoOfEquipoListNewEquipo);
                    }
                }
            }
            for (Equipo equipoList1NewEquipo : equipoList1New) {
                if (!equipoList1Old.contains(equipoList1NewEquipo)) {
                    Directortecnico oldIdasistentetecnicoOfEquipoList1NewEquipo = equipoList1NewEquipo.getIdasistentetecnico();
                    equipoList1NewEquipo.setIdasistentetecnico(directortecnico);
                    equipoList1NewEquipo = em.merge(equipoList1NewEquipo);
                    if (oldIdasistentetecnicoOfEquipoList1NewEquipo != null && !oldIdasistentetecnicoOfEquipoList1NewEquipo.equals(directortecnico)) {
                        oldIdasistentetecnicoOfEquipoList1NewEquipo.getEquipoList1().remove(equipoList1NewEquipo);
                        oldIdasistentetecnicoOfEquipoList1NewEquipo = em.merge(oldIdasistentetecnicoOfEquipoList1NewEquipo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Short id = directortecnico.getIddirectortecnico();
                if (findDirectortecnico(id) == null) {
                    throw new NonexistentEntityException("The directortecnico with id " + id + " no longer exists.");
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
            Directortecnico directortecnico;
            try {
                directortecnico = em.getReference(Directortecnico.class, id);
                directortecnico.getIddirectortecnico();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The directortecnico with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Equipo> equipoListOrphanCheck = directortecnico.getEquipoList();
            for (Equipo equipoListOrphanCheckEquipo : equipoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Directortecnico (" + directortecnico + ") cannot be destroyed since the Equipo " + equipoListOrphanCheckEquipo + " in its equipoList field has a non-nullable iddirectortecnico field.");
            }
            List<Equipo> equipoList1OrphanCheck = directortecnico.getEquipoList1();
            for (Equipo equipoList1OrphanCheckEquipo : equipoList1OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Directortecnico (" + directortecnico + ") cannot be destroyed since the Equipo " + equipoList1OrphanCheckEquipo + " in its equipoList1 field has a non-nullable idasistentetecnico field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(directortecnico);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Directortecnico> findDirectortecnicoEntities() {
        return findDirectortecnicoEntities(true, -1, -1);
    }

    public List<Directortecnico> findDirectortecnicoEntities(int maxResults, int firstResult) {
        return findDirectortecnicoEntities(false, maxResults, firstResult);
    }

    private List<Directortecnico> findDirectortecnicoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Directortecnico.class));
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

    public Directortecnico findDirectortecnico(Short id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Directortecnico.class, id);
        } finally {
            em.close();
        }
    }

    public int getDirectortecnicoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Directortecnico> rt = cq.from(Directortecnico.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
