package pl.luczak.michal.joboffersapp.validation.controller.api;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

class FieldErrorMessagesTest {

    @Test
    void testToString() {
        // GIVEN
        String expectedString = """
                {
                    "field":"testField",
                    "messages":[
                        "testValue"
                    ]
                }
                """.trim();
        ArrayList<String> testValue = new ArrayList<>();
        testValue.add("testValue");
        FieldErrorMessages fieldErrorMessages = new FieldErrorMessages("testField", testValue);

        // WHEN
        String messagesString = fieldErrorMessages.toString();

        // THEN
        assertThat(messagesString).isEqualToIgnoringWhitespace(expectedString);
    }
}