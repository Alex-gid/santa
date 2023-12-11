package dev.cher.santa.service;

import com.vdurmont.emoji.EmojiParser;
import dev.cher.santa.config.BotConfig;
import dev.cher.santa.model.Ads;
import dev.cher.santa.model.AdsRepository;
import dev.cher.santa.model.User;
import dev.cher.santa.model.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final UserService userService;
    private final UserRepository userRepository;
    private final BotConfig config;
    private final AdsRepository adsRepository;
    static final String IMAGE = "https://i.ibb.co/FKFBH6B/razdel-v-razrabotke-1.jpg";
    static final String IMAGE_ONE = "https://i.ibb.co/mR3kZkG/one.png";
    static final String IMAGE_TWO = "https://i.ibb.co/zRKVsH8/two.png";
    static final String IMAGE_THREE = "https://i.ibb.co/vhGZ3jW/three.png";
    static final String IMAGE_FOUR = "https://i.ibb.co/hW3g7cQ/four.png";
    static final String IMAGE_FIVE = "https://i.ibb.co/yy8ym15/five.png";
    static final String IMAGE_SIX = "https://i.ibb.co/1d5vK2J/six.png";
    static final String IMAGE_SEVEN = "https://i.ibb.co/G9frPPy/seven.png";
    static final String IMAGE_EIGHT = "https://i.ibb.co/6J4fwdn/eight.png";
    static final String IMAGE_HELP = "https://i.ibb.co/P6NjbfT/help.png";
    static final String IMAGE_DEVELOPER = "https://i.ibb.co/w4DnpHr/developer.png";
    static final String IMAGE_END = "https://i.ibb.co/H4kscxP/end.png";
    static final String HELP_TEXT = "Этот бот создан исключительно ради  забавы. \n\n" +
            "В меню присутствует следующий список команд: \n\n" +
            "Type /start начать всё с начала \n\n" +
            "Type /present нажимая на эту команду ты сможешь написать подарок который ты хочешь, да, если что, то можно поменять, функция доступна до 18.12\n\n" +
            "Type /help Вызов меню с подсказками \n\n" +
            "Type /developer если что-то сломалось можно написать разработчику он починит и поможет\n\n";

    static final String YES_BUTTON = "YES_BUTTON";
    static final String NO_BUTTON = "NO_BUTTON";

    @Autowired
    public TelegramBot(UserService userService, BotConfig config, UserRepository userRepository, AdsRepository adsRepository) {
        this.userService = userService;
        this.config = config;
        List<BotCommand> listOfCommands = new ArrayList<>();
        listOfCommands.add(new BotCommand("/start", "ZANOVO"));
        listOfCommands.add(new BotCommand("/present", "HOCHU PODAROK"));
        listOfCommands.add(new BotCommand("/help", "PODSKASKI"));
        listOfCommands.add(new BotCommand("/developer", "message developer"));
        try {
            this.execute(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("Error setting bot's commands list " + e.getMessage());
        }
        this.userRepository = userRepository;
        this.adsRepository = adsRepository;
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getBotToken();
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {

            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            User userState = userRepository.findById(chatId).orElse(null);

            if (userState != null && "AWAITING_FIO".equals(userState.getState())) {
                handleFioInput(userState, update.getMessage().getText());
                return;
            }

            if (userState != null && "AWAITING_PRESENT".equals(userState.getState())) {
                handlePresentInput(userState, update.getMessage().getText());
                return;
            }

            if (messageText.contains("/send") && config.getOwnerId() == chatId) {
                var textToSend = EmojiParser.parseToUnicode(messageText.substring(messageText.indexOf(" ")));
                var users = userRepository.findAll();

                for (User user : users) {
                    prepareAndSendMessage(user.getChatId(), textToSend);
                }
            } else {

                switch (messageText) {
                    case "/start":
                        registerUser(update.getMessage());
                        startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                        break;
                    case "/help":
                        sendPhoto(chatId, IMAGE_HELP);
                        prepareAndSendMessage(chatId, HELP_TEXT);
                        break;
                    case "/send":
                        break;
                    case "/register":
                        register(chatId);
                        break;
                    case "/present":
                        userState = userRepository.findById(chatId).orElse(new User());
                        userState.setState("AWAITING_FIO");
                        userRepository.save(userState);
                        sendPhoto(chatId, IMAGE_FOUR);
                        prepareAndSendMessage(chatId, "Отлично, пиши Фамилию и Имя, прямо сообщением, не стесняйся)\n\nПомни чтобы Санта смог тебя найти ему нужны корректные ФИО, а иначе есть шанс остаться без подарка)");
                        break;
                    case "/developer":
                        sendPhoto(chatId, IMAGE_DEVELOPER);
                        prepareAndSendMessage(chatId, "пока в личку @aleksandr_cherkasov, если время хватит допилю нормально)");
                        break;
                    default:
                        prepareAndSendMessage(chatId, "Я всего лишь бот для Тайного Санты, если есть вопросы/пожелания нажмите /developer и мой хозяин вас услышит");

                }


            }
        }


        if (update.hasCallbackQuery()) {

            String callBackData = update.getCallbackQuery().getData();
            long messageId = update.getCallbackQuery().getMessage().getMessageId();
            long chaId = update.getCallbackQuery().getMessage().getChatId();

            switch (callBackData) {

                case YES_BUTTON:
                    String text = EmojiParser.parseToUnicode(":christmas_tree:Отлично, " + update.getCallbackQuery().getFrom().getFirstName() + ", теперь ты в деле!:christmas_tree:\n" +
                            "Самое время объяснить тебе что к чему – ты же наверняка не новичок в этом деле? А даже если и так – это не сложно!\n" +
                            "Правила очень просты:\n\n" +
                            "1.  Мы знакомимся :santa:\n" +
                            "2.  Ты пишешь какой ты хочешь новогодний подарок :gift:\n" +
                            "3.  18 декабря случайным образом ты получаешь ФИО человека, для которого необходимо подготовить новогоднее чудо.\n\n" +
                            "4.  Новогодний подарок нужно подарить человеку в период с 25 по 29 декабря :christmas_tree:\n" +
                            "ОБРАЩАЕМ ВНИМАНИЕ: мы договариваемся, что все новогодние подарки в пределах 500 :moneybag: \n\n" +
                            "А теперь, когда ты знаком с правилами, давай закрепим результат клятвой:\n" +
                            "Нажимая Точно да! ты принимаешь на себя ответственность за Новогоднюю магию :snowman_with_snow: \n" +
                            "Нажимая Точно нет! ты можешь удалить себя из праздника :deer:\n");
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(String.valueOf(chaId));
                    sendPhoto(chaId, IMAGE_TWO);
                    sendMessage.setText(text);
                    sendMessage.setReplyMarkup(createYesNoButtonsInMessage("Точно да!", "Точно да!", "Точно нет", NO_BUTTON));
                    executeMessage(sendMessage);
                    break;

                case NO_BUTTON:
                    String textNoButton = EmojiParser.parseToUnicode("Ну что же ты дружок подводишь Санту:santa:!\n" +
                            "Если не хочешь, чтобы тебя превратили в оленя :deer:, подумай ещё раз… хо-хо-хо\n");
                    SendMessage sendMessageNoButton = new SendMessage();
                    sendMessageNoButton.setChatId(String.valueOf(chaId));
                    sendPhoto(chaId, IMAGE_SEVEN);
                    sendMessageNoButton.setText(textNoButton);
                    sendMessageNoButton.setReplyMarkup(createYesNoButtonsInMessage("Не хочу быть оленем(", "Не хочу быть оленем(", "До свидания", "До свидания"));
                    executeMessage(sendMessageNoButton);
                    break;

                case "Не хочу быть оленем(":
                    String text1 = EmojiParser.parseToUnicode("Никогда не поздно передумать :santa:\n" +
                            "Правила очень просты:\n\n" +
                            "1.  Мы знакомимся :santa:\n" +
                            "2.  Ты пишешь какой ты хочешь новогодний подарок :gift:\n" +
                            "3.  18 декабря случайным образом ты получаешь ФИО человека, для которого необходимо подготовить новогоднее чудо.\n\n" +
                            "4.  Новогодний подарок нужно подарить человеку в период с 25 по 29 декабря :christmas_tree:\n" +
                            "ОБРАЩАЕМ ВНИМАНИЕ: мы договариваемся, что все новогодние подарки в пределах 500 :moneybag: \n\n" +
                            "Чтобы принять участие нажимай кнопку : Ура! Я не олень! :deer: \n");
                    SendMessage send1 = new SendMessage();
                    send1.setChatId(String.valueOf(chaId));
                    sendPhoto(chaId, IMAGE_EIGHT);
                    send1.setText(text1);
                    send1.setReplyMarkup(createYesNoButtonsInMessage("Ура! Я не олень!", "Точно да!", "До свидания", "До свидания"));
                    executeMessage(send1);
                    break;

                case "До свидания":
                    String reset = EmojiParser.parseToUnicode("Если надумаешь вернуться, в MENU есть кнопка ZANOVO\nС Наступающим Новым Годом! :deer: ");
                    SendMessage sendReset = new SendMessage();
                    sendReset.setChatId(String.valueOf(chaId));
                    sendPhoto(chaId, IMAGE_END);
                    sendReset.setText(reset);
                    executeMessage(sendReset);
                    userService.deleteUser(chaId);
                    break;

                case "Точно да!":
                    String textYes = EmojiParser.parseToUnicode("Начинаем готовиться к магическим деяниям! \n" +
                            "Чтобы получить подарок нужно подарить подарок, всё просто! :santa: :gift:\n " +
                            "Чтобы твой Санта не тыкал пальцем в небо, ты можешь написать что ты хочешь, а можешь ничего не писать, пусть гадает.\n\n" +
                            "В левом нижнем углу ты видишь голубую кнопку MENU.\n" +
                            "Нажми на неё и выбери пункт HOCHU PODAROK!");
                    SendMessage messageYes = new SendMessage();
                    messageYes.setChatId(String.valueOf(chaId));
                    sendPhoto(chaId, IMAGE_THREE);
                    messageYes.setText(textYes);
                    executeMessage(messageYes);
                    break;
            }
        }
    }

    private void handlePresentInput(User userState, String present) {
        userState.setPresent(present);
        userState.setState(null);
        userRepository.save(userState);
        sendPhoto(userState.getChatId(), IMAGE_SIX);
        prepareAndSendMessage(userState.getChatId(), "Желаемый подарок сохранен: " + present +
                "\n\nЕсли всё перепуталось, то можешь начать заново нажав /present. " +
                "\n\nЕсли же всё хорошо, то спасибо за участие) " +
                "\nТы узнаешь кому ты даришь подарок 18.12." +
                "\n\nС Наступающим Новым Годом!!!:santa: :gift::deer::christmas_tree:");

    }

    private void handleFioInput(User userState, String text) {
        userState.setFullName(text);
        userState.setState("AWAITING_PRESENT");
        userRepository.save(userState);
        sendPhoto(userState.getChatId(), IMAGE_FIVE);
        prepareAndSendMessage(userState.getChatId(),
                "Твоему Тайному Санте придёт: " + "\n\n" + text + "\n\n" +
                        "А теперь пиши что ты хочешь на Получить от Санты " +
                        "\nМожешь прям ссылку отправить мне) " +
                        "\nПомни об ограничении бюджета (500р)\n");
    }

    private void register(long chatId) {

        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Do you really want to register?");

        //Кнопки в сообщении
        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();

        var yesButton = new InlineKeyboardButton();
        yesButton.setText("Yes");
        yesButton.setCallbackData(YES_BUTTON);

        var noButton = new InlineKeyboardButton();
        noButton.setText("No");
        noButton.setCallbackData(NO_BUTTON);

        rowInLine.add(yesButton);
        rowInLine.add(noButton);
        rowsInline.add(rowInLine);
        markupInLine.setKeyboard(rowsInline);
        message.setReplyMarkup(markupInLine);

        executeMessage(message);

    }

    private void registerUser(Message msg) {

        if (userRepository.findById(msg.getChatId()).isEmpty()) {

            var chatId = msg.getChatId();
            var chat = msg.getChat();

            User user = new User();
            user.setChatId(chatId);
            user.setFirstName(chat.getFirstName());
            user.setLastName(chat.getLastName());
            user.setUserName(chat.getUserName());
            user.setRegisteredAt(new Timestamp(System.currentTimeMillis()));

            userRepository.save(user);
            log.info("user saved:" + user);
        }
    }

    private void startCommandReceived(long chatId, String name) {

        String answer = EmojiParser.parseToUnicode(
                ":santa:" + " Привет, " + name + "! Добро пожаловать в нашу новогоднюю авантюру: \"Тайный Санта\"! " + ":gift:" + "\n" +
                        "Уже есть праздничное настроение? Давай его создавать! " + "\n" + "\n" +

                        ":sparkles:" + " Настало время тайных подарков, улыбок и немного магии. " +
                        "Попрощайся с грустью и добавь волшебства в наш рабочий коллектив. Ведь дарить подарки — это кайф! " + ":sparkles:" + "\n" + "\n" +

                        "В \"Тайном Санте\" каждый из нас станет чуть-чуть Дедом Морозом (или Бабой Ягой, если кто любит приключения " + ":stuck_out_tongue_winking_eye:" + ")." +
                        "Подарим друг другу улыбки, тепло и возможно, пару нелепых, но забавных сюрпризов." + ":christmas_tree:" + "\n" + "\n" +

                        "Давайте делать этот Новый год ярче вместе! " + ":star2:" + "\n" + "\n" +
                        "Готов стать частью этой авантюры, жми на кнопку Погнали!");

        log.info("Replied to user " + name);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendPhoto(chatId, IMAGE_ONE);
        sendMessage.setText(answer);
        sendMessage.setReplyMarkup(createYesNoButtonsInMessage("Погнали", YES_BUTTON, "Не, я пас", NO_BUTTON));
        executeMessage(sendMessage);
    }

    private void sendPhoto(long chatId, String image) {
        SendPhoto photo = new SendPhoto();
        photo.setChatId(String.valueOf(chatId));
        photo.setPhoto(new InputFile(image));

        try {
            execute(photo);
        } catch (TelegramApiException e) {
            log.error("Error occurred: " + e.getMessage());
        }
    }

    private void executeEditMessageText(String text, long chatId, long messageId) {
        EditMessageText message = new EditMessageText();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        message.setMessageId((int) messageId);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Error occurred: " + e.getMessage());
        }
    }

    private Message executeMessage(SendMessage message) {
        try {
            return execute(message);
        } catch (TelegramApiException e) {
            log.error("Error occurred: " + e.getMessage());
            return null;
        }

    }

    private void prepareAndSendMessage(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(EmojiParser.parseToUnicode(textToSend));
        executeMessage(message);
    }

    @Scheduled(cron = "${cron.scheduled}") //сек мин час дата месяц деньнедели
    private void sendAds() {
        Long adId = 2L;
        var users = userRepository.findAll();
        Optional<Ads> adOptional = adsRepository.findById(adId);
        Ads ad = adOptional.get();

        for (User user : users) {
            prepareAndSendMessage(user.getChatId(), ad.getAd());
        }
    }

//    @Scheduled(cron = "${cron.scheduledsolo}")
//    private void sendAdToSpecificUser() {
//        Long adId = 2L;// идентификатор объявления
//        Long userId = 957450146L;// идентификатор пользователя
//
//        Optional<Ads> adOptional = adsRepository.findById(adId);
//        Optional<User> userOptional = userRepository.findById(userId);
//
//        if (!adOptional.isPresent() || !userOptional.isPresent()) {
//            return; // или обработайте случай, когда объявление или пользователь не найден
//        }
//
//        Ads ad = adOptional.get();
//        User user = userOptional.get();
//
//        prepareAndSendMessage(user.getChatId(), ad.getAd());
//    }


    private InlineKeyboardMarkup createYesNoButtonsInMessage(String buttonOneName, String buttonOneCallback, String buttonTwoName, String buttonTwoCallback) {

        InlineKeyboardButton yesButton = new InlineKeyboardButton();
        yesButton.setText(buttonOneName);
        yesButton.setCallbackData(buttonOneCallback);
        InlineKeyboardButton noButton = new InlineKeyboardButton();
        noButton.setText(buttonTwoName);
        noButton.setCallbackData(buttonTwoCallback);

        List<InlineKeyboardButton> rowInLine = new ArrayList<>();
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        rowInLine.add(yesButton);
        rowInLine.add(noButton);

        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        rowsInline.add(rowInLine);
        markupInline.setKeyboard(rowsInline);

        return markupInline;

    }

//Кнопки в клавиатуре

    private ReplyKeyboardMarkup createButtonsInKeyboard(Long chatId) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();

        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        row.add("Все подарки в разработке, пожалуйста обратитесь попозже Хо-Хо-Хо");
        keyboardRows.add(row);

        row = new KeyboardRow();
        row.add("Все подарки в разработке, пожалуйста обратитесь попозже Хо-Хо-Хо");
        keyboardRows.add(row);

        keyboardMarkup.setKeyboard(keyboardRows);

        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Ваш текст сообщения");
        message.setReplyMarkup(createButtonsInKeyboard(chatId));
        executeMessage(message);
        message.setReplyMarkup(keyboardMarkup);
        return keyboardMarkup;
    }
}