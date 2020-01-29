/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.javeriana.mundialrusia.datos;

import java.io.Serializable;
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
@Table(name = "CATEGORIAPORESTADIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Categoriaporestadio.findAll", query = "SELECT c FROM Categoriaporestadio c")
    , @NamedQuery(name = "Categoriaporestadio.findByIdcategoria", query = "SELECT c FROM Categoriaporestadio c WHERE c.categoriaporestadioPK.idcategoria = :idcategoria")
    , @NamedQuery(name = "Categoriaporestadio.findByIdestadio", query = "SELECT c FROM Categoriaporestadio c WHERE c.categoriaporestadioPK.idestadio = :idestadio")
    , @NamedQuery(name = "Categoriaporestadio.findByEjemplo", query = "SELECT c FROM Categoriaporestadio c WHERE c.ejemplo = :ejemplo")})
public class Categoriaporestadio implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CategoriaporestadioPK categoriaporestadioPK;
    @Column(name = "EJEMPLO")
    private String ejemplo;
    @JoinColumn(name = "IDCATEGORIA", referencedColumnName = "IDCATEGORIA", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Categoria categoria;
    @JoinColumn(name = "IDESTADIO", referencedColumnName = "IDESTADIO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Estadio estadio;

    public Categoriaporestadio() {
    }

    public Categoriaporestadio(CategoriaporestadioPK categoriaporestadioPK) {
        this.categoriaporestadioPK = categoriaporestadioPK;
    }

    public Categoriaporestadio(short idcategoria, short idestadio) {
        this.categoriaporestadioPK = new CategoriaporestadioPK(idcategoria, idestadio);
    }

    public CategoriaporestadioPK getCategoriaporestadioPK() {
        return categoriaporestadioPK;
    }

    public void setCategoriaporestadioPK(CategoriaporestadioPK categoriaporestadioPK) {
        this.categoriaporestadioPK = categoriaporestadioPK;
    }

    public String getEjemplo() {
        return ejemplo;
    }

    public void setEjemplo(String ejemplo) {
        this.ejemplo = ejemplo;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Estadio getEstadio() {
        return estadio;
    }

    public void setEstadio(Estadio estadio) {
        this.estadio = estadio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (categoriaporestadioPK != null ? categoriaporestadioPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Categoriaporestadio)) {
            return false;
        }
        Categoriaporestadio other = (Categoriaporestadio) object;
        if ((this.categoriaporestadioPK == null && other.categoriaporestadioPK != null) || (this.categoriaporestadioPK != null && !this.categoriaporestadioPK.equals(other.categoriaporestadioPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.javeriana.mundialrusia.datos.Categoriaporestadio[ categoriaporestadioPK=" + categoriaporestadioPK + " ]";
    }
    
}
