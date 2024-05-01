package com.felipe.pizzeria.service;

import com.felipe.pizzeria.persistence.entity.UserEntity;
import com.felipe.pizzeria.persistence.entity.UserRoleEntity;
import com.felipe.pizzeria.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserSecurityService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserSecurityService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            UserEntity userEntity = this.userRepository.findById(username)
                .orElseThrow(()-> new UsernameNotFoundException("User "+ username + "not found"));

            //de la clase userEntity obtenemos los roles que son de tipo lista le hacemos un map para sacar unicamente el STRING que contiene el rol y ese lo convierto en un array
            String[] roles = userEntity.getRoles().stream().map(UserRoleEntity::getRole).toArray(String[]::new);

            return User.builder()
                    .username(userEntity.getUsername())
                    .password(userEntity.getPassword())
                    .authorities(this.grantedAuthorities(roles))
                    .accountLocked(userEntity.getLocked())
                    .disabled(userEntity.getDisabled())
                    .build();  //convierte el builder en UserDetails.
    }

    //metodo que retorna los permisos especificos que vamos a tener
    private String[] getAuthorities(String role){
        if ("ADMIN".equals(role) || "CUSTOMER".equals(role)){
            return new String[] {"random_order"}; //el nombre de nuestro permiso.
        }
        //si no es administrador o cliente
        return new String[] {};
    }

    //metodo privado que devuelve una lista de grantedauthorities, va recibir el array de string caprutado en roles.
    private List<GrantedAuthority> grantedAuthorities(String[] roles) {

        //creamos una lista cuyo tamaño es del tamaño de los roles
        List<GrantedAuthority> authorities = new ArrayList<>(roles.length);

        //ciclo for each a los roles que tengo.
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role));

            //hacemos este bucle donde hacemos un llamado al metodfo que creamos y le enviamos el role que obtuvimos en el ciclo anterior
            for (String authority : this.getAuthorities(role)) {
                //authority permiso plano
                authorities.add(new SimpleGrantedAuthority(authority));
            }
        }
        return authorities;
    }
}
