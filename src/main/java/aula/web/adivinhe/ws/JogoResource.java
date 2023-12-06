package aula.web.adivinhe.ws;

import aulas.web.adivinhe.entity.Jogo;
import aulas.web.adivinhe.entity.JogoPK;
import jakarta.annotation.security.RolesAllowed;
import java.net.URI;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

@Path("/jogo")
public class JogoResource extends BaseResource {

    public static final SimpleDateFormat DATA_JOGO_FORMAT = new SimpleDateFormat(Jogo.DATA_JOGO_PATTERN);
    public static final String URI_JOGO = "/jogo/info?jogador=%d&dataHora=%s";
    
    @Operation(summary = "Lista de todos os jogos",
               description = "Retorna a lista de todos os jogos de todos os jogadores")
    @APIResponse(responseCode = "200",
                 description = "Sucesso na obtenção da lista de jogos",
                 content = @Content(mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(implementation = Jogo[].class))
                )
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("admin")
    public Response listJogos() {
        var jogos = Jogo.listAll();
        return Response.ok(jogos).build();
    }
    
    @Operation(summary = "Lista de jogos de um jogador",
               description = "Retorna a lista de jogos do jogador correspondente ao código")
    @APIResponse(responseCode = "200",
                 description = "Sucesso na obtenção da lista de jogos",
                 content = @Content(mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(implementation = Jogo[].class))
                )
    @GET
    @Path("/jogador/{jogador}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"admin", "jogador"})
    public Response listJogos(Integer jogador) {
        verificaPermissao(jogador);
        var jogos = Jogo.list("jogoPK.jogador", jogador);
        return Response.ok(jogos).build();
    }
    
    @Operation(summary = "Informações de um jogo",
               description = "Retorna os dados de um jogo específico, informado o código do jogador e data do jogo")
    @APIResponse(responseCode = "200",
                 description = "Sucesso na obtenção das informações do jogo",
                 content = @Content(mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(implementation = Jogo.class))
    )
    @GET
    @Path("/info")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"admin", "jogador"})
    public Response infoJogo(@Parameter(name = "jogador", description = "O código do jogador", example = "123")
                             @QueryParam("jogador") Integer jogador,
                             @Parameter(name = "dataHora", description = "Data e hora do jogo", example = "2023-05-27T13:14:15-0300")
                             @QueryParam("dataHora") String dataHora)
            throws ParseException {
        verificaPermissao(jogador);
        var dh = DATA_JOGO_FORMAT.parse(dataHora);
        var jogoPK = new JogoPK(jogador, dh);
        Jogo j = Jogo.findById(jogoPK);
        return Response.ok(j).build();
    }
    
    @Operation(summary = "Registra um novo jogo",
               description = "Registra um jogo para o jogador especificado")
    @APIResponse(responseCode = "200",
                 description = "Sucesso na obtenção das informações do jogo"
    )
    @POST
    @Path("/new")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    @RolesAllowed({"admin", "jogador"})
    public Response insertJogo(@Valid Jogo jogo) {
        verificaPermissao(jogo.jogoPK.jogador);
        jogo.persist();
        String dataHora = DATA_JOGO_FORMAT.format(jogo.jogoPK.dataHora);
        String uri = String.format(URI_JOGO, jogo.jogoPK.jogador, dataHora);
        System.out.println(uri);
        return Response.created(URI.create(uri)).build();
    }
}