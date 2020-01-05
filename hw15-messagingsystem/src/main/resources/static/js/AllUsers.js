var stompClient = null;

$( document ).ready(connect());

function connect() {
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, onConnected, {});
}

function onConnected() {
    stompClient.subscribe('/topic/answer', onMessageReceived);

    stompClient.send("/app/server",
        {},
        JSON.stringify({type: 'JOIN_ALL_USERS_PAGE',
            dataForCreate: null,
            createdData: null,
            dataCollection: null})
    )
}

function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);

    if (message.type == 'RESPONSE_ALL_USERS_PAGE') {

        var users = message.dataCollection;
        users.forEach(function (user) {
            $('tbody').append(`
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.name}</td>
                        <td>${user.age}</td>
                    </tr>
                `)
        });
    }
}