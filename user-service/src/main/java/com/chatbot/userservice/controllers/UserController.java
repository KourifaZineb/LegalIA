package com.chatbot.userservice.controllers;

import com.chatbot.userservice.dtos.UserDTO;
import com.chatbot.userservice.entities.enums.Language;
import com.chatbot.userservice.entities.enums.userStatus;
import com.chatbot.userservice.response.DefaultResponse;
import com.chatbot.userservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Validated @RequestBody UserDTO userDTO) {
        UserDTO createUser = userService.createUser(userDTO);
        return new ResponseEntity<>(createUser, HttpStatus.CREATED);

    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        Optional<UserDTO> userOpt = userService.getUserById(id);
        return userOpt.map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

   @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<UserDTO>> getUsersByStatus(@PathVariable userStatus status) {
        List<UserDTO> users = userService.getUsersByStatus(status);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO user) {
        user.setUserId(id);
        UserDTO updatedUser = userService.updateUser(user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DefaultResponse> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(DefaultResponse.builder().returnCode("000").returnMessage("Success").build(), HttpStatus.OK);
    }

    @PutMapping("/language/{id}")
    public ResponseEntity<DefaultResponse> changeLanguage(@PathVariable Long id, @RequestBody Map<String, String> request) {
        try {
            String languageStr = request.get("preferredLanguage");
            if (languageStr == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            // Vérifiez que la valeur correspond à une valeur enum valide
            Language language = Language.valueOf(languageStr);
            userService.changeUserLanguage(id, language);
            return new ResponseEntity<>(DefaultResponse.builder().returnCode("000").returnMessage("Success").build(), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            // Si la conversion en enum échoue
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/login")
    public ResponseEntity<DefaultResponse> login(@RequestParam String email, @RequestParam String password) {
        boolean authenticated = userService.authenticateUser(email, password);
        if (authenticated) {
            return new ResponseEntity<>(DefaultResponse.builder().returnCode("000").returnMessage("Success").build(), HttpStatus.OK);
        }
        return new ResponseEntity<>(DefaultResponse.builder().returnCode("111").returnMessage("Invalid credentials").build(), HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/language/{language}")
    public ResponseEntity<List<UserDTO>> getUsersByPreferredLanguage(@PathVariable Language language) {
        List<UserDTO> users = userService.searchByPreferredLanguage(language);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/check-email/{email}")
    public ResponseEntity<Map<String, Boolean>> checkEmailExists(@PathVariable String email) {
        boolean exists = userService.existsByEmail(email);
        return new ResponseEntity<>(Map.of("exists", exists), HttpStatus.OK);
    }

}
