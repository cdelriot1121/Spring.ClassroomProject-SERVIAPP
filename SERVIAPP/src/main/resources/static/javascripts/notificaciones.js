
const notificationBell = document.getElementById('notificationBell');
const notifications = document.getElementById('notifications');


notificationBell.addEventListener('click', function () {
    if (notifications.classList.contains('show')) {
        notifications.classList.remove('show');
        notifications.classList.add('hide');
    } else {
        notifications.classList.remove('hide');
        notifications.classList.add('show');
    }
});

window.addEventListener('click', function(event) {
    if (!notificationBell.contains(event.target) && !notifications.contains(event.target)) {
        notifications.classList.remove('show');
        notifications.classList.add('hide');
    }
});
