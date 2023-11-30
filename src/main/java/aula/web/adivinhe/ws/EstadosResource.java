package aula.web.adivinhe.ws;

import aulas.web.adivinhe.entity.Estados;
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

@Path("/estados")
public class EstadosResource {

    
    @Operation(summary = "Dados do Estado",
               description = "Retorna os dados de um estado cadastrado")
    @APIResponse(responseCode = "200",
                 description = "Estado existe. Os dados são retornados.",
                 content = @Content(mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(implementation = Estados.class))
                )
    @APIResponse(responseCode = "204",
                 description = "O estado não existe. Conteúdo vazio.")
    @APIResponse(responseCode = "403",
                 description = "Usuário não tem permissão para acessar dados do jogador.")
    @GET
    @Path("/info/{ibge}")
    @Produces(MediaType.APPLICATION_JSON)
    public Estados infoEstados(Integer ibge) {
        Estados estados = Estados.findById(ibge);
        return estados;
    }
    
    
    
    
    @Operation(summary = "Lista de todos os estados",
               description = "Retorna a lista de todos os estados cadastrados")
    @APIResponse(responseCode = "200",
                 description = "Sucesso na obtenção da lista de estados",
                 content = @Content(mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(implementation = Estados[].class))
                )
    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"admin","jogador"})
    public List<Estados> listEstados() {
        List<Estados> estados = Estados.listAll();
        return estados;
    }   
    
    
    
    private void verificaPermissao(Estados estados) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

