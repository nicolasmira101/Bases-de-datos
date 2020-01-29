/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.javeriana.mundialrusia.datos;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author nicolasmiranda
 */
@Entity
@Table(name = "PARTIDO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Partido.findAll", query = "SELECT p FROM Partido p")
    , @NamedQuery(name = "Partido.findByIdpartido", query = "SELECT p FROM Partido p WHERE p.idpartido = :idpartido")
    , @NamedQuery(name = "Partido.findByFecha", query = "SELECT p FROM Partido p WHERE p.fecha = :fecha")})
public class Partido implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDPARTIDO")
    private Short idpartido;
    @Basic(optional = false)
    @Column(name = "FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idpartido")
    private List<Gol> golList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "partido")
    private List<Categoriaporpartido> categoriaporpartidoList;
    @JoinColumn(name = "IDCALENDARIO", referencedColumnName = "IDCALENDARIO")
    @ManyToOne(optional = false)
    private Calendario idcalendario;
    @JoinColumn(name = "IDEQUIPOVISITANTE", referencedColumnName = "IDEQUIPO")
    @ManyToOne(optional = false)
    private Equipo idequipovisitante;
    @JoinColumn(name = "IDEQUIPOLOCAL", referencedColumnName = "IDEQUIPO")
    @ManyToOne(optional = false)
    private Equipo idequipolocal;
    @JoinColumn(name = "IDESTADIO", referencedColumnName = "IDESTADIO")
    @ManyToOne(optional = false)
    private Estadio idestadio;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "partido")
    private List<Juezporpartido> juezporpartidoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idpartido")
    private List<Tarjeta> tarjetaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "partido")
    private List<Clienteporpartido> clienteporpartidoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "partido")
    private List<Jugadorporpartido> jugadorporpartidoList;

    public Partido() {
    }

    public Partido(Short idpartido) {
        this.idpartido = idpartido;
    }

    public Partido(Short idpartido, Date fecha) {
        this.idpartido = idpartido;
        this.fecha = fecha;
    }

    public Short getIdpartido() {
        return idpartido;
    }

    public void setIdpartido(Short idpartido) {
        this.idpartido = idpartido;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @XmlTransient
    public List<Gol> getGolList() {
        return golList;
    }

    public void setGolList(List<Gol> golList) {
        this.golList = golList;
    }

    @XmlTransient
    public List<Categoriaporpartido> getCategoriaporpartidoList() {
        return categoriaporpartidoList;
    }

    public void setCategoriaporpartidoList(List<Categoriaporpartido> categoriaporpartidoList) {
        this.categoriaporpartidoList = categoriaporpartidoList;
    }

    public Calendario getIdcalendario() {
        return idcalendario;
    }

    public void setIdcalendario(Calendario idcalendario) {
        this.idcalendario = idcalendario;
    }

    public Equipo getIdequipovisitante() {
        return idequipovisitante;
    }

    public void setIdequipovisitante(Equipo idequipovisitante) {
        this.idequipovisitante = idequipovisitante;
    }

    public Equipo getIdequipolocal() {
        return idequipolocal;
    }

    public void setIdequipolocal(Equipo idequipolocal) {
        this.idequipolocal = idequipolocal;
    }

    public Estadio getIdestadio() {
        return idestadio;
    }

    public void setIdestadio(Estadio idestadio) {
        this.idestadio = idestadio;
    }

    @XmlTransient
    public List<Juezporpartido> getJuezporpartidoList() {
        return juezporpartidoList;
    }

    public void setJuezporpartidoList(List<Juezporpartido> juezporpartidoList) {
        this.juezporpartidoList = juezporpartidoList;
    }

    @XmlTransient
    public List<Tarjeta> getTarjetaList() {
        return tarjetaList;
    }

    public void setTarjetaList(List<Tarjeta> tarjetaList) {
        this.tarjetaList = tarjetaList;
    }

    @XmlTransient
    public List<Clienteporpartido> getClienteporpartidoList() {
        return clienteporpartidoList;
    }

    public void setClienteporpartidoList(List<Clienteporpartido> clienteporpartidoList) {
        this.clienteporpartidoList = clienteporpartidoList;
    }

    @XmlTransient
    public List<Jugadorporpartido> getJugadorporpartidoList() {
        return jugadorporpartidoList;
    }

    public void setJugadorporpartidoList(List<Jugadorporpartido> jugadorporpartidoList) {
        this.jugadorporpartidoList = jugadorporpartidoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpartido != null ? idpartido.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Partido)) {
            return false;
        }
        Partido other = (Partido) object;
        if ((this.idpartido == null && other.idpartido != null) || (this.idpartido != null && !this.idpartido.equals(other.idpartido))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.javeriana.mundialrusia.datos.Partido[ idpartido=" + idpartido + " ]";
    }
    
}
