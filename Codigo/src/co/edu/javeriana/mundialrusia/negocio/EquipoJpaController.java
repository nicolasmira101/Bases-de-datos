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
import co.edu.javeriana.mundialrusia.datos.Directortecnico;
import co.edu.javeriana.mundialrusia.datos.Equipo;
import co.edu.javeriana.mundialrusia.datos.Jugador;
import java.util.ArrayList;
import java.util.List;
import co.edu.javeriana.mundialrusia.datos.Partido;
import co.edu.javeriana.mundialrusia.negocio.exceptions.IllegalOrphanException;
import co.edu.javeriana.mundialrusia.negocio.exceptions.NonexistentEntityException;
import co.edu.javeriana.mundialrusia.negocio.exceptions.PreexistingEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author nicolasmiranda
 */
public class EquipoJpaController implements Serializable {

    public EquipoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Equipo equipo) throws PreexistingEntityException, Exception {
        if (equipo.getJugadorList() == null) {
            equipo.setJugadorList(new ArrayList<Jugador>());
        }
        if (equipo.getPartidoList() == null) {
            equipo.setPartidoList(new ArrayList<Partido>());
        }
        if (equipo.getPartidoList1() == null) {
            equipo.setPartidoList1(new ArrayList<Partido>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Directortecnico iddirectortecnico = equipo.getIddirectortecnico();
            if (iddirectortecnico != null) {
                iddirectortecnico = em.getReference(iddirectortecnico.getClass(), iddirectortecnico.getIddirectortecnico());
                equipo.setIddirectortecnico(iddirectortecnico);
            }
            Directortecnico idasistentetecnico = equipo.getIdasistentetecnico();
            if (idasistentetecnico != null) {
                idasistentetecnico = em.getReference(idasistentetecnico.getClass(), idasistentetecnico.getIddirectortecnico());
                equipo.setIdasistentetecnico(idasistentetecnico);
            }
            List<Jugador> attachedJugadorList = new ArrayList<Jugador>();
            for (Jugador jugadorListJugadorToAttach : equipo.getJugadorList()) {
                jugadorListJugadorToAttach = em.getReference(jugadorListJugadorToAttach.getClass(), jugadorListJugadorToAttach.getIdjugador());
                attachedJugadorList.add(jugadorListJugadorToAttach);
            }
            equipo.setJugadorList(attachedJugadorList);
            List<Partido> attachedPartidoList = new ArrayList<Partido>();
            for (Partido partidoListPartidoToAttach : equipo.getPartidoList()) {
                partidoListPartidoToAttach = em.getReference(partidoListPartidoToAttach.getClass(), partidoListPartidoToAttach.getIdpartido());
                attachedPartidoList.add(partidoListPartidoToAttach);
            }
            equipo.setPartidoList(attachedPartidoList);
            List<Partido> attachedPartidoList1 = new ArrayList<Partido>();
            for (Partido partidoList1PartidoToAttach : equipo.getPartidoList1()) {
                partidoList1PartidoToAttach = em.getReference(partidoList1PartidoToAttach.getClass(), partidoList1PartidoToAttach.getIdpartido());
                attachedPartidoList1.add(partidoList1PartidoToAttach);
            }
            equipo.setPartidoList1(attachedPartidoList1);
            em.persist(equipo);
            if (iddirectortecnico != null) {
                iddirectortecnico.getEquipoList().add(equipo);
                iddirectortecnico = em.merge(iddirectortecnico);
            }
            if (idasistentetecnico != null) {
                idasistentetecnico.getEquipoList().add(equipo);
                idasistentetecnico = em.merge(idasistentetecnico);
            }
            for (Jugador jugadorListJugador : equipo.getJugadorList()) {
                Equipo oldIdequipoOfJugadorListJugador = jugadorListJugador.getIdequipo();
                jugadorListJugador.setIdequipo(equipo);
                jugadorListJugador = em.merge(jugadorListJugador);
                if (oldIdequipoOfJugadorListJugador != null) {
                    oldIdequipoOfJugadorListJugador.getJugadorList().remove(jugadorListJugador);
                    oldIdequipoOfJugadorListJugador = em.merge(oldIdequipoOfJugadorListJugador);
                }
            }
            for (Partido partidoListPartido : equipo.getPartidoList()) {
                Equipo oldIdequipovisitanteOfPartidoListPartido = partidoListPartido.getIdequipovisitante();
                partidoListPartido.setIdequipovisitante(equipo);
                partidoListPartido = em.merge(partidoListPartido);
                if (oldIdequipovisitanteOfPartidoListPartido != null) {
                    oldIdequipovisitanteOfPartidoListPartido.getPartidoList().remove(partidoListPartido);
                    oldIdequipovisitanteOfPartidoListPartido = em.merge(oldIdequipovisitanteOfPartidoListPartido);
                }
            }
            for (Partido partidoList1Partido : equipo.getPartidoList1()) {
                Equipo oldIdequipolocalOfPartidoList1Partido = partidoList1Partido.getIdequipolocal();
                partidoList1Partido.setIdequipolocal(equipo);
                partidoList1Partido = em.merge(partidoList1Partido);
                if (oldIdequipolocalOfPartidoList1Partido != null) {
                    oldIdequipolocalOfPartidoList1Partido.getPartidoList1().remove(partidoList1Partido);
                    oldIdequipolocalOfPartidoList1Partido = em.merge(oldIdequipolocalOfPartidoList1Partido);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEquipo(equipo.getIdequipo()) != null) {
                throw new PreexistingEntityException("Equipo " + equipo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Equipo equipo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Equipo persistentEquipo = em.find(Equipo.class, equipo.getIdequipo());
            Directortecnico iddirectortecnicoOld = persistentEquipo.getIddirectortecnico();
            Directortecnico iddirectortecnicoNew = equipo.getIddirectortecnico();
            Directortecnico idasistentetecnicoOld = persistentEquipo.getIdasistentetecnico();
            Directortecnico idasistentetecnicoNew = equipo.getIdasistentetecnico();
            List<Jugador> jugadorListOld = persistentEquipo.getJugadorList();
            List<Jugador> jugadorListNew = equipo.getJugadorList();
            List<Partido> partidoListOld = persistentEquipo.getPartidoList();
            List<Partido> partidoListNew = equipo.getPartidoList();
            List<Partido> partidoList1Old = persistentEquipo.getPartidoList1();
            List<Partido> partidoList1New = equipo.getPartidoList1();
            List<String> illegalOrphanMessages = null;
            for (Jugador jugadorListOldJugador : jugadorListOld) {
                if (!jugadorListNew.contains(jugadorListOldJugador)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Jugador " + jugadorListOldJugador + " since its idequipo field is not nullable.");
                }
            }
            for (Partido partidoListOldPartido : partidoListOld) {
                if (!partidoListNew.contains(partidoListOldPartido)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Partido " + partidoListOldPartido + " since its idequipovisitante field is not nullable.");
                }
            }
            for (Partido partidoList1OldPartido : partidoList1Old) {
                if (!partidoList1New.contains(partidoList1OldPartido)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Partido " + partidoList1OldPartido + " since its idequipolocal field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (iddirectortecnicoNew != null) {
                iddirectortecnicoNew = em.getReference(iddirectortecnicoNew.getClass(), iddirectortecnicoNew.getIddirectortecnico());
                equipo.setIddirectortecnico(iddirectortecnicoNew);
            }
            if (idasistentetecnicoNew != null) {
                idasistentetecnicoNew = em.getReference(idasistentetecnicoNew.getClass(), idasistentetecnicoNew.getIddirectortecnico());
                equipo.setIdasistentetecnico(idasistentetecnicoNew);
            }
            List<Jugador> attachedJugadorListNew = new ArrayList<Jugador>();
            for (Jugador jugadorListNewJugadorToAttach : jugadorListNew) {
                jugadorListNewJugadorToAttach = em.getReference(jugadorListNewJugadorToAttach.getClass(), jugadorListNewJugadorToAttach.getIdjugador());
                attachedJugadorListNew.add(jugadorListNewJugadorToAttach);
            }
            jugadorListNew = attachedJugadorListNew;
            equipo.setJugadorList(jugadorListNew);
            List<Partido> attachedPartidoListNew = new ArrayList<Partido>();
            for (Partido partidoListNewPartidoToAttach : partidoListNew) {
                partidoListNewPartidoToAttach = em.getReference(partidoListNewPartidoToAttach.getClass(), partidoListNewPartidoToAttach.getIdpartido());
                attachedPartidoListNew.add(partidoListNewPartidoToAttach);
            }
            partidoListNew = attachedPartidoListNew;
            equipo.setPartidoList(partidoListNew);
            List<Partido> attachedPartidoList1New = new ArrayList<Partido>();
            for (Partido partidoList1NewPartidoToAttach : partidoList1New) {
                partidoList1NewPartidoToAttach = em.getReference(partidoList1NewPartidoToAttach.getClass(), partidoList1NewPartidoToAttach.getIdpartido());
                attachedPartidoList1New.add(partidoList1NewPartidoToAttach);
            }
            partidoList1New = attachedPartidoList1New;
            equipo.setPartidoList1(partidoList1New);
            equipo = em.merge(equipo);
            if (iddirectortecnicoOld != null && !iddirectortecnicoOld.equals(iddirectortecnicoNew)) {
                iddirectortecnicoOld.getEquipoList().remove(equipo);
                iddirectortecnicoOld = em.merge(iddirectortecnicoOld);
            }
            if (iddirectortecnicoNew != null && !iddirectortecnicoNew.equals(iddirectortecnicoOld)) {
                iddirectortecnicoNew.getEquipoList().add(equipo);
                iddirectortecnicoNew = em.merge(iddirectortecnicoNew);
            }
            if (idasistentetecnicoOld != null && !idasistentetecnicoOld.equals(idasistentetecnicoNew)) {
                idasistentetecnicoOld.getEquipoList().remove(equipo);
                idasistentetecnicoOld = em.merge(idasistentetecnicoOld);
            }
            if (idasistentetecnicoNew != null && !idasistentetecnicoNew.equals(idasistentetecnicoOld)) {
                idasistentetecnicoNew.getEquipoList().add(equipo);
                idasistentetecnicoNew = em.merge(idasistentetecnicoNew);
            }
            for (Jugador jugadorListNewJugador : jugadorListNew) {
                if (!jugadorListOld.contains(jugadorListNewJugador)) {
                    Equipo oldIdequipoOfJugadorListNewJugador = jugadorListNewJugador.getIdequipo();
                    jugadorListNewJugador.setIdequipo(equipo);
                    jugadorListNewJugador = em.merge(jugadorListNewJugador);
                    if (oldIdequipoOfJugadorListNewJugador != null && !oldIdequipoOfJugadorListNewJugador.equals(equipo)) {
                        oldIdequipoOfJugadorListNewJugador.getJugadorList().remove(jugadorListNewJugador);
                        oldIdequipoOfJugadorListNewJugador = em.merge(oldIdequipoOfJugadorListNewJugador);
                    }
                }
            }
            for (Partido partidoListNewPartido : partidoListNew) {
                if (!partidoListOld.contains(partidoListNewPartido)) {
                    Equipo oldIdequipovisitanteOfPartidoListNewPartido = partidoListNewPartido.getIdequipovisitante();
                    partidoListNewPartido.setIdequipovisitante(equipo);
                    partidoListNewPartido = em.merge(partidoListNewPartido);
                    if (oldIdequipovisitanteOfPartidoListNewPartido != null && !oldIdequipovisitanteOfPartidoListNewPartido.equals(equipo)) {
                        oldIdequipovisitanteOfPartidoListNewPartido.getPartidoList().remove(partidoListNewPartido);
                        oldIdequipovisitanteOfPartidoListNewPartido = em.merge(oldIdequipovisitanteOfPartidoListNewPartido);
                    }
                }
            }
            for (Partido partidoList1NewPartido : partidoList1New) {
                if (!partidoList1Old.contains(partidoList1NewPartido)) {
                    Equipo oldIdequipolocalOfPartidoList1NewPartido = partidoList1NewPartido.getIdequipolocal();
                    partidoList1NewPartido.setIdequipolocal(equipo);
                    partidoList1NewPartido = em.merge(partidoList1NewPartido);
                    if (oldIdequipolocalOfPartidoList1NewPartido != null && !oldIdequipolocalOfPartidoList1NewPartido.equals(equipo)) {
                        oldIdequipolocalOfPartidoList1NewPartido.getPartidoList1().remove(partidoList1NewPartido);
                        oldIdequipolocalOfPartidoList1NewPartido = em.merge(oldIdequipolocalOfPartidoList1NewPartido);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Short id = equipo.getIdequipo();
                if (findEquipo(id) == null) {
                    throw new NonexistentEntityException("The equipo with id " + id + " no longer exists.");
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
            Equipo equipo;
            try {
                equipo = em.getReference(Equipo.class, id);
                equipo.getIdequipo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The equipo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Jugador> jugadorListOrphanCheck = equipo.getJugadorList();
            for (Jugador jugadorListOrphanCheckJugador : jugadorListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Equipo (" + equipo + ") cannot be destroyed since the Jugador " + jugadorListOrphanCheckJugador + " in its jugadorList field has a non-nullable idequipo field.");
            }
            List<Partido> partidoListOrphanCheck = equipo.getPartidoList();
            for (Partido partidoListOrphanCheckPartido : partidoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Equipo (" + equipo + ") cannot be destroyed since the Partido " + partidoListOrphanCheckPartido + " in its partidoList field has a non-nullable idequipovisitante field.");
            }
            List<Partido> partidoList1OrphanCheck = equipo.getPartidoList1();
            for (Partido partidoList1OrphanCheckPartido : partidoList1OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Equipo (" + equipo + ") cannot be destroyed since the Partido " + partidoList1OrphanCheckPartido + " in its partidoList1 field has a non-nullable idequipolocal field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Directortecnico iddirectortecnico = equipo.getIddirectortecnico();
            if (iddirectortecnico != null) {
                iddirectortecnico.getEquipoList().remove(equipo);
                iddirectortecnico = em.merge(iddirectortecnico);
            }
            Directortecnico idasistentetecnico = equipo.getIdasistentetecnico();
            if (idasistentetecnico != null) {
                idasistentetecnico.getEquipoList().remove(equipo);
                idasistentetecnico = em.merge(idasistentetecnico);
            }
            em.remove(equipo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Equipo> findEquipoEntities() {
        return findEquipoEntities(true, -1, -1);
    }

    public List<Equipo> findEquipoEntities(int maxResults, int firstResult) {
        return findEquipoEntities(false, maxResults, firstResult);
    }

    private List<Equipo> findEquipoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Equipo.class));
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

    public Equipo findEquipo(Short id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Equipo.class, id);
        } finally {
            em.close();
        }
    }

    public int getEquipoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Equipo> rt = cq.from(Equipo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
