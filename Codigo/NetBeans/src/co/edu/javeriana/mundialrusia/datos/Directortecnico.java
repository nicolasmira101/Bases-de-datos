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
@Table(name = "DIRECTORTECNICO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Directortecnico.findAll", query = "SELECT d FROM Directortecnico d")
    , @NamedQuery(name = "Directortecnico.findByIddirectortecnico", query = "SELECT d FROM Directortecnico d WHERE d.iddirectortecnico = :iddirectortecnico")
    , @NamedQuery(name = "Directortecnico.findByNombre", query = "SELECT d FROM Directortecnico d WHERE d.nombre = :nombre")
    , @NamedQuery(name = "Directortecnico.findByApellido", query = "SELECT d FROM Directortecnico d WHERE d.apellido = :apellido")
    , @NamedQuery(name = "Directortecnico.findByCargo", query = "SELECT d FROM Directortecnico d WHERE d.cargo = :cargo")})
public class Directortecnico implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDDIRECTORTECNICO")
    private Short iddirectortecnico;
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "APELLIDO")
    private String apellido;
    @Basic(optional = false)
    @Column(name = "CARGO")
    private String cargo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iddirectortecnico")
    private List<Equipo> equipoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idasistentetecnico")
    private List<Equipo> equipoList1;

    public Directortecnico() {
    }

    public Directortecnico(Short iddirectortecnico) {
        this.iddirectortecnico = iddirectortecnico;
    }

    public Directortecnico(Short iddirectortecnico, String nombre, String apellido, String cargo) {
        this.iddirectortecnico = iddirectortecnico;
        this.nombre = nombre;
        this.apellido = apellido;
        this.cargo = cargo;
    }

    public Short getIddirectortecnico() {
        return iddirectortecnico;
    }

    public void setIddirectortecnico(Short iddirectortecnico) {
        this.iddirectortecnico = iddirectortecnico;
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

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    @XmlTransient
    public List<Equipo> getEquipoList() {
        return equipoList;
    }

    public void setEquipoList(List<Equipo> equipoList) {
        this.equipoList = equipoList;
    }

    @XmlTransient
    public List<Equipo> getEquipoList1() {
        return equipoList1;
    }

    public void setEquipoList1(List<Equipo> equipoList1) {
        this.equipoList1 = equipoList1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iddirectortecnico != null ? iddirectortecnico.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Directortecnico)) {
            return false;
        }
        Directortecnico other = (Directortecnico) object;
        if ((this.iddirectortecnico == null && other.iddirectortecnico != null) || (this.iddirectortecnico != null && !this.iddirectortecnico.equals(other.iddirectortecnico))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.javeriana.mundialrusia.datos.Directortecnico[ iddirectortecnico=" + iddirectortecnico + " ]";
    }
    
}
