package org.EventManagement.services;

import org.EventManagement.database.UserRepository;
import org.EventManagement.models.Event;
import org.EventManagement.models.User;

import java.util.List;

public class UserService {
    private final UserRepository userRepository;

    // dependency injection by constructor
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public boolean authenticateUser(String username, String password){
        return userRepository.authenticateUser(username, password);
    }
    public void addUser(User user){
        userRepository.addUser(user);
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
//    public List<User> searchUsersByUsername(String name) {
//        return userRepository.searchUsersByName(name);
//    }

}
