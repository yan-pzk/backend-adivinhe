package aula.web.adivinhe.ws;

import aulas.web.adivinhe.entity.Municipios;
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

@Path("/municipios")
public class MunicipiosResource {

    
    @Operation(summary = "Dados do Municipio",
               description = "Retorna os dados de um municipio cadastrado")
    @APIResponse(responseCode = "200",
                 description = "Municipio existe. Os dados são retornados.",
                 content = @Content(mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(implementation = Municipios.class))
                )
    @APIResponse(responseCode = "204",
                 description = "O municipio não existe. Conteúdo vazio.")
    @APIResponse(responseCode = "403",
                 description = "Usuário não tem permissão para acessar dados do jogador.")
    @GET
    @Path("/info/{ibge7}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"admin", "jogador"})
    public Municipios infoMunicipios(Integer ibge7) {
        Municipios municipios = Municipios.findById(ibge7);
        verificaPermissao(municipios);
        return municipios;
    }
    
    
    
    
    @Operation(summary = "Lista de todos os municipios",
               description = "Retorna a lista de todos os municipios cadastrados")
    @APIResponse(responseCode = "200",
                 description = "Sucesso na obtenção da lista de municipios",
                 content = @Content(mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(implementation = Municipios[].class))
                )
    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"admin","jogador"})
    public List<Municipios> listMunicipios() {
        List<Municipios> municipios = Municipios.listAll();
        return municipios;
    }   
    
    
    
    private void verificaPermissao(Municipios municipio) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

