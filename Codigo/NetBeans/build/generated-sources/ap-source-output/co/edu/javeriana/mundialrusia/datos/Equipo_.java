package co.edu.javeriana.mundialrusia.datos;

import co.edu.javeriana.mundialrusia.datos.Directortecnico;
import co.edu.javeriana.mundialrusia.datos.Jugador;
import co.edu.javeriana.mundialrusia.datos.Partido;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-05-16T08:05:55")
@StaticMetamodel(Equipo.class)
public class Equipo_ { 

    public static volatile ListAttribute<Equipo, Jugador> jugadorList;
    public static volatile SingularAttribute<Equipo, Directortecnico> iddirectortecnico;
    public static volatile SingularAttribute<Equipo, String> confederacion;
    public static volatile ListAttribute<Equipo, Partido> partidoList;
    public static volatile SingularAttribute<Equipo, Short> idequipo;
    public static volatile ListAttribute<Equipo, Partido> partidoList1;
    public static volatile SingularAttribute<Equipo, String> nombre;
    public static volatile SingularAttribute<Equipo, Directortecnico> idasistentetecnico;

}