var stompClient = null;

$( document ).ready(function() {
    $('#addForm').submit(function (event) {
        event.preventDefault();

        var socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, onConnected, {});
    });
});

function onConnected() {
    stompClient.subscribe('/topic/answer', onMessageReceived);

    stompClient.send("/app/server",
        {},
        JSON.stringify({type: 'JOIN_ADD_USER_PAGE', 
            dataForCreate: {name: $('#name').val(), age: $('#age').val()},
            createdData: null,
            dataCollection: null})
    )
}

function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);

    if (message.type == 'RESPONSE_ADD_USER_PAGE') {
        var user = message.createdData;
        if (user.id != null) {
            window.location.assign('/all_users');
        }
    }
}