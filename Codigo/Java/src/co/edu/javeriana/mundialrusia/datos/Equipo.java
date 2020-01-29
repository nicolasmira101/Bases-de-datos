/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.javeriana.mundialrusia.datos;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author nicolasmiranda
 */
@Entity
@Table(name = "EQUIPO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Equipo.findAll", query = "SELECT e FROM Equipo e")
    , @NamedQuery(name = "Equipo.findByIdequipo", query = "SELECT e FROM Equipo e WHERE e.idequipo = :idequipo")
    , @NamedQuery(name = "Equipo.findByNombre", query = "SELECT e FROM Equipo e WHERE e.nombre = :nombre")
    , @NamedQuery(name = "Equipo.findByConfederacion", query = "SELECT e FROM Equipo e WHERE e.confederacion = :confederacion")
    , @NamedQuery(name = "Equipo.findByGrupo", query = "SELECT e FROM Equipo e WHERE e.grupo = :grupo")})
public class Equipo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDEQUIPO")
    private Short idequipo;
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "CONFEDERACION")
    private String confederacion;
    @Basic(optional = false)
    @Column(name = "GRUPO")
    private String grupo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idequipo")
    private List<Jugador> jugadorList;
    @JoinColumn(name = "IDDIRECTORTECNICO", referencedColumnName = "IDDIRECTORTECNICO")
    @ManyToOne(optional = false)
    private Directortecnico iddirectortecnico;
    @JoinColumn(name = "IDASISTENTETECNICO", referencedColumnName = "IDDIRECTORTECNICO")
    @ManyToOne(optional = false)
    private Directortecnico idasistentetecnico;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idequipovisitante")
    private List<Partido> partidoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idequipolocal")
    private List<Partido> partidoList1;

    public Equipo() {
    }

    public Equipo(Short idequipo) {
        this.idequipo = idequipo;
    }

    public Equipo(Short idequipo, String nombre, String confederacion, String grupo) {
        this.idequipo = idequipo;
        this.nombre = nombre;
        this.confederacion = confederacion;
        this.grupo = grupo;
    }

    public Short getIdequipo() {
        return idequipo;
    }

    public void setIdequipo(Short idequipo) {
        this.idequipo = idequipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getConfederacion() {
        return confederacion;
    }

    public void setConfederacion(String confederacion) {
        this.confederacion = confederacion;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    @XmlTransient
    public List<Jugador> getJugadorList() {
        return jugadorList;
    }

    public void setJugadorList(List<Jugador> jugadorList) {
        this.jugadorList = jugadorList;
    }

    public Directortecnico getIddirectortecnico() {
        return iddirectortecnico;
    }

    public void setIddirectortecnico(Directortecnico iddirectortecnico) {
        this.iddirectortecnico = iddirectortecnico;
    }

    public Directortecnico getIdasistentetecnico() {
        return idasistentetecnico;
    }

    public void setIdasistentetecnico(Directortecnico idasistentetecnico) {
        this.idasistentetecnico = idasistentetecnico;
    }

    @XmlTransient
    public List<Partido> getPartidoList() {
        return partidoList;
    }

    public void setPartidoList(List<Partido> partidoList) {
        this.partidoList = partidoList;
    }

    @XmlTransient
    public List<Partido> getPartidoList1() {
        return partidoList1;
    }

    public void setPartidoList1(List<Partido> partidoList1) {
        this.partidoList1 = partidoList1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idequipo != null ? idequipo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Equipo)) {
            return false;
        }
        Equipo other = (Equipo) object;
        if ((this.idequipo == null && other.idequipo != null) || (this.idequipo != null && !this.idequipo.equals(other.idequipo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.javeriana.mundialrusia.datos.Equipo[ idequipo=" + idequipo + " ]";
    }
    
}
