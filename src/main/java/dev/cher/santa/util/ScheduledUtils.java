package dev.cher.santa.util;

import com.vdurmont.emoji.EmojiParser;
import dev.cher.santa.model.Ads;
import dev.cher.santa.model.AdsRepository;
import dev.cher.santa.model.User;
import dev.cher.santa.model.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static dev.cher.santa.util.MessageUtils.*;

@Slf4j
@Component
public class ScheduledUtils {
    private final UserRepository userRepository;
    private final TelegramLongPollingBot telegramLongPollingBot;
    private final AdsRepository adsRepository;
    @Autowired
    public ScheduledUtils(UserRepository userRepository, TelegramLongPollingBot telegramLongPollingBot, AdsRepository adsRepository) {
        this.userRepository = userRepository;
        this.telegramLongPollingBot = telegramLongPollingBot;
        this.adsRepository = adsRepository;
    }

    @Scheduled (cron = "${cron.scheduledsecretsanta}")
    public void assignSecretSanta() {
        List<User> usersToParticipate = new ArrayList<>();
        List<User> allUsers = (List<User>) userRepository.findAll();

        for (User user: allUsers) {
            if(user.getPresent() != null && !user.getPresent().isEmpty()) {
                user.setSecretSantaId(user.getChatId());
                usersToParticipate.add(user);
                System.out.println("ищем подходящих юзеров ");
            }
        }
        Collections.shuffle(usersToParticipate);
        System.out.println("shuffle");

        for (int i = 0; i < usersToParticipate.size(); i++) {
            User giver = usersToParticipate.get(i);
            User receiver = usersToParticipate.get((i + 1) % usersToParticipate.size());
            giver.setGiftReceiverId(receiver.getChatId());
            System.out.println("назначем");
        }
        userRepository.saveAll(usersToParticipate);
    }

    @Scheduled(cron = "${cron.scheduledMondayTurovsky}")
    private void letterMondayTurovsky() {
        List<User> allUser = (List<User>) userRepository.findAll();

        for (User user : allUser) {
            if (user.getPresent() == null) {
                Optional<User> receiverOpt = userRepository.findById(user.getGiftReceiverId());
                if (receiverOpt.isPresent()) {
                    String message = "Ты не завершил регистрацию для участия в игре 'Тайный Санта'... " +
                            "\n\nЕсли ты хочешь запрыгнуть на корабль который уже уплыл и всё таки принять участие, нажимай ДА!";
                    log.info("Отправляем сообщение о проебосам " + user.getFirstName());
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(String.valueOf(user.getChatId()));
                    MessageUtils.sendPhoto(telegramLongPollingBot, user.getChatId(), BotImage.IMAGE_DISTRIBUTION_TUROVSKY.getImageUrl());
                    sendMessage.setText(message);
                    sendMessage.setReplyMarkup(KeyboardUtils.createYesNoButtonsInMessage(
                            "ДА", BotCallBack.BUTTON_TUROVSKY_YES.getCallback(),
                            "НЕТ", BotCallBack.BUTTON_TUROVSKY_NO.getCallback()));
                    MessageUtils.executeMessage(telegramLongPollingBot, sendMessage);
                }
            }
        }
    }

    @Scheduled(cron = "${cron.scheduledMondaySurvey}")
    private void letterMondaySurvey() {
        List<User> allUsers = (List<User>) userRepository.findAll();

        for (User user : allUsers) {
            String message = EmojiParser.parseToUnicode(user.getFirstName() + "!" + " Отличные новости! " +
                    "\nТеперь ты знаешь, кому ты будешь дарить подарок в нашем Secret Santa. " +
                    "\n\nНо есть еще одно важное решение, которое нужно принять: когда лучше всего вручить этот особенный подарок?" +
                    "\n\n:star: АНОНИМНОЕ ГОЛОСОВАНИЕ! :star:" +
                    "\n1: Вручить подарок на корпоративе 28.12 в 20:00 в зале CrossStrong" +
                    "\n2: Самостоятельно в период с 25.12 по 29.12." +
                    "\n\nДавай поможем Санте принять решение! Какой вариант тебе кажется лучше?"

            );
            log.info("Отправляем Survey" + user.getFirstName());
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(String.valueOf(user.getChatId()));
            sendPhoto(telegramLongPollingBot, user.getChatId(), BotImage.IMAGE_VOTING_CORPORATE.getImageUrl());
            sendMessage.setText(message);
            sendMessage.setReplyMarkup(KeyboardUtils.createYesNoButtonsInMessage(
                    "Хочу на корпоративе", BotCallBack.VOTING_FOR_CORPORATE_YES.getCallback(),
                    "Хочу сам", BotCallBack.VOTING_FOR_CORPORATE_NO.getCallback()));
            executeMessage(telegramLongPollingBot, sendMessage);
        }
    }

    @Scheduled(cron = "${cron.scheduledMondayDistribution}")
    private void letterMondayDistribution() {
        List<User> allUser = (List<User>) userRepository.findAll();
        for (User user : allUser) {
            if (user.getGiftReceiverId() != null) {
                Optional<User> receiverOpt = userRepository.findById(user.getGiftReceiverId());
                if (receiverOpt.isPresent()) {
                    User receiver = receiverOpt.get();
                    String message = EmojiParser.parseToUnicode(
                            "Поздравляю! " + user.getFirstName() + ", ты стал Сантой!!!" +
                                    "\n\nТебе предстоит поздравить: " + receiver.getFullName() +
                                    "\n\nА подарок: " + receiver.getPresent());
                    log.info("Отправляем Distribution" + user.getFirstName() + receiver.getFullName() + receiver.getPresent());
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(String.valueOf(user.getChatId()));
                    sendPhoto(telegramLongPollingBot, user.getChatId(), BotImage.IMAGE_DISTRIBUTION_ON.getImageUrl());
                    sendMessage.setText(message);
                    executeMessage(telegramLongPollingBot, sendMessage);
                }
            } else {
                String message = "К сожалению, ты не завершил регистрацию для участия в игре 'Тайный Санта'... \n\nМожет быть, на следующий год у тебя всё получится!";
                log.info("Отправляем сообщение о незавершенной регистрации " + user.getFirstName());
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(String.valueOf(user.getChatId()));
                sendPhoto(telegramLongPollingBot, user.getChatId(), BotImage.IMAGE_DISTRIBUTION_OFF.getImageUrl());
                sendMessage.setText(message);
                executeMessage(telegramLongPollingBot, sendMessage);
            }
        }
    }

        @Scheduled(cron = "${cron.scheduledsolo}") //сек мин час дата месяц день_недели
    private void sendAds() {
        Long adId = 2L;
        var users = userRepository.findAll();
        Optional<Ads> adOptional = adsRepository.findById(adId);
        Ads ad = adOptional.get();

        for (User user : users) {
            prepareAndSendMessage(telegramLongPollingBot,user.getChatId(), ad.getAd());
        }
    }

    @Scheduled(cron = "${cron.scheduledTest}")
    private void sendAdToSpecificUser() {
        Long adId = 2L;// идентификатор объявления
        Long userId = 957450146L;// идентификатор пользователя

        Optional<Ads> adOptional = adsRepository.findById(adId);
        Optional<User> userOptional = userRepository.findById(userId);

        if (!adOptional.isPresent() || !userOptional.isPresent()) {
            return; // или обработайте случай, когда объявление или пользователь не найден
        }

        Ads ad = adOptional.get();
        User user = userOptional.get();
        log.info("Error occurred");

        prepareAndSendMessage(telegramLongPollingBot, user.getChatId(), ad.getAd());
    }

    @Scheduled(cron = "${cron.scheduledTest}")
    private void testScheduled() {
        Long userId = 1536048324L;
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getGiftReceiverId() != null) {
                Optional<User> receiverOpt = userRepository.findById(user.getGiftReceiverId());
                if (receiverOpt.isPresent()) {
                    User receiver = receiverOpt.get();

                    String message = EmojiParser.parseToUnicode(
                            "Поздравляю! " + user.getFirstName() + ", ты стал Сантой!!!" +
                                    "\n\nТебе предстоит поздравить: " + receiver.getFullName() +
                                    "\n\nА подарок: " + receiver.getPresent());

                    log.info("Отправляем Distribution" + user.getFirstName() + receiver.getFullName() + receiver.getPresent());

                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(String.valueOf(user.getChatId()));
                    sendPhoto(telegramLongPollingBot, user.getChatId(), BotImage.IMAGE_LETTER_CONGRATULATION.getImageUrl());

                    sendMessage.setText(message);
                    executeMessage(telegramLongPollingBot, sendMessage);
                }
            }
        }
    }

    @Scheduled(cron = "${cron.scheduledSunday}")
    private void newsLetterSunday() {
        List<User> allUsers = (List<User>) userRepository.findAll();

        for (User user : allUsers) {
            if (user.getFullName() == null || user.getFullName().isEmpty()) {
                String message = EmojiParser.parseToUnicode("Привет, " + user.getFirstName() + "!" +
                        "\n\nРад что ты тоже хочешь принять участие в этом событии :santa: " +
                        "\nДля того чтобы получить подарок тебе нужно завершить регистрацию" +
                        "\n\nНажимай 'Регистрация', у тебя всё получиться :santa:" +
                        "\nНажимай 'Отказываюсь', если оно тебе не любишь подарки");
                log.info("Отправляем тормозам" + user.getFirstName());

                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(String.valueOf(user.getChatId()));
                sendPhoto(telegramLongPollingBot, user.getChatId(), BotImage.IMAGE_LETTER_ONE.getImageUrl());

                sendMessage.setText(message);
                sendMessage.setReplyMarkup(KeyboardUtils.createYesNoButtonsInMessage("Регистрация", "Точно да!", "Отказываюсь", "До свидания"));
                executeMessage(telegramLongPollingBot, sendMessage);
            }
        }
    }

    @Scheduled(cron = "${cron.scheduledMondayWarning}")
    private void LetterMondayWarning() {
        List<User> allUsers = (List<User>) userRepository.findAll();

        for (User user : allUsers) {

            String message = EmojiParser.parseToUnicode("Привет, " + user.getFirstName() + "!" +
                    "\n\nНапоминаю, что время регистрации на наше весёлое событие Secret Santa подходит к концу! " +
                    "\n\n:santa: Регистрация закрывается сегодня в 15:00.:gift:" +

                    "\nЕсли есть желание изменить какие-либо данные или предпочтения относительно подарков, еще есть время до 15:00." +
                    "\nДостаточно в меню нажать HOCHU PODAROK и заполнить заново" +
                    "\nЭто ваш последний шанс внести изменения, чтобы убедиться, получишь то, что хочешь!" +
                    "\n\nДавайте сделаем этот Secret Santa незабываемым для всех!:christmas_tree:"
            );

            log.info("Отправляем WARNING" + user.getFirstName());

            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(String.valueOf(user.getChatId()));
            sendPhoto(telegramLongPollingBot, user.getChatId(), BotImage.IMAGE_LETTER_WARNING.getImageUrl());

            sendMessage.setText(message);
            executeMessage(telegramLongPollingBot, sendMessage);

        }
    }

    @Scheduled(cron = "${cron.scheduledMondayCongratulation}")
    private void letterMondayCongratulation() {
        List<User> allUsers = (List<User>) userRepository.findAll();

        for (User user : allUsers) {

            String message = EmojiParser.parseToUnicode(
                    user.getFirstName() + ", рад сообщить, что регистрация на Secret Santa закрыта. " +
                            "\nСпасибо за участие!" + " У нас получилось собрать почти 40 участников, это круто" +
                            "\n\nВажный момент: в 18:00 ты узнаешь, кому будешь дарить подарок. " +
                            "\nГотовься узнать, кого вы сделаете счастливым в этом году! :gift:");

            log.info("Отправляем Congratulation" + user.getFirstName());

            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(String.valueOf(user.getChatId()));
            sendPhoto(telegramLongPollingBot, user.getChatId(), BotImage.IMAGE_LETTER_CONGRATULATION.getImageUrl());

            sendMessage.setText(message);
            executeMessage(telegramLongPollingBot, sendMessage);

        }
    }
}
