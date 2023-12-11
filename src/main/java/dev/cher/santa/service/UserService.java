package dev.cher.santa.service;

import dev.cher.santa.model.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Transactional
    public void deleteUser(Long chatId) {
        System.out.println("Удаление пользователя с chatId: " + chatId);
        userRepository.deleteByChatId(chatId);
    }
}
