package com.tasks.test.subscriber.service;

import com.tasks.test.subscriber.dto.ActionDTO;
import com.tasks.test.subscriber.entities.Action;
import com.tasks.test.subscriber.entities.Purchase;
import com.tasks.test.subscriber.entities.Subscription;
import com.tasks.test.subscriber.repository.SubscriberRepository;
import com.tasks.test.subscriber.enums.ActionType;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Реализация сервиса подписчика сообщений
 */
@RequiredArgsConstructor
@Service
public class SubscriberServiceImpl implements SubscriberService<ActionDTO>
{
    private static final Logger LOG = Logger.getLogger(SubscriberService.class);
    private final SubscriberRepository repository;

    @Override
    public ResponseEntity receive(ActionDTO action)
    {
        LOG.debug("Принят запрос " + action.toString());
        repository.save(convert(action));
        return new ResponseEntity(HttpStatus.CREATED);
    }

    private static Action convert(ActionDTO action)
    {
        if (action.getType() == ActionType.SUBSCRIPTION)
        {
            return Subscription.buildInstance(action);
        }
        else if (action.getType() == ActionType.PURCHASE)
        {
            return Purchase.buildInstance(action);
        }
        return null;
    }
}