package com.tasks.test.subscriber.service;

import org.springframework.http.ResponseEntity;


/**
 * Сервис подписчика сообщений
 */
public interface SubscriberService<T>
{
    ResponseEntity receive(T entity) throws Exception;
}
