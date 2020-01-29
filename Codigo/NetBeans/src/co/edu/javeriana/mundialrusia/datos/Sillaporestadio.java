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
import javax.persistence.JoinColumns;
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
@Table(name = "SILLAPORESTADIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sillaporestadio.findAll", query = "SELECT s FROM Sillaporestadio s")
    , @NamedQuery(name = "Sillaporestadio.findByIdsilla", query = "SELECT s FROM Sillaporestadio s WHERE s.sillaporestadioPK.idsilla = :idsilla")
    , @NamedQuery(name = "Sillaporestadio.findByFila", query = "SELECT s FROM Sillaporestadio s WHERE s.sillaporestadioPK.fila = :fila")
    , @NamedQuery(name = "Sillaporestadio.findByIdestadio", query = "SELECT s FROM Sillaporestadio s WHERE s.sillaporestadioPK.idestadio = :idestadio")
    , @NamedQuery(name = "Sillaporestadio.findByEjemplo", query = "SELECT s FROM Sillaporestadio s WHERE s.ejemplo = :ejemplo")})
public class Sillaporestadio implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SillaporestadioPK sillaporestadioPK;
    @Column(name = "EJEMPLO")
    private String ejemplo;
    @JoinColumn(name = "IDESTADIO", referencedColumnName = "IDESTADIO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Estadio estadio;
    @JoinColumns({
        @JoinColumn(name = "IDSILLA", referencedColumnName = "IDSILLA", insertable = false, updatable = false)
        , @JoinColumn(name = "FILA", referencedColumnName = "FILA", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Silla silla;

    public Sillaporestadio() {
    }

    public Sillaporestadio(SillaporestadioPK sillaporestadioPK) {
        this.sillaporestadioPK = sillaporestadioPK;
    }

    public Sillaporestadio(short idsilla, String fila, short idestadio) {
        this.sillaporestadioPK = new SillaporestadioPK(idsilla, fila, idestadio);
    }

    public SillaporestadioPK getSillaporestadioPK() {
        return sillaporestadioPK;
    }

    public void setSillaporestadioPK(SillaporestadioPK sillaporestadioPK) {
        this.sillaporestadioPK = sillaporestadioPK;
    }

    public String getEjemplo() {
        return ejemplo;
    }

    public void setEjemplo(String ejemplo) {
        this.ejemplo = ejemplo;
    }

    public Estadio getEstadio() {
        return estadio;
    }

    public void setEstadio(Estadio estadio) {
        this.estadio = estadio;
    }

    public Silla getSilla() {
        return silla;
    }

    public void setSilla(Silla silla) {
        this.silla = silla;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sillaporestadioPK != null ? sillaporestadioPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sillaporestadio)) {
            return false;
        }
        Sillaporestadio other = (Sillaporestadio) object;
        if ((this.sillaporestadioPK == null && other.sillaporestadioPK != null) || (this.sillaporestadioPK != null && !this.sillaporestadioPK.equals(other.sillaporestadioPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.javeriana.mundialrusia.datos.Sillaporestadio[ sillaporestadioPK=" + sillaporestadioPK + " ]";
    }
    
}
