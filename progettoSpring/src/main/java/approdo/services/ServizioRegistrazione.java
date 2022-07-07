package approdo.services;

import approdo.DTO.UtenteDTO;
import approdo.entities.Utente;
import approdo.exception.KeycloakErrorException;
import approdo.exception.MailUtenteAlreadyExistsException;
import approdo.exception.UNUtenteAlreadyExistsException;
import approdo.repositories.UtenteRepository;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import javax.ws.rs.core.Response;
import java.util.*;

@Service
public class ServizioRegistrazione{

    @Autowired
    private UtenteRepository userRepository;

    @Value("${keycloak.serverUrl}")
    private String url;

    @Value("${keycloak.username_admin}")
    private String username_admin;

    @Value("${keycloak.password_admin}")
    private String password_admin;

    @Value("${keycloak.nome_client}")
    private String nome_client;

    @Value("${keycloak.ruolo}")
    private String ruolo;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.clientId}")
    private String clientId;

    @Value("${keycloak.clientSecret}")
    private String clientSecret;


    @Transactional(readOnly = false)
    public Utente registraUtente(UtenteDTO user) throws MailUtenteAlreadyExistsException, KeycloakErrorException {
        if ( userRepository.existsByEmail(user.getEmail())&&user.isValid()) {
            throw new MailUtenteAlreadyExistsException();
        }
        Utente ut=new Utente();
        ut.setUtente(user.isUtente());
        ut.setNome(user.getNome());
        ut.setCognome(user.getCognome());
        ut.setEmail(user.getEmail());
        ut.setPhone(user.getPhone());

        if(registraUtenteKeycloak(ut,user.getPassword()))
            return userRepository.save(ut);
        else
            throw new KeycloakErrorException();
    }

    @Transactional(readOnly = true)
    public List<Utente> getAllUsers() {
        return userRepository.findAll();
    }


    @Transactional(readOnly = true)
    public Utente getUtente(String email) {
        return userRepository.findByEmail(email).get(0);
    }

    boolean registraUtenteKeycloak(Utente utente,String password) {

        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(url)
                .realm(realm)
                .grantType(OAuth2Constants.PASSWORD)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .username(username_admin)
                .password(password_admin)
                .build();


        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(utente.getEmail());
        user.setEmail(utente.getEmail());


        // Get realm
        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();
        // Create user (requires manage-users role)
        Response response = usersResource.create(user);
        if (response.getStatusInfo() != Response.Status.CREATED) {
            System.out.printf("Repsonse: %s %s%n", response.getStatus(), response.getStatusInfo());
            return false;
        }


        System.out.println(response.getLocation());
        String userId = CreatedResponseUtil.getCreatedId(response);

        CredentialRepresentation passwordCred = new CredentialRepresentation();
        passwordCred.setType(CredentialRepresentation.PASSWORD);
        passwordCred.setValue(password);

        UserResource userResource = usersResource.get(userId);
        // Set password credential
        userResource.resetPassword(passwordCred);

        ClientRepresentation app1Client = realmResource.clients().findByClientId(nome_client).get(0);
//
//        // Get client level role (requires view-clients role)
        RoleRepresentation userClientRole = realmResource.clients().get(app1Client.getId()).roles().get(ruolo).toRepresentation();
//
//        // Assign client level role to user
        userResource.roles().clientLevel(app1Client.getId()).add(Arrays.asList(userClientRole));

        return true;
    }
}
