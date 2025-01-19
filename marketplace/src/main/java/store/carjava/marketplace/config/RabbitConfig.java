package store.carjava.marketplace.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    /**
     * RabbitMQ에서 사용할 큐를 생성합니다.
     * - 이름: "chat.queue"
     * - durable = true: 서버가 재시작되더라도 큐가 유지됩니다.
     */
    @Bean
    public Queue chatQueue() {
        return new Queue("chat.queue", true); // durable = true
    }

    /**
     * 메시지를 JSON 형식으로 직렬화/역직렬화하는 컨버터를 설정합니다.
     * - Spring AMQP는 기본적으로 SimpleMessageConverter를 사용하는데, 이는 String, byte[], Serializable 타입만 지원합니다.
     * - Jackson2JsonMessageConverter는 객체를 JSON으로 변환하여 저장/전송합니다.
     */
    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * RabbitTemplate을 생성합니다.
     * - RabbitTemplate은 RabbitMQ와의 메시지 송수신을 담당하는 핵심 객체입니다.
     * - ConnectionFactory를 통해 RabbitMQ 연결을 관리합니다.
     * - Jackson2JsonMessageConverter를 설정하여 JSON 메시지 처리를 가능하게 합니다.
     *
     * @param connectionFactory RabbitMQ와 연결을 생성하고 관리하는 객체.
     * @return RabbitTemplate 객체.
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        // JSON 컨버터를 RabbitTemplate에 설정합니다.
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
