package dev.cher.santa.model;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    void deleteByChatId(Long chatId);
}
