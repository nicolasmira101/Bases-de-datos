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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "SILLA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Silla.findAll", query = "SELECT s FROM Silla s")
    , @NamedQuery(name = "Silla.findByIdsilla", query = "SELECT s FROM Silla s WHERE s.sillaPK.idsilla = :idsilla")
    , @NamedQuery(name = "Silla.findByFila", query = "SELECT s FROM Silla s WHERE s.sillaPK.fila = :fila")
    , @NamedQuery(name = "Silla.findByDisponible", query = "SELECT s FROM Silla s WHERE s.disponible = :disponible")})
public class Silla implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SillaPK sillaPK;
    @Basic(optional = false)
    @Column(name = "DISPONIBLE")
    private String disponible;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "silla")
    private List<Sillaporestadio> sillaporestadioList;
    @JoinColumn(name = "IDCATEGORIA", referencedColumnName = "IDCATEGORIA")
    @ManyToOne(optional = false)
    private Categoria idcategoria;

    public Silla() {
    }

    public Silla(SillaPK sillaPK) {
        this.sillaPK = sillaPK;
    }

    public Silla(SillaPK sillaPK, String disponible) {
        this.sillaPK = sillaPK;
        this.disponible = disponible;
    }

    public Silla(short idsilla, String fila) {
        this.sillaPK = new SillaPK(idsilla, fila);
    }

    public SillaPK getSillaPK() {
        return sillaPK;
    }

    public void setSillaPK(SillaPK sillaPK) {
        this.sillaPK = sillaPK;
    }

    public String getDisponible() {
        return disponible;
    }

    public void setDisponible(String disponible) {
        this.disponible = disponible;
    }

    @XmlTransient
    public List<Sillaporestadio> getSillaporestadioList() {
        return sillaporestadioList;
    }

    public void setSillaporestadioList(List<Sillaporestadio> sillaporestadioList) {
        this.sillaporestadioList = sillaporestadioList;
    }

    public Categoria getIdcategoria() {
        return idcategoria;
    }

    public void setIdcategoria(Categoria idcategoria) {
        this.idcategoria = idcategoria;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sillaPK != null ? sillaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Silla)) {
            return false;
        }
        Silla other = (Silla) object;
        if ((this.sillaPK == null && other.sillaPK != null) || (this.sillaPK != null && !this.sillaPK.equals(other.sillaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.javeriana.mundialrusia.datos.Silla[ sillaPK=" + sillaPK + " ]";
    }
    
}
