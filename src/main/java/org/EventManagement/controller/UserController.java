package org.EventManagement.controller;

import org.EventManagement.database.UserRepository;
import org.EventManagement.models.User;

import org.mindrot.jbcrypt.BCrypt;

import java.util.List;

public class UserController {
    private final UserRepository userRepository;

    // dependency injection by constructor
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public boolean authenticateUser(String email, String password){
        User user = userRepository.findByEmail(email);

        if(user == null){
            return false;
        }
        else {
            System.out.println(password);
            System.out.println(user.getPassword());
            System.out.println(verifyPassword(password, user.getPassword()));
            return verifyPassword(password, user.getPassword());
        }
    }

    public boolean addUser(User user){
        user.setPassword(hashPassword(user.getPassword()));
        return userRepository.addUser(user);
    }
    public Boolean updateUser(User user) {
        return userRepository.updateUser(user);
    }
    public boolean deleteUser(int id) {
        return userRepository.deleteUser(id);
    }
    public String get_user_role(String email){
        return userRepository.get_user_role(email);
    }
    public User getUserById(int userId){
        return userRepository.getUserById(userId);
    }
    public List<User> getAllUsers(){
        return userRepository.getAllUsers();
    }
//    public Boolean searchUsersByUsername(String username) {
//        return userRepository.searchUsersByUsername(username);

//    }
    public String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }
    public boolean verifyPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);

    }
}
