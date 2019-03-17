
function validate() {
    var result = true;
    var name = $('#name').val();
    var phone = $('#phone').val();
    if (name == '') {
        result = false;
        alert('Please, enter your Name');
    }
    if (phone == '') {
        result = false;
        alert('Please, enter your Phone Number');
    }
    return result;
}

function fillHall() {
    $.ajax({
        url: '/hall',
        method: 'POST',
        complete: function (response) {
            var result = "";
            var hall = JSON.parse(response);
            for (var i = 0; i < hall.length; i++) {
                var place = hall[i];
                alert(place);
                result +=
            "<td><input type = \"radio\" name = \"place\" value =\"" + place.get("id") + "\">"
                + "Ряд " + place.get("row") + ", Место " + place.get("place") + " " + place.get("availability") + "</td>";
            }
            $('#place').html(result);
        }
    });
}
