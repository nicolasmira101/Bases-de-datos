/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.javeriana.mundialrusia.datos;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author nicolasmiranda
 */
@Entity
@Table(name = "CATEGORIAPORPARTIDO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Categoriaporpartido.findAll", query = "SELECT c FROM Categoriaporpartido c")
    , @NamedQuery(name = "Categoriaporpartido.findByIdcategoria", query = "SELECT c FROM Categoriaporpartido c WHERE c.categoriaporpartidoPK.idcategoria = :idcategoria")
    , @NamedQuery(name = "Categoriaporpartido.findByIdpartido", query = "SELECT c FROM Categoriaporpartido c WHERE c.categoriaporpartidoPK.idpartido = :idpartido")
    , @NamedQuery(name = "Categoriaporpartido.findByPrecio", query = "SELECT c FROM Categoriaporpartido c WHERE c.precio = :precio")})
public class Categoriaporpartido implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CategoriaporpartidoPK categoriaporpartidoPK;
    @Basic(optional = false)
    @Column(name = "PRECIO")
    private short precio;
    @JoinColumn(name = "IDCATEGORIA", referencedColumnName = "IDCATEGORIA", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Categoria categoria;
    @JoinColumn(name = "IDPARTIDO", referencedColumnName = "IDPARTIDO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Partido partido;

    public Categoriaporpartido() {
    }

    public Categoriaporpartido(CategoriaporpartidoPK categoriaporpartidoPK) {
        this.categoriaporpartidoPK = categoriaporpartidoPK;
    }

    public Categoriaporpartido(CategoriaporpartidoPK categoriaporpartidoPK, short precio) {
        this.categoriaporpartidoPK = categoriaporpartidoPK;
        this.precio = precio;
    }

    public Categoriaporpartido(short idcategoria, short idpartido) {
        this.categoriaporpartidoPK = new CategoriaporpartidoPK(idcategoria, idpartido);
    }

    public CategoriaporpartidoPK getCategoriaporpartidoPK() {
        return categoriaporpartidoPK;
    }

    public void setCategoriaporpartidoPK(CategoriaporpartidoPK categoriaporpartidoPK) {
        this.categoriaporpartidoPK = categoriaporpartidoPK;
    }

    public short getPrecio() {
        return precio;
    }

    public void setPrecio(short precio) {
        this.precio = precio;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Partido getPartido() {
        return partido;
    }

    public void setPartido(Partido partido) {
        this.partido = partido;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (categoriaporpartidoPK != null ? categoriaporpartidoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Categoriaporpartido)) {
            return false;
        }
        Categoriaporpartido other = (Categoriaporpartido) object;
        if ((this.categoriaporpartidoPK == null && other.categoriaporpartidoPK != null) || (this.categoriaporpartidoPK != null && !this.categoriaporpartidoPK.equals(other.categoriaporpartidoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.javeriana.mundialrusia.datos.Categoriaporpartido[ categoriaporpartidoPK=" + categoriaporpartidoPK + " ]";
    }
    
}
