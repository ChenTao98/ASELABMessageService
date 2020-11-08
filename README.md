# Message 微服务
## 部署
1. git pull
2. mvn clean package -DskipTests
3. 打开target目录运行jar包 nohup java -jar -.jar --spring.profiles.active=product & 其中./nohup.out是输出文件