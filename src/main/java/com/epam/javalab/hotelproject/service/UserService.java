package com.epam.javalab.hotelproject.service;

import com.epam.javalab.hotelproject.model.User;
import com.epam.javalab.hotelproject.repository.UserDAO;
import com.epam.javalab.hotelproject.repository.UserRepository;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Provides API for actions with users such as: Register, Authorize
 *
 * @author Maksim Starshinov, Sergei Petriankin
 * @version 1.0
 * @since 1.0
 */
public class UserService {
    private SecurityService securityService = new SecurityService();
    private UserDAO userDAO = new UserRepository();

    /**
     * @param user bean from user input
     * @return <code>true</code> if registration was successful, otherwise <code>false</code>
     */
    public boolean registerUser(User user) {
        if (!validateUserBean(user)) {
            return false;
        }

        if (!loginIsAvailable(user.getLogin())) {
            return false;
        }

        user.setPassword(securityService.hash(user.getPassword()));

        return saveUser(user);
    }

    /**
     * @param user bean from user input
     * @return <code>true</code> if authorization was successful, otherwise, if login and password didn't match <code>false</code>
     */
    public boolean authorize(User user) {
        if (!validateUserBean(user)) {
            return false;
        }

        return securityService.authorize(user);
    }

    private boolean saveUser(User user) {
        return userDAO.insertUser(user);
    }

    private boolean loginIsAvailable(String login) {
        return userDAO.findByLogin(login).getLogin().isEmpty();
    }

    // TODO what about email?
    private boolean validateUserBean(User user) {
        if (user.getLogin() == null || user.getPassword() == null) {
            return false;
        }

        if (user.getLogin().isEmpty() || user.getPassword().isEmpty()) {
            return false;
        }

        return true;
    }
}