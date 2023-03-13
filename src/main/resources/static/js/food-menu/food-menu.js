window.onload = function() {
    var foodTypeSelect = document.getElementById("foodKind");
    var foodKindSelect = document.getElementById("foodType");

    foodTypeSelect.addEventListener("change", function() {
        var selectedType = foodTypeSelect.value;
        var kinds = [];
        if(selectedType !== "") {
            fetch(`/food-menu/add/${encodeURIComponent(selectedType)}`)
                .then(response => response.json())
                .then(data => {
                    kinds = data;
                    populateFoodKindSelect(kinds);
                })
                .catch(error => console.error(error));
        } else {
            populateFoodKindSelect(kinds);
        }
    });

    function populateFoodKindSelect(kinds) {
        foodKindSelect.innerHTML = "";
        kinds.forEach(function(kind) {
            var option = document.createElement("option");
            option.value = kind;
            option.text = kind;
            foodKindSelect.appendChild(option);
        });
    }
};





