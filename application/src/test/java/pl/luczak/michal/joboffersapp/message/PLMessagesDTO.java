package pl.luczak.michal.joboffersapp.message;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.ResourceBundle;

@Getter
@Component
class PLMessagesDTO {

    private final String notNullMessage;
    private final String notBlankMessage;
    private final String invalidTypeMessage;
    private final String invalidFormatMessage;
    private final String wrongLinkPatternMessage;
    private final String wrongSize;

    public PLMessagesDTO() {
        this.notNullMessage = getMessageFromProperties("not.null");
        this.notBlankMessage = getMessageFromProperties("not.blank");
        this.invalidTypeMessage = getMessageFromProperties("invalid.type");
        this.invalidFormatMessage = getMessageFromProperties("invalid.format");
        this.wrongLinkPatternMessage = getMessageFromProperties("wrong.link.pattern");
        this.wrongSize = getMessageFromProperties("wrong.size");
    }

    private String getMessageFromProperties(String key) {
        ResourceBundle bundle = ResourceBundle.getBundle(
                "messages/messages", Locale.forLanguageTag("pl")
        );
        return bundle.getString(key);
    }
}

