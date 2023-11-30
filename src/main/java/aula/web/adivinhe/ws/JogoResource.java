package aula.web.adivinhe.ws;

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
    
    
    @DELETE
    @Path("/{codigoJogador}/jogos/{idJogo}")
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
    
    
    
    @DELETE
    @Path("/{codigoJogador}/jogos")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response deleteAllJogosFromJogador(@PathParam("codigoJogador") Integer codigoJogador) {
        Jogador jogador = Jogador.findById(codigoJogador);
        if (jogador == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Jogador não encontrado.").build();
        }

        // Supondo que exista uma relação entre Jogador e Jogo, onde Jogador.jogos é uma coleção de jogos.
        jogador.jogos.forEach(jogo -> jogo.delete());

        // Confirme se você quer fazer commit imediato da transação ou se a mesma será gerenciada de outra forma.
        // Se necessário, chame entityManager.flush() para garantir que as operações de delete sejam executadas imediatamente.

        return Response.status(Response.Status.NO_CONTENT).build();
    } 
     
    
    
    
    
}