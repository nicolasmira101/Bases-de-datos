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
@Table(name = "CATEGORIA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Categoria.findAll", query = "SELECT c FROM Categoria c")
    , @NamedQuery(name = "Categoria.findByIdcategoria", query = "SELECT c FROM Categoria c WHERE c.idcategoria = :idcategoria")})
public class Categoria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDCATEGORIA")
    private Short idcategoria;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "categoria")
    private List<Categoriaporestadio> categoriaporestadioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "categoria")
    private List<Categoriaporpartido> categoriaporpartidoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idcategoria")
    private List<Silla> sillaList;

    public Categoria() {
    }

    public Categoria(Short idcategoria) {
        this.idcategoria = idcategoria;
    }

    public Short getIdcategoria() {
        return idcategoria;
    }

    public void setIdcategoria(Short idcategoria) {
        this.idcategoria = idcategoria;
    }

    @XmlTransient
    public List<Categoriaporestadio> getCategoriaporestadioList() {
        return categoriaporestadioList;
    }

    public void setCategoriaporestadioList(List<Categoriaporestadio> categoriaporestadioList) {
        this.categoriaporestadioList = categoriaporestadioList;
    }

    @XmlTransient
    public List<Categoriaporpartido> getCategoriaporpartidoList() {
        return categoriaporpartidoList;
    }

    public void setCategoriaporpartidoList(List<Categoriaporpartido> categoriaporpartidoList) {
        this.categoriaporpartidoList = categoriaporpartidoList;
    }

    @XmlTransient
    public List<Silla> getSillaList() {
        return sillaList;
    }

    public void setSillaList(List<Silla> sillaList) {
        this.sillaList = sillaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcategoria != null ? idcategoria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Categoria)) {
            return false;
        }
        Categoria other = (Categoria) object;
        if ((this.idcategoria == null && other.idcategoria != null) || (this.idcategoria != null && !this.idcategoria.equals(other.idcategoria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.javeriana.mundialrusia.datos.Categoria[ idcategoria=" + idcategoria + " ]";
    }
    
}
