package com.felipe.pizzeria.web.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    private final UserDetailsService userDetailsService;

    @Autowired
    public JwtFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }
    //implementacion del metodo que valida las peticiones cada vez que lleguen.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //para validar un JWT debemos que hacer los siguientes pasos
        //1 validar que sea un Header-Authorization valido, que no este vacio o nulo
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader ==  null || authHeader.isEmpty() || !authHeader.startsWith("Bearer")){
            filterChain.doFilter(request, response); //si esta vacio,nulo o no inicie con Bearer dejamos que siga la peticion ya que es una peticion invalida
            return;
        }
        //2 validar que el JWT sea valido
        String jwt = authHeader.split(" ")[1].trim(); //split nos separa los indeces donde bearer es 0 y el token 1
        //para capturar nuestro jwt vamos a usar un espacio en blanco el cual esta despues de la palabra beader en el autorizathio de headers

        if (!this.jwtUtil.isValid(jwt)){
            filterChain.doFilter(request, response); //si no es valido el jwt entonces le dice que la peticion es incorrecta y es un error 403 o 401
            return;
        }

        //3 cargar el usuario del UserDetailsService
        String username = this.jwtUtil.getUsername(jwt);

        User user = (User) this.userDetailsService.loadUserByUsername(username); //cargando nuestro usuario
        //4 cargar al usuario en el contexto de seguridad
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                user.getUsername(), user.getPassword(), user.getAuthorities()
        );

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); //para obtener los detalles.
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        System.out.println(authenticationToken);

        filterChain.doFilter(request, response);
    }
}
