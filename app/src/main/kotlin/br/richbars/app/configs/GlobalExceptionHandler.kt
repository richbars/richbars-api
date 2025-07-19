package br.richbars.app.configs

import com.fasterxml.jackson.databind.exc.MismatchedInputException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleJsonParseError(ex: HttpMessageNotReadableException): ResponseEntity<Map<String, Any>> {
        val cause = ex.cause
        val message = if (cause is MismatchedInputException && cause.path.isNotEmpty()) {
            val fieldName = cause.path.joinToString(".") { it.fieldName }
            "Missing or invalid field: $fieldName"
        } else {
            "Malformed JSON in request"
        }

        val body = mapOf(
            "statusCode" to 400,
            "message" to message
        )

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body)
    }
}
