package approdo.controllers;
import approdo.DTO.UtenteDTO;
import approdo.entities.Utente;
import approdo.exception.KeycloakErrorException;
import approdo.exception.MailUtenteAlreadyExistsException;
import approdo.services.ServizioRegistrazione;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;



@RestController
@RequestMapping("/account")
public class ControllerRegistrazione{

        @Autowired
        private ServizioRegistrazione accountingService;

        @PostMapping("/registrazione")
        public ResponseEntity create(@RequestBody @Valid UtenteDTO user) {
            try {
                Utente added = accountingService.registraUtente(user);
                return new ResponseEntity("Utente creato", HttpStatus.OK);
            } catch (MailUtenteAlreadyExistsException e) {
                return new ResponseEntity("ERROR_MAIL_USER_ALREADY_EXISTS", HttpStatus.BAD_REQUEST);
            }catch (KeycloakErrorException e) {
            return new ResponseEntity("KEYCLOAK_ERROR", HttpStatus.BAD_REQUEST);
            }
        }
        @GetMapping("/utente/{email}")
        public Utente getUtente(@PathVariable String email) {
            return accountingService.getUtente(email);
        }

}

