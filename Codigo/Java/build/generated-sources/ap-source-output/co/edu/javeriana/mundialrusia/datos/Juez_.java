package co.edu.javeriana.mundialrusia.datos;

import co.edu.javeriana.mundialrusia.datos.Juezporpartido;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-05-16T08:05:55")
@StaticMetamodel(Juez.class)
public class Juez_ { 

    public static volatile SingularAttribute<Juez, Short> idjuez;
    public static volatile SingularAttribute<Juez, String> apellido;
    public static volatile SingularAttribute<Juez, String> nombre;
    public static volatile ListAttribute<Juez, Juezporpartido> juezporpartidoList;
    public static volatile SingularAttribute<Juez, String> nacionalidad;

}