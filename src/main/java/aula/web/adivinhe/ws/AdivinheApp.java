package aula.web.adivinhe.ws;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;

/**
 * Ponto de entrada da API.
 * @author Wilson Horstmeyer Bogado
 */
@ApplicationPath("/")
@OpenAPIDefinition(
    info = @Info(title = "Webservice de apoio ao jogo Adivinhe!",
                 description = "Permite gerenciar jogos e jogadores",
                 version = "1.0")
)
public class AdivinheApp extends Application {
    
}
