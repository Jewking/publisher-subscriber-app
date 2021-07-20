package com.tasks.test.subscriber.repository;

import com.tasks.test.subscriber.entities.Action;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий подписчика сообщений
 */
@Repository
public interface SubscriberRepository extends CrudRepository<Action, Long>
{
}