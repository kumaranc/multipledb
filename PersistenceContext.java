package com.multipledb.demo.configurationtest;

import com.multipledb.demo.dbtest1.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        basePackages = { "com.multipledb.demo.dbtest1" },
        entityManagerFactoryRef = "db1EntityManagerFactory",
        transactionManagerRef = "transactionManager"
)
@EnableTransactionManagement
class PersistenceContext {

    @Autowired
    public Environment env;

    @Primary
    @Bean(name = "dataSource")
    @ConfigurationProperties(prefix="app.datasource.db1")
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("app.datasource.db1.driverClassName"));
        dataSource.setUrl(env.getProperty("app.datasource.db1.url"));
        dataSource.setUsername(env.getProperty("app.datasource.db1.username"));
        dataSource.setPassword(env.getProperty("app.datasource.db1.password"));

        return dataSource;
    }

    @Primary
    @Bean(name = "db1EntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean EntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("dataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages(Data.class)
                .build();
    }

    @Primary
    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(
            final @Qualifier("db1EntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

//    @Bean(destroyMethod = "close")
//    public DataSource dataSource(Environment env) {
//        HikariConfig dataSourceConfig = new HikariConfig();
//        dataSourceConfig.setDriverClassName(env.getRequiredProperty("app.datasource.db1.driverClassName"));
//        dataSourceConfig.setJdbcUrl(env.getRequiredProperty("app.datasource.db1.url"));
//        dataSourceConfig.setUsername(env.getRequiredProperty("app.datasource.db1.username"));
//        dataSourceConfig.setPassword(env.getRequiredProperty("app.datasource.db1.password"));
//
//        return (DataSource) new HikariDataSource(dataSourceConfig);
//    }
//
//    @Bean(name = "entityManagerFactory")
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource,
//                                                                Environment env) {
//        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean
//                = new LocalContainerEntityManagerFactoryBean();
//        entityManagerFactoryBean.setDataSource((javax.sql.DataSource) dataSource);
//        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
//        entityManagerFactoryBean.setPackagesToScan("com.multipledb.demo.dbtest1");
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
////        jpaProperties.put("hibernate.ejb.naming_strategy",
////                env.getRequiredProperty("hibernate.ejb.naming_strategy")
////        );
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
//        entityManagerFactoryBean.setJpaProperties(jpaProperties);
//
//        return entityManagerFactoryBean;
//    }

}