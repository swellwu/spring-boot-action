# spring-boot-mybatis
## 整合spring-boot 和 mybatis，实现自动注入mapper
1. pom依赖
```
    <properties>
        <mybatis-spring-boot>1.2.0</mybatis-spring-boot>
        <mysql-connector>5.1.43</mysql-connector>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>${mybatis-spring-boot}</version>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>${mysql-connector}</version>
        </dependency>
    </dependencies>
```
2. application配置
```
# 数据源配置
spring.datasource.url=jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf-8
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.jdbc.Driver

# mybatis配置,设置po和mapper路径
mybatis.type-aliases-package=com.swellwu.po
mybatis.mapper-locations=classpath:mapper/*Mapper.xml
```

