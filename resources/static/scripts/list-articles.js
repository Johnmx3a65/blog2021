function changeLimit(){
    let selectBox = document.getElementById("article-limit");
    let selectedValue = selectBox.options[selectBox.selectedIndex].value;

    let tempArray = window.location.href.split("?");
    let searchParameters = tempArray[1];

    tempArray = searchParameters.split("&");

    let limit = tempArray[0];

    if(limit.localeCompare(`limit=${selectedValue}`) !== 0)
        window.location.href = window.location.href.replace(limit, `limit=${selectedValue}`);
}