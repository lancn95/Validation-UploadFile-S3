
var stompClient = null;
var notificationCartCount = 0;
$(document).ready(function() {
    console.log("Index page is ready");
    connect();
    // $("#notification-cart").text(notificationCartCount)
});

function connect(){
    var socket = new SockJS("/our-websocket");
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame)
        stompClient.subscribe('/topic/global-notifications', function (message) {
            console.log(message)
            $("#title-cart").text(message.body);
            notificationCartCount = notificationCartCount + 1;
            updateNotificationDisplay();
        });
    });


}

function updateNotificationDisplay() {
    if (notificationCartCount == 0) {
        $('#notification-cart').hide();
    } else {
        $('#notification-cart').show();
        $('#notification-cart').text(notificationCartCount);
    }
}