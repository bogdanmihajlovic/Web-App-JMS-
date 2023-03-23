package entities;

import entities.RecenzijaPK;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2023-02-22T03:35:51")
@StaticMetamodel(Recenzija.class)
public class Recenzija_ { 

    public static volatile SingularAttribute<Recenzija, RecenzijaPK> recenzijaPK;
    public static volatile SingularAttribute<Recenzija, Double> ocena;
    public static volatile SingularAttribute<Recenzija, String> opis;

}