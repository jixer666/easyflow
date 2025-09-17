//package com.abc.common;
//
//import com.alibaba.otter.canal.client.CanalConnector;
//import com.alibaba.otter.canal.protocol.CanalEntry;
//import com.alibaba.otter.canal.protocol.exception.CanalClientException;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Component;
//import com.alibaba.otter.canal.protocol.Message;
//
//import javax.annotation.PreDestroy;
//import java.util.List;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.TimeUnit;
//
//@Slf4j
//@Component
//public class CanalCacheSyncService {
//
//    private final CanalConnector canalConnector;
//    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
//
//    @Autowired
//    public CanalCacheSyncService(CanalConnector canalConnector) {
//        this.canalConnector = canalConnector;
//        // 启动监听线程
//        startListener();
//    }
//
//    private void startListener() {
//        executorService.submit(() -> {
//            while (!Thread.currentThread().isInterrupted()) {
//                try {
//                    // 获取消息（100条，超时时间1秒）
//                    Message message = canalConnector.getWithoutAck(100, 1000L, TimeUnit.MILLISECONDS);
//                    long batchId = message.getId();
//                    int size = message.getEntries().size();
//
//                    if (batchId == -1 || size == 0) {
//                        // 没有数据时休眠
//                        Thread.sleep(1000);
//                    } else {
//                        // 处理消息
//                        processEntries(message.getEntries());
//                        // 确认消息已消费
//                        canalConnector.ack(batchId);
//                    }
//                } catch (Exception e) {
//                    log.error("Canal监听异常", e);
//                    try {
//                        // 发生异常时回滚
//                        canalConnector.rollback();
//                    } catch (CanalClientException ex) {
//                        log.error("Canal回滚异常", ex);
//                    }
//                }
//            }
//        });
//    }
//
//    private void processEntries(List<CanalEntry.Entry> entries) {
//        for (CanalEntry.Entry entry : entries) {
//            // 过滤非事务和DDL操作
//            if (entry.getEntryType() != CanalEntry.EntryType.ROWDATA) {
//                continue;
//            }
//
//            try {
//                CanalEntry.RowChange rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
//                CanalEntry.EventType eventType = rowChange.getEventType();
//                String tableName = entry.getHeader().getTableName();
//
//                // 处理行数据变更
//                for (CanalEntry.RowData rowData : rowChange.getRowDatasList()) {
//                    handleRowData(tableName, eventType, rowData);
//                }
//            } catch (Exception e) {
//                log.error("处理Canal消息异常", e);
//            }
//        }
//    }
//
//    private void handleRowData(String tableName, CanalEntry.EventType eventType, CanalEntry.RowData rowData) {
//        // 根据表名和事件类型处理不同的缓存更新逻辑
//        if ("sys_user".equals(tableName)) {
//            handleUserTable(eventType, rowData);
//        }
//        // 可以添加更多表的处理逻辑
//    }
//
//    private void handleUserTable(CanalEntry.EventType eventType, CanalEntry.RowData rowData) {
//        // 获取主键ID（假设用户表主键为id）
//        Long userId = getPrimaryKey(rowData, eventType);
//        if (userId == null) {
//            return;
//        }
//
//        System.out.println("监听user表更改");
//        switch (eventType) {
//            case INSERT:
//            case UPDATE:
//                System.out.println("user表更新");
//
//                // 新增或更新时，删除缓存（或更新缓存）
//                //redisTemplate.delete(cacheKey);
//                // 或者主动加载最新数据到缓存
//                // loadUserToCache(userId);
//                break;
//            case DELETE:
//                // 删除时，删除缓存
//                //redisTemplate.delete(cacheKey);
//                break;
//            default:
//                // 其他事件类型不处理
//                break;
//        }
//    }
//
//    private Long getPrimaryKey(CanalEntry.RowData rowData, CanalEntry.EventType eventType) {
//        List<CanalEntry.Column> columns = eventType == CanalEntry.EventType.DELETE
//            ? rowData.getBeforeColumnsList()
//            : rowData.getAfterColumnsList();
//
//        for (CanalEntry.Column column : columns) {
//            if (column.getIsKey()) {
//                return Long.parseLong(column.getValue());
//            }
//        }
//        return null;
//    }
//
//    @PreDestroy
//    public void destroy() {
//        // 关闭线程池
//        executorService.shutdown();
//        try {
//            if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
//                executorService.shutdownNow();
//            }
//        } catch (InterruptedException e) {
//            executorService.shutdownNow();
//        }
//        // 断开Canal连接
//        canalConnector.disconnect();
//    }
//}
