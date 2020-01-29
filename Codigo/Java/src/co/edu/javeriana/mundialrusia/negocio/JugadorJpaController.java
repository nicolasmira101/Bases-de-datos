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
import co.edu.javeriana.mundialrusia.datos.Equipo;
import co.edu.javeriana.mundialrusia.datos.Pais;
import co.edu.javeriana.mundialrusia.datos.Club;
import java.util.ArrayList;
import java.util.List;
import co.edu.javeriana.mundialrusia.datos.Gol;
import co.edu.javeriana.mundialrusia.datos.Jugador;
import co.edu.javeriana.mundialrusia.datos.Tarjeta;
import co.edu.javeriana.mundialrusia.datos.Jugadorporpartido;
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
public class JugadorJpaController implements Serializable {

    public JugadorJpaController() {
        this.emf = Persistence.createEntityManagerFactory("MundialRusiaPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Jugador jugador) throws PreexistingEntityException, Exception {
        if (jugador.getClubList() == null) {
            jugador.setClubList(new ArrayList<Club>());
        }
        if (jugador.getGolList() == null) {
            jugador.setGolList(new ArrayList<Gol>());
        }
        if (jugador.getTarjetaList() == null) {
            jugador.setTarjetaList(new ArrayList<Tarjeta>());
        }
        if (jugador.getJugadorporpartidoList() == null) {
            jugador.setJugadorporpartidoList(new ArrayList<Jugadorporpartido>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Equipo idequipo = jugador.getIdequipo();
            if (idequipo != null) {
                idequipo = em.getReference(idequipo.getClass(), idequipo.getIdequipo());
                jugador.setIdequipo(idequipo);
            }
            Pais idpais = jugador.getIdpais();
            if (idpais != null) {
                idpais = em.getReference(idpais.getClass(), idpais.getIdpais());
                jugador.setIdpais(idpais);
            }
            List<Club> attachedClubList = new ArrayList<Club>();
            for (Club clubListClubToAttach : jugador.getClubList()) {
                clubListClubToAttach = em.getReference(clubListClubToAttach.getClass(), clubListClubToAttach.getIdclub());
                attachedClubList.add(clubListClubToAttach);
            }
            jugador.setClubList(attachedClubList);
            List<Gol> attachedGolList = new ArrayList<Gol>();
            for (Gol golListGolToAttach : jugador.getGolList()) {
                golListGolToAttach = em.getReference(golListGolToAttach.getClass(), golListGolToAttach.getIdgol());
                attachedGolList.add(golListGolToAttach);
            }
            jugador.setGolList(attachedGolList);
            List<Tarjeta> attachedTarjetaList = new ArrayList<Tarjeta>();
            for (Tarjeta tarjetaListTarjetaToAttach : jugador.getTarjetaList()) {
                tarjetaListTarjetaToAttach = em.getReference(tarjetaListTarjetaToAttach.getClass(), tarjetaListTarjetaToAttach.getIdtarjeta());
                attachedTarjetaList.add(tarjetaListTarjetaToAttach);
            }
            jugador.setTarjetaList(attachedTarjetaList);
            List<Jugadorporpartido> attachedJugadorporpartidoList = new ArrayList<Jugadorporpartido>();
            for (Jugadorporpartido jugadorporpartidoListJugadorporpartidoToAttach : jugador.getJugadorporpartidoList()) {
                jugadorporpartidoListJugadorporpartidoToAttach = em.getReference(jugadorporpartidoListJugadorporpartidoToAttach.getClass(), jugadorporpartidoListJugadorporpartidoToAttach.getJugadorporpartidoPK());
                attachedJugadorporpartidoList.add(jugadorporpartidoListJugadorporpartidoToAttach);
            }
            jugador.setJugadorporpartidoList(attachedJugadorporpartidoList);
            em.persist(jugador);
            if (idequipo != null) {
                idequipo.getJugadorList().add(jugador);
                idequipo = em.merge(idequipo);
            }
            if (idpais != null) {
                idpais.getJugadorList().add(jugador);
                idpais = em.merge(idpais);
            }
            for (Club clubListClub : jugador.getClubList()) {
                clubListClub.getJugadorList().add(jugador);
                clubListClub = em.merge(clubListClub);
            }
            for (Gol golListGol : jugador.getGolList()) {
                Jugador oldIdjugadorOfGolListGol = golListGol.getIdjugador();
                golListGol.setIdjugador(jugador);
                golListGol = em.merge(golListGol);
                if (oldIdjugadorOfGolListGol != null) {
                    oldIdjugadorOfGolListGol.getGolList().remove(golListGol);
                    oldIdjugadorOfGolListGol = em.merge(oldIdjugadorOfGolListGol);
                }
            }
            for (Tarjeta tarjetaListTarjeta : jugador.getTarjetaList()) {
                Jugador oldIdjugadorOfTarjetaListTarjeta = tarjetaListTarjeta.getIdjugador();
                tarjetaListTarjeta.setIdjugador(jugador);
                tarjetaListTarjeta = em.merge(tarjetaListTarjeta);
                if (oldIdjugadorOfTarjetaListTarjeta != null) {
                    oldIdjugadorOfTarjetaListTarjeta.getTarjetaList().remove(tarjetaListTarjeta);
                    oldIdjugadorOfTarjetaListTarjeta = em.merge(oldIdjugadorOfTarjetaListTarjeta);
                }
            }
            for (Jugadorporpartido jugadorporpartidoListJugadorporpartido : jugador.getJugadorporpartidoList()) {
                Jugador oldJugadorOfJugadorporpartidoListJugadorporpartido = jugadorporpartidoListJugadorporpartido.getJugador();
                jugadorporpartidoListJugadorporpartido.setJugador(jugador);
                jugadorporpartidoListJugadorporpartido = em.merge(jugadorporpartidoListJugadorporpartido);
                if (oldJugadorOfJugadorporpartidoListJugadorporpartido != null) {
                    oldJugadorOfJugadorporpartidoListJugadorporpartido.getJugadorporpartidoList().remove(jugadorporpartidoListJugadorporpartido);
                    oldJugadorOfJugadorporpartidoListJugadorporpartido = em.merge(oldJugadorOfJugadorporpartidoListJugadorporpartido);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findJugador(jugador.getIdjugador()) != null) {
                throw new PreexistingEntityException("Jugador " + jugador + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Jugador jugador) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Jugador persistentJugador = em.find(Jugador.class, jugador.getIdjugador());
            Equipo idequipoOld = persistentJugador.getIdequipo();
            Equipo idequipoNew = jugador.getIdequipo();
            Pais idpaisOld = persistentJugador.getIdpais();
            Pais idpaisNew = jugador.getIdpais();
            List<Club> clubListOld = persistentJugador.getClubList();
            List<Club> clubListNew = jugador.getClubList();
            List<Gol> golListOld = persistentJugador.getGolList();
            List<Gol> golListNew = jugador.getGolList();
            List<Tarjeta> tarjetaListOld = persistentJugador.getTarjetaList();
            List<Tarjeta> tarjetaListNew = jugador.getTarjetaList();
            List<Jugadorporpartido> jugadorporpartidoListOld = persistentJugador.getJugadorporpartidoList();
            List<Jugadorporpartido> jugadorporpartidoListNew = jugador.getJugadorporpartidoList();
            List<String> illegalOrphanMessages = null;
            for (Gol golListOldGol : golListOld) {
                if (!golListNew.contains(golListOldGol)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Gol " + golListOldGol + " since its idjugador field is not nullable.");
                }
            }
            for (Tarjeta tarjetaListOldTarjeta : tarjetaListOld) {
                if (!tarjetaListNew.contains(tarjetaListOldTarjeta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Tarjeta " + tarjetaListOldTarjeta + " since its idjugador field is not nullable.");
                }
            }
            for (Jugadorporpartido jugadorporpartidoListOldJugadorporpartido : jugadorporpartidoListOld) {
                if (!jugadorporpartidoListNew.contains(jugadorporpartidoListOldJugadorporpartido)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Jugadorporpartido " + jugadorporpartidoListOldJugadorporpartido + " since its jugador field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idequipoNew != null) {
                idequipoNew = em.getReference(idequipoNew.getClass(), idequipoNew.getIdequipo());
                jugador.setIdequipo(idequipoNew);
            }
            if (idpaisNew != null) {
                idpaisNew = em.getReference(idpaisNew.getClass(), idpaisNew.getIdpais());
                jugador.setIdpais(idpaisNew);
            }
            List<Club> attachedClubListNew = new ArrayList<Club>();
            for (Club clubListNewClubToAttach : clubListNew) {
                clubListNewClubToAttach = em.getReference(clubListNewClubToAttach.getClass(), clubListNewClubToAttach.getIdclub());
                attachedClubListNew.add(clubListNewClubToAttach);
            }
            clubListNew = attachedClubListNew;
            jugador.setClubList(clubListNew);
            List<Gol> attachedGolListNew = new ArrayList<Gol>();
            for (Gol golListNewGolToAttach : golListNew) {
                golListNewGolToAttach = em.getReference(golListNewGolToAttach.getClass(), golListNewGolToAttach.getIdgol());
                attachedGolListNew.add(golListNewGolToAttach);
            }
            golListNew = attachedGolListNew;
            jugador.setGolList(golListNew);
            List<Tarjeta> attachedTarjetaListNew = new ArrayList<Tarjeta>();
            for (Tarjeta tarjetaListNewTarjetaToAttach : tarjetaListNew) {
                tarjetaListNewTarjetaToAttach = em.getReference(tarjetaListNewTarjetaToAttach.getClass(), tarjetaListNewTarjetaToAttach.getIdtarjeta());
                attachedTarjetaListNew.add(tarjetaListNewTarjetaToAttach);
            }
            tarjetaListNew = attachedTarjetaListNew;
            jugador.setTarjetaList(tarjetaListNew);
            List<Jugadorporpartido> attachedJugadorporpartidoListNew = new ArrayList<Jugadorporpartido>();
            for (Jugadorporpartido jugadorporpartidoListNewJugadorporpartidoToAttach : jugadorporpartidoListNew) {
                jugadorporpartidoListNewJugadorporpartidoToAttach = em.getReference(jugadorporpartidoListNewJugadorporpartidoToAttach.getClass(), jugadorporpartidoListNewJugadorporpartidoToAttach.getJugadorporpartidoPK());
                attachedJugadorporpartidoListNew.add(jugadorporpartidoListNewJugadorporpartidoToAttach);
            }
            jugadorporpartidoListNew = attachedJugadorporpartidoListNew;
            jugador.setJugadorporpartidoList(jugadorporpartidoListNew);
            jugador = em.merge(jugador);
            if (idequipoOld != null && !idequipoOld.equals(idequipoNew)) {
                idequipoOld.getJugadorList().remove(jugador);
                idequipoOld = em.merge(idequipoOld);
            }
            if (idequipoNew != null && !idequipoNew.equals(idequipoOld)) {
                idequipoNew.getJugadorList().add(jugador);
                idequipoNew = em.merge(idequipoNew);
            }
            if (idpaisOld != null && !idpaisOld.equals(idpaisNew)) {
                idpaisOld.getJugadorList().remove(jugador);
                idpaisOld = em.merge(idpaisOld);
            }
            if (idpaisNew != null && !idpaisNew.equals(idpaisOld)) {
                idpaisNew.getJugadorList().add(jugador);
                idpaisNew = em.merge(idpaisNew);
            }
            for (Club clubListOldClub : clubListOld) {
                if (!clubListNew.contains(clubListOldClub)) {
                    clubListOldClub.getJugadorList().remove(jugador);
                    clubListOldClub = em.merge(clubListOldClub);
                }
            }
            for (Club clubListNewClub : clubListNew) {
                if (!clubListOld.contains(clubListNewClub)) {
                    clubListNewClub.getJugadorList().add(jugador);
                    clubListNewClub = em.merge(clubListNewClub);
                }
            }
            for (Gol golListNewGol : golListNew) {
                if (!golListOld.contains(golListNewGol)) {
                    Jugador oldIdjugadorOfGolListNewGol = golListNewGol.getIdjugador();
                    golListNewGol.setIdjugador(jugador);
                    golListNewGol = em.merge(golListNewGol);
                    if (oldIdjugadorOfGolListNewGol != null && !oldIdjugadorOfGolListNewGol.equals(jugador)) {
                        oldIdjugadorOfGolListNewGol.getGolList().remove(golListNewGol);
                        oldIdjugadorOfGolListNewGol = em.merge(oldIdjugadorOfGolListNewGol);
                    }
                }
            }
            for (Tarjeta tarjetaListNewTarjeta : tarjetaListNew) {
                if (!tarjetaListOld.contains(tarjetaListNewTarjeta)) {
                    Jugador oldIdjugadorOfTarjetaListNewTarjeta = tarjetaListNewTarjeta.getIdjugador();
                    tarjetaListNewTarjeta.setIdjugador(jugador);
                    tarjetaListNewTarjeta = em.merge(tarjetaListNewTarjeta);
                    if (oldIdjugadorOfTarjetaListNewTarjeta != null && !oldIdjugadorOfTarjetaListNewTarjeta.equals(jugador)) {
                        oldIdjugadorOfTarjetaListNewTarjeta.getTarjetaList().remove(tarjetaListNewTarjeta);
                        oldIdjugadorOfTarjetaListNewTarjeta = em.merge(oldIdjugadorOfTarjetaListNewTarjeta);
                    }
                }
            }
            for (Jugadorporpartido jugadorporpartidoListNewJugadorporpartido : jugadorporpartidoListNew) {
                if (!jugadorporpartidoListOld.contains(jugadorporpartidoListNewJugadorporpartido)) {
                    Jugador oldJugadorOfJugadorporpartidoListNewJugadorporpartido = jugadorporpartidoListNewJugadorporpartido.getJugador();
                    jugadorporpartidoListNewJugadorporpartido.setJugador(jugador);
                    jugadorporpartidoListNewJugadorporpartido = em.merge(jugadorporpartidoListNewJugadorporpartido);
                    if (oldJugadorOfJugadorporpartidoListNewJugadorporpartido != null && !oldJugadorOfJugadorporpartidoListNewJugadorporpartido.equals(jugador)) {
                        oldJugadorOfJugadorporpartidoListNewJugadorporpartido.getJugadorporpartidoList().remove(jugadorporpartidoListNewJugadorporpartido);
                        oldJugadorOfJugadorporpartidoListNewJugadorporpartido = em.merge(oldJugadorOfJugadorporpartidoListNewJugadorporpartido);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = jugador.getIdjugador();
                if (findJugador(id) == null) {
                    throw new NonexistentEntityException("The jugador with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Jugador jugador;
            try {
                jugador = em.getReference(Jugador.class, id);
                jugador.getIdjugador();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The jugador with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Gol> golListOrphanCheck = jugador.getGolList();
            for (Gol golListOrphanCheckGol : golListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Jugador (" + jugador + ") cannot be destroyed since the Gol " + golListOrphanCheckGol + " in its golList field has a non-nullable idjugador field.");
            }
            List<Tarjeta> tarjetaListOrphanCheck = jugador.getTarjetaList();
            for (Tarjeta tarjetaListOrphanCheckTarjeta : tarjetaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Jugador (" + jugador + ") cannot be destroyed since the Tarjeta " + tarjetaListOrphanCheckTarjeta + " in its tarjetaList field has a non-nullable idjugador field.");
            }
            List<Jugadorporpartido> jugadorporpartidoListOrphanCheck = jugador.getJugadorporpartidoList();
            for (Jugadorporpartido jugadorporpartidoListOrphanCheckJugadorporpartido : jugadorporpartidoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Jugador (" + jugador + ") cannot be destroyed since the Jugadorporpartido " + jugadorporpartidoListOrphanCheckJugadorporpartido + " in its jugadorporpartidoList field has a non-nullable jugador field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Equipo idequipo = jugador.getIdequipo();
            if (idequipo != null) {
                idequipo.getJugadorList().remove(jugador);
                idequipo = em.merge(idequipo);
            }
            Pais idpais = jugador.getIdpais();
            if (idpais != null) {
                idpais.getJugadorList().remove(jugador);
                idpais = em.merge(idpais);
            }
            List<Club> clubList = jugador.getClubList();
            for (Club clubListClub : clubList) {
                clubListClub.getJugadorList().remove(jugador);
                clubListClub = em.merge(clubListClub);
            }
            em.remove(jugador);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Jugador> findJugadorEntities() {
        return findJugadorEntities(true, -1, -1);
    }

    public List<Jugador> findJugadorEntities(int maxResults, int firstResult) {
        return findJugadorEntities(false, maxResults, firstResult);
    }

    private List<Jugador> findJugadorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Jugador.class));
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

    public Jugador findJugador(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Jugador.class, id);
        } finally {
            em.close();
        }
    }

    public int getJugadorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Jugador> rt = cq.from(Jugador.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
