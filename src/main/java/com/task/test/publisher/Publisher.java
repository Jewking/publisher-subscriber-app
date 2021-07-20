package com.task.test.publisher;

import com.tasks.test.subscriber.enums.ActionType;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import java.security.SecureRandom;

/**
 * Класс для генерации и отправления сообщений
 */
public class Publisher
{
    private static final SecureRandom random = new SecureRandom();
    private static final String HOST = "http://localhost:9999/sub/add";

    public static void main(String[] args) throws Exception
    {
        for (int i = 5; i != 0; i--)
        {
            new Thread(() ->
            {
                while (true)
                {
                    try (CloseableHttpClient httpClient = HttpClientBuilder.create().build())
                    {
                        HttpPost httpPost = new HttpPost(HOST);
                        httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");
                        httpPost.setEntity(buildPostBody(generateMessage()));
                        httpClient.execute(httpPost);
                        Thread.sleep(15000);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }).start();
            Thread.sleep(3000);
        }
    }

    /**
     * Генерация сообщений
     * @return сообщение
     */
    private static Message generateMessage()
    {
        Message message = new Message();
        message.setMsisdn((long) (Math.random() * 500000000L));
        message.setTimestamp(System.currentTimeMillis());
        message.setType(randomEnum());
        return message;
    }

    /**
     * Построение тело запроса для отправки сообщения
     * @param message сообщение
     * @return тело запроса
     * @throws Exception исключение
     */
    private static StringEntity buildPostBody(Message message) throws Exception
    {
        JSONObject json = new JSONObject();
        json.put("msisdn", message.getMsisdn());
        json.put("timestamp", message.getTimestamp());
        json.put("type", message.getType());
        return new StringEntity(json.toString());
    }

    /**
     * Рандомайзер для перечисления типов сообщения
     * @return рандомное перечисление
     */
    private static ActionType randomEnum()
    {
        int x = random.nextInt((ActionType.class).getEnumConstants().length);
        return (ActionType.class).getEnumConstants()[x];
    }
}
