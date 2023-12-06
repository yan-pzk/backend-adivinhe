package aulas.web.adivinhe.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.PasswordType;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;
import jakarta.json.bind.annotation.JsonbDateFormat;
import java.util.List;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import java.util.Date;

/**
 * Representa um jogador no banco de dados.
 * @author Wilson Horstmeyer Bogado
 */
@Entity
@Table(name = "jogador")
@UserDefinition
public class Jogador extends PanacheEntityBase {
    
    public static final String DATA_NASC_PATTERN = "yyyy-MM-dd";
    
    @Id
    @NotNull
    public Integer codigo;
    
    @NotNull
    @Username
    public String apelido;

    @NotNull
    public String nome;
    
    @NotNull
    @Email
    public String email;
    
    @NotNull
    @JsonbTransient
    @Password(PasswordType.CLEAR)
    public String senha;
    
    @NotNull
    @Past
    @Temporal(TemporalType.DATE)
    @JsonbDateFormat(DATA_NASC_PATTERN)
    @Column(name = "data_nasc")
    public Date dataNasc;
    
    @Embedded
    public Endereco endereco;

    @OneToMany
    @JoinColumn(name = "jogador", referencedColumnName = "codigo")
    @JsonbTransient
    public List<Jogo> jogos;
    
    @Roles
    public String getRoles() {
        String role = "admin".equals(apelido) ? apelido : "jogador";
        return role;
    }
}
