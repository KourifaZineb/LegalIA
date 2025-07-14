package com.chatbot.userservice.services;

import com.chatbot.commonlibrary.dtos.UserDTO;
import java.util.List;
import java.util.UUID;

public interface UserService {
    UserDTO createUser(UserDTO dto);
    UserDTO getUserById(UUID id);
    List<UserDTO> getAllUsers();
    UserDTO updateUser(UUID id, UserDTO dto);
    void deleteUser(UUID id);
    /**
     * Create or update a local user record based on Keycloak’s JWT claims.
     * @param keycloakId   the JWT “sub”
     * @param email        the JWT “email”
     * @param firstName    the JWT “given_name”
     * @param lastName     the JWT “family_name”
     */
    /*void provisionUser(String keycloakId,
                       String email,
                       String firstName,
                       String lastName);*/
}
