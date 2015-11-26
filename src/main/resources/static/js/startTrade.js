/**
 * Created by Shaun Cooper on 2015-10-16.
 */
//Créer la liste des objets dans la zone d'échange
function makeIDList(){
    var x = document.getElementsByClassName("itemID");
    var i;
    for (i = 0; i < x.length; i++) {
        document.getElementById("tradeObjectsID").value += x[i].value + ";";
    }
    if(document.getElementById("newTradeMoneyInput") !== null) {
        if(document.getElementById("newTradeMoneyInput").value > 0) {
            document.getElementById("newTradeMoneyValue").value = document.getElementById("newTradeMoneyInput").value;
        }else{
            document.getElementById("newTradeMoneyValue").value = 0;
        }
    }else {
        document.getElementById("newTradeMoneyValue").value = 0;
    }
    document.getElementById("startTradeForm").submit();
}
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
function goBack(){
    window.history.back();
}
function sendChat(userName){
    if(document.getElementById('chatEnter').value.trim() != "")
    {
        var text = document.getElementById('chatEnter').value;
        document.getElementById('chatEnter').value = "";
        document.getElementById('chatBoxStartExchange').value += (userName + ": " + text + "\n");
    }
}
//Hover des petites images du modal InfoItem
function hover(element) {
    $('.mainImage').attr('src', element.getAttribute('src'));
}
//Function qui vérifi si le trade est valid
function checkValidTrade(){
    if($('#opponentExchangeItemsStart ul li').length < 1) {
        document.getElementById("btn-send-trade").style.pointerEvents = "none";
        document.getElementById("btn-send-trade").innerText = "Sélectionnez un item \n pour l'échange"
        $('#btn-send-trade').addClass('btn-warning');
        $('#btn-send-trade').removeClass('btn-success');
    }else {
        document.getElementById("btn-send-trade").style.pointerEvents = "auto";
        document.getElementById("btn-send-trade").innerText = "Envoyer l'offre!";
        $('#btn-send-trade').addClass('btn-success');
        $('#btn-send-trade').removeClass('btn-warning');
    }
}
function checkAdjustForScrollBar(element){
    (function($) {
        $.fn.has_scrollbar = function() {
            var divnode = this.get(0);
            if(divnode.scrollHeight > divnode.clientHeight)
                return true;
        }
    })(jQuery);
    if(element.has_scrollbar()) {
        element.css("padding-left", "0px");
    }
    else{
        element.css("padding-left", "12px");
    }
}
$(window).resize(function() {
    checkAdjustForScrollBar($("#OpponentInventory"));
    if($(window).width() < 480)
    {
        //Mobile
        $(".startTrade-OpponentInventoryContainer").insertBefore("#exchangeZone");
    }
    else if($(window).width() <= 1024) //Glitch dans le matrix , 1006 affect quand lecran est a 1024
    {
        //Tablet
        $(".startTrade-OpponentInventoryContainer").insertBefore("#exchangeZone");
    }
    else
    {
        //Desktop
        $(".startTrade-OpponentInventoryContainer").insertAfter("#exchangeZone");
    }
});
$(init);
function init() {
    checkAdjustForScrollBar($("#OpponentInventory"));
    $("#OpponentInventory").bind("DOMSubtreeModified", function() {
        checkAdjustForScrollBar($("#OpponentInventory"));
    });
    // mettre les items non selectable (selection de text, highlight bleu ...)
    $("#OpponentInventory").disableSelection();
    $("#opponentExchangeItemsStart").disableSelection();
    $(".OpponentItem").disableSelection();
    //  inventaireUser et zoneEchangeUser
    var $OpponentInventory = $("#OpponentInventory"),
        $OpponentExchange = $("#opponentExchangeItemsStart");
    //Mettre les items inventaire draggable
    //inventaire opposant
    $("li", $OpponentInventory).draggable({
        cancel: ".ui-icon", // clicking an icon won't initiate dragging
        revert: "invalid", // when not dropped, the item will revert back to its initial position
        containment: "document",
        helper: "clone",
        cursor: "pointer"
    });
    // Met zoneEchange droppable, en acceptant les items de l'inventaire
    //inventaire opposant -> echange opposant
    $OpponentExchange.droppable({
        accept: "#OpponentInventory > li",
        activeClass: "ui-state-highlight",
        drop: function (event, ui) {
            insertExchangeOpponent(ui.draggable);
        }
    });
    //Met inventaire droppable, en acceptant les items de l'zoneEchange
    //opponentExchangeItemsStart -> inventaire Opposant
    $OpponentInventory.droppable({
        accept: "#opponentExchangeItemsStart li",
        activeClass: "custom-state-active",
        drop: function (event, ui) {
            recycleOpponentItem(ui.draggable);
        }
    });
    // item mis en echangeOpposant function
    var recycle_icon = "<img/>";
    function insertExchangeOpponent( $item ) {
        $item.fadeOut(function() {
            var $list = $( "ul", $OpponentExchange).length ?
                $( "ul", $OpponentExchange ) :
                $( "<ul class='OpponentInventory ui-helper-reset'/>" ).appendTo( $OpponentExchange );
            if($item.hasClass("money")){
                $item.find( ".item-image" ).replaceWith("<div class='divInputMoney'><label class='lb-money'>$</label><input id='newTradeMoneyInput' name='newTradeMoneyInput'  class='inputMoney' style='z-index:1000;' type='number'/></div>");
                $item.append( recycle_icon ).appendTo( $list).fadeIn(function() {
                    $item
                        .animate({ width: "80px" , height: "80px"})
                        .find( ".item-image" )
                        .animate({  width: "80px", height: "78px" , maxWidth: "80px", maxHeight: "78px" });
                    $item
                        .find(".optionsExchangeItem")
                        .animate({top: "1px"});
                });
            }else{
                $item.append( recycle_icon ).appendTo( $list).fadeIn(function() {
                    $item
                        .animate({ width: "80px" , height: "80px"})
                        .find( ".item-image" )
                        .animate({  width: "80px", height: "78px" , maxWidth: "80px", maxHeight: "78px" });
                });
                $item.find("input").addClass("itemID");
            }
            $item.find( ".icon-exchange" ).addClass("icon-refresh");
            $item.find( ".icon-refresh" ).removeClass("icon-exchange");
            $item.find(".optionsInventoryItem").addClass("optionsExchangeItem");
            $item.find(".optionsExchangeItem").removeClass("optionsInventoryItem");
            checkValidTrade();
        });
    }
    // item recycle Opposant function
    var zoneEchange_icon = "<img/>";
    function recycleOpponentItem( $item ) {
        $item.fadeOut(function() {
            $item.find(".divInputMoney").replaceWith("<img class='item-image' src='images/item-dollar-sign.png' alt=''>");
            $item
                .find("input")
                .removeClass("itemID")
                .end()
                .css( "width", "130").css("height", "125px").css("max-width", "130px").css("max-height", "125px")
                .append( zoneEchange_icon )
                .find( ".item-image")
                .css( "width", "100%").css("height", "80%").css("max-width", "130px").css("max-height", "105px")
                .end()
                .appendTo( $OpponentInventory )
                .fadeIn();
            $item.find( ".icon-refresh" ).addClass("icon-exchange");
            $item.find( ".icon-exchange" ).removeClass("icon-refresh");
            $item.find(".optionsExchangeItem").addClass("optionsInventoryItem");
            $item.find(".optionsInventoryItem").removeClass("optionsExchangeItem");
            checkValidTrade();
        });
    }
    //Délégation de click et de moveover sur certains objets
    //btn Opposant
    $("ul.OpponentInventory > li").click(function (event) {
        var $item = $(this),
            $target = $(event.target);
        if ($target.is(".icon-exchange")) {
            insertExchangeOpponent($item);
        } else if ($target.is(".icon-refresh")) {
            recycleOpponentItem($item);
        }
        return false;
    });
    //un item Opposant
    $(".OpponentItem")
        .mouseover(function () {
            $("#OpponentInventory li").draggable({
                cancel: ".ui-icon", cancel: ".info-icon", cancel: ".inputMoney",  // click sur un icon d'option d'item cancel le drag
                revert: "invalid", // lorsqu'il n'est pas droppé dans un bon div, revert l'item à sa place original
                containment: "document",
                helper: "clone",
                cursor: "move"
            })
        });
    $(".OpponentItem")
        .mouseover(function () {
            $("#opponentExchangeItemsStart li").draggable({
                cancel: ".ui-icon", cancel: ".info-icon", cancel: ".inputMoney", // click sur un icon d'option d'item cancel le drag
                revert: "invalid", // lorsqu'il n'est pas droppé dans un bon div, revert l'item à sa place original
                containment: "document",
                helper: "clone",
                cursor: "move"
            })
        });
    $(".js-modal-close, .modal-overlay").click(function () {
            modalClose();
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
        if (e.keyCode == 13) {
            $("#btn-enter").click();
        }
    });
    document.getElementById('startTradeForm').onsubmit = function() {
            return false;
    }
    var modal, modalInfoItem = document.getElementsByName("modal-Item-Info");
    for(modal in modalInfoItem){
        modalInfoItem[modal].addEventListener('click', function (e) {
            modalClose();
        }, false);
        //Prevent event bubbling if click occurred within modal content body
        modalInfoItem[modal].children[0].addEventListener('click', function (e) {
            e.stopPropagation();
        }, false);
    }
    /* Width ajustable du div échange qui est horizontaly scrollable */
    $(document).ready(function () {
        var container_width = 0 * $(".container-inner").length;
        $(".container-inner").css("width", container_width + "!important");
    });
}
