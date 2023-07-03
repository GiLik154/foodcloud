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

@Transactional
@Service
@RequiredArgsConstructor
public class UserService implements UserDeleter, UserRegister, UserLogin, UserUpdater {
    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final PointRegister pointRegister;

    @Override
    public void register(UserJoinerCommend userJoinerCommend) {
        User user = new com.example.foodcloud.domain.user.domain.User(userJoinerCommend.getName(), userJoinerCommend.getPassword(), userJoinerCommend.getPhone());

        checkDuplicate(userJoinerCommend.getName());

        user.encodePassword(bCryptPasswordEncoder);

        userRepository.save(user);

        pointRegister.register(user.getId());
    }

    private void checkDuplicate(String name) {
        if (userRepository.existsByName(name)) {
            throw new UserNameDuplicateException();
        }
    }

    @Override
    public void delete(String name, String password) {
        User user = userRepository.findByName(name).orElseThrow(() -> new UsernameNotFoundException("User name not found"));

        valid(user, password);

        userRepository.deleteByName(name);
    }

    @Override
    public Long login(String name, String password) {
        User user = userRepository.findByName(name).orElseThrow(() -> new UsernameNotFoundException("User name not found"));

        valid(user, password);

        return user.getId();
    }

    private void valid(User user, String password){
        if (!user.isValidPassword(bCryptPasswordEncoder, password)) throw new BadCredentialsException("Invalid password");
    }

    @Override
    public void update(Long userId, String newPhone) {
        userRepository.findById(userId).ifPresent(user -> user.update(newPhone));
    }
}
