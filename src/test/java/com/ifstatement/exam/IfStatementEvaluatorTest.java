package com.ifstatement.exam;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IfStatementEvaluatorTest {

    @Test
    void evaluateIfElseJson_WithTrueConditions_ReturnsTrue() throws JsonProcessingException {
        // Given
        String json = "{\"type\":\"if\",\"conditions\":[{\"conditionType\":\"==\",\"left\":\"a\",\"right\":\"abc\"},{\"conditionType\":\">\",\"left\":\"b\",\"right\":\"4\"}],\"elseStatement\":null,\"thenResult\":\"true\"}";

        // When
        boolean result = new IfStatementEvaluator().evaluateIfElseJson("abc", 5, json);

        // Then
        assertTrue(result);
    }

    @Test
    void evaluateIfElseJson_WithFalseConditions_ReturnsFalse() throws JsonProcessingException {
        // Given
        final String json = "{\"type\":\"if\",\"conditions\":[{\"conditionType\":\"==\",\"left\":\"a\",\"right\":\"abc\"},{\"conditionType\":\">\",\"left\":\"b\",\"right\":\"4\"}],\"elseStatement\":{\"type\":\"if\",\"conditions\":[{\"conditionType\":\"<\",\"left\":\"b\",\"right\":\"10\"}],\"elseStatement\":{\"type\":null,\"conditions\":null,\"elseStatement\":null,\"thenResult\":\"false\"},\"thenResult\":\"false\"},\"thenResult\":\"true\"}";

        // When
        boolean result = new IfStatementEvaluator().evaluateIfElseJson("abca", 15, json);

        // Then
        assertFalse(result);
    }

    @Test
    void evaluateIfElseJson_WithInvalidJson_ThrowsJsonProcessingException() {
        // Given
        String invalidJson = "invalidJson";

        // When & Then
        assertThrows(JsonProcessingException.class, () ->
                new IfStatementEvaluator().evaluateIfElseJson("abc", 5, invalidJson));
    }

    // Add more test cases as needed for different scenarios

    // For example, you may want to test cases where the input JSON represents nested if-else statements, etc.
}
