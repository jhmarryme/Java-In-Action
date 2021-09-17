package com.jhmarryme.task.job;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.elasticjob.api.ShardingContext;
import org.apache.shardingsphere.elasticjob.simple.job.SimpleJob;
import org.springframework.stereotype.Service;

/**
 *
 * @author JiaHao Wang
 * @date 2021/9/17 11:27
 */
@Slf4j
@Service
public class MySimpleJob implements SimpleJob {
    @Override
    public void execute(ShardingContext shardingContext) {
        switch (shardingContext.getShardingItem()) {
            case 0:
                // do something by sharding item 0
                log.info("分片1：执行任务");
                break;
            case 1:
                // do something by sharding item 1
                log.info("分片2：执行任务");
                break;
            case 2:
                // do something by sharding item 2
                log.info("分片3：执行任务");
                break;
            // case n: ...
            default:
                break;
        }
    }
}
