# 使用 Maven 官方镜像构建 Java 项目
FROM maven:3.6.3-jdk-8-slim AS build

# 设置工作目录
WORKDIR /app

# 复制 pom.xml 和源代码到容器中
COPY pom.xml /app/
COPY src /app/src/

# 使用 Maven 构建项目，跳过测试
RUN mvn clean package -DskipTests

# 使用 MySQL 官方镜像
FROM mysql:5.7

# 设置 MySQL 环境变量
ENV MYSQL_ROOT_PASSWORD=root_emsp!emsp@
ENV MYSQL_DATABASE=emsp
ENV MYSQL_USER=emsp
ENV MYSQL_PASSWORD=emsp

# 将 db 目录下的 SQL 文件复制到 MySQL 初始化目录
COPY db /docker-entrypoint-initdb.d/

# 将从 Maven 构建的 JAR 文件复制到容器
COPY --from=build /app/target/*.jar /app/emsp-account-token-service.jar

# 安装 JDK 8，确保容器中可以运行 Java 应用
RUN apt-get update && apt-get install -y openjdk-8-jdk

# 暴露 MySQL 端口
EXPOSE 3306

# 暴露 Java 应用端口
EXPOSE 8080

# 启动 MySQL 和 Java 应用服务
CMD service mysql start && java -jar /app/emsp-account-token-service.jar
