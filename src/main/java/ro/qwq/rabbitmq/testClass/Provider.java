package ro.qwq.rabbitmq.testClass;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ro.qwq.rabbitmq.constant.HelloRabbitMQConstants;
import ro.qwq.rabbitmq.domain.ConnectionInfo;
import ro.qwq.rabbitmq.utils.RabbitMQUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


/**
 * @ClassName: Provider
 * @Description: 生产者测试模型
 * @Author: JiuyeXD
 * @Date: 2021/7/9 10:05
 * @Version: 1.0
 */
@SpringBootTest
public class Provider {

    /**
     * @Description: 生产消息
     * @return: void
     */
    @Test
    public void testSendMessage() throws InterruptedException, IOException, TimeoutException {
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
        // 发布消息
        for (int i = 0; i < 60; i++){
            // 50ms发送一个消息
            Thread.sleep(1000);
            /*
             * 发布消息
             * 参数1: 交换机名称
             * 参数2: 队列名称
             * 参数3: 传递消息额外配置
             * 参数4: 消息的具体内容
             */
            channel.basicPublish("", HelloRabbitMQConstants.QUEUES, null, ("hello rabbitMQ! No." + (i+1)).getBytes());
            System.out.println("消息已发送" + (i+1));
        }
        RabbitMQUtils.closeStream(channel,connection);
    }
}
