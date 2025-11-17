
package com.Heladeria.Backend.Security;
import com.Heladeria.Backend.Security.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils; // ¡Necesitas esta importación!
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    // Inyectamos la interfaz, que Spring enlaza con CustomUserDetailsService
    private final UserDetailsService userDetailsService; 

    // Constructor para inyección de dependencias
    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    // El corazón del filtro, se ejecuta en cada petición
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain filterChain) throws ServletException, IOException {
        
        // 1. Obtener el token del encabezado (método auxiliar)
        String token = getTokenFromRequest(request);

        // 2. Validar el Token y autenticar
        if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
            
            // Obtener el nombre de usuario (email)
            String username = jwtTokenProvider.getUsername(token); 

            // Cargar los detalles del usuario desde la base de datos
            UserDetails userDetails = userDetailsService.loadUserByUsername(username); 

            // Crear el objeto de autenticación
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null, // Contraseña no es necesaria aquí (ya está autenticado por el token)
                userDetails.getAuthorities()
            );

            // Establecer detalles de la petición (IP, Sesión)
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // Colocar el objeto de autenticación en el Contexto de Seguridad
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        // Continuar con la cadena de filtros
        filterChain.doFilter(request, response);
    }
    
    // --- Método Auxiliar para Extraer el Token ---
    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        // Verificar si existe y empieza con "Bearer "
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // Quitar el prefijo "Bearer "
        }
        return null;
    }
}