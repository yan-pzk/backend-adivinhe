package aula.web.adivinhe.ws;

import aulas.web.adivinhe.entity.Estados;
import aulas.web.adivinhe.entity.Jogador;
import aulas.web.adivinhe.entity.Jogo;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import java.util.List;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import java.util.Set;
import java.util.stream.Collectors;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

@Path("/jogador")
public class JogadorResource {

    @GET
    @Path("/info/{codigo}")
    @Produces(MediaType.APPLICATION_JSON)
    public Jogador infoJogador(Integer codigo) {
        Jogador jogador = Jogador.findById(codigo);
        return jogador;
    }
    
    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Jogador> listJogadores() {
        List<Jogador> jogadores = Jogador.listAll();
        return jogadores;
    }

    
    
    
    @Operation(summary = "Cria um jogador",
               description = "Cria um jogador com base nos dados fornecidos")
    @APIResponse(responseCode = "201",
                 description = "Caso os dados estejam ok, é criado um novo jogador",
                 content = @Content(mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(implementation = Jogador.class))
                )
    @APIResponse(responseCode = "409",
                 description = "Conflito: o jogador com o código fornecido já existe.")
    @APIResponse(responseCode = "403",
                 description = "Usuário não tem permissão.")
    @APIResponse(responseCode = "400",
                 description = "Dados inválidos foram fornecidos.")
    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response createJogador(@Valid Jogador jogador) {
        try {
            if (Jogador.findById(jogador.codigo) != null) {
                return Response.status(Response.Status.CONFLICT).entity("Jogador com código " + jogador.codigo + " já existe.").build();
            }

            Jogador.persist(jogador);
            if (jogador.isPersistent()) {
                return Response.status(Response.Status.CREATED).entity(jogador).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao persistir o jogador.").build();
            }
        } catch (ConstraintViolationException e) {
            Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
            String errorMessage = violations.stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .collect(Collectors.joining(", "));
            return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
        }
    }
    
    
    
    @Operation(summary = "Atualiza os dados de um jogador",
               description = "Atualiza os dados de um jogador com base nos dados fornecidos")
    @APIResponse(responseCode = "200",
                 description = "Caso os dados estejam ok, é atualizado os dados do jogador",
                 content = @Content(mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(implementation = Estados.class))
                )
    @APIResponse(responseCode = "403",
                 description = "Usuário não tem permissão.")   
    @PUT
    @Path("/update/{codigo}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response updateJogador(@PathParam("codigo") Integer codigo, Jogador jogadorAtualizado) {
        Jogador jogadorExistente = Jogador.findById(codigo);

        if (jogadorExistente == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        jogadorExistente.apelido = jogadorAtualizado.apelido;
        jogadorExistente.nome = jogadorAtualizado.nome;
        jogadorExistente.email = jogadorAtualizado.email;
        

        jogadorExistente.dataNasc = jogadorAtualizado.dataNasc;
        jogadorExistente.endereco = jogadorAtualizado.endereco;

        jogadorExistente.persist();

        return Response.ok(jogadorExistente).build();
    }
    
    
    
    @Operation(summary = "Faz a deleção dos dados do jogador e dos jogos",
               description = "Delete os dados do jogador e os jogos relacionados")
    @APIResponse(responseCode = "200",
                 description = "Caso os dados estejam ok, é deletado os dados do jogador e dos jogos",
                 content = @Content(mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(implementation = Estados.class))
                )
    @APIResponse(responseCode = "403",
                 description = "Usuário não tem permissão.")    
    @DELETE
    @Path("/{codigoJogador}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response deleteJogador(@PathParam("codigoJogador") Integer codigoJogador) {
        Jogador jogador = Jogador.findById(codigoJogador);
        if (jogador == null) {
            return Response.status(Status.NOT_FOUND).entity("Jogador não encontrado.").build();
        }


        jogador.delete();

        return Response.status(Status.NO_CONTENT).build();
    }

    
    
    
    private void verificaPermissao(Jogador jogador) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}