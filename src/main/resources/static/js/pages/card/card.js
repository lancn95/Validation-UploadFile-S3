var stompClient = null;

$(document).ready(function() {
    console.log("Index page is ready");
    connect();

    $("#add-to-card").on('click', function () {
        sendMessage();
    })
});
function connect(){
    var socket = new SockJS('/our-websocket');
    stompClient = Stomp.over(socket);
}
function sendMessage(){
    console.log("sending message");
    stompClient.send("/ws/message", {}, JSON.stringify({'messageContent': $("#card-title").text()})
    )
}


