package org.EventManagement.controller;

import org.EventManagement.database.UserRepository;
import org.EventManagement.models.User;

public class UserController {
    private final UserRepository userRepository;

    // dependency injection by constructor
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public boolean authenticateUser(String username, String password){
        return userRepository.authenticateUser(username, password);
    }
    public boolean addUser(User user){
        return userRepository.addUser(user);
    }
    public String get_user_role(String username){
        return userRepository.get_user_role(username);
    }
//    public List<User> getAllUsers(){
//        return userRepository.getAllUsers();
//    }
//    public void updateUser(User user) {
//        userRepository.updateUser(user);
//    }
//    public void deleteUser(int id) {
//        userRepository.deleteUser(id);
//    }
//    public Boolean searchUsersByUsername(String username) {
//        return userRepository.searchUsersByUsername(username);
//    }

}
