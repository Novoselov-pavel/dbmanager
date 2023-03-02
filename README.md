# Менеджер баз данных

---
##версия 1.0.1
Примечание: для работы необходима Java 17+

Позволяет выполнять изменения баз данных с помощью liquibase скриптов.
В качестве параметров передается путь до основного файла с liquibase скриптом.

В версии 1.0.1 поддержана работа с базами данных *postgresql*.
Возможности при работе с базами данных postgre:
+ создание схемы и пользователя в базе даных
+ удаление схемы и пользователя в базе данных
+ обновление схемы базы данных в соответствии с liquibase скриптами из под указанного пользователя

**Для баз данных postgresql операции создания базы не поддерживаются!**

Консольные команды:

**Вызов справки**

    java -jar dbmanger-1.0.1.jar h
или

    java -jar dbmanger-1.0.1.jar help

**Создание пользователя, схемы и проливка liquibase скриптов**

    java -jar dbmanger-1.0.1.jar createSchema update --url=jdbc:postgresql://localhost:5432/ --admin=postgres --adminPassword=postgres --database=template3 --schema=tmpl --userName=tmpl --userPassword=tmpl --changelog=/home/pavel/Job/dbmanager/ChangelogExample/rootChangeLog.xml

Пример liquibase скриптов:  <a href="/ChangelogExample/rootChangeLog.xml">rootChangeLog.xml</a>

###Информация для разработчиков

Тесты в классе PostgresOperationProviderTest используют библиотеку otj-pg-embedded, для их корректной работы, необходимо:
+ установить docker
+ установить virtualbox
+ установить docker-machine
+ создать локальный сертификат ssl
+ создать новую виртуальную машину (на нее и будет разворачиваться otj-pg-embedded при старте теста)

для ubuntu
virtualbox скачивается отсюда: https://www.virtualbox.org/wiki/Linux_Downloads

Установка docker-machine, создание сертификата и создание виртуальной машины выполняется из командной строки, версию docker-machine брать последнюю из репозитория:

    base=https://github.com/docker/machine/releases/download/v0.16.2 && curl -L $base/docker-machine-$(uname -s)-$(uname -m) >/tmp/docker-machine && sudo mv /tmp/docker-machine /usr/local/bin/docker-machine && chmod +x /usr/local/bin/docker-machine

    openssl req -newkey rsa:4096 -nodes -sha256 -keyout certs/dockerrepo.key -x509 -days 365 -out certs/dockerrepo.crt -subj /CN=local-registry

    docker-machine create --driver virtualbox --virtualbox-memory "2048" --virtualbox-hostonly-cidr 192.168.56.1/21 default

BPM схема процесса см. <a href="/doc/Описание процесса работы сервиса.bpmn">Описание процесса работы сервиса.bpmn</a>.
Открывается с помощью camunda modeler.

