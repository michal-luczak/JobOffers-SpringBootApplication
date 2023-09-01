package pl.luczak.michal.joboffersapp.message;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ContextConfiguration;

import java.util.Locale;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@ContextConfiguration(classes = {
        MessageConfig.class,
        ENMessagesDTO.class,
        PLMessagesDTO.class
})
class MessageSourceTest {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private PLMessagesDTO plMessagesDTO;

    @Autowired
    private ENMessagesDTO enMessagesDTO;

    @ParameterizedTest
    @MethodSource("getMessages")
    void should_return_message_in_specific_language(
            Locale local,
            String notNullErrorMessage,
            String notBlankErrorMessage,
            String invalidTypeErrorMessage,
            String invalidFormatErrorMessage,
            String wrongLinkPatternMessage
    ) {
        // GIVEN && WHEN
        String notNullResolvedMessage = messageSource.getMessage(
                "not.null", null, local
        );
        String notBlankResolvedMessage = messageSource.getMessage(
                "not.blank", null, local
        );
        String invalidTypeResolvedMessage = messageSource.getMessage(
                "invalid.type", new Object[] {"abc", "type"}, local
        );
        String invalidFormatResolvedMessage = messageSource.getMessage(
                "invalid.format", null, local
        );
        String wrongLinkPatternResolvedMessage = messageSource.getMessage(
                "wrong.link.pattern", null, local
        );

        // THEN
        assertThat(notNullResolvedMessage)
                .isEqualTo(notNullErrorMessage);
        assertThat(notBlankResolvedMessage)
                .isEqualTo(notBlankErrorMessage);
        assertThat(invalidTypeResolvedMessage)
                .isEqualTo(invalidTypeErrorMessage
                        .replace("{0}", "abc")
                        .replace("{1}", "type")
                );
        assertThat(invalidFormatResolvedMessage)
                .isEqualTo(invalidFormatErrorMessage);
        assertThat(wrongLinkPatternResolvedMessage)
                .isEqualTo(wrongLinkPatternMessage);
    }

    private Stream<Arguments> getMessages() {
        return Stream.of(
                Arguments.of(
                        Locale.forLanguageTag("pl"),
                        plMessagesDTO.getNotNullMessage(),
                        plMessagesDTO.getNotBlankMessage(),
                        plMessagesDTO.getInvalidTypeMessage(),
                        plMessagesDTO.getInvalidFormatMessage(),
                        plMessagesDTO.getWrongLinkPatternMessage()
                ),
                Arguments.of(
                        Locale.ENGLISH,
                        enMessagesDTO.getNotNullMessage(),
                        enMessagesDTO.getNotBlankMessage(),
                        enMessagesDTO.getInvalidTypeMessage(),
                        enMessagesDTO.getInvalidFormatMessage(),
                        enMessagesDTO.getWrongLinkPatternMessage()
                )
        );
    }
}

