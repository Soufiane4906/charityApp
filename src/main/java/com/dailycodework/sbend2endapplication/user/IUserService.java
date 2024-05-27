package com.dailycodework.sbend2endapplication.user;

import com.dailycodework.sbend2endapplication.registration.RegistrationRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

/**
 * @author Sampson Alfred
 */

public interface IUserService {
    List<User> getAllUsers();
    User registerUser(RegistrationRequest registrationRequest);
   Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);

    void updateUser(Long id, String firstName, String lastName, String email);

    void deleteUser(Long id);


    //upload image user
    void uploadUserImage(MultipartFile file, Long userId);
}
