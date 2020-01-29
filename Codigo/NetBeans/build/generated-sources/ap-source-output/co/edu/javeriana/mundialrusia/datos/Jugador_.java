package co.edu.javeriana.mundialrusia.datos;

import co.edu.javeriana.mundialrusia.datos.Club;
import co.edu.javeriana.mundialrusia.datos.Equipo;
import co.edu.javeriana.mundialrusia.datos.Gol;
import co.edu.javeriana.mundialrusia.datos.Jugadorporpartido;
import co.edu.javeriana.mundialrusia.datos.Pais;
import co.edu.javeriana.mundialrusia.datos.Tarjeta;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-05-16T08:05:55")
@StaticMetamodel(Jugador.class)
public class Jugador_ { 

    public static volatile ListAttribute<Jugador, Gol> golList;
    public static volatile SingularAttribute<Jugador, Short> numero;
    public static volatile SingularAttribute<Jugador, Short> peso;
    public static volatile ListAttribute<Jugador, Tarjeta> tarjetaList;
    public static volatile SingularAttribute<Jugador, Short> estatura;
    public static volatile SingularAttribute<Jugador, Equipo> idequipo;
    public static volatile SingularAttribute<Jugador, String> nombre;
    public static volatile ListAttribute<Jugador, Club> clubList;
    public static volatile SingularAttribute<Jugador, Date> fechanacimiento;
    public static volatile SingularAttribute<Jugador, String> foto;
    public static volatile SingularAttribute<Jugador, Pais> idpais;
    public static volatile ListAttribute<Jugador, Jugadorporpartido> jugadorporpartidoList;
    public static volatile SingularAttribute<Jugador, String> apellido;
    public static volatile SingularAttribute<Jugador, Long> idjugador;

}