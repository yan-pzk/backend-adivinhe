package aula.web.adivinhe.ws;

import aulas.web.adivinhe.entity.Estados;
import aulas.web.adivinhe.entity.Jogador;
import aulas.web.adivinhe.entity.Jogo;
import aulas.web.adivinhe.entity.JogoPK;
import java.net.URI;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

@Path("/jogo")
public class JogoResource {

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listJogos() {
        var jogos = Jogo.listAll();
        return Response.ok(jogos).build();
    }
    
    @GET
    @Path("/jogador/{jogador}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listJogos(Integer jogador) {
        var jogos = Jogo.list("jogoPK.jogador", jogador);
        return Response.ok(jogos).build();
    }
    
    @GET
    @Path("/info/{jogador}/{jogo}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response infoJogo(Integer jogador, Date dataHora) {
        var jogoPK = new JogoPK(jogador, dataHora);
        Jogo j = Jogo.findById(jogoPK);
        return Response.ok(j).build();
    }
    
    @POST
    @Path("/new")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response insertJogo(@Valid Jogo jogo) {
        jogo.persist();
        Instant instant = Instant.ofEpochMilli(jogo.jogoPK.dataHora.getTime());
        String iso = DateTimeFormatter.ISO_INSTANT.format(instant);
        return Response.created(URI.create("/jogo/info/" + jogo.jogoPK.jogador + "/" + iso)).build();
    }
    
    
    
    @Operation(summary = "Faz a deleção do jogo de um determinado jogador",
               description = "Delete o jogo informado do jogador informado")
    @APIResponse(responseCode = "200",
                 description = "Caso os dados estejam ok, é deletado o jogo do jogador",
                 content = @Content(mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(implementation = Estados.class))
                )
    @APIResponse(responseCode = "403",
                 description = "Usuário não tem permissão.")    
    @DELETE
    @Path("/{jogador}/jogos/{jogo}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response deleteJogo(@PathParam("codigoJogador") Integer codigoJogador, @PathParam("idJogo") Long idJogo) {
        Jogador jogador = Jogador.findById(codigoJogador);
        if (jogador == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Jogador não encontrado.").build();
        }

        Jogo jogo = Jogo.findById(idJogo);
        if (jogo == null || !jogo.jogador.equals(jogador)) {
            return Response.status(Response.Status.NOT_FOUND).entity("Jogo não encontrado ou não pertence ao jogador indicado.").build();
        }

        jogo.delete();

        return Response.status(Response.Status.NO_CONTENT).build();
    }
    
    
    
    @Operation(summary = "Faz a deleção dos jogos de um determinado jogador",
               description = "Deleta os jogos do jogador informado")
    @APIResponse(responseCode = "200",
                 description = "Caso os dados estejam ok, os jogos são deletados",
                 content = @Content(mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(implementation = Estados.class))
                )
    @APIResponse(responseCode = "403",
                 description = "Usuário não tem permissão.")       
    @DELETE
    @Path("/{jogador}/jogos")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response deleteAllJogosFromJogador(@PathParam("jogador") Integer codigoJogador) {
        Jogador jogador = Jogador.findById(codigoJogador);
        if (jogador == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Jogador não encontrado.").build();
        }

        jogador.jogos.forEach(jogo -> jogo.delete());

        return Response.status(Response.Status.NO_CONTENT).build();
    } 
       
    
    
    
}