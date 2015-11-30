/**
 * Created by Shaun Cooper on 2015-10-27.
 */
var bFirstReceive = false;
function makeIDList(){
    document.getElementById("tradeState").value = "F";
    var x = document.getElementsByClassName("itemID");
    var i;
    for (i = 0; i < x.length; i++) {
        document.getElementById("tradeObjectsID").value += x[i].value + ";";
    }
    makeMoneyObjects();
    document.getElementById("tradeForm").submit();
}
//Transmet les valeurs d'argent à des inputs pour post au controlleur
function makeMoneyObjects(){
    if(document.getElementById("userMoneyInput") !== null) {
        if(document.getElementById("userMoneyInput").value > 0) {
            document.getElementById("userMoneyValue").value = document.getElementById("userMoneyInput").value;
        }else{
            document.getElementById("userMoneyValue").value = 0;
        }
    }else {
        document.getElementById("userMoneyValue").value = 0;
    }
    if(document.getElementById("opponentMoneyInput") !== null) {
        if(document.getElementById("opponentMoneyInput").value > 0) {
            document.getElementById("opponentMoneyValue").value = document.getElementById("opponentMoneyInput").value;
        }else{
            document.getElementById("opponentMoneyValue").value = 0;
        }
    }else {
        document.getElementById("opponentMoneyValue").value = 0;
    }
}
//Hover des petites images du modal InfoItem
function hover(element) {
    $('.mainImage').attr('src', element.getAttribute('src'));
}
function CompleteTransaction(){
    document.getElementById("tradeState").value = "T";
    var x = document.getElementsByClassName("itemID");
    var i;
    for (i = 0; i < x.length; i++) {
        document.getElementById("tradeObjectsID").value += x[i].value + ";";
    }
    document.getElementById("tradeForm").submit();
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
function sendChat(userName){
    if(document.getElementById('chatEnter').value.trim() != "")
    {
        var text = document.getElementById('chatEnter').value;
        document.getElementById('chatEnter').value = "";
        document.getElementById('chatBoxStartExchange').value += (userName + ": " + text + "\n");
    }
}
//Function qui vérifi si le trade est valid
function checkValidTrade(){
    var isValid = false;
    if ($('#userExchangeItems ul li').length < 1) {
        isValid = false;
    } else {
        isValid = true;
    }
    if ($('#opponentExchangeItems ul li').length < 1) {
        isValid = false;
    } else {
        if (!isValid)
            isValid = false;
        else
            isValid = true;
    }
    if (isValid) {
        document.getElementById("btn-send-trade").style.pointerEvents = "auto";
        document.getElementById("btn-send-trade").innerText = "Envoyer la \n contre-offre!";
        $('#btn-send-trade').addClass('btn-success');
        $('#btn-send-trade').removeClass('btn-warning');
    }
    else {
        document.getElementById("btn-send-trade").style.pointerEvents = "none";
        document.getElementById("btn-send-trade").innerText = "Sélectionnez un item \n pour l'échange";
        $('#btn-send-trade').addClass('btn-warning');
        $('#btn-send-trade').removeClass('btn-success');
    }

    //Il y a eu changement de l'offre donc l'offre ne peut pas etre accepté
    document.getElementById("btn-accept-trade").innerText = "Réinitialiser l'offre";
    $('#btn-accept-trade').addClass('btn-danger');
    $('#btn-accept-trade').removeClass('btn-success');
    changeAcceptButton();

}
function changeAcceptButton(){
    document.getElementById('btn-accept-trade').onclick = function () { location.reload() };
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
function resize(){
    checkAdjustForScrollBar($("#UserInventory"));
    checkAdjustForScrollBar($("#OpponentInventory"));
    if($(window).width() < 480)
    {
        //Mobile
        $(".OpponentInventoryContainer").insertAfter(".UserInventoryContainer");
    }
    else if($(window).width() <= 1006) //Glitch dans le matrix , 1006 affect quand lecran est a 1024
    {
        //Tablet
        $(".OpponentInventoryContainer").insertAfter(".UserInventoryContainer");
    }
    else
    {
        //Desktop
        $(".OpponentInventoryContainer").insertAfter("#exchangeZone");
    }
}
$(window).resize(function() {
    resize();
});
$( init );
resize();
function init() {
    checkAdjustForScrollBar($("#UserInventory"));
    checkAdjustForScrollBar($("#OpponentInventory"));
    $("#UserInventory").bind("DOMSubtreeModified", function() {
        checkAdjustForScrollBar($("#UserInventory"));
    });
    $("#OpponentInventory").bind("DOMSubtreeModified", function() {
        checkAdjustForScrollBar($("#OpponentInventory"));
    });

    //Vérifier si c'est la première fois qu'un user reçoit un trade pour enlever le bouton d'accepter l'offre
    document.getElementById("btn-send-trade").style.pointerEvents = "none";
    document.getElementById("btn-send-trade").innerText = "Sélectionnez un item \n pour l'échange"
    if($('#userExchangeItems ul li').length >= 1) {
        bFirstReceive = true;
    }
    if($('#opponentExchangeItems ul li').length < 1) {
        bFirstReceive = true;
    }else{
        bFirstReceive = false;
    }
    if(bFirstReceive){
        //C'est la premiere fois qu'on reçoit une offre
        document.getElementById("btn-accept-trade").innerText = "Réinitialiser l'offre";
        $('#btn-accept-trade').addClass('btn-danger');
        $('#btn-accept-trade').removeClass('btn-success');
        changeAcceptButton();
    }

    // mettre les items non selectable (selection de text, highlight bleu ...)
    $( "#UserInventory" ).disableSelection();
    $( "#UserExchange" ).disableSelection();
    $( "#OpponentInventory" ).disableSelection();
    $( "#OpponentExchange" ).disableSelection();
    $( ".UserItem" ).disableSelection();
    $( ".OpponentItem" ).disableSelection();
    //  UserInventory et UserExchange
    var $UserInventory = $( "#UserInventory" ),
        $UserExchange = $( "#userExchangeItems" ),
        $OpponentInventory = $( "#OpponentInventory" ),
        $OpponentExchange = $( "#opponentExchangeItems" );

    //Mettre les items inventaire draggable
    //inventaire user
    $( "li", $UserInventory ).draggable({
        cancel: ".ui-icon", // clicking an icon won't initiate dragging
        revert: "invalid", // when not dropped, the item will revert back to its initial position
        containment: "document",
        helper: "clone",
        cursor: "pointer",
    });
    //inventaire opposant
    $( "li", $OpponentInventory ).draggable({
        cancel: ".ui-icon", // clicking an icon won't initiate dragging
        revert: "invalid", // when not dropped, the item will revert back to its initial position
        containment: "document",
        helper: "clone",
        cursor: "pointer"
    });

    // Met zoneEchange droppable, en acceptant les items de l'inventaire
    //inventaire user -> echange user
    $UserExchange.droppable({
        accept: "#UserInventory > li",
        activeClass: "ui-state-highlight",
        drop: function( event, ui ) {
            insertExchangeUser( ui.draggable );
        }
    });
    //inventaire opposant -> echange opposant
    $OpponentExchange.droppable({
        accept: "#OpponentInventory > li",
        activeClass: "ui-state-highlight",
        drop: function( event, ui ) {
            insertExchangeOpponent( ui.draggable );
        }
    });

    //Met inventaire droppable, en acceptant les items de l'zoneEchange
    //UserExchange -> inventaire User
    $UserInventory.droppable({
        accept: "#UserExchange li",
        activeClass: "custom-state-active",
        drop: function( event, ui ) {
            recycleUserItem( ui.draggable );
        }
    });
    //OpponentExchange -> inventaire Opposant
    $OpponentInventory.droppable({
        accept: "#OpponentExchange li",
        activeClass: "custom-state-active",
        drop: function( event, ui ) {
            recycleOpponentItem( ui.draggable );
        }
    });

    // item mis en echangeUser function
    var recycle_icon = "<img/>";
    function insertExchangeUser( $item ) {
        $item.fadeOut(function() {
            var $list = $( "ul", $UserExchange).length ?
                $( "ul", $UserExchange ) :
                $( "<ul class='UserInventory ui-helper-reset'/>" ).appendTo( $UserExchange );
            if($item.hasClass("money")){
                $item.find( ".item-image" ).replaceWith("<div class='divInputMoney'><label class='lb-money'>$</label><input id='userMoneyInput' name='userMoneyInput'  class='inputMoney' style='z-index:1000;' type='number'/></div>");
                $item.append( recycle_icon ).appendTo( $list).fadeIn(function() {
                    $item
                        .animate({ width: "80px" , height: "80px" })
                        .find( ".item-image" )
                        .animate({  width: "80px", height: "78px" , maxWidth: "80px", maxHeight: "78px" });
                    $item
                        .find(".optionsExchangeItem")
                        .animate({top: "1px"});
                });
            }else{
                $item.append( recycle_icon ).appendTo( $list).fadeIn(function() {
                    $item
                        .animate({ width: "80px" , height: "80px" })
                        .find( ".item-image" )
                        .animate({  width: "80px", height: "78px" ,maxWidth: "80px", maxHeight: "78px"});
                });
                $item.find("input").addClass("itemID");
            }
            $item.find( ".icon-exchange" ).addClass("icon-refresh");
            $item.find( ".icon-refresh" ).removeClass("icon-exchange");
            $item.find(".icon-refresh").attr('title', "Enlever l'item/service du troc");
            $item.find(".optionsInventoryItem").addClass("optionsExchangeItem");
            $item.find(".optionsExchangeItem").removeClass("optionsInventoryItem");
            $item.find(".optionsInventoryMoney").addClass("optionsExchangeMoney");
            $item.find(".optionsExchangeMoney").removeClass("optionsInventoryMoney");
            checkValidTrade();
        });
    }
    // item mis en echangeOpposant function
    function insertExchangeOpponent( $item ) {
        $item.fadeOut(function() {
            var $list = $( "ul", $OpponentExchange).length ?
                $( "ul", $OpponentExchange ) :
                $( "<ul class='OpponentInventory ui-helper-reset'/>" ).appendTo( $OpponentExchange );
            if($item.hasClass("money")){
                $item.find( ".item-image" ).replaceWith("<div class='divInputMoney'><label class='lb-money'>$</label><input id='opponentMoneyInput' name='opponentMoneyInput' class='inputMoney' style='z-index:1000;' type='number'/></div>");
                $item.append( recycle_icon ).appendTo( $list).fadeIn(function() {
                    $item
                        .animate({ width: "80px" , height: "80px" })
                        .find( ".item-image" )
                        .animate({  width: "80px", height: "78px" , maxWidth: "80px", maxHeight: "78px"});
                    $item
                        .find(".optionsExchangeItem")
                        .animate({top: "1px"});
                });
            }else{
                $item.append( recycle_icon ).appendTo( $list).fadeIn(function() {
                    $item
                        .animate({ width: "80px" , height: "80px" })
                        .find( ".item-image" )
                        .animate({  width: "80px", height: "78px" ,maxWidth: "80px", maxHeight: "78px"});
                });
                $item.find("input").addClass("itemID");
            }
            $item.find( ".icon-exchange" ).addClass("icon-refresh");
            $item.find( ".icon-refresh" ).removeClass("icon-exchange");
            $item.find(".icon-refresh").attr('title', "Enlever l'item/service du troc");
            $item.find(".optionsInventoryItem").addClass("optionsExchangeItem");
            $item.find(".optionsExchangeItem").removeClass("optionsInventoryItem");
            $item.find(".optionsInventoryMoney").addClass("optionsExchangeMoney");
            $item.find(".optionsExchangeMoney").removeClass("optionsInventoryMoney");
            checkValidTrade();
        });
    }

    // item recycle user function
    var exchange_Icon = "<img/>";
    function recycleUserItem( $item ) {
        $item.fadeOut(function() {
            $item.find(".divInputMoney").replaceWith("<img class='item-image' src='images/item-dollar-sign.png' alt=''/>");
            $item
                .find("input")
                .removeClass("itemID")
                .end()
                .css( "width", "130px").css("height", "125px").css("max-width", "130px").css("max-height", "125px")
                .append( exchange_Icon )
                .find( ".item-image")
                .css( "width", "100%").css("height", "80%").css("max-width", "130px").css("max-height", "105px")
                .end()
                .appendTo( $UserInventory )
                .fadeIn();
            $item.find( ".icon-refresh" ).addClass("icon-exchange");
            $item.find( ".icon-exchange" ).removeClass("icon-refresh");
            $item.find(".icon-exchange").attr('title', "Ajouter l'item/service au troc");
            $item.find(".optionsExchangeItem").addClass("optionsInventoryItem");
            $item.find(".optionsInventoryItem").removeClass("optionsExchangeItem");
            $item.find(".optionsExchangeMoney").addClass("optionsInventoryMoney");
            $item.find(".optionsInventoryMoney").removeClass("optionsExchangeMoney");
            checkValidTrade();
        });
    }
    // item recycle Opposant function
    function recycleOpponentItem( $item ) {
        $item.fadeOut(function() {
            $item.find(".divInputMoney").replaceWith("<img class='item-image' src='images/item-dollar-sign.png' alt=''/>");
            $item
                .find("input")
                .removeClass("itemID")
                .end()
                .css( "width", "130px").css("height", "125px").css("max-width", "130px").css("max-height", "125px")
                .append( exchange_Icon )
                .find( ".item-image")
                .css( "width", "100%").css("height", "80%").css("max-width", "130px").css("max-height", "105px")
                .end()
                .appendTo( $OpponentInventory )
                .fadeIn();
            $item.find( ".icon-refresh" ).addClass("icon-exchange");
            $item.find( ".icon-exchange" ).removeClass("icon-refresh");
            $item.find(".icon-exchange").attr('title', "Ajouter l'item/service au troc");
            $item.find(".optionsExchangeItem").addClass("optionsInventoryItem");
            $item.find(".optionsInventoryItem").removeClass("optionsExchangeItem");
            $item.find(".optionsExchangeMoney").addClass("optionsInventoryMoney");
            $item.find(".optionsInventoryMoney").removeClass("optionsExchangeMoney");
            checkValidTrade();
        });
    }

    //Délégation de click et de moveover sur certains objets
    //btn User
    $( "ul.UserInventory > li" ).click(function( event ) {
        var $item = $( this ),
            $target = $( event.target );

        if ( $target.is( ".icon-exchange" ) ) {
            insertExchangeUser( $item );
        } else if ( $target.is( ".icon-refresh" ) ) {
            recycleUserItem( $item );
        }

        return false;
    });
    //btn Opposant
    $( "ul.OpponentInventory > li" ).click(function( event ) {
        var $item = $( this ),
            $target = $( event.target );

        if ( $target.is( ".icon-exchange" ) ) {
            insertExchangeOpponent( $item );
        } else if ( $target.is( ".icon-refresh" ) ) {
            recycleOpponentItem( $item );
        }
        return false;
    });
    //un item User
    $( ".UserItem" )
        .mouseover(function() {
            $("#UserInventory li").draggable({
                cancel: ".ui-icon", cancel:".info-icon", cancel:".inputMoney",  // click sur un icon d'option d'item cancel le drag
                revert: "invalid", // lorsqu'il n'est pas droppé dans un bon div, revert l'item à sa place original
                containment: "document",
                helper: "clone",
                cursor: "move"
            })
        });
    $( ".UserItem" )
        .mouseover(function() {
            $("#UserExchange li").draggable({
                cancel: ".ui-icon", cancel:".info-icon", cancel:".inputMoney", // click sur un icon d'option d'item cancel le drag
                revert: "invalid", // lorsqu'il n'est pas droppé dans un bon div, revert l'item à sa place original
                containment: "document",
                helper: "clone",
                cursor: "move"
            })
        });
    //un item Opposant
    $( ".OpponentItem" )
        .mouseover(function() {
            $("#OpponentInventory li").draggable({
                cancel: ".ui-icon", cancel:".info-icon", cancel:".inputMoney",  // click sur un icon d'option d'item cancel le drag
                revert: "invalid", // lorsqu'il n'est pas droppé dans un bon div, revert l'item à sa place original
                containment: "document",
                helper: "clone",
                cursor: "move"
            })
        });
    $( ".OpponentItem" )
        .mouseover(function() {
            $("#OpponentExchange li").draggable({
                cancel: ".ui-icon", cancel:".info-icon", cancel:".inputMoney", // click sur un icon d'option d'item cancel le drag
                revert: "invalid", // lorsqu'il n'est pas droppé dans un bon div, revert l'item à sa place original
                containment: "document",
                helper: "clone",
                cursor: "move"
            })
        });

    $(".js-modal-close, .modal-overlay").click(function() {
        $(".modal-box, .modal-overlay").fadeOut(500, function() {
            $(".modal-box, .modal-overlay").removeClass("modal-box-show");
        });
    });

    $(window).resize(function() {
        $(".modal-box").css({
            top: ($(window).height() - $(".modal-box").outerHeight()) / 2,
            left: ($(window).width() - $(".modal-box").outerWidth()) / 2
        });
    });
    $(window).resize();

    /* modal close et click exterieure du modal */
    function modalClose() {
        $(".modal-box, .modal-overlay").fadeOut(500, function() {
            $(".modal-box, .modal-overlay").removeClass("modal-box-show");
        });
    }

    // Handle ESC key (key code 27)
    document.addEventListener('keyup', function(e) {
        if (e.keyCode == 27) {
            modalClose();
        }
        if (e.keyCode == 13) {
            $("#btn-enter").click();
        }
    });
    document.getElementById('tradeForm').onsubmit = function() {
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

    /* Delegate Scroll images InfoItem */
    $('#divSubImages').delegate('img','click', function(){
        $('#mainImage').attr('src',$(this).attr('src').replace('sub','main'));
    });
    $('#divSubImages').delegate('img','hover', function(){
        $('#mainImage').attr('src',$(this).attr('src').replace('sub','main'));
    });

    /* Width ajustable du div échange qui est horizontaly scrollable */
    $(document).ready(function() {
        var container_width = 0 * $(".container-inner").length;
        $(".container-inner").css("width", container_width+"!important");
    });
}

