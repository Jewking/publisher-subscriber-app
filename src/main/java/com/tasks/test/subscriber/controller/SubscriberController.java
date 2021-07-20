package com.tasks.test.subscriber.controller;

import com.tasks.test.subscriber.dto.ActionDTO;
import com.tasks.test.subscriber.service.SubscriberServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


/**
 * Рест-контроллер подписчика сообщений
 */
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/sub", produces = APPLICATION_JSON_VALUE)
public class SubscriberController
{
    private static final Logger LOG = Logger.getLogger(SubscriberController.class);
    private final SubscriberServiceImpl service;

    @PostMapping("/add")
    public ResponseEntity receive (@Valid @RequestBody ActionDTO action)
    {
        return service.receive(action);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleAllExceptions (Exception ex)
    {
        LOG.error(ex.getMessage(), ex);
        return new ResponseEntity<>(ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleMethodArgumentNotValidException (MethodArgumentNotValidException ex)
    {
        LOG.error("Ошибка валидации перевадаемого сообщения!", ex);
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
