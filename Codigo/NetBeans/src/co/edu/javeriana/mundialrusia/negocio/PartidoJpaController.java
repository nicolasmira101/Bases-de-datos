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
import co.edu.javeriana.mundialrusia.datos.Calendario;
import co.edu.javeriana.mundialrusia.datos.Equipo;
import co.edu.javeriana.mundialrusia.datos.Estadio;
import co.edu.javeriana.mundialrusia.datos.Gol;
import java.util.ArrayList;
import java.util.List;
import co.edu.javeriana.mundialrusia.datos.Categoriaporpartido;
import co.edu.javeriana.mundialrusia.datos.Juezporpartido;
import co.edu.javeriana.mundialrusia.datos.Tarjeta;
import co.edu.javeriana.mundialrusia.datos.Clienteporpartido;
import co.edu.javeriana.mundialrusia.datos.Jugadorporpartido;
import co.edu.javeriana.mundialrusia.datos.Partido;
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
public class PartidoJpaController implements Serializable {

    public PartidoJpaController() {
        this.emf = Persistence.createEntityManagerFactory("MundialRusiaPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Partido partido) throws PreexistingEntityException, Exception {
        if (partido.getGolList() == null) {
            partido.setGolList(new ArrayList<Gol>());
        }
        if (partido.getCategoriaporpartidoList() == null) {
            partido.setCategoriaporpartidoList(new ArrayList<Categoriaporpartido>());
        }
        if (partido.getJuezporpartidoList() == null) {
            partido.setJuezporpartidoList(new ArrayList<Juezporpartido>());
        }
        if (partido.getTarjetaList() == null) {
            partido.setTarjetaList(new ArrayList<Tarjeta>());
        }
        if (partido.getClienteporpartidoList() == null) {
            partido.setClienteporpartidoList(new ArrayList<Clienteporpartido>());
        }
        if (partido.getJugadorporpartidoList() == null) {
            partido.setJugadorporpartidoList(new ArrayList<Jugadorporpartido>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Calendario idcalendario = partido.getIdcalendario();
            if (idcalendario != null) {
                idcalendario = em.getReference(idcalendario.getClass(), idcalendario.getIdcalendario());
                partido.setIdcalendario(idcalendario);
            }
            Equipo idequipovisitante = partido.getIdequipovisitante();
            if (idequipovisitante != null) {
                idequipovisitante = em.getReference(idequipovisitante.getClass(), idequipovisitante.getIdequipo());
                partido.setIdequipovisitante(idequipovisitante);
            }
            Equipo idequipolocal = partido.getIdequipolocal();
            if (idequipolocal != null) {
                idequipolocal = em.getReference(idequipolocal.getClass(), idequipolocal.getIdequipo());
                partido.setIdequipolocal(idequipolocal);
            }
            Estadio idestadio = partido.getIdestadio();
            if (idestadio != null) {
                idestadio = em.getReference(idestadio.getClass(), idestadio.getIdestadio());
                partido.setIdestadio(idestadio);
            }
            List<Gol> attachedGolList = new ArrayList<Gol>();
            for (Gol golListGolToAttach : partido.getGolList()) {
                golListGolToAttach = em.getReference(golListGolToAttach.getClass(), golListGolToAttach.getIdgol());
                attachedGolList.add(golListGolToAttach);
            }
            partido.setGolList(attachedGolList);
            List<Categoriaporpartido> attachedCategoriaporpartidoList = new ArrayList<Categoriaporpartido>();
            for (Categoriaporpartido categoriaporpartidoListCategoriaporpartidoToAttach : partido.getCategoriaporpartidoList()) {
                categoriaporpartidoListCategoriaporpartidoToAttach = em.getReference(categoriaporpartidoListCategoriaporpartidoToAttach.getClass(), categoriaporpartidoListCategoriaporpartidoToAttach.getCategoriaporpartidoPK());
                attachedCategoriaporpartidoList.add(categoriaporpartidoListCategoriaporpartidoToAttach);
            }
            partido.setCategoriaporpartidoList(attachedCategoriaporpartidoList);
            List<Juezporpartido> attachedJuezporpartidoList = new ArrayList<Juezporpartido>();
            for (Juezporpartido juezporpartidoListJuezporpartidoToAttach : partido.getJuezporpartidoList()) {
                juezporpartidoListJuezporpartidoToAttach = em.getReference(juezporpartidoListJuezporpartidoToAttach.getClass(), juezporpartidoListJuezporpartidoToAttach.getJuezporpartidoPK());
                attachedJuezporpartidoList.add(juezporpartidoListJuezporpartidoToAttach);
            }
            partido.setJuezporpartidoList(attachedJuezporpartidoList);
            List<Tarjeta> attachedTarjetaList = new ArrayList<Tarjeta>();
            for (Tarjeta tarjetaListTarjetaToAttach : partido.getTarjetaList()) {
                tarjetaListTarjetaToAttach = em.getReference(tarjetaListTarjetaToAttach.getClass(), tarjetaListTarjetaToAttach.getIdtarjeta());
                attachedTarjetaList.add(tarjetaListTarjetaToAttach);
            }
            partido.setTarjetaList(attachedTarjetaList);
            List<Clienteporpartido> attachedClienteporpartidoList = new ArrayList<Clienteporpartido>();
            for (Clienteporpartido clienteporpartidoListClienteporpartidoToAttach : partido.getClienteporpartidoList()) {
                clienteporpartidoListClienteporpartidoToAttach = em.getReference(clienteporpartidoListClienteporpartidoToAttach.getClass(), clienteporpartidoListClienteporpartidoToAttach.getClienteporpartidoPK());
                attachedClienteporpartidoList.add(clienteporpartidoListClienteporpartidoToAttach);
            }
            partido.setClienteporpartidoList(attachedClienteporpartidoList);
            List<Jugadorporpartido> attachedJugadorporpartidoList = new ArrayList<Jugadorporpartido>();
            for (Jugadorporpartido jugadorporpartidoListJugadorporpartidoToAttach : partido.getJugadorporpartidoList()) {
                jugadorporpartidoListJugadorporpartidoToAttach = em.getReference(jugadorporpartidoListJugadorporpartidoToAttach.getClass(), jugadorporpartidoListJugadorporpartidoToAttach.getJugadorporpartidoPK());
                attachedJugadorporpartidoList.add(jugadorporpartidoListJugadorporpartidoToAttach);
            }
            partido.setJugadorporpartidoList(attachedJugadorporpartidoList);
            em.persist(partido);
            if (idcalendario != null) {
                idcalendario.getPartidoList().add(partido);
                idcalendario = em.merge(idcalendario);
            }
            if (idequipovisitante != null) {
                idequipovisitante.getPartidoList().add(partido);
                idequipovisitante = em.merge(idequipovisitante);
            }
            if (idequipolocal != null) {
                idequipolocal.getPartidoList().add(partido);
                idequipolocal = em.merge(idequipolocal);
            }
            if (idestadio != null) {
                idestadio.getPartidoList().add(partido);
                idestadio = em.merge(idestadio);
            }
            for (Gol golListGol : partido.getGolList()) {
                Partido oldIdpartidoOfGolListGol = golListGol.getIdpartido();
                golListGol.setIdpartido(partido);
                golListGol = em.merge(golListGol);
                if (oldIdpartidoOfGolListGol != null) {
                    oldIdpartidoOfGolListGol.getGolList().remove(golListGol);
                    oldIdpartidoOfGolListGol = em.merge(oldIdpartidoOfGolListGol);
                }
            }
            for (Categoriaporpartido categoriaporpartidoListCategoriaporpartido : partido.getCategoriaporpartidoList()) {
                Partido oldPartidoOfCategoriaporpartidoListCategoriaporpartido = categoriaporpartidoListCategoriaporpartido.getPartido();
                categoriaporpartidoListCategoriaporpartido.setPartido(partido);
                categoriaporpartidoListCategoriaporpartido = em.merge(categoriaporpartidoListCategoriaporpartido);
                if (oldPartidoOfCategoriaporpartidoListCategoriaporpartido != null) {
                    oldPartidoOfCategoriaporpartidoListCategoriaporpartido.getCategoriaporpartidoList().remove(categoriaporpartidoListCategoriaporpartido);
                    oldPartidoOfCategoriaporpartidoListCategoriaporpartido = em.merge(oldPartidoOfCategoriaporpartidoListCategoriaporpartido);
                }
            }
            for (Juezporpartido juezporpartidoListJuezporpartido : partido.getJuezporpartidoList()) {
                Partido oldPartidoOfJuezporpartidoListJuezporpartido = juezporpartidoListJuezporpartido.getPartido();
                juezporpartidoListJuezporpartido.setPartido(partido);
                juezporpartidoListJuezporpartido = em.merge(juezporpartidoListJuezporpartido);
                if (oldPartidoOfJuezporpartidoListJuezporpartido != null) {
                    oldPartidoOfJuezporpartidoListJuezporpartido.getJuezporpartidoList().remove(juezporpartidoListJuezporpartido);
                    oldPartidoOfJuezporpartidoListJuezporpartido = em.merge(oldPartidoOfJuezporpartidoListJuezporpartido);
                }
            }
            for (Tarjeta tarjetaListTarjeta : partido.getTarjetaList()) {
                Partido oldIdpartidoOfTarjetaListTarjeta = tarjetaListTarjeta.getIdpartido();
                tarjetaListTarjeta.setIdpartido(partido);
                tarjetaListTarjeta = em.merge(tarjetaListTarjeta);
                if (oldIdpartidoOfTarjetaListTarjeta != null) {
                    oldIdpartidoOfTarjetaListTarjeta.getTarjetaList().remove(tarjetaListTarjeta);
                    oldIdpartidoOfTarjetaListTarjeta = em.merge(oldIdpartidoOfTarjetaListTarjeta);
                }
            }
            for (Clienteporpartido clienteporpartidoListClienteporpartido : partido.getClienteporpartidoList()) {
                Partido oldPartidoOfClienteporpartidoListClienteporpartido = clienteporpartidoListClienteporpartido.getPartido();
                clienteporpartidoListClienteporpartido.setPartido(partido);
                clienteporpartidoListClienteporpartido = em.merge(clienteporpartidoListClienteporpartido);
                if (oldPartidoOfClienteporpartidoListClienteporpartido != null) {
                    oldPartidoOfClienteporpartidoListClienteporpartido.getClienteporpartidoList().remove(clienteporpartidoListClienteporpartido);
                    oldPartidoOfClienteporpartidoListClienteporpartido = em.merge(oldPartidoOfClienteporpartidoListClienteporpartido);
                }
            }
            for (Jugadorporpartido jugadorporpartidoListJugadorporpartido : partido.getJugadorporpartidoList()) {
                Partido oldPartidoOfJugadorporpartidoListJugadorporpartido = jugadorporpartidoListJugadorporpartido.getPartido();
                jugadorporpartidoListJugadorporpartido.setPartido(partido);
                jugadorporpartidoListJugadorporpartido = em.merge(jugadorporpartidoListJugadorporpartido);
                if (oldPartidoOfJugadorporpartidoListJugadorporpartido != null) {
                    oldPartidoOfJugadorporpartidoListJugadorporpartido.getJugadorporpartidoList().remove(jugadorporpartidoListJugadorporpartido);
                    oldPartidoOfJugadorporpartidoListJugadorporpartido = em.merge(oldPartidoOfJugadorporpartidoListJugadorporpartido);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPartido(partido.getIdpartido()) != null) {
                throw new PreexistingEntityException("Partido " + partido + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Partido partido) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Partido persistentPartido = em.find(Partido.class, partido.getIdpartido());
            Calendario idcalendarioOld = persistentPartido.getIdcalendario();
            Calendario idcalendarioNew = partido.getIdcalendario();
            Equipo idequipovisitanteOld = persistentPartido.getIdequipovisitante();
            Equipo idequipovisitanteNew = partido.getIdequipovisitante();
            Equipo idequipolocalOld = persistentPartido.getIdequipolocal();
            Equipo idequipolocalNew = partido.getIdequipolocal();
            Estadio idestadioOld = persistentPartido.getIdestadio();
            Estadio idestadioNew = partido.getIdestadio();
            List<Gol> golListOld = persistentPartido.getGolList();
            List<Gol> golListNew = partido.getGolList();
            List<Categoriaporpartido> categoriaporpartidoListOld = persistentPartido.getCategoriaporpartidoList();
            List<Categoriaporpartido> categoriaporpartidoListNew = partido.getCategoriaporpartidoList();
            List<Juezporpartido> juezporpartidoListOld = persistentPartido.getJuezporpartidoList();
            List<Juezporpartido> juezporpartidoListNew = partido.getJuezporpartidoList();
            List<Tarjeta> tarjetaListOld = persistentPartido.getTarjetaList();
            List<Tarjeta> tarjetaListNew = partido.getTarjetaList();
            List<Clienteporpartido> clienteporpartidoListOld = persistentPartido.getClienteporpartidoList();
            List<Clienteporpartido> clienteporpartidoListNew = partido.getClienteporpartidoList();
            List<Jugadorporpartido> jugadorporpartidoListOld = persistentPartido.getJugadorporpartidoList();
            List<Jugadorporpartido> jugadorporpartidoListNew = partido.getJugadorporpartidoList();
            List<String> illegalOrphanMessages = null;
            for (Gol golListOldGol : golListOld) {
                if (!golListNew.contains(golListOldGol)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Gol " + golListOldGol + " since its idpartido field is not nullable.");
                }
            }
            for (Categoriaporpartido categoriaporpartidoListOldCategoriaporpartido : categoriaporpartidoListOld) {
                if (!categoriaporpartidoListNew.contains(categoriaporpartidoListOldCategoriaporpartido)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Categoriaporpartido " + categoriaporpartidoListOldCategoriaporpartido + " since its partido field is not nullable.");
                }
            }
            for (Juezporpartido juezporpartidoListOldJuezporpartido : juezporpartidoListOld) {
                if (!juezporpartidoListNew.contains(juezporpartidoListOldJuezporpartido)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Juezporpartido " + juezporpartidoListOldJuezporpartido + " since its partido field is not nullable.");
                }
            }
            for (Tarjeta tarjetaListOldTarjeta : tarjetaListOld) {
                if (!tarjetaListNew.contains(tarjetaListOldTarjeta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Tarjeta " + tarjetaListOldTarjeta + " since its idpartido field is not nullable.");
                }
            }
            for (Clienteporpartido clienteporpartidoListOldClienteporpartido : clienteporpartidoListOld) {
                if (!clienteporpartidoListNew.contains(clienteporpartidoListOldClienteporpartido)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Clienteporpartido " + clienteporpartidoListOldClienteporpartido + " since its partido field is not nullable.");
                }
            }
            for (Jugadorporpartido jugadorporpartidoListOldJugadorporpartido : jugadorporpartidoListOld) {
                if (!jugadorporpartidoListNew.contains(jugadorporpartidoListOldJugadorporpartido)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Jugadorporpartido " + jugadorporpartidoListOldJugadorporpartido + " since its partido field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idcalendarioNew != null) {
                idcalendarioNew = em.getReference(idcalendarioNew.getClass(), idcalendarioNew.getIdcalendario());
                partido.setIdcalendario(idcalendarioNew);
            }
            if (idequipovisitanteNew != null) {
                idequipovisitanteNew = em.getReference(idequipovisitanteNew.getClass(), idequipovisitanteNew.getIdequipo());
                partido.setIdequipovisitante(idequipovisitanteNew);
            }
            if (idequipolocalNew != null) {
                idequipolocalNew = em.getReference(idequipolocalNew.getClass(), idequipolocalNew.getIdequipo());
                partido.setIdequipolocal(idequipolocalNew);
            }
            if (idestadioNew != null) {
                idestadioNew = em.getReference(idestadioNew.getClass(), idestadioNew.getIdestadio());
                partido.setIdestadio(idestadioNew);
            }
            List<Gol> attachedGolListNew = new ArrayList<Gol>();
            for (Gol golListNewGolToAttach : golListNew) {
                golListNewGolToAttach = em.getReference(golListNewGolToAttach.getClass(), golListNewGolToAttach.getIdgol());
                attachedGolListNew.add(golListNewGolToAttach);
            }
            golListNew = attachedGolListNew;
            partido.setGolList(golListNew);
            List<Categoriaporpartido> attachedCategoriaporpartidoListNew = new ArrayList<Categoriaporpartido>();
            for (Categoriaporpartido categoriaporpartidoListNewCategoriaporpartidoToAttach : categoriaporpartidoListNew) {
                categoriaporpartidoListNewCategoriaporpartidoToAttach = em.getReference(categoriaporpartidoListNewCategoriaporpartidoToAttach.getClass(), categoriaporpartidoListNewCategoriaporpartidoToAttach.getCategoriaporpartidoPK());
                attachedCategoriaporpartidoListNew.add(categoriaporpartidoListNewCategoriaporpartidoToAttach);
            }
            categoriaporpartidoListNew = attachedCategoriaporpartidoListNew;
            partido.setCategoriaporpartidoList(categoriaporpartidoListNew);
            List<Juezporpartido> attachedJuezporpartidoListNew = new ArrayList<Juezporpartido>();
            for (Juezporpartido juezporpartidoListNewJuezporpartidoToAttach : juezporpartidoListNew) {
                juezporpartidoListNewJuezporpartidoToAttach = em.getReference(juezporpartidoListNewJuezporpartidoToAttach.getClass(), juezporpartidoListNewJuezporpartidoToAttach.getJuezporpartidoPK());
                attachedJuezporpartidoListNew.add(juezporpartidoListNewJuezporpartidoToAttach);
            }
            juezporpartidoListNew = attachedJuezporpartidoListNew;
            partido.setJuezporpartidoList(juezporpartidoListNew);
            List<Tarjeta> attachedTarjetaListNew = new ArrayList<Tarjeta>();
            for (Tarjeta tarjetaListNewTarjetaToAttach : tarjetaListNew) {
                tarjetaListNewTarjetaToAttach = em.getReference(tarjetaListNewTarjetaToAttach.getClass(), tarjetaListNewTarjetaToAttach.getIdtarjeta());
                attachedTarjetaListNew.add(tarjetaListNewTarjetaToAttach);
            }
            tarjetaListNew = attachedTarjetaListNew;
            partido.setTarjetaList(tarjetaListNew);
            List<Clienteporpartido> attachedClienteporpartidoListNew = new ArrayList<Clienteporpartido>();
            for (Clienteporpartido clienteporpartidoListNewClienteporpartidoToAttach : clienteporpartidoListNew) {
                clienteporpartidoListNewClienteporpartidoToAttach = em.getReference(clienteporpartidoListNewClienteporpartidoToAttach.getClass(), clienteporpartidoListNewClienteporpartidoToAttach.getClienteporpartidoPK());
                attachedClienteporpartidoListNew.add(clienteporpartidoListNewClienteporpartidoToAttach);
            }
            clienteporpartidoListNew = attachedClienteporpartidoListNew;
            partido.setClienteporpartidoList(clienteporpartidoListNew);
            List<Jugadorporpartido> attachedJugadorporpartidoListNew = new ArrayList<Jugadorporpartido>();
            for (Jugadorporpartido jugadorporpartidoListNewJugadorporpartidoToAttach : jugadorporpartidoListNew) {
                jugadorporpartidoListNewJugadorporpartidoToAttach = em.getReference(jugadorporpartidoListNewJugadorporpartidoToAttach.getClass(), jugadorporpartidoListNewJugadorporpartidoToAttach.getJugadorporpartidoPK());
                attachedJugadorporpartidoListNew.add(jugadorporpartidoListNewJugadorporpartidoToAttach);
            }
            jugadorporpartidoListNew = attachedJugadorporpartidoListNew;
            partido.setJugadorporpartidoList(jugadorporpartidoListNew);
            partido = em.merge(partido);
            if (idcalendarioOld != null && !idcalendarioOld.equals(idcalendarioNew)) {
                idcalendarioOld.getPartidoList().remove(partido);
                idcalendarioOld = em.merge(idcalendarioOld);
            }
            if (idcalendarioNew != null && !idcalendarioNew.equals(idcalendarioOld)) {
                idcalendarioNew.getPartidoList().add(partido);
                idcalendarioNew = em.merge(idcalendarioNew);
            }
            if (idequipovisitanteOld != null && !idequipovisitanteOld.equals(idequipovisitanteNew)) {
                idequipovisitanteOld.getPartidoList().remove(partido);
                idequipovisitanteOld = em.merge(idequipovisitanteOld);
            }
            if (idequipovisitanteNew != null && !idequipovisitanteNew.equals(idequipovisitanteOld)) {
                idequipovisitanteNew.getPartidoList().add(partido);
                idequipovisitanteNew = em.merge(idequipovisitanteNew);
            }
            if (idequipolocalOld != null && !idequipolocalOld.equals(idequipolocalNew)) {
                idequipolocalOld.getPartidoList().remove(partido);
                idequipolocalOld = em.merge(idequipolocalOld);
            }
            if (idequipolocalNew != null && !idequipolocalNew.equals(idequipolocalOld)) {
                idequipolocalNew.getPartidoList().add(partido);
                idequipolocalNew = em.merge(idequipolocalNew);
            }
            if (idestadioOld != null && !idestadioOld.equals(idestadioNew)) {
                idestadioOld.getPartidoList().remove(partido);
                idestadioOld = em.merge(idestadioOld);
            }
            if (idestadioNew != null && !idestadioNew.equals(idestadioOld)) {
                idestadioNew.getPartidoList().add(partido);
                idestadioNew = em.merge(idestadioNew);
            }
            for (Gol golListNewGol : golListNew) {
                if (!golListOld.contains(golListNewGol)) {
                    Partido oldIdpartidoOfGolListNewGol = golListNewGol.getIdpartido();
                    golListNewGol.setIdpartido(partido);
                    golListNewGol = em.merge(golListNewGol);
                    if (oldIdpartidoOfGolListNewGol != null && !oldIdpartidoOfGolListNewGol.equals(partido)) {
                        oldIdpartidoOfGolListNewGol.getGolList().remove(golListNewGol);
                        oldIdpartidoOfGolListNewGol = em.merge(oldIdpartidoOfGolListNewGol);
                    }
                }
            }
            for (Categoriaporpartido categoriaporpartidoListNewCategoriaporpartido : categoriaporpartidoListNew) {
                if (!categoriaporpartidoListOld.contains(categoriaporpartidoListNewCategoriaporpartido)) {
                    Partido oldPartidoOfCategoriaporpartidoListNewCategoriaporpartido = categoriaporpartidoListNewCategoriaporpartido.getPartido();
                    categoriaporpartidoListNewCategoriaporpartido.setPartido(partido);
                    categoriaporpartidoListNewCategoriaporpartido = em.merge(categoriaporpartidoListNewCategoriaporpartido);
                    if (oldPartidoOfCategoriaporpartidoListNewCategoriaporpartido != null && !oldPartidoOfCategoriaporpartidoListNewCategoriaporpartido.equals(partido)) {
                        oldPartidoOfCategoriaporpartidoListNewCategoriaporpartido.getCategoriaporpartidoList().remove(categoriaporpartidoListNewCategoriaporpartido);
                        oldPartidoOfCategoriaporpartidoListNewCategoriaporpartido = em.merge(oldPartidoOfCategoriaporpartidoListNewCategoriaporpartido);
                    }
                }
            }
            for (Juezporpartido juezporpartidoListNewJuezporpartido : juezporpartidoListNew) {
                if (!juezporpartidoListOld.contains(juezporpartidoListNewJuezporpartido)) {
                    Partido oldPartidoOfJuezporpartidoListNewJuezporpartido = juezporpartidoListNewJuezporpartido.getPartido();
                    juezporpartidoListNewJuezporpartido.setPartido(partido);
                    juezporpartidoListNewJuezporpartido = em.merge(juezporpartidoListNewJuezporpartido);
                    if (oldPartidoOfJuezporpartidoListNewJuezporpartido != null && !oldPartidoOfJuezporpartidoListNewJuezporpartido.equals(partido)) {
                        oldPartidoOfJuezporpartidoListNewJuezporpartido.getJuezporpartidoList().remove(juezporpartidoListNewJuezporpartido);
                        oldPartidoOfJuezporpartidoListNewJuezporpartido = em.merge(oldPartidoOfJuezporpartidoListNewJuezporpartido);
                    }
                }
            }
            for (Tarjeta tarjetaListNewTarjeta : tarjetaListNew) {
                if (!tarjetaListOld.contains(tarjetaListNewTarjeta)) {
                    Partido oldIdpartidoOfTarjetaListNewTarjeta = tarjetaListNewTarjeta.getIdpartido();
                    tarjetaListNewTarjeta.setIdpartido(partido);
                    tarjetaListNewTarjeta = em.merge(tarjetaListNewTarjeta);
                    if (oldIdpartidoOfTarjetaListNewTarjeta != null && !oldIdpartidoOfTarjetaListNewTarjeta.equals(partido)) {
                        oldIdpartidoOfTarjetaListNewTarjeta.getTarjetaList().remove(tarjetaListNewTarjeta);
                        oldIdpartidoOfTarjetaListNewTarjeta = em.merge(oldIdpartidoOfTarjetaListNewTarjeta);
                    }
                }
            }
            for (Clienteporpartido clienteporpartidoListNewClienteporpartido : clienteporpartidoListNew) {
                if (!clienteporpartidoListOld.contains(clienteporpartidoListNewClienteporpartido)) {
                    Partido oldPartidoOfClienteporpartidoListNewClienteporpartido = clienteporpartidoListNewClienteporpartido.getPartido();
                    clienteporpartidoListNewClienteporpartido.setPartido(partido);
                    clienteporpartidoListNewClienteporpartido = em.merge(clienteporpartidoListNewClienteporpartido);
                    if (oldPartidoOfClienteporpartidoListNewClienteporpartido != null && !oldPartidoOfClienteporpartidoListNewClienteporpartido.equals(partido)) {
                        oldPartidoOfClienteporpartidoListNewClienteporpartido.getClienteporpartidoList().remove(clienteporpartidoListNewClienteporpartido);
                        oldPartidoOfClienteporpartidoListNewClienteporpartido = em.merge(oldPartidoOfClienteporpartidoListNewClienteporpartido);
                    }
                }
            }
            for (Jugadorporpartido jugadorporpartidoListNewJugadorporpartido : jugadorporpartidoListNew) {
                if (!jugadorporpartidoListOld.contains(jugadorporpartidoListNewJugadorporpartido)) {
                    Partido oldPartidoOfJugadorporpartidoListNewJugadorporpartido = jugadorporpartidoListNewJugadorporpartido.getPartido();
                    jugadorporpartidoListNewJugadorporpartido.setPartido(partido);
                    jugadorporpartidoListNewJugadorporpartido = em.merge(jugadorporpartidoListNewJugadorporpartido);
                    if (oldPartidoOfJugadorporpartidoListNewJugadorporpartido != null && !oldPartidoOfJugadorporpartidoListNewJugadorporpartido.equals(partido)) {
                        oldPartidoOfJugadorporpartidoListNewJugadorporpartido.getJugadorporpartidoList().remove(jugadorporpartidoListNewJugadorporpartido);
                        oldPartidoOfJugadorporpartidoListNewJugadorporpartido = em.merge(oldPartidoOfJugadorporpartidoListNewJugadorporpartido);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Short id = partido.getIdpartido();
                if (findPartido(id) == null) {
                    throw new NonexistentEntityException("The partido with id " + id + " no longer exists.");
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
            Partido partido;
            try {
                partido = em.getReference(Partido.class, id);
                partido.getIdpartido();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The partido with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Gol> golListOrphanCheck = partido.getGolList();
            for (Gol golListOrphanCheckGol : golListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Partido (" + partido + ") cannot be destroyed since the Gol " + golListOrphanCheckGol + " in its golList field has a non-nullable idpartido field.");
            }
            List<Categoriaporpartido> categoriaporpartidoListOrphanCheck = partido.getCategoriaporpartidoList();
            for (Categoriaporpartido categoriaporpartidoListOrphanCheckCategoriaporpartido : categoriaporpartidoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Partido (" + partido + ") cannot be destroyed since the Categoriaporpartido " + categoriaporpartidoListOrphanCheckCategoriaporpartido + " in its categoriaporpartidoList field has a non-nullable partido field.");
            }
            List<Juezporpartido> juezporpartidoListOrphanCheck = partido.getJuezporpartidoList();
            for (Juezporpartido juezporpartidoListOrphanCheckJuezporpartido : juezporpartidoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Partido (" + partido + ") cannot be destroyed since the Juezporpartido " + juezporpartidoListOrphanCheckJuezporpartido + " in its juezporpartidoList field has a non-nullable partido field.");
            }
            List<Tarjeta> tarjetaListOrphanCheck = partido.getTarjetaList();
            for (Tarjeta tarjetaListOrphanCheckTarjeta : tarjetaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Partido (" + partido + ") cannot be destroyed since the Tarjeta " + tarjetaListOrphanCheckTarjeta + " in its tarjetaList field has a non-nullable idpartido field.");
            }
            List<Clienteporpartido> clienteporpartidoListOrphanCheck = partido.getClienteporpartidoList();
            for (Clienteporpartido clienteporpartidoListOrphanCheckClienteporpartido : clienteporpartidoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Partido (" + partido + ") cannot be destroyed since the Clienteporpartido " + clienteporpartidoListOrphanCheckClienteporpartido + " in its clienteporpartidoList field has a non-nullable partido field.");
            }
            List<Jugadorporpartido> jugadorporpartidoListOrphanCheck = partido.getJugadorporpartidoList();
            for (Jugadorporpartido jugadorporpartidoListOrphanCheckJugadorporpartido : jugadorporpartidoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Partido (" + partido + ") cannot be destroyed since the Jugadorporpartido " + jugadorporpartidoListOrphanCheckJugadorporpartido + " in its jugadorporpartidoList field has a non-nullable partido field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Calendario idcalendario = partido.getIdcalendario();
            if (idcalendario != null) {
                idcalendario.getPartidoList().remove(partido);
                idcalendario = em.merge(idcalendario);
            }
            Equipo idequipovisitante = partido.getIdequipovisitante();
            if (idequipovisitante != null) {
                idequipovisitante.getPartidoList().remove(partido);
                idequipovisitante = em.merge(idequipovisitante);
            }
            Equipo idequipolocal = partido.getIdequipolocal();
            if (idequipolocal != null) {
                idequipolocal.getPartidoList().remove(partido);
                idequipolocal = em.merge(idequipolocal);
            }
            Estadio idestadio = partido.getIdestadio();
            if (idestadio != null) {
                idestadio.getPartidoList().remove(partido);
                idestadio = em.merge(idestadio);
            }
            em.remove(partido);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Partido> findPartidoEntities() {
        return findPartidoEntities(true, -1, -1);
    }

    public List<Partido> findPartidoEntities(int maxResults, int firstResult) {
        return findPartidoEntities(false, maxResults, firstResult);
    }

    private List<Partido> findPartidoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Partido.class));
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

    public Partido findPartido(Short id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Partido.class, id);
        } finally {
            em.close();
        }
    }

    public int getPartidoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Partido> rt = cq.from(Partido.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
