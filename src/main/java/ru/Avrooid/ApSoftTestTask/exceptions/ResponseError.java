package ru.Avrooid.ApSoftTestTask.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

/**
 * Возвращаемое значение при обнаружении ошибки
 */
public class ResponseError {

    /**
     * Текст сообщения
     */
    private final String message;

    /**
     * Название ошибки
     */
    private final Code code;

    /**
     * Отфарматированное время обнаружения ошибки
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime time = LocalDateTime.now();

    public ResponseError(String message, Code code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public Code getCode() {
        return code;
    }
}
