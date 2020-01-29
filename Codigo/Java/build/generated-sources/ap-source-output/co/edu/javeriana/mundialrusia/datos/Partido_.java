package co.edu.javeriana.mundialrusia.datos;

import co.edu.javeriana.mundialrusia.datos.Calendario;
import co.edu.javeriana.mundialrusia.datos.Categoriaporpartido;
import co.edu.javeriana.mundialrusia.datos.Clienteporpartido;
import co.edu.javeriana.mundialrusia.datos.Equipo;
import co.edu.javeriana.mundialrusia.datos.Estadio;
import co.edu.javeriana.mundialrusia.datos.Gol;
import co.edu.javeriana.mundialrusia.datos.Juezporpartido;
import co.edu.javeriana.mundialrusia.datos.Jugadorporpartido;
import co.edu.javeriana.mundialrusia.datos.Tarjeta;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-05-16T08:05:55")
@StaticMetamodel(Partido.class)
public class Partido_ { 

    public static volatile SingularAttribute<Partido, Calendario> idcalendario;
    public static volatile SingularAttribute<Partido, Date> fecha;
    public static volatile SingularAttribute<Partido, Equipo> idequipolocal;
    public static volatile ListAttribute<Partido, Gol> golList;
    public static volatile ListAttribute<Partido, Jugadorporpartido> jugadorporpartidoList;
    public static volatile SingularAttribute<Partido, Short> idpartido;
    public static volatile ListAttribute<Partido, Tarjeta> tarjetaList;
    public static volatile ListAttribute<Partido, Categoriaporpartido> categoriaporpartidoList;
    public static volatile ListAttribute<Partido, Clienteporpartido> clienteporpartidoList;
    public static volatile SingularAttribute<Partido, Equipo> idequipovisitante;
    public static volatile SingularAttribute<Partido, Estadio> idestadio;
    public static volatile ListAttribute<Partido, Juezporpartido> juezporpartidoList;

}