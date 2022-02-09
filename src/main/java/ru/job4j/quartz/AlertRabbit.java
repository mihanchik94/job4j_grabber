package ru.job4j.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;

public class AlertRabbit {
        public static void main(String[] args) throws ClassNotFoundException {
            Properties properties = getProperty();
            Class.forName(properties.getProperty("db_driver"));
            try (Connection connection = DriverManager.getConnection(
                    properties.getProperty("db_url"),
                    properties.getProperty("db_login"),
                    properties.getProperty("db_password"))
            ) {
                Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
                scheduler.start();
                JobDataMap data = new JobDataMap();
                data.put("connection", connection);
                JobDetail job = newJob(Rabbit.class)
                        .usingJobData(data)
                        .build();
                SimpleScheduleBuilder times = simpleSchedule()
                        .withIntervalInSeconds(Integer.parseInt(properties.getProperty("rabbit.interval")))
                        .repeatForever();
                Trigger trigger = newTrigger()
                        .startNow()
                        .withSchedule(times)
                        .build();
                scheduler.scheduleJob(job, trigger);
                Thread.sleep(5000);
                scheduler.shutdown();
            } catch (Exception se) {
                se.printStackTrace();
            }
        }


        public static Properties getProperty() {
            Properties properties = new Properties();
            try (InputStream inputStream = AlertRabbit.class.getClassLoader().getResourceAsStream("rabbit.properties")) {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }


    public static class Rabbit implements Job {
        public Rabbit() {
            System.out.println(hashCode());
        }

        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            System.out.println("Rabbit runs here ...");
            try {
                Connection connection = (Connection) context
                        .getJobDetail()
                        .getJobDataMap()
                        .get("connection");
                try (PreparedStatement statement = connection.prepareStatement(
                        "insert into rabbit(created_date) values(current_timestamp)")) {
                    statement.execute();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        }
}
