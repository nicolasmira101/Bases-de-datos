package co.edu.javeriana.mundialrusia.datos;

import co.edu.javeriana.mundialrusia.datos.Equipo;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-05-16T08:05:55")
@StaticMetamodel(Directortecnico.class)
public class Directortecnico_ { 

    public static volatile SingularAttribute<Directortecnico, Short> iddirectortecnico;
    public static volatile SingularAttribute<Directortecnico, String> apellido;
    public static volatile ListAttribute<Directortecnico, Equipo> equipoList;
    public static volatile SingularAttribute<Directortecnico, String> cargo;
    public static volatile SingularAttribute<Directortecnico, String> nombre;
    public static volatile ListAttribute<Directortecnico, Equipo> equipoList1;

}