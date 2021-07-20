package com.tasks.test.subscriber;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.tasks.test.subscriber.controller.SubscriberController;
import com.tasks.test.subscriber.dto.ActionDTO;
import com.tasks.test.subscriber.enums.ActionType;
import com.tasks.test.subscriber.service.SubscriberServiceImpl;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SubscriberControllerTest
{
    private static final Long MSISDN = 12435L;
    private static final Long TIMESTAMP = 15015015015L;
    private static final ActionType TYPE = ActionType.SUBSCRIPTION;

    private static final Long INVALID_MSISDN = 12435L;
    private static final Long INVALID_TIMESTAMP = 15015015015L;
    private static final ActionType INVALID_TYPE = ActionType.SUBSCRIPTION;

    private static final ActionDTO VALID_ACTION = buildActionDto(MSISDN, TIMESTAMP, TYPE);
    private static final ActionDTO INVALID_TYPE_ACTION = buildActionDto(MSISDN, TIMESTAMP, INVALID_TYPE);
    private static final ActionDTO INVALID_VALUES_ACTION = buildActionDto(INVALID_MSISDN, INVALID_TIMESTAMP, TYPE);

    private static final String RECEIVE_URI = "/sub/add";
    private MockHttpServletRequestBuilder receiveRequestBuilder;

    private SubscriberServiceImpl service;
    private SubscriberController controller;
    private MockMvc mockMvc;

    @Before
    public void before()
    {
        service = mock(SubscriberServiceImpl.class);
        controller = spy(new SubscriberController(service));
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void receiveSuccess() throws Exception
    {
        prepareReceiveRequest(VALID_ACTION);

        given(service.receive(any())).willReturn(new ResponseEntity(HttpStatus.CREATED));

        mockMvc.perform(receiveRequestBuilder).andExpect(status().isCreated());

        verify(controller, times(1)).receive(any());
    }


    @Test
    public void receiveInvalidTypeBadRequest() throws Exception
    {
        prepareReceiveRequest(INVALID_TYPE_ACTION);

        given(service.receive(any())).willAnswer(i -> { throw mock(InvalidFormatException.class); });

        mockMvc.perform(receiveRequestBuilder).andExpect(status().isBadRequest());

        verify(controller, times(1)).receive(any());
    }

    @Test
    public void receiveInvalidValuesBadRequest() throws Exception
    {
        prepareReceiveRequest(INVALID_VALUES_ACTION);

        given(service.receive(any())).willAnswer(i -> { throw mock(MethodArgumentNotValidException.class); });

        mockMvc.perform(receiveRequestBuilder).andExpect(status().isBadRequest());

        verify(controller, times(1)).receive(any());
    }


    private void prepareReceiveRequest(ActionDTO action) throws JSONException
    {
        receiveRequestBuilder = MockMvcRequestBuilders.post(RECEIVE_URI).contentType(APPLICATION_JSON_VALUE);

        JSONObject json = new JSONObject();
        json.put("id", 1L);
        json.put("msisdn", action.getMsisdn());
        json.put("timestamp", action.getTimestamp());
        json.put("type", action.getType());

        receiveRequestBuilder.content(json.toString());
    }

    private static ActionDTO buildActionDto(Long msisdn, Long timestamp, ActionType actionType)
    {
        ActionDTO actionDTO = new ActionDTO();
        actionDTO.setMsisdn(msisdn);
        actionDTO.setTimestamp(timestamp);
        actionDTO.setType(actionType);
        return actionDTO;
    }
}
