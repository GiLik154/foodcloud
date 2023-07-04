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
public class UserService implements UserDeleter, UserRegister, UserUpdater {
    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final PointRegister pointRegister;

    @Override
    public void register(UserJoinerCommend userJoinerCommend) {
        User user = new User(userJoinerCommend.getUsername(), userJoinerCommend.getPassword(), userJoinerCommend.getPhone());

        checkDuplicate(userJoinerCommend.getUsername());

        user.encodePassword(bCryptPasswordEncoder);

        userRepository.save(user);

        pointRegister.register(user.getId());
    }

    private void checkDuplicate(String username) {
        if (userRepository.existsByName(username)) throw new UserNameDuplicateException();
    }

    @Override
    public void update(String username, String newPhone) {
        User user = userFindByName(username);

        user.update(newPhone);
    }

    @Override
    public void delete(String username, String password) {
        User user = userFindByName(username);

        valid(user, password);

        userRepository.deleteByName(username);
    }

    private void valid(User user, String password){
        if (!user.isValidPassword(bCryptPasswordEncoder, password)) throw new BadCredentialsException("Invalid password");
    }

    private User userFindByName(String username){
        return userRepository.findByName(username).orElseThrow(() -> new UsernameNotFoundException("User name not found"));
    }
}
