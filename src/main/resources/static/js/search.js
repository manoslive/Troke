/**
 * Created by Manu on 2015-10-15.
 */
//Fonction qui permet de gÃ©rer les checked categories et le drop down
function checkCB(mainCat) {
    if ($("#" + mainCat).prop('checked')) {
        $("." + mainCat).prop('checked', false);
        document.getElementById(mainCat + 'Category').className = "category-Selection collapse in";
    }
}

function getCheckCBValue(checkBoxId){
    if($("#" + checkBoxId).prop('checked', true)){
        location.href="/category?categoryName=" + checkBoxId;
    }
}