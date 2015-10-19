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

function getCheckBoxCatValue(checkBoxId){
    if($("#" + checkBoxId).prop('checked', true)){
        var catCheckedCb = $('.catCb:checked').map(function(){
            return this.value;
        });
        location.href="/category?categoryName=" + checkBoxId + "&checkedCatList=" + catCheckedCb;
    }
}

function getCheckBoxSubCatValue(checkBoxId){
    if($("#" + checkBoxId).prop('checked',true)){
        var subCatCheckedCb = $('.subCatCb:checked').map(function(){
            return this.value;
        });
        location.href="/subcategory?subCategoryName=" + checkBoxId + "&checkedSubCatList=" + subCatCheckedCb;
    }
}

function setCheckBoxState(checkedCatList, checkedSubCatList){
    checkedCatList.forEach(function(cat){
        $("#" + cat).prop('checked', true);
    })
    checkedSubCatList.forEach(function(subCat){
        $("#" + subCat).prop('checked', true);
    })
}