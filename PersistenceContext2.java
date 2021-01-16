package com.multipledb.demo.configurationtest;

import com.multipledb.demo.dbtest2.DataTest;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(
        basePackages = { "com.multipledb.demo.dbtest2" },
        entityManagerFactoryRef = "db2EntityManagerFactory",
        transactionManagerRef = "db2TransactionManager"
)
@EnableTransactionManagement
class PersistenceContext2 {

    @Autowired
    public Environment env;

    @Bean(name = "db2DataSource")
    @ConfigurationProperties(prefix="app.datasource.db2")
    public DataSource barDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("app.datasource.db2.driverClassName"));
        dataSource.setUrl(env.getProperty("app.datasource.db2.url"));
        dataSource.setUsername(env.getProperty("app.datasource.db2.username"));
        dataSource.setPassword(env.getProperty("app.datasource.db2.password"));

        return dataSource;
    }

    @Bean(name = "db2EntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean barEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("db2DataSource") DataSource barDataSource) {
        return builder
                .dataSource(barDataSource)
                .packages("com.multipledb.demo.dbtest2")
                .persistenceUnit("dbtest2")
                .build();
    }

    @Bean(name = "db2TransactionManager")
    public PlatformTransactionManager barTransactionManager(
            @Qualifier("db2EntityManagerFactory") EntityManagerFactory barEntityManagerFactory) {
        return new JpaTransactionManager(barEntityManagerFactory);
    }

//
//    @Bean(destroyMethod = "close")
//    public DataSource dataSources(Environment env) {
//        HikariConfig dataSourceConfig = new HikariConfig();
//        dataSourceConfig.setDriverClassName(env.getRequiredProperty("db.driver"));
//        dataSourceConfig.setJdbcUrl(env.getRequiredProperty("db.url1"));
//        dataSourceConfig.setUsername(env.getRequiredProperty("db.username"));
//        dataSourceConfig.setPassword(env.getRequiredProperty("db.password"));
//
//        return new HikariDataSource(dataSourceConfig);
//    }
//
//    @Bean
//    LocalContainerEntityManagerFactoryBean entityManagerFactories(Environment env) {
//        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean1
//                = new LocalContainerEntityManagerFactoryBean();
//        entityManagerFactoryBean1.setDataSource(dataSources(env));
//        entityManagerFactoryBean1.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
//        entityManagerFactoryBean1.setPackagesToScan("com.multipledb.demo.dbtest2");
//
//        Properties jpaProperties = new Properties();
//
//        //Configures the used database dialect. This allows Hibernate to create SQL
//        //that is optimized for the used database.
//        jpaProperties.put("hibernate.dialect", env.getRequiredProperty("hibernate.dialect"));
//
//        //Specifies the action that is invoked to the database when the Hibernate
//        //SessionFactory is created or closed.
//        jpaProperties.put("hibernate.hbm2ddl.auto",
//                env.getRequiredProperty("hibernate.hbm2ddl.auto")
//        );
//
//        //Configures the naming strategy that is used when Hibernate creates
//        //new database objects and schema elements
//        jpaProperties.put("hibernate.ejb.naming_strategy",
//                env.getRequiredProperty("hibernate.ejb.naming_strategy")
//        );
//
//        //If the value of this property is true, Hibernate writes all SQL
//        //statements to the console.
//        jpaProperties.put("hibernate.show_sql",
//                env.getRequiredProperty("hibernate.show_sql")
//        );
//
//        //If the value of this property is true, Hibernate will format the SQL
//        //that is written to the console.
//        jpaProperties.put("hibernate.format_sql",
//                env.getRequiredProperty("hibernate.format_sql")
//        );
//
//        entityManagerFactoryBean1.setJpaProperties(jpaProperties);
//
//        return entityManagerFactoryBean1;
//    }
//
//    @Bean
//    JpaTransactionManager transactionManagers(EntityManagerFactory entityManagerFactory) {
//        JpaTransactionManager transactionManager = new JpaTransactionManager();
//        transactionManager.setEntityManagerFactory(entityManagerFactory);
//        return transactionManager;
//    }
}