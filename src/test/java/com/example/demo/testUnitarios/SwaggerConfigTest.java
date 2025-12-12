package com.example.demo.testUnitarios;

import com.example.demo.configuration.SwaggerConfig;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SwaggerConfigTest {

    @Test
    void testCustomOpenAPI_FullCoverage() {
        SwaggerConfig swaggerConfig = new SwaggerConfig();

        OpenAPI openAPI = swaggerConfig.customOpenAPI();

        assertNotNull(openAPI, "El objeto OpenAPI no debería ser nulo");
        assertNotNull(openAPI.getInfo());
        assertEquals("API Microservicio Productos", openAPI.getInfo().getTitle());
        assertEquals("1.0", openAPI.getInfo().getVersion());
        assertEquals("Documentación para la gestión del catálogo de productos", openAPI.getInfo().getDescription());

        assertNotNull(openAPI.getSecurity());
        assertFalse(openAPI.getSecurity().isEmpty());
        assertTrue(openAPI.getSecurity().get(0).containsKey("BearerAuth"), 
                   "Debe contener el esquema BearerAuth");

        assertNotNull(openAPI.getComponents());
        assertNotNull(openAPI.getComponents().getSecuritySchemes());
        SecurityScheme authScheme = openAPI.getComponents().getSecuritySchemes().get("BearerAuth");
        
        assertNotNull(authScheme, "El esquema BearerAuth debe estar definido en componentes");
        assertEquals(SecurityScheme.Type.HTTP, authScheme.getType());
        assertEquals("bearer", authScheme.getScheme());
        assertEquals("JWT", authScheme.getBearerFormat());
    }
}