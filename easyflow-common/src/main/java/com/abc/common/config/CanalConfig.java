//package com.abc.common.config;
//
//import com.alibaba.otter.canal.client.CanalConnector;
//import com.alibaba.otter.canal.client.CanalConnectors;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.net.InetSocketAddress;
//
//@Configuration
//public class CanalConfig {
//
//    @Value("${canal.server.host}")
//    private String host;
//
//    @Value("${canal.server.port}")
//    private int port;
//
//    @Value("${canal.server.destination}")
//    private String destination;
//
//    @Bean
//    public CanalConnector canalConnector() {
//        // 创建Canal连接器
//        CanalConnector connector = CanalConnectors.newSingleConnector(
//            new InetSocketAddress(host, port),
//            destination,
//            "",
//            ""
//        );
//        // 连接Canal服务
//        connector.connect();
//        // 订阅需要监听的表（全量订阅使用".*\\..*"）
//        connector.subscribe(".*\\\\..*");
//        // 回滚到上次消费位置
//        connector.rollback();
//        return connector;
//    }
//}