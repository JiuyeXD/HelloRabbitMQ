package ro.qwq.rabbitmq.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: ConnectionInfo
 * @Description: 连接信息
 * @Author: JiuyeXD
 * @Date: 2021/7/9 11:48
 * @Version: 1.0
 */
@Data
public class ConnectionInfo implements Serializable {
    private String host;
    private Integer port;
    private String vhostName;
    private String username;
    private String password;
    private String queues;

    public ConnectionInfo(String host, Integer port, String vhostName, String username, String password) {
        this.host = host;
        this.port = port;
        this.vhostName = vhostName;
        this.username = username;
        this.password = password;
    }
}
