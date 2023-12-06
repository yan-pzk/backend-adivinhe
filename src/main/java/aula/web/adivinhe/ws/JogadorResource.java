package aula.web.adivinhe.ws;

import aulas.web.adivinhe.entity.Jogador;
import jakarta.annotation.security.RolesAllowed;
import java.util.List;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

@Path("/jogador")
public class JogadorResource extends BaseResource {
    
    @Operation(summary = "Dados do jogador",
               description = "Retorna os dados de um jogador cadastrado")
    @APIResponse(responseCode = "200",
                 description = "Jogador existe. Os dados são retornados.",
                 content = @Content(mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(implementation = Jogador.class))
                )
    @APIResponse(responseCode = "204",
                 description = "O jogador não existe. Conteúdo vazio.")
    @APIResponse(responseCode = "403",
                 description = "Usuário não tem permissão para acessar dados do jogador.")
    @GET
    @Path("/info/{codigo}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"admin", "jogador"})
    public Jogador infoJogador(Integer codigo) {
        Jogador jogador = Jogador.findById(codigo);
        verificaPermissao(jogador);
        return jogador;
    }

    @Operation(summary = "Dados do jogador",
               description = "Retorna os dados de um jogador cadastrado")
    @APIResponse(responseCode = "200",
                 description = "Jogador existe. Os dados são retornados.",
                 content = @Content(mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(implementation = Jogador.class))
                )
    @APIResponse(responseCode = "204",
                 description = "O jogador não existe. Conteúdo vazio.")
    @APIResponse(responseCode = "403",
                 description = "Usuário não tem permissão para acessar dados do jogador.")
    @GET
    @Path("/info/apelido/{apelido}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"admin", "jogador"})
    public Jogador infoJogador(String apelido) {
        Jogador jogador = Jogador.find("apelido", apelido).singleResult();
        verificaPermissao(jogador);
        return jogador;
    }
    
    @Operation(summary = "Lista de todos os jogadores",
               description = "Retorna a lista de todos os jogadores cadastrados")
    @APIResponse(responseCode = "200",
                 description = "Sucesso na obtenção da lista de jogadores",
                 content = @Content(mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(implementation = Jogador[].class))
                )
    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("admin")
    public List<Jogador> listJogadores() {
        List<Jogador> jogadores = Jogador.listAll();
        return jogadores;
    }
}