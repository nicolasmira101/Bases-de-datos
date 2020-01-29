package co.edu.javeriana.mundialrusia.datos;

import co.edu.javeriana.mundialrusia.datos.Jugador;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-05-16T08:05:55")
@StaticMetamodel(Club.class)
public class Club_ { 

    public static volatile ListAttribute<Club, Jugador> jugadorList;
    public static volatile SingularAttribute<Club, String> entrenador;
    public static volatile SingularAttribute<Club, Short> fundacion;
    public static volatile SingularAttribute<Club, Short> idclub;
    public static volatile SingularAttribute<Club, String> nombre;
    public static volatile SingularAttribute<Club, String> estadio;

}