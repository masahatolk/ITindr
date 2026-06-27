package com.hits.impl

import com.hits.impl.data.validator.ChatMessageValidator
import com.hits.impl.data.validator.ValidationResult
import org.junit.Assert.*
import org.junit.Test

class ChatMessageValidatorTest {

    @Test
    fun `empty message returns error`() {
        val result = ChatMessageValidator.validate("   ")
        assertTrue(result is ValidationResult.Error)
    }

    @Test
    fun `valid message passes`() {
        val result = ChatMessageValidator.validate("message is valid")
        assertTrue(result is ValidationResult.Success)
    }

    @Test
    fun `message is trimmed`() {
        val result = ChatMessageValidator.validate("   message   ")
        assertEquals("message", (result as ValidationResult.Success).value)
    }

    @Test
    fun `message longer than 250 chars fails`() {
        val longText = "ab".repeat(126)
        val result = ChatMessageValidator.validate(longText)
        assertTrue(result is ValidationResult.Error)
    }

    @Test
    fun `html tags are forbidden`() {
        val result = ChatMessageValidator.validate("<b>hello</b>")
        assertTrue(result is ValidationResult.Error)
    }

    @Test
    fun `too many repeated chars fails`() {
        val result = ChatMessageValidator.validate("aaaaaaa aaaaaaaaaaaa")
        assertTrue(result is ValidationResult.Error)
    }

    @Test
    fun `too many new lines fails`() {
        val result = ChatMessageValidator.validate("a\n\n\n\nb")
        assertTrue(result is ValidationResult.Error)
    }

    @Test
    fun `edge case exactly 250 chars passes`() {
        val text = "ab".repeat(125)
        val result = ChatMessageValidator.validate(text)
        assertTrue(result is ValidationResult.Success)
    }
}