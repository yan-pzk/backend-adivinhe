package aulas.web.adivinhe.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import java.util.List;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;


@Entity
@Table(name = "estados")
public class Estados extends PanacheEntityBase {
    
    @Id
    @NotNull
    public Integer ibge;
    
    @NotNull
    public String estado;

    @NotNull
    public String uf;
    
    @NotNull
    public String regiao;
    
    
    @OneToMany
    @JoinColumn(name = "uf", referencedColumnName = "uf", insertable=false, updatable=false)
    @JsonbTransient
    public List<Municipios> municipios;
    
}
