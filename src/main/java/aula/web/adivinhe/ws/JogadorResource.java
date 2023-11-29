package aula.web.adivinhe.ws;

import aulas.web.adivinhe.entity.Jogador;
import java.util.List;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

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

    private void verificaPermissao(Jogador jogador) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}