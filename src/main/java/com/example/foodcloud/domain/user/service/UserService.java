package com.example.foodcloud.domain.user.service;

import com.example.foodcloud.domain.payment.service.point.PointRegister;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import com.example.foodcloud.domain.user.service.commend.UserJoinerCommend;
import com.example.foodcloud.exception.UserNameDuplicateException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserRegister, UserUpdater, UserDeleter {
    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final PointRegister pointRegister;

    @Override
    public void register(UserJoinerCommend commend) {
        validateDuplicate(commend.getUsername());

        User user = new User(commend.getUsername(), commend.getPassword(), commend.getPhone());

        user.encodePassword(bCryptPasswordEncoder);

        userRepository.save(user);

        pointRegister.register(user.getId());
    }

    private void validateDuplicate(String username) {
        if (userRepository.existsByName(username)) throw new UserNameDuplicateException("Duplicate User Name");
    }

    @Override
    public void update(String username, String newPhone) {
        User user = findByName(username);

        user.update(newPhone);
    }

    @Override
    public void delete(String username, String password) {
        User user = findByName(username);

        valid(user, password);

        userRepository.deleteByName(username);
    }

    private User findByName(String username){
        return userRepository.findByName(username).orElseThrow(() -> new UsernameNotFoundException("User name not found"));
    }

    private void valid(User user, String password){
        if (!user.isValidPassword(bCryptPasswordEncoder, password)) throw new BadCredentialsException("Invalid password");
    }
}
