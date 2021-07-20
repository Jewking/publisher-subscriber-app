package com.tasks.test.subscriber;

import com.tasks.test.subscriber.dto.ActionDTO;
import com.tasks.test.subscriber.entities.Action;
import com.tasks.test.subscriber.entities.Purchase;
import com.tasks.test.subscriber.entities.Subscription;
import com.tasks.test.subscriber.enums.ActionType;
import com.tasks.test.subscriber.repository.SubscriberRepository;
import com.tasks.test.subscriber.service.SubscriberServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class SubscriberServiceTest
{
    private static final Long MSISDN = 12435L;
    private static final Long TIMESTAMP = 15015015015L;
    private static final ActionType TYPE = ActionType.SUBSCRIPTION;
    private static final ActionDTO VALID_ACTION = buildActionDto();

    private SubscriberServiceImpl service;
    private SubscriberRepository repository;

    @Before
    public void before()
    {
        repository = mock(SubscriberRepository.class);
        service = new SubscriberServiceImpl(repository);
    }

    @Test
    public void receiveSuccess ()
    {
        when(repository.save(convert())).thenReturn(convert());

        ResponseEntity response = service.receive(VALID_ACTION);

        verify(repository, times(1)).save(any());

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    private static ActionDTO buildActionDto()
    {
        ActionDTO actionDTO = new ActionDTO();
        actionDTO.setMsisdn(MSISDN);
        actionDTO.setTimestamp(TIMESTAMP);
        actionDTO.setType(TYPE);
        return actionDTO;
    }

    private static Action convert()
    {
        if (VALID_ACTION.getType() == ActionType.SUBSCRIPTION)
        {
            return Subscription.buildInstance(VALID_ACTION);
        }
        else if (VALID_ACTION.getType() == ActionType.PURCHASE)
        {
            return Purchase.buildInstance(VALID_ACTION);
        }
        return null;
    }
}