package com.felipe.pizzeria.web.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtil {

    private static String SECRET_KEY = "p1p3_p1zz4"; //palabra clave
    private static Algorithm ALGORITHM = Algorithm.HMAC256(SECRET_KEY); //algoritmo
    public String create(String username){
        return JWT.create()
                .withSubject(username) //asunto siempre sera el usuario en cuestion
                .withIssuer("pipe-pizza") //quien lo crea
                .withIssuedAt(new Date()) //fecha que fue creada
                .withExpiresAt(new Date(System.currentTimeMillis()+ TimeUnit.DAYS.toMillis(15))) //token expira en 15 dias apartir de la fecha actual
                .sign(ALGORITHM); //es la firma de nuestro JWT
    }


}
