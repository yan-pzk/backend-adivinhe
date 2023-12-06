package aula.web.adivinhe.ws;

import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/status")
@Tag(name = "Monitor")
@PermitAll
public class MonitorResource {

    @Operation(summary = "Verificação do status da aplicação",
               description = "Uma chamada bem-sucedida indica que a aplicação está respondendo")
    @APIResponse(responseCode = "200",
                 content = @Content(mediaType = MediaType.TEXT_PLAIN))
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String status() {
        return "Serviço ativo";
    }
}