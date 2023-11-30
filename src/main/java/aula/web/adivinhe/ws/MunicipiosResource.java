package aula.web.adivinhe.ws;

import aulas.web.adivinhe.entity.Municipios;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.DefaultValue;
import java.util.List;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
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
    public Municipios infoMunicipios(Integer ibge7) {
        Municipios municipios = Municipios.findById(ibge7);
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
    public List<Municipios> listMunicipios() {
        List<Municipios> municipios = Municipios.listAll();
        return municipios;
    }   
    
    

    @Operation(summary = "Lista de todos os municipios de forma paginada",
               description = "Retorna a lista de todos os municipios cadastrados de maneira paginada")
    @APIResponse(responseCode = "200",
                 description = "Sucesso na obtenção da lista de municipios paginada",
                 content = @Content(mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(implementation = Municipios[].class))
                )
    @GET
    @Path("/list_paginated")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Municipios> listMunicipios(@QueryParam("page") @DefaultValue("0") int page,
                                            @QueryParam("size") @DefaultValue("20") int size) {
        PanacheQuery<Municipios> query = Municipios.findAll();
        query.page(page, size);
        List<Municipios> municipios = query.list();
        return municipios;
    }
    
    
    
    @Operation(summary = "Lista de todos os municipios de um determinado estado",
               description = "Retorna a lista de todos os municipios de um determinado estado")
    @APIResponse(responseCode = "200",
                 description = "Sucesso na obtenção da lista de municipios paginada",
                 content = @Content(mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(implementation = Municipios[].class))
                )   
    @GET
    @Path("/por-estado/{uf}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Municipios> listMunicipiosByEstado(@PathParam("uf") String uf) {
        return Municipios.list("uf", uf);
    }
    
    
}


