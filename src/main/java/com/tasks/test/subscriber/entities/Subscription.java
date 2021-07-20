package com.tasks.test.subscriber.entities;

import com.tasks.test.subscriber.dto.ActionDTO;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Сущность принимаего Subscription
 */
@Entity
@Table(name = "SUBSCRIPTION")
public class Subscription extends Action
{
    public static Subscription buildInstance (ActionDTO action)
    {
        Subscription subscription = new Subscription();
        subscription.setId(action.getId());
        subscription.setMsisdn(action.getMsisdn());
        subscription.setTimestamp(action.getTimestamp());
        return subscription;
    }
}
