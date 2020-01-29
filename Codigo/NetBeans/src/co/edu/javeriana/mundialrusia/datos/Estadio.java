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
@Table(name = "ESTADIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Estadio.findAll", query = "SELECT e FROM Estadio e")
    , @NamedQuery(name = "Estadio.findByIdestadio", query = "SELECT e FROM Estadio e WHERE e.idestadio = :idestadio")
    , @NamedQuery(name = "Estadio.findByNombre", query = "SELECT e FROM Estadio e WHERE e.nombre = :nombre")
    , @NamedQuery(name = "Estadio.findBySede", query = "SELECT e FROM Estadio e WHERE e.sede = :sede")
    , @NamedQuery(name = "Estadio.findByCapacidad", query = "SELECT e FROM Estadio e WHERE e.capacidad = :capacidad")})
public class Estadio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDESTADIO")
    private Short idestadio;
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "SEDE")
    private String sede;
    @Basic(optional = false)
    @Column(name = "CAPACIDAD")
    private int capacidad;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estadio")
    private List<Categoriaporestadio> categoriaporestadioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idestadio")
    private List<Partido> partidoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estadio")
    private List<Sillaporestadio> sillaporestadioList;

    public Estadio() {
    }

    public Estadio(Short idestadio) {
        this.idestadio = idestadio;
    }

    public Estadio(Short idestadio, String nombre, String sede, int capacidad) {
        this.idestadio = idestadio;
        this.nombre = nombre;
        this.sede = sede;
        this.capacidad = capacidad;
    }

    public Short getIdestadio() {
        return idestadio;
    }

    public void setIdestadio(Short idestadio) {
        this.idestadio = idestadio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    @XmlTransient
    public List<Categoriaporestadio> getCategoriaporestadioList() {
        return categoriaporestadioList;
    }

    public void setCategoriaporestadioList(List<Categoriaporestadio> categoriaporestadioList) {
        this.categoriaporestadioList = categoriaporestadioList;
    }

    @XmlTransient
    public List<Partido> getPartidoList() {
        return partidoList;
    }

    public void setPartidoList(List<Partido> partidoList) {
        this.partidoList = partidoList;
    }

    @XmlTransient
    public List<Sillaporestadio> getSillaporestadioList() {
        return sillaporestadioList;
    }

    public void setSillaporestadioList(List<Sillaporestadio> sillaporestadioList) {
        this.sillaporestadioList = sillaporestadioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idestadio != null ? idestadio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Estadio)) {
            return false;
        }
        Estadio other = (Estadio) object;
        if ((this.idestadio == null && other.idestadio != null) || (this.idestadio != null && !this.idestadio.equals(other.idestadio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.javeriana.mundialrusia.datos.Estadio[ idestadio=" + idestadio + " ]";
    }
    
}
