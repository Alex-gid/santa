package dev.cher.santa;

import dev.cher.santa.model.User;
import dev.cher.santa.model.UserRepository;
import dev.cher.santa.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @BeforeEach
    void setUp() {
        // Создание и сохранение тестового пользователя
        User user = new User();
        user.setChatId(123456L);
        userRepository.save(user);
    }

    @Test
    @Transactional
    void deleteUser() {
        // Проверка наличия пользователя до удаления
        assertTrue(userRepository.findById(123456L).isPresent());

        // Вызов метода для удаления
        userService.deleteUser(123456L);

        // Проверка отсутствия пользователя после удаления
        assertFalse(userRepository.findById(123456L).isPresent());
    }
}
