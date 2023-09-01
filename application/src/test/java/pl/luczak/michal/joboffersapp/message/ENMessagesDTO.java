package pl.luczak.michal.joboffersapp.message;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.ResourceBundle;

@Getter
@Component
class ENMessagesDTO {

    private final String notNullMessage;
    private final String notBlankMessage;
    private final String invalidTypeMessage;
    private final String invalidFormatMessage;
    private final String wrongLinkPatternMessage;

    public ENMessagesDTO() {
        this.notNullMessage = getMessageFromProperties("not.null");
        this.notBlankMessage = getMessageFromProperties("not.blank");
        this.invalidTypeMessage = getMessageFromProperties("invalid.type");
        this.invalidFormatMessage = getMessageFromProperties("invalid.format");
        this.wrongLinkPatternMessage = getMessageFromProperties("wrong.link.pattern");
    }

    private String getMessageFromProperties(String key) {
        ResourceBundle bundle = ResourceBundle.getBundle(
                "messages/messages", Locale.forLanguageTag("en")
        );
        return bundle.getString(key);
    }
}
