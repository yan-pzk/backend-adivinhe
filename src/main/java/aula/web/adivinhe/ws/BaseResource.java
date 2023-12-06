package aula.web.adivinhe.ws;

import aulas.web.adivinhe.entity.Jogador;
import jakarta.inject.Inject;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.core.SecurityContext;

/**
 * Classe base para os servíços da API.
 * @author Wilson Horstmeyer Bogado
 */
public class BaseResource {
    @Inject
    private SecurityContext securityContext;

    protected String getPrincipal() {
        return securityContext.getUserPrincipal().getName();
    }
    
    protected boolean isPermitido(Jogador jogador) {
        return securityContext.isUserInRole("admin") ||
               (jogador != null && jogador.apelido.equals(getPrincipal()));
               
    }
    
    protected void verificaPermissao(Jogador jogador) {
        if (!isPermitido(jogador)) {
            throw new ForbiddenException();
        }
    }
    
    protected void verificaPermissao(Integer codigo) {
        Jogador jogador = Jogador.findById(codigo);
        verificaPermissao(jogador);
    }
}
