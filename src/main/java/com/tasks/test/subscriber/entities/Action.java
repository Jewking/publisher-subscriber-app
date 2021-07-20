package com.tasks.test.subscriber.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;

/**
 * Суперкласс получаемого сообщения
 */
@Getter
@Setter
@MappedSuperclass
public class Action
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long msisdn;

    private Long timestamp;
}
