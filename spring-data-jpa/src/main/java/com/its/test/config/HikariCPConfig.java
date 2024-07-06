package com.its.test.config;

import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.sql.DataSource;

@Configuration
@EnableScheduling
public class HikariCPConfig {

    private static final Logger logger = LoggerFactory.getLogger(HikariCPConfig.class);

    private final DataSource dataSource;

    public HikariCPConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Scheduled(fixedRate = 60000) // Log every minute
    public void logHikariCPMetrics() {
        if (dataSource instanceof HikariDataSource) {
            HikariDataSource hikariDataSource = (HikariDataSource) dataSource;
            logger.info("HikariCP Pool Metrics:");
            logger.info("Active Connections: {}", hikariDataSource.getHikariPoolMXBean().getActiveConnections());
            logger.info("Idle Connections: {}", hikariDataSource.getHikariPoolMXBean().getIdleConnections());
            logger.info("Total Connections: {}", hikariDataSource.getHikariPoolMXBean().getTotalConnections());
            logger.info("Threads Awaiting Connection: {}", hikariDataSource.getHikariPoolMXBean().getThreadsAwaitingConnection());
        }
    }

}
