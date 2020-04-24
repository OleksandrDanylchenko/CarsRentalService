package ua.alexd.CarRentService.service;

import org.apache.commons.lang3.math.NumberUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ua.alexd.CarRentService.domain.User;
import ua.alexd.CarRentService.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    public Optional<User> getUserById(String id) {
        try {
            var longId = NumberUtils.createLong(id);
            return userRepository.findById(longId);
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public boolean addNewUser(User newUser) {
        return saveUser(newUser);
    }

    public boolean updateUser(@NotNull User updUser) {
        var userFromDB = getUserById(String.valueOf(updUser.getId()));
        if (userFromDB.isPresent()) {
            BeanUtils.copyProperties(updUser, userFromDB.get(), "id");
            return saveUser(userFromDB.get());
        }
        return false;
    }

    private boolean saveUser(User saveUser) {
        try {
            userRepository.save(saveUser);
        } catch (IllegalArgumentException | DataIntegrityViolationException ignored) {
            return false;
        }
        return true;
    }

    public boolean deleteUser(String id) {
        var deletionUser = getUserById(id);
        if (deletionUser.isPresent()) {
            userRepository.delete(deletionUser.get());
            return true;
        }
        return false;
    }
}