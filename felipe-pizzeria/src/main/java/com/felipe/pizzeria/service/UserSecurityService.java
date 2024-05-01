package com.felipe.pizzeria.service;

import com.felipe.pizzeria.persistence.entity.UserEntity;
import com.felipe.pizzeria.persistence.entity.UserRoleEntity;
import com.felipe.pizzeria.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
                    .roles(roles)
                    .accountLocked(userEntity.getLocked())
                    .disabled(userEntity.getDisabled())
                    .build();  //convierte el builder en UserDetails.
    }
}
