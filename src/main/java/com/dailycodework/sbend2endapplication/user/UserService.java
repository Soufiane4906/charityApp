package com.dailycodework.sbend2endapplication.user;

import com.dailycodework.sbend2endapplication.registration.RegistrationRequest;
import com.dailycodework.sbend2endapplication.registration.token.VerificationTokenService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author Sampson Alfred
 */
@Service
@RequiredArgsConstructor

public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenService verificationTokenService;


    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User registerUser(RegistrationRequest registration) {
        var user = new User(registration.getFirstName(), registration.getLastName(),
                registration.getEmail(),
                passwordEncoder.encode(registration.getPassword()),
                Arrays.asList(new Role("ROLE_USER")));
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found")));
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional
    @Override
    public void updateUser(Long id, String firstName, String lastName, String email) {
        userRepository.update(firstName, lastName, email, id);
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        Optional<User> theUser = userRepository.findById(id);
        theUser.ifPresent(user -> verificationTokenService.deleteUserToken(user.getId()));
        userRepository.deleteById(id);
    }

    //upload image user
    @Override
    public void uploadUserImage(MultipartFile file, Long userId) {
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload empty file");
        }
        if (!Arrays.asList("image/jpeg", "image/png", "image/gif").contains(file.getContentType())) {
            throw new IllegalStateException("File must be an image [jpeg, png, gif]");
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        String fileName = userId + "_" + file.getOriginalFilename(); // Nom du fichier avec l'id de l'utilisateur
        String folder = "src/main/resources/static/images/user/";
        Path path = Paths.get(folder + fileName);
        try {
            Files.write(path, file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        user.setUserImage(fileName);
        userRepository.save(user);
    }


    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User not found"));
    }
}
