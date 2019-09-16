FROM zywlxh/oojava8
VOLUME /tmp
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
ADD target/*.jar app.jar
ADD bin/startup.sh /
RUN chmod 777 startup.sh
ENV JAVA_OPTS=""
ENTRYPOINT ["/startup.sh"]