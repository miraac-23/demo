package com.example.demo.controller;

import com.example.demo.config.TokenInfo;
import com.example.demo.dto.auth.AuthenticationRequest;
import com.example.demo.dto.payload.JwtResponse;
import com.example.demo.dto.user.UserAddDto;
import com.example.demo.dto.user.UserResultDto;
import com.example.demo.entity.UserEntity;
import com.example.demo.exception.AppValidationException;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;


    @PostMapping("/add")
    public UserResultDto addUser(@RequestBody UserAddDto userAddDto) {
        return userService.userAdd(userAddDto);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> loginUser(@RequestBody AuthenticationRequest authenticationRequest) {

        Optional<UserEntity> kullanici = userRepository.findByEmail(authenticationRequest.getUsername());

        if (!kullanici.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Kullanıcı adı alanı hatalı");
        }

        if (!BCrypt.checkpw(authenticationRequest.getPassword(), kullanici.get().getPassword())) {
            throw new AppValidationException("Parola hatalı. Tekrar deneyin.");
        }

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        User user = (User) authentication.getPrincipal();
        Key key = new SecretKeySpec(Base64.getDecoder().decode(TokenInfo.SECRET),
                SignatureAlgorithm.HS256.getJcaName());

        long currentMillis = System.currentTimeMillis();
        Date now = new Date(currentMillis);
        JwtBuilder builder = Jwts.builder()
                .setIssuedAt(now)
                .setSubject(user.getUsername())
                .setIssuer(TokenInfo.ISSUER)
                .setExpiration(new Date(System.currentTimeMillis() + TokenInfo.EXPIRATION_TIME))
                .signWith(key);

        return ResponseEntity.ok(new JwtResponse(builder.compact(), user.getUsername(), grantedAuthoritiesConvertString(user.getAuthorities()), kullanici.get().getId()));

    }

    private static List<String> grantedAuthoritiesConvertString(Collection<? extends GrantedAuthority> grantedAuthorities) {
        List<String> role = new ArrayList<>();
        for (GrantedAuthority grantedAuthority : grantedAuthorities) {
            role.add(grantedAuthority.getAuthority());
        }
        return role;
    }

    @RequestMapping("/admin")
    public String loginAdmin() {
        return "Admin kullanıcı girişi başarılı";
    }


}
