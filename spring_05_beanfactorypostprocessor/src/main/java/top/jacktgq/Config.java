package top.jacktgq;

import com.alibaba.druid.pool.DruidDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import top.jacktgq.component.Bean2;

import javax.sql.DataSource;

/**
 * @Author XiYue
 * @Date 2022/3/29--12:27
 * @Description
 */
@Configuration
@ComponentScan("top.jacktgq.component")
public class Config {
    @Bean
    public Bean1 bean1() {
        return new Bean1();
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource) {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        return sqlSessionFactoryBean;
    }

    @Bean(initMethod = "init")
    public DruidDataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/springtest");
        dataSource.setUsername("root");
        dataSource.setPassword("TGQ@XiYue123");
        return dataSource;
    }

    public Bean2 bean2() {
        return new Bean2();
    }

    /*@Bean
    public MapperFactoryBean<Mapper1> mapperFactoryBean1(SqlSessionFactory sqlSessionFactory) {
        MapperFactoryBean<Mapper1> mapperFactoryBean = new MapperFactoryBean<>(Mapper1.class);
        mapperFactoryBean.setSqlSessionFactory(sqlSessionFactory);
        return mapperFactoryBean;
    }

    @Bean
    public MapperFactoryBean<Mapper2> mapperFactoryBean2(SqlSessionFactory sqlSessionFactory) {
        MapperFactoryBean<Mapper2> mapperFactoryBean = new MapperFactoryBean<>(Mapper2.class);
        mapperFactoryBean.setSqlSessionFactory(sqlSessionFactory);
        return mapperFactoryBean;
    }*/
}
