/**
 * Created by Ysteinthal on 2015-11-23.
 */

var width = $(window).width();
$(window).resize(function(){
    if($(this).width() <=767){

        $("#col1").css("width", "100%");
        $("#col2").css("width", "100%");
        $("#col3").css("width", "100%");

    }
});
var deleteTradeID;
function showDeleteConfirmation(tradeID){
    document.querySelector('#openModalDelete').classList.add('modal-box-show');
    deleteTradeID = tradeID;
}
function modalClose() {
    $(".modal-box, .modal-overlay").fadeOut(500, function () {
        $(".modal-box, .modal-overlay").removeClass("modal-box-show");
    });
}

function deleteTrade(){
    alert(deleteTradeID);
    document.getElementById("tradeID").value = deleteTradeID;
    document.getElementById("deleteTradeForm").submit();
}
$(init);
function init(){
    $(".js-modal-close, .modalDialogDelete").click(function () {
        modalClose();
    });
    // Handle ESC key (key code 27)
    document.addEventListener('keyup', function (e) {
        if (e.keyCode == 27) {
            modalClose();
        }
    });
    //Prevent le close si on click sur le modal lui-meme
    document.getElementById("openModalDelete").children[0].addEventListener('click', function (e) {
        e.stopPropagation();
    }, false);
}