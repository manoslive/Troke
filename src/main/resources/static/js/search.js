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
    if(document.getElementById(checkBoxId).checked)
    {
        location.href = "/category?categoryName=" + checkBoxId + "&catIsChecked=" + true;
    }
    else{
        location.href = "/category?categoryName=" + checkBoxId + "&catIsChecked=" + false;
    }

}
function getCheckBoxSubCatValue(checkBoxId) {
    if ($("#" + checkBoxId).prop('checked')) {
        //$("#" + checkBoxId).checked = false;
        location.href = "/subcategory?subCategoryName=" + checkBoxId + "&subCatIsChecked=" + true;
    }
    else{
        //$("#" + checkBoxId).checked = true;
        location.href = "/subcategory?subCategoryName=" + checkBoxId + "&subCatIsChecked=" + false;
    }
}
function getCheckBoxSubCatValueLink(checkBoxId) {
    if ($("#" + checkBoxId).prop('checked')) {
        $("#" + checkBoxId).prop('checked', false);
        location.href = "/subcategory?subCategoryName=" + checkBoxId + "&subCatIsChecked=" + false;
    }
    else{
        $("#" + checkBoxId).prop('checked', true);
        location.href = "/subcategory?subCategoryName=" + checkBoxId + "&subCatIsChecked=" + true;
    }
}
/* Modal Info Item */
var currentModalID;
var currentInfoID;
function showModalInfoItem(ObjectID){
    currentInfoID = ObjectID;
    currentModalID = '#modal-InfoItem' + ObjectID;
    document.querySelector(currentModalID).classList.add('modal-box-show');
    var modal = document.getElementById('modal-InfoItem'+ObjectID);
    var images = modal.getElementsByClassName("subImage");
    hover(images[0]);
}
//Hover des petites images du modal InfoItem
function hover(element) {
    $('.mainImage').attr('src', element.getAttribute('src'));
}
$(init);
function init() {
    $(".js-modal-close, .modal-overlay").click(function () {
        $(".modal-box, .modal-overlay").fadeOut(500, function () {
            $(".modal-box, .modal-overlay").removeClass("modal-box-show");
        });
    });

    $(window).resize(function () {
        $(".modal-box").css({
            top: ($(window).height() - $(".modal-box").outerHeight()) / 2,
            left: ($(window).width() - $(".modal-box").outerWidth()) / 2
        });
    });
    $(window).resize();

    /* modal close et click exterieure du modal */
    function modalClose() {
        $(".modal-box, .modal-overlay").fadeOut(500, function () {
            $(".modal-box, .modal-overlay").removeClass("modal-box-show");
        });
    }

// Handle ESC key (key code 27)
    document.addEventListener('keyup', function (e) {
        if (e.keyCode == 27) {
            modalClose();
        }
    });
    document.getElementById('tradeForm').onsubmit = function () {
        return false;
    }
    var modal, modalInfoItem = document.getElementsByName("modal-Item-Info");
    for (modal in modalInfoItem) {
        modalInfoItem[modal].addEventListener('click', function (e) {
            modalClose();
        }, false);
        //Prevent event bubbling if click occurred within modal content body
        modalInfoItem[modal].children[0].addEventListener('click', function (e) {
            e.stopPropagation();
        }, false);
    }

    /* Delegate Scroll images InfoItem */
    $('#divSubImages').delegate('img', 'click', function () {
        $('#mainImage').attr('src', $(this).attr('src').replace('sub', 'main'));
    });
    $('#divSubImages').delegate('img', 'hover', function () {
        $('#mainImage').attr('src', $(this).attr('src').replace('sub', 'main'));
    });
}