package com.task.test.publisher;

import com.tasks.test.subscriber.enums.ActionType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message
{
    private Long id;
    private Long msisdn;
    private Long timestamp;
    private ActionType type;
}
