package aula.web.adivinhe.ws;

import aulas.web.adivinhe.entity.Jogador;
import aulas.web.adivinhe.entity.Jogo;
import jakarta.transaction.Transactional;
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

    
    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response createJogador(Jogador jogador) {
        // Antes de persistir, você deve verificar se o jogador com o mesmo código já existe para evitar duplicatas
        if (Jogador.findById(jogador.codigo) != null) {
            return Response.status(Response.Status.CONFLICT).entity("Jogador com código " + jogador.codigo + " já existe.").build();
        }

        // Você pode adicionar a lógica de validação ou hash da senha aqui

        Jogador.persist(jogador);
        if (jogador.isPersistent()) {
            return Response.status(Response.Status.CREATED).entity(jogador).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    
    @PUT
    @Path("/update/{codigo}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response updateJogador(@PathParam("codigo") Integer codigo, Jogador jogadorAtualizado) {
        Jogador jogadorExistente = Jogador.findById(codigo);

        if (jogadorExistente == null) {
            // Jogador com o código fornecido não encontrado.
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        // Atualize os campos do jogador existente com os valores do jogador atualizado.
        jogadorExistente.apelido = jogadorAtualizado.apelido;
        jogadorExistente.nome = jogadorAtualizado.nome;
        jogadorExistente.email = jogadorAtualizado.email;
        // Não atualize a senha ou outros campos sensíveis diretamente sem validar ou hash-los adequadamente.
        jogadorExistente.dataNasc = jogadorAtualizado.dataNasc;
        jogadorExistente.endereco = jogadorAtualizado.endereco;

        // Persiste as alterações no banco de dados.
        jogadorExistente.persist();

        return Response.ok(jogadorExistente).build();
    }
    
    
    
    @DELETE
    @Path("/delete/{codigo}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response deleteJogador(@PathParam("codigo") Integer codigo) {
        Jogador jogador = Jogador.findById(codigo);
        if (jogador == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        // Primeiro, exclui todos os jogos associados ao jogador
        jogador.jogos.forEach(jogo -> jogo.delete());

        // Agora, exclui o próprio jogador
        jogador.delete();

        return Response.status(Response.Status.NO_CONTENT).build();
    }
    
    
    
    
    private void verificaPermissao(Jogador jogador) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}