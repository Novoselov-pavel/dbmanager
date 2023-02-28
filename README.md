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

###Информация для разработчика


