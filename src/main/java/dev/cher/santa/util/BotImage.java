package dev.cher.santa.util;

public enum BotImage {
    IMAGE("https://i.ibb.co/FKFBH6B/razdel-v-razrabotke-1.jpg"),
    IMAGE_ONE("https://i.ibb.co/mR3kZkG/one.png"),
    IMAGE_TWO("https://i.ibb.co/zRKVsH8/two.png"),
    IMAGE_THREE("https://i.ibb.co/vhGZ3jW/three.png"),
    IMAGE_FOUR("https://i.ibb.co/hW3g7cQ/four.png"),
    IMAGE_FIVE("https://i.ibb.co/yy8ym15/five.png"),
    IMAGE_SIX("https://i.ibb.co/1d5vK2J/six.png"),
    IMAGE_SEVEN("https://i.ibb.co/G9frPPy/seven.png"),
    IMAGE_EIGHT("https://i.ibb.co/6J4fwdn/eight.png"),
    IMAGE_HELP("https://i.ibb.co/P6NjbfT/help.png"),
    IMAGE_DEVELOPER("https://i.ibb.co/w4DnpHr/developer.png"),
    IMAGE_LETTER_ONE("https://i.ibb.co/WPZVpK1/DALL-E-2023-12-15-15-19-06-A-wide-format-digital-banner-design-for-a-fitness-club-themed-Secret-Sant.png"),
    IMAGE_LETTER_WARNING("https://i.ibb.co/qCW5xqw/DALL-E-2023-12-15-16-17-18-A-vibrant-and-energetic-wide-format-digital-banner-for-a-fitness-club-s-S.png"),
    IMAGE_LETTER_CONGRATULATION("https://i.ibb.co/mGGfnyg/DALL-E-2023-12-15-17-01-57-A-vibrant-and-engaging-wide-format-digital-banner-for-the-closure-of-a-fi.png"),
    IMAGE_VOTING_CORPORATE("https://i.ibb.co/X8Cj2LQ/DALL-E-2023-12-15-17-49-46-A-lively-and-colorful-digital-banner-for-a-Secret-Santa-event-voting-The.png"),
    IMAGE_DISTRIBUTION_ON("https://i.ibb.co/C5Y4SLt/DALL-E-2023-12-18-17-29-18-A-wide-holiday-greeting-card-featuring-a-fit-and-athletic-Santa-Claus-in.png"),
    IMAGE_DISTRIBUTION_OFF("https://i.ibb.co/P108MG0/DALL-E-2023-12-18-17-33-35-A-wide-holiday-card-featuring-a-fit-and-athletic-Santa-Claus-in-a-gym-set.png"),
    IMAGE_DISTRIBUTION_TUROVSKY("https://i.ibb.co/R3Rzsf5/DALL-E-2023-12-19-14-58-03-A-comical-and-whimsical-illustration-showing-a-cartoon-person-frantically.png"),
    IMAGE_FACEPALM("https://i.ibb.co/Fwr9LBg/DALL-E-2023-12-19-15-07-27-A-humorous-meme-style-image-of-Santa-Claus-doing-a-facepalm-Santa-depicte.png"),
    IMAGE_SANTA_END("https://i.ibb.co/gS7dVM7/DALL-E-2023-12-15-18-04-59-A-vibrant-and-celebratory-digital-banner-designed-to-express-gratitude-fo.png"),
    IMAGE_FACEPALM_NO("https://i.ibb.co/4ZXB7z5/DALL-E-2023-12-19-15-11-08-A-humorous-and-exaggerated-cartoon-of-Santa-Claus-with-a-wide-eyed-surpri.png"),
    IMAGE_END("https://i.ibb.co/H4kscxP/end.png");

    private final String url;

    BotImage(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return url;
    }
}
