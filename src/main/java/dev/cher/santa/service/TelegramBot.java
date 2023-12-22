package dev.cher.santa.service;

import com.vdurmont.emoji.EmojiParser;
import dev.cher.santa.config.BotConfig;
import dev.cher.santa.model.User;
import dev.cher.santa.model.UserRepository;
import dev.cher.santa.util.BotCallBack;
import dev.cher.santa.util.BotImage;
import dev.cher.santa.util.KeyboardUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static dev.cher.santa.util.MessageUtils.*;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final UserService userService;
    private final UserRepository userRepository;
    private final BotConfig config;
    static final String HELP_TEXT = "Этот бот создан исключительно ради  забавы. \n\n" +
            "В меню присутствует следующий список команд: \n\n" +
            "Type /start начать всё с начала \n\n" +
            "Type /present нажимая на эту команду ты сможешь написать подарок который ты хочешь, да, если что, то можно поменять, функция доступна до 18.12\n\n" +
            "Type /help Вызов меню с подсказками \n\n" +
            "Type /developer если что-то сломалось можно написать разработчику он починит и поможет\n\n";
    @Autowired
    public TelegramBot(UserService userService, BotConfig config, UserRepository userRepository) {
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

            if (userState != null && "VOTING_FOR_CORPORATE_YES".equals(userState.getState())) {
                votingForCorporateYes(userState);
                return;
            }

            if (userState != null && "VOTING_FOR_CORPORATE_NO".equals(userState.getState())) {
                votingForCorporateNo(userState);
                return;
            }

            if (messageText.contains("/send") && config.getOwnerId() == chatId) {
                var textToSend = EmojiParser.parseToUnicode(messageText.substring(messageText.indexOf(" ")));
                var users = userRepository.findAll();

                for (User user : users) {
                    prepareAndSendMessage(this, user.getChatId(), textToSend);
                }
            } else {

                switch (messageText) {
                    case "/start":
                        registerUser(update.getMessage());
                        startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                        break;
                    case "/help":
                        sendPhoto(this, chatId, BotImage.IMAGE_HELP.getImageUrl());
                        prepareAndSendMessage(this, chatId, HELP_TEXT);
                        break;
                    case "/send":
                        break;
                    case "/register":
                        register(chatId);
                        break;
                    case "/present":
//                        prepareAndSendMessage(chatId, "Регистрация подарков закрыта, Хо-Хо-Хо");

                        userState = userRepository.findById(chatId).orElse(new User());
                        userState.setState("AWAITING_FIO");
                        userRepository.save(userState);
                        sendPhoto(this, chatId, BotImage.IMAGE_FOUR.getImageUrl());

                        prepareAndSendMessage(this, chatId, "Отлично, пиши Фамилию и Имя, прямо сообщением, не стесняйся)\n\nПомни чтобы Санта смог тебя найти ему нужны корректные ФИО, а иначе есть шанс остаться без подарка)");
                        break;
                    case "/developer":
                        sendPhoto(this, chatId, BotImage.IMAGE_DEVELOPER.getImageUrl());
                        prepareAndSendMessage(this, chatId, "пока в личку @aleksandr_cherkasov, если время хватит допилю нормально)");
                        break;
                    default:
                        prepareAndSendMessage(this, chatId, "Я всего лишь бот для Тайного Санты, если есть вопросы/пожелания нажмите /developer и мой хозяин вас услышит");
                }
            }
        }

        if (update.hasCallbackQuery()) {

            long chaId = update.getCallbackQuery().getMessage().getChatId();

            switch (BotCallBack.fromString(update.getCallbackQuery().getData())) {
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
                    sendPhoto(this, chaId, BotImage.IMAGE_TWO.getImageUrl());
                    sendMessage.setText(text);
                    sendMessage.setReplyMarkup(KeyboardUtils.createYesNoButtonsInMessage("Точно да!", "Точно да!", "Точно нет", BotCallBack.NO_BUTTON.getCallback()));
                    executeMessage(this, sendMessage);
                    break;

                case NO_BUTTON:
                    String textNoButton = EmojiParser.parseToUnicode("Ну что же ты дружок подводишь Санту:santa:!\n" +
                            "Если не хочешь, чтобы тебя превратили в оленя :deer:, подумай ещё раз… хо-хо-хо\n");
                    SendMessage sendMessageNoButton = new SendMessage();
                    sendMessageNoButton.setChatId(String.valueOf(chaId));
                    sendPhoto(this, chaId, BotImage.IMAGE_SEVEN.getImageUrl());
                    sendMessageNoButton.setText(textNoButton);
                    sendMessageNoButton.setReplyMarkup(KeyboardUtils.createYesNoButtonsInMessage(
                            "Не хочу быть оленем(", BotCallBack.NO_BUTTON_NE_HOCHU.getCallback(),
                            "До свидания", BotCallBack.NO_BUTTON_BUY.getCallback()));
                    executeMessage(this, sendMessageNoButton);
                    break;

                case VOTING_FOR_CORPORATE_YES:
                    Optional<User> userStateOpt = userRepository.findById(chaId);
                    if (userStateOpt.isPresent()) {
                        User userState = userStateOpt.get();
                        userState.setState("Yes");
                        userRepository.save(userState);
                    }

                    sendPhoto(this, chaId, BotImage.IMAGE_SANTA_END.getImageUrl());
                    prepareAndSendMessage(this, chaId, "Спасибо, за участие в Secret Santa авантюре!" +
                            "\nБлагодаря тебе и всем участникам, декабрь стал ярче и интереснее." +
                            "\n\nВот уже на пороге Новый Год, время чудес и новых начинаний." +
                            "\nПусть он принесёт много радостных моментов, успехов и исполнения самых заветных желаний." +
                            "\n\nОт всей души желаем тебе счастливого Нового года!" +
                            "\nПусть он будет полон счастья, здоровья, любви и новых свершений!");
                    log.info("голосование ЗА " + update.getCallbackQuery().getFrom().getFirstName());
                    System.out.println(update.getCallbackQuery().getFrom().getFirstName() + " проголосовал ЗА");
                    break;

                case VOTING_FOR_CORPORATE_NO:
                    Optional<User> userStateOptNO = userRepository.findById(chaId);
                    if (userStateOptNO.isPresent()) {
                        User userState = userStateOptNO.get();
                        userState.setState("NO");
                        userRepository.save(userState);
                    }

                    sendPhoto(this, chaId, BotImage.IMAGE_SANTA_END.getImageUrl());
                    prepareAndSendMessage(this, chaId, "Спасибо, за участие в Secret Santa авантюре!" +
                            "\nБлагодаря тебе и всем участникам, декабрь стал ярче и интереснее." +
                            "\n\nВот уже на пороге Новый Год, время чудес и новых начинаний." +
                            "\nПусть он принесёт много радостных моментов, успехов и исполнения самых заветных желаний." +
                            "\n\nОт всей души желаем тебе счастливого Нового года!" +
                            "\nПусть он будет полон счастья, здоровья, любви и новых свершений!");
                    log.info("голосование ЗА " + update.getCallbackQuery().getFrom().getFirstName());
                    System.out.println(update.getCallbackQuery().getFrom().getFirstName() + " проголосовал ПРОТИВ");
                    break;

                case NO_BUTTON_NE_HOCHU:
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
                    sendPhoto(this, chaId, BotImage.IMAGE_EIGHT.getImageUrl());
                    send1.setText(text1);
                    send1.setReplyMarkup(KeyboardUtils.createYesNoButtonsInMessage(
                            "Ура! Я не олень!", BotCallBack.NO_BUTTON_NE_HOCHU_YES.getCallback(),
                            "До свидания", BotCallBack.NO_BUTTON_BUY.getCallback()));
                    executeMessage(this, send1);
                    break;

                case NO_BUTTON_BUY:
                    String reset = EmojiParser.parseToUnicode("Если надумаешь вернуться, в MENU есть кнопка ZANOVO\nС Наступающим Новым Годом! :deer: ");
                    SendMessage sendReset = new SendMessage();
                    sendReset.setChatId(String.valueOf(chaId));
                    sendPhoto(this, chaId, BotImage.IMAGE_END.getImageUrl());
                    sendReset.setText(reset);
                    executeMessage(this, sendReset);
                    userService.deleteUser(chaId);
                    break;

                case NO_BUTTON_NE_HOCHU_YES:
                    String textYes = EmojiParser.parseToUnicode("Начинаем готовиться к магическим деяниям! \n" +
                            "Чтобы получить подарок нужно подарить подарок, всё просто! :santa: :gift:\n " +
                            "Чтобы твой Санта не тыкал пальцем в небо, ты можешь написать что ты хочешь, а можешь ничего не писать, пусть гадает.\n\n" +
                            "В левом нижнем углу ты видишь голубую кнопку MENU.\n" +
                            "Нажми на неё и выбери пункт HOCHU PODAROK!");
                    SendMessage messageYes = new SendMessage();
                    messageYes.setChatId(String.valueOf(chaId));
                    sendPhoto(this, chaId, BotImage.IMAGE_THREE.getImageUrl());
                    messageYes.setText(textYes);
                    executeMessage(this, messageYes);
                    break;
                case BUTTON_TUROVSKY_YES:
                    String textDA = EmojiParser.parseToUnicode("Пиши в личку @aleksandr_cherkasov что ты хочешь на НГ, что-нибудь придумаю... ");
                    SendMessage messageDA = new SendMessage();
                    messageDA.setChatId(String.valueOf(chaId));
                    sendPhoto(this, chaId, BotImage.IMAGE_FACEPALM.getImageUrl());
                    messageDA.setText(textDA);
                    executeMessage(this, messageDA);
                    break;
                case BUTTON_TUROVSKY_NO:
                    String textNET = EmojiParser.parseToUnicode("На нет и суда нет");
                    SendMessage messageNET = new SendMessage();
                    messageNET.setChatId(String.valueOf(chaId));
                    sendPhoto(this, chaId, BotImage.IMAGE_FACEPALM_NO.getImageUrl());
                    messageNET.setText(textNET);
                    executeMessage(this, messageNET);
                    break;

            }
        }
    }

    private void handlePresentInput(User userState, String present) {
        userState.setPresent(present);
        userState.setState(null);
        userRepository.save(userState);
        sendPhoto(this, userState.getChatId(), BotImage.IMAGE_SIX.getImageUrl());
        prepareAndSendMessage(this, userState.getChatId(), "Желаемый подарок сохранен: " + present +
                "\n\nЕсли всё перепуталось, то можешь начать заново нажав /present. " +
                "\n\nЕсли же всё хорошо, то спасибо за участие) " +
                "\nТы узнаешь кому ты даришь подарок 18.12." +
                "\n\nС Наступающим Новым Годом!!!:santa: :gift::deer::christmas_tree:");

    }

    private void handleFioInput(User userState, String text) {
        userState.setFullName(text);
        userState.setState("AWAITING_PRESENT");
        userRepository.save(userState);
        sendPhoto(this, userState.getChatId(), BotImage.IMAGE_FIVE.getImageUrl());
        prepareAndSendMessage(this, userState.getChatId(),
                "Твоему Тайному Санте придёт: " + "\n\n" + text + "\n\n" +
                        "А теперь пиши что ты хочешь на Получить от Санты " +
                        "\nМожешь прям ссылку отправить мне) " +
                        "\nПомни об ограничении бюджета (500р)\n");
    }

    private void votingForCorporateYes(User userState) {
        userState.setState("YES");
        userRepository.save(userState);
        sendPhoto(this, userState.getChatId(), BotImage.IMAGE_SANTA_END.getImageUrl());
        prepareAndSendMessage(this, userState.getChatId(), "Спасибо, за участие в Secret Santa авантюре!" +
                "\nБлагодаря тебе и всем участникам, декабрь стал ярче и интереснее." +
                "\n\nВот уже на пороге Новый Год, время чудес и новых начинаний." +
                "\nПусть он принесёт много радостных моментов, успехов и исполнения самых заветных желаний." +
                "\n\nОт всей души желаем тебе счастливого Нового года!" +
                "\nПусть он будет полон счастья, здоровья, любви и новых свершений!");
        log.info("голосование ЗА " + userState.getFullName());
        System.out.println(userState.getFullName() + " проголосовал ЗА");


    }

    private void votingForCorporateNo(User userState) {
        userState.setState("NO");
        userRepository.save(userState);
        sendPhoto(this, userState.getChatId(), BotImage.IMAGE_SANTA_END.getImageUrl());
        prepareAndSendMessage(this, userState.getChatId(), "Спасибо, за участие в Secret Santa авантюре!" +
                "\nБлагодаря тебе и всем участникам, декабрь стал ярче и интереснее." +
                "\n\nВот уже на пороге Новый Год, время чудес и новых начинаний." +
                "\nПусть он принесёт много радостных моментов, успехов и исполнения самых заветных желаний." +
                "\nОт всей души желаем тебе счастливого Нового года!" +
                "\nПусть он будет полон счастья, здоровья, любви и новых свершений!");
        log.info("голосование ПРОТИВ " + userState.getFullName());
        System.out.println(userState.getFullName() + " проголосовал против");


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
        yesButton.setCallbackData(BotCallBack.YES_BUTTON.getCallback());

        var noButton = new InlineKeyboardButton();
        noButton.setText("No");
        noButton.setCallbackData(BotCallBack.NO_BUTTON.getCallback());

        rowInLine.add(yesButton);
        rowInLine.add(noButton);
        rowsInline.add(rowInLine);
        markupInLine.setKeyboard(rowsInline);
        message.setReplyMarkup(markupInLine);

        executeMessage(this, message);

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
                        "Подарим друг другу улыбки, тепло и возможно, пару нелепых, но забавных сюрпризов." + ":chr istmas_tree:" + "\n" + "\n" +

                        "Давайте делать этот Новый год ярче вместе! " + ":star2:" + "\n" + "\n" +
                        "Готов стать частью этой авантюры, жми на кнопку Погнали!");

        log.info("Replied to user " + name);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendPhoto(this, chatId, BotImage.IMAGE_ONE.getImageUrl());
        sendMessage.setText(answer);
        sendMessage.setReplyMarkup(KeyboardUtils.createYesNoButtonsInMessage("Погнали", BotCallBack.YES_BUTTON.getCallback(), "Не, я пас", BotCallBack.NO_BUTTON.getCallback()));
        executeMessage(this, sendMessage);
    }









}