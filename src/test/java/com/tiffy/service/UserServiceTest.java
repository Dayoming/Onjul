package com.tiffy.service;

import com.tiffy.entity.User;
import com.tiffy.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestPropertySource(locations = "classpath:application-test.properties")
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        passwordEncoder = new BCryptPasswordEncoder();
        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    @DisplayName("회원가입 테스트")
    public void createUserTest() {
        String username = "testuser";
        String email = "test@example.com";
        String password = "password123";
        String nickname = "TestNickname";

        // Mocking userRepository save method
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setNickname(nickname);

        when(userRepository.save(any(User.class))).thenReturn(user);

        User createdUser = userService.create(username, email, password, nickname);

        // Assertions
        assertThat(createdUser).isNotNull();
        assertThat(createdUser.getUsername()).isEqualTo(username);
        assertThat(createdUser.getEmail()).isEqualTo(email);
        assertThat(passwordEncoder.matches(password, createdUser.getPassword())).isTrue();
        assertThat(createdUser.getNickname()).isEqualTo(nickname);
    }

    @Test
    @DisplayName("중복 회원가입 테스트")
    public void createUserTest_UsernameAlreadyExists() {
        String username = "testuser";
        String email = "test@example.com";
        String password = "password123";
        String nickname = "TestNickname";

        // Simulate userRepository throwing DataIntegrityViolationException on save
        when(userRepository.save(any(User.class))).thenThrow(new DataIntegrityViolationException("이미 등록된 사용자입니다."));

        // Using assertThatThrownBy to expect an exception
        assertThatThrownBy(() -> {
            userService.create(username, email, password, nickname);
        }).isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("이미 등록된 사용자입니다.");
    }
}
