package ro.qwq.rabbitmq.testClass;

import com.rabbitmq.client.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ro.qwq.rabbitmq.constant.HelloRabbitMQConstants;
import ro.qwq.rabbitmq.domain.ConnectionInfo;
import ro.qwq.rabbitmq.utils.RabbitMQUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName: Consumer
 * @Description: 消费者测试模型
 * @Author: JiuyeXD
 * @Date: 2021/7/9 11:36
 * @Version: 1.0
 */
@SpringBootTest
public class Consumer {
    @Test
    void testGetMessage() throws IOException, InterruptedException, TimeoutException {
        // 获取连接对象
        Connection connection = RabbitMQUtils.getConnection(new ConnectionInfo
                (HelloRabbitMQConstants.HOST,
                        HelloRabbitMQConstants.PORT,
                        HelloRabbitMQConstants.V_HOST_NAME,
                        HelloRabbitMQConstants.USER_NAME,
                        HelloRabbitMQConstants.PASSWORD
                ));
        // 获取通道
        Channel channel = RabbitMQUtils.getChannel(connection,HelloRabbitMQConstants.QUEUES);
        /*
         * 消费消息
         * 参数1: 被消费的队列名称
         * 参数2: 开始消息的自动确认机制
         * 参数3: 消费时的回调接口
         */
        channel.basicConsume(HelloRabbitMQConstants.QUEUES, true, new DefaultConsumer(channel){

            @Override // 最后一个参数: 消息队列中列出的消息
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                System.out.println("Message: " + new String(body));
            }
        });
        /*
         * 因为junit test不支持多线程模型
         * 设置主线程休眠 让主线程保持存活
         * 防止回调的时候线程被杀
         */
        Thread.sleep(Integer.MAX_VALUE);
    }
}
