package aulas.web.adivinhe.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import java.util.List;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;




@Entity
@Table(name = "municipios")
public class Municipios extends PanacheEntityBase {
    
    @Id
    @NotNull
    public Integer ibge7;
    
    @NotNull
    public Integer ibge;

    @NotNull
    public String uf_mun;
    
    @NotNull
    public String uf;

    @NotNull
    public String município;    
    
    @NotNull
    public String regiao;    
    
    
    @ManyToOne
    @JoinColumn(name = "uf", referencedColumnName = "uf")
    @JsonbTransient
    public Estados estados;
    
}
