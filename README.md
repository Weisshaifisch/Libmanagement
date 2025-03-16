Приложение является простым примером сервиса работы с пользователями и книгами в библиотеке. Реализует REST API с помощью jersey. 
Позволяет работать с тремя сущностями: книгами, пользователями и карточками, контролирующими выдачу/возврат книг.
Реализованы следующие операции:

    для пользователей - создание, изменение, поиск, удаление и получение списка взятых книг за указанный период либо всех
    для книг - создание, изменение, поиск, удаление
    для карточек - получение списка всех взятых книг, поиск конкретной карточки, выдачу книги пользователю, возврат книги пользователем

Полная спецификация API приложения на основе описания Open API находится в файле swagger.yaml. Его можно импортировать в Swagger Editor 
(https://editor.swagger.io/) для просмотра спецификации API в более удобном виде.

В приложении используется база данных H2 с в памяти. Так же для удобства запуска в приложении используется встроенный Tomcat 10.1.39.
Это упрощает запуск приложения и работы с ним.

Сборка проекта:
    mvn clean package
    
Запуск приложения
    cd target/bin
    launch libmanagement.bat