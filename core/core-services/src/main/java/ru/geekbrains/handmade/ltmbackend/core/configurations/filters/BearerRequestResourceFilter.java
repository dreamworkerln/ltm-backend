package ru.geekbrains.handmade.ltmbackend.core.configurations.filters;


import com.sun.xml.bind.v2.TODO;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import ru.geekbrains.handmade.ltmbackend.oauth_utils.data.TokenType;
import ru.geekbrains.handmade.ltmbackend.oauth_utils.services.JwtTokenService;
import ru.geekbrains.handmade.ltmbackend.utils.StringUtils;
import ru.geekbrains.handmade.ltmbackend.utils.data.enums.UserRole;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.plaf.basic.BasicEditorPaneUI;
import java.io.IOException;
import java.util.*;

/**
 * Bearer authentication filter
 * <br>Normally JWT should be signed with the pubkey instead using HMAC secret
 * Normally resource server should use claims from jwt itself rather than use some services
 * to obtain user username, principals, roles/privileges and other info
 * (Because user database located on authentication server and therefore unavailable for resource server)
 */
@Component
@Slf4j
public class BearerRequestResourceFilter extends OncePerRequestFilter {

    private final JwtTokenService jwtTokenService;
    //private final UserService userService;

    @Autowired
    public BearerRequestResourceFilter(JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
        //this.userService = userService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain chain)
        throws ServletException, IOException {

        // get header "Authorization"
        final String requestTokenHeader = request.getHeader("Authorization");


        // JWT Token is in the form "Bearer token". Remove Bearer word and get only the Token
        if (!StringUtils.isBlank(requestTokenHeader) &&
            requestTokenHeader.startsWith("Bearer ")) {
            try {
                String jwtToken = requestTokenHeader.substring(7);

                // decode & validate token, will throw exception if token not valid
                Claims claims = jwtTokenService.decodeJWT(jwtToken);

                UsernamePasswordAuthenticationToken authToken = getAuthToken(claims);
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // configure Spring Security to manually set authentication
                // After setting the Authentication in the context, we specify
                // that the current user is authenticated.
                // So it passes the Spring Security Configurations successfully.
                SecurityContextHolder.getContext().setAuthentication(authToken);

            } catch (Exception e) {
                log.info("JWT Token not valid: ", e);
                // User will stay Anonymous
            }
        }

//        // ASAP EDC
//        // TODO REMOVE ME!!!!
//        // DEBUGZ !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//        Collection<? extends GrantedAuthority> grantedAuthorities =
//        //    new HashSet<GrantedAuthority>(Collections.singletonList(new SimpleGrantedAuthority(UserRole.ADMIN.getName())));
//            new HashSet<GrantedAuthority>(Arrays.asList(
//                new SimpleGrantedAuthority(UserRole.ADMIN.getName()),
//                new SimpleGrantedAuthority(UserRole.MANAGER.getName()),
//                new SimpleGrantedAuthority(UserRole.USER.getName())
//            ));
//
//        UserDetails userDetails =
//            new org.springframework.security.core.userdetails.User(
//                "user",
//                "",
//                grantedAuthorities);
//        UsernamePasswordAuthenticationToken authToken =
//            new UsernamePasswordAuthenticationToken(userDetails, null, grantedAuthorities);
//        SecurityContextHolder.getContext().setAuthentication(authToken);
//        // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!


        chain.doFilter(request, response);
    }

    // ------------------------------------------------------------


    /**
     * Cook spring security.authentication token
     * <br> Here we load User from DB by token claims.subject,
     * because auth server and resource server located on same machine
     * and can access same DB
     * @param claims token claims
     * @throws RuntimeException user not found, user without roles, token type not allowed
     */
    private UsernamePasswordAuthenticationToken getAuthToken(Claims claims) {

        UsernamePasswordAuthenticationToken result;

        TokenType type;
        try {
            type = TokenType.getByName((String)claims.get("type"));
        }
        catch (Exception e) {
            throw new BadCredentialsException("Unknown token type");
        }
        if (type != TokenType.ACCESS) {
            throw new BadCredentialsException("Token type " + type + " not allowed");
        }

        Set<GrantedAuthority> grantedAuthorities = UserRole.rolesToGrantedAuthority(claims.get(TokenType.TOKEN_AUTHORITIES));

        if(grantedAuthorities.size() == 0) {
            throw new InsufficientAuthenticationException("User without roles");
        }

        /*
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(claims.getSubject(), "[PROTECTED]", authorities);

        // if jwt is valid configure Spring Security to manually set authentication
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
            userDetails, null, userDetails.getAuthorities());

        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        claims.get()

        // load user from DB
        User user = userService.findByUsername(claims.getSubject())
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if(user.getRoles().size() == 0) {
            throw new InsufficientAuthenticationException("User without roles");
        }
        // Берем роли пользователя из БД, какие ему назначены
        // Collection<? extends GrantedAuthority> grantedAuthorities = UserRole.rolesToGrantedAuthority(user.getRoles());
        // Готовим grantedAuthorities
        */



        // Готовим UserDetails
        UserDetails userDetails =
            new User(
                claims.getSubject(),
                "",
                grantedAuthorities);

        result = new UsernamePasswordAuthenticationToken(userDetails, null, grantedAuthorities);

        return result;
    }

}