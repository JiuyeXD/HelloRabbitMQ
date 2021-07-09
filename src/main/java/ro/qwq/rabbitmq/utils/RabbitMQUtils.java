package ro.qwq.rabbitmq.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import ro.qwq.rabbitmq.domain.ConnectionInfo;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName: GetChannel
 * @Description: TODO
 * @Author: JiuyeXD
 * @Date: 2021/7/9 11:45
 * @Version: 1.0
 */
public class RabbitMQUtils {

    /** 创建连接MQ的工厂对象 */
    private static final ConnectionFactory connectionFactory;
    static{
        connectionFactory = new ConnectionFactory();
    }
    public static Connection getConnection(ConnectionInfo connInfo) throws IOException, TimeoutException {
        // 设置目标主机
        connectionFactory.setHost(connInfo.getHost());
        // 设置目标主机端口
        connectionFactory.setPort(connInfo.getPort());
        // 设置目标虚拟主机
        connectionFactory.setVirtualHost(connInfo.getVhostName());
        // 设置用户名与密码
        connectionFactory.setUsername(connInfo.getUsername());
        connectionFactory.setPassword(connInfo.getPassword());
        // 创建连接对象
        Connection connection = connectionFactory.newConnection();
        return connection;
    }
    public static Channel getChannel(Connection connection,String queue) throws IOException {
        // 通过连接对象获取通道
        Channel channel = connection.createChannel();
        /*
         * 通道绑定对应的消息队列
         * 参数1: 队列名称 (如果不存在则创建)
         * 参数2: 队列是否持久化 (true / false)
         * 参数3: 是否独占队列 (true / false)
         * 参数4: 消费完成后是否自动删除队列 (true / false)
         * 参数5: 额外附加参数
         */
        channel.queueDeclare(queue, false, false, false, null);
        return channel;
    }

    public static void closeStream(Channel channel,Connection connection) throws IOException, TimeoutException {
        if(channel != null){
            // 关闭通道
            channel.close();
        }
        if (connection != null){
            //关闭连接
            connection.close();
        }
    }
}
