/**
 * Created by Manu on 2015-10-15.
 */
//Fonction qui permet de gérer les checked categories et le drop down
function checkCB(mainCat) {
    if ($("#" + mainCat).prop('checked')) {
        document.getElementById(mainCat + 'Category').className = "category-Selection collapse in";
    }
}

function getCheckBoxCatValue(checkBoxId) {
    if ($("#" + checkBoxId).prop('checked', true)) {
        location.href = "/category?categoryName=" + checkBoxId + "&catIsChecked=" + false;
    }
    else {
        $('#' + checkBoxId).prop('checked', false);
        location.href = "/category?categoryName=" + checkBoxId + "&catIsChecked=" + true;
    }
}

function getCheckBoxSubCatValue(checkBoxId) {
    if ($("#" + checkBoxId).prop('checked', true)) {
        $("#" + checkBoxId).checked = false;
        location.href = "/subcategory?subCategoryName=" + checkBoxId + "&subCatIsChecked=" + false;
    }
    else {
        $('#' + checkBoxId).prop('checked', false);
        $("#" + checkBoxId).checked = true;
        location.href = "/subcategory?subCategoryName=" + checkBoxId + "&subCatIsChecked=" + true;
    }
}