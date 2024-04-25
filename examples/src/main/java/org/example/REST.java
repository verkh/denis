package org.example;

public class REST {

    /*
        Есть разные принципы построения коммуникации между приложениями:

        SOAP - simple object access protocol (на основе XML)
        RPC/gRPC - remote procedure call/ google protobuf RPC(XML), gRPC(Protobuf)
        REST - representational state transfer (Json)

        ----------- RPC ----------

        RPC - websocket, socket + TCP/UDP protocols, data layer = XML/Protobuf

        RPC оперирует функциями
        - submitNewDate(arguments)
        - sendSignalToUser(arguments)
        - registerNewUserAndRunChecks(arguments) - создаст пользователя, и запустить валидации пользователей,
                                                   в результате которого удалятся какие-то заказы

        ----------- REST ----------

        REST оперирует ресурсами.
        - users
        - user_adresses
        - orders
        - user_roles

        REQUEST METHODS: GET, PUT, PATCH, POST, DELETE ...

        <существительное>/<существительное>/{path_argument}/<существительное>

        глаголы в REST не допускаются, но везде бывают исключения (e.g. POST <address>:<port>/users/validate),
                                       И в таком случае лучше использовать POST

        REST Tools: OpenAPI https://www.baeldung.com/java-openapi-generator-server

        ------- GET
        GET - по канону ничего никогда не меняет и никогда не принимает никаких данных

        если тебе нужно получить данные, но со сложным фильтром - использовать POST или GET + queryParameters
            - POST /users/ +
            {
                "filter" : {
                    "age": "<40",
                    "namesOrigin" : "local",
                    "sex" : "female",
                    ...
                }
            }
            - GET /users?age=$lt40&nameOrigin=local&sex=female&...

        PUT - обновляет данные, замещая полностью весь ресурс
            - если у тебя передается какой-то пользователь, то вся строка в базе будет заменена новыми данными по ID

        PATCH - когда нужно обновить какое-то одно или два или три свойства
            - в этом случае, если ты хочешь обновить только имя, то должно обновиться только имя

        POST - создать новый объект
             - вызвать какую-то функцию на бекенеде (e.g. POST <address>:<port>/users/validate) лучше избегать такого
             - логин, потому что нужна безопасность запроса

        DELETE - удаление ресурса

        Факультативное чтение: idempotancy, идемпотентные запросы
        status codes: https://restfulapi.net/http-status-codes/
     */

}
