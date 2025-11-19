package tn.esprit.twin.twin2demo.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "Twin2Demo API",
                version = "1.0.0",
                description = "API de démonstration (Clients, Restaurants, Chaînes).",
                contact = @Contact(name = "Twin2Demo", email = "support@twin2demo.local")
        ),
        servers = {
                @Server(url = "http://localhost:8081", description = "Local Dev")
        }
)
@Configuration
// (Optionnel) si tu avais un header d’auth, tu pourrais définir un scheme ici :
// @SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")
public class OpenApiConfig {}
