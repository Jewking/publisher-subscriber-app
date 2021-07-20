package com.tasks.test.subscriber.entities;

import com.tasks.test.subscriber.dto.ActionDTO;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Сущность принимаего Purchase
 */
@Entity
@Table(name = "PURCHASE")
public class Purchase extends Action
{
    public static Purchase buildInstance (ActionDTO action)
    {
        Purchase purchase = new Purchase();
        purchase.setId(action.getId());
        purchase.setMsisdn(action.getMsisdn());
        purchase.setTimestamp(action.getTimestamp());
        return purchase;
    }
}