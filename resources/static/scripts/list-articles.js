if(window.location.href.includes("/category/")){
    let limitValue = window.location.href.split("?")[1].split("&")[0].split("=")[1];

    let selectBox = document.getElementById(`article-limit-option-${limitValue}`);

    selectBox.setAttribute("selected", "selected");
}

function changeLimit(){
    let selectBox = document.getElementById("article-limit");
    let selectedValue = selectBox.options[selectBox.selectedIndex].value;

    let limit = window.location.href.split("?")[1].split("&")[0];

    if(limit.localeCompare(`limit=${selectedValue}`) !== 0)
        window.location.href = window.location.href.replace(limit, `limit=${selectedValue}`);
}