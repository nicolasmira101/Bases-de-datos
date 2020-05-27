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
@Table(name = "JUEZ")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Juez.findAll", query = "SELECT j FROM Juez j")
    , @NamedQuery(name = "Juez.findByIdjuez", query = "SELECT j FROM Juez j WHERE j.idjuez = :idjuez")
    , @NamedQuery(name = "Juez.findByNombre", query = "SELECT j FROM Juez j WHERE j.nombre = :nombre")
    , @NamedQuery(name = "Juez.findByApellido", query = "SELECT j FROM Juez j WHERE j.apellido = :apellido")
    , @NamedQuery(name = "Juez.findByNacionalidad", query = "SELECT j FROM Juez j WHERE j.nacionalidad = :nacionalidad")})
public class Juez implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDJUEZ")
    private Short idjuez;
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "APELLIDO")
    private String apellido;
    @Basic(optional = false)
    @Column(name = "NACIONALIDAD")
    private String nacionalidad;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "juez")
    private List<Juezporpartido> juezporpartidoList;

    public Juez() {
    }

    public Juez(Short idjuez) {
        this.idjuez = idjuez;
    }

    public Juez(Short idjuez, String nombre, String apellido, String nacionalidad) {
        this.idjuez = idjuez;
        this.nombre = nombre;
        this.apellido = apellido;
        this.nacionalidad = nacionalidad;
    }

    public Short getIdjuez() {
        return idjuez;
    }

    public void setIdjuez(Short idjuez) {
        this.idjuez = idjuez;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    @XmlTransient
    public List<Juezporpartido> getJuezporpartidoList() {
        return juezporpartidoList;
    }

    public void setJuezporpartidoList(List<Juezporpartido> juezporpartidoList) {
        this.juezporpartidoList = juezporpartidoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idjuez != null ? idjuez.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Juez)) {
            return false;
        }
        Juez other = (Juez) object;
        if ((this.idjuez == null && other.idjuez != null) || (this.idjuez != null && !this.idjuez.equals(other.idjuez))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.javeriana.mundialrusia.datos.Juez[ idjuez=" + idjuez + " ]";
    }
    
}
