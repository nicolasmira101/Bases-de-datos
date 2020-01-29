package co.edu.javeriana.mundialrusia.datos;

import co.edu.javeriana.mundialrusia.datos.Partido;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-05-16T08:05:55")
@StaticMetamodel(Calendario.class)
public class Calendario_ { 

    public static volatile SingularAttribute<Calendario, Short> idcalendario;
    public static volatile SingularAttribute<Calendario, String> fase;
    public static volatile ListAttribute<Calendario, Partido> partidoList;

}