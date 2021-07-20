package com.tasks.test.subscriber.dto;

import com.tasks.test.subscriber.enums.ActionType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;


/**
 * ДТО получаемого сообщения
 */
@Setter
@Getter
public class ActionDTO
{
    @Min(1)
    private Long id;

    @Min(1)
    private Long msisdn;

    @Min(1)
    private Long timestamp;

    private ActionType type;

    @Override
    public String toString() {
        return "[id: " + id + ", msisdn: " + msisdn + ", timestamp: " + timestamp + ", type: " + type.toString() + "]";
    }
}
