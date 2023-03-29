for (var i = 0; i <= 24; i++) {
    var hour = i < 10 ? "0" + i : i;
    var option = document.createElement("option");
    option.value = hour + ":00";
    option.text = hour + ":00";
    document.getElementById("openHours").appendChild(option);
    document.getElementById("closeHours").appendChild(option.cloneNode(true));
}