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
@Table(name = "JUEZPORPARTIDO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Juezporpartido.findAll", query = "SELECT j FROM Juezporpartido j")
    , @NamedQuery(name = "Juezporpartido.findByIdpartido", query = "SELECT j FROM Juezporpartido j WHERE j.juezporpartidoPK.idpartido = :idpartido")
    , @NamedQuery(name = "Juezporpartido.findByIdjuez", query = "SELECT j FROM Juezporpartido j WHERE j.juezporpartidoPK.idjuez = :idjuez")
    , @NamedQuery(name = "Juezporpartido.findByRol", query = "SELECT j FROM Juezporpartido j WHERE j.rol = :rol")})
public class Juezporpartido implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected JuezporpartidoPK juezporpartidoPK;
    @Basic(optional = false)
    @Column(name = "ROL")
    private String rol;
    @JoinColumn(name = "IDJUEZ", referencedColumnName = "IDJUEZ", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Juez juez;
    @JoinColumn(name = "IDPARTIDO", referencedColumnName = "IDPARTIDO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Partido partido;

    public Juezporpartido() {
    }

    public Juezporpartido(JuezporpartidoPK juezporpartidoPK) {
        this.juezporpartidoPK = juezporpartidoPK;
    }

    public Juezporpartido(JuezporpartidoPK juezporpartidoPK, String rol) {
        this.juezporpartidoPK = juezporpartidoPK;
        this.rol = rol;
    }

    public Juezporpartido(short idpartido, short idjuez) {
        this.juezporpartidoPK = new JuezporpartidoPK(idpartido, idjuez);
    }

    public JuezporpartidoPK getJuezporpartidoPK() {
        return juezporpartidoPK;
    }

    public void setJuezporpartidoPK(JuezporpartidoPK juezporpartidoPK) {
        this.juezporpartidoPK = juezporpartidoPK;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Juez getJuez() {
        return juez;
    }

    public void setJuez(Juez juez) {
        this.juez = juez;
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
        hash += (juezporpartidoPK != null ? juezporpartidoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Juezporpartido)) {
            return false;
        }
        Juezporpartido other = (Juezporpartido) object;
        if ((this.juezporpartidoPK == null && other.juezporpartidoPK != null) || (this.juezporpartidoPK != null && !this.juezporpartidoPK.equals(other.juezporpartidoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.javeriana.mundialrusia.datos.Juezporpartido[ juezporpartidoPK=" + juezporpartidoPK + " ]";
    }
    
}
