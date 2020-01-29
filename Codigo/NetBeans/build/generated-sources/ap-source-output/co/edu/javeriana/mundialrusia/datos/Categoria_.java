package co.edu.javeriana.mundialrusia.datos;

import co.edu.javeriana.mundialrusia.datos.Categoriaporpartido;
import co.edu.javeriana.mundialrusia.datos.Estadio;
import co.edu.javeriana.mundialrusia.datos.Silla;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-05-16T08:05:55")
@StaticMetamodel(Categoria.class)
public class Categoria_ { 

    public static volatile ListAttribute<Categoria, Estadio> estadioList;
    public static volatile ListAttribute<Categoria, Categoriaporpartido> categoriaporpartidoList;
    public static volatile ListAttribute<Categoria, Silla> sillaList;
    public static volatile SingularAttribute<Categoria, Short> idcategoria;

}