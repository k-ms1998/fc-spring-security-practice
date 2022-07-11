package com.fastcampus.SpringSecurityPractice.service;

import com.fastcampus.SpringSecurityPractice.constant.Role;
import com.fastcampus.SpringSecurityPractice.domain.user.User;
import com.fastcampus.SpringSecurityPractice.domain.user.UserRegisterDto;
import com.fastcampus.SpringSecurityPractice.exception.AlreadyRegisteredException;
import com.fastcampus.SpringSecurityPractice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 유저 등록
     */
    @Transactional
    public User signup(UserRegisterDto userRegisterDto) {
        String username = userRegisterDto.getUsername();
        String password = userRegisterDto.getPassword();

        if (userRepository.findByUsername(username) != null) {
            throw new AlreadyRegisteredException();
        }

        User user = User.of(username, passwordEncoder.encode(password), Role.USER);
        return userRepository.save(user);
    }

    /**
     * Username 으로 유저 찾기
     */
    public User findByUsername(String username) {

        return userRepository.findByUsername(username);
    }


}
