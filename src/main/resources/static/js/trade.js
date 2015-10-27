/**
 * Created by Shaun Cooper on 2015-10-27.
 */
function makeIDList(){
    var x = document.getElementsByClassName("itemID");
    var i;
    for (i = 0; i < x.length; i++) {
        alert("id: "+x[i].value);
        document.getElementById("tradeObjectsID").value += x[i].value + ";";
    }
    //document.getElementById("startTradeForm").submit();
}
function sendChat(userName){
    if(document.getElementById('chatEnter').value.trim() != "")
    {
        var text = document.getElementById('chatEnter').value;
        document.getElementById('chatEnter').value = "";
        document.getElementById('chatBoxStartExchange').value += (userName + ": " + text + "\n");
    }
}
$( init );
function init() {
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
    var recycle_icon = "<img src='images/recycle_icon.png' class='ui-icon icon-refresh'/>";
    function insertExchangeUser( $item ) {
        $item.fadeOut(function() {
            var $list = $( "ul", $UserExchange).length ?
                $( "ul", $UserExchange ) :
                $( "<ul class='UserInventory ui-helper-reset'/>" ).appendTo( $UserExchange );
            if($item.hasClass("money")){
                $item.find( ".item-image" ).replaceWith("<div class='divInputMoney'<label class='lb-money'>$</label><input  class='inputMoney' style='z-index:1000;' type='number'/></div>");
                $item.find( ".icon-exchange" ).remove();
                $item.append( recycle_icon ).appendTo( $list).fadeIn(function() {
                    $item
                        .animate({ width: "80px" , height: "80px" })
                        .find( ".item-image" )
                        .animate({  width: "80px", height: "60px" });
                });
            }else{
                $item.find( ".icon-exchange" ).remove();
                $item.append( recycle_icon ).appendTo( $list).fadeIn(function() {
                    $item
                        .animate({ width: "80px" , height: "80px" })
                        .find( ".item-image" )
                        .animate({  width: "80px", height: "60px" ,maxWidth: "80px", maxHeight: "60px"});
                });
            }
            $item.find("input").addClass("userItemID");
        });
    }
    // item mis en echangeOpposant function
    function insertExchangeOpponent( $item ) {
        $item.fadeOut(function() {
            var $list = $( "ul", $OpponentExchange).length ?
                $( "ul", $OpponentExchange ) :
                $( "<ul class='OpponentInventory ui-helper-reset'/>" ).appendTo( $OpponentExchange );
            if($item.hasClass("money")){
                $item.find( ".item-image" ).replaceWith("<div class='divInputMoney'<label class='lb-money'>$</label><input  class='inputMoney' style='z-index:1000;' type='number'/></div>");
                $item.find( ".icon-exchange" ).remove();
                $item.append( recycle_icon ).appendTo( $list).fadeIn(function() {
                    $item
                        .animate({ width: "80px" , height: "80px" })
                        .find( ".item-image" )
                        .animate({  width: "80px", height: "60px" });
                });
            }else{
                $item.find( ".icon-exchange" ).remove();
                $item.append( recycle_icon ).appendTo( $list).fadeIn(function() {
                    $item
                        .animate({ width: "80px" , height: "80px"})
                        .find( ".item-image" )
                        .animate({  width: "80px", height: "60px" , maxWidth: "80px", maxHeight: "60px" });
                });
            }
            $item.find("input").addClass("opponentItemID");
        });
    }

    // item recycle user function
    var exchange_Icon = "<img src='images/Logo-transparent.png' class='ui-icon icon-exchange'/>";
    function recycleUserItem( $item ) {
        $item.fadeOut(function() {
            $item.find(".divInputMoney").replaceWith("<img class='item-image' src='images/item-dollar-sign.png' alt=''>");
            $item
                .find("input")
                .removeClass("userItemID")
                .end()
                .find( ".icon-refresh" )
                .remove()
                .end()
                .css( "width", "44%").css("height", "125px")
                .append( exchange_Icon )
                .find( ".item-image")
                .css( "width", "100%").css("height", "80px").css("max-width", "100%").css("max-height", "80px")
                .end()
                .appendTo( $UserInventory )
                .fadeIn();
        });
    }
    // item recycle Opposant function
    function recycleOpponentItem( $item ) {
        $item.fadeOut(function() {
            $item.find(".divInputMoney").replaceWith("<img class='item-image' src='images/item-dollar-sign.png' alt=''>");
            $item
                .find("input")
                .removeClass("opponentItemID")
                .end()
                .find( ".icon-refresh" )
                .remove()
                .end()
                .css( "width", "44%").css("height", "125px")
                .append( exchange_Icon )
                .find( ".item-image")
                .css( "width", "100%").css("height", "80px").css("max-width", "100%").css("max-height", "80px")
                .end()
                .appendTo( $OpponentInventory )
                .fadeIn();
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

    /* Open Modal Info Item */

    var appendthis =  ("<div class='modal-overlay js-modal-close'></div>");

    $('a[data-modal-id]').click(function(e) {
        e.preventDefault();
        $("body").append(appendthis);
        $(".modal-overlay").fadeTo(500, 0.7);
        $(".js-modalbox").fadeIn(500);
        var modalBox = $(this).attr('data-modal-id');
        $('#'+modalBox).fadeIn($(this).data());
    });


    $(".js-modal-close, .modal-overlay").click(function() {
        $(".modal-box, .modal-overlay").fadeOut(500, function() {
            $(".modal-overlay").remove();
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
            $(".modal-overlay").remove();
        });
    }

    // Handle ESC key (key code 27)
    document.addEventListener('keyup', function(e) {
        if (e.keyCode == 27) {
            modalClose();
        }
    });

    var modalInfoItem = document.getElementById("modal-InfoItem");

    modalInfoItem.addEventListener('click', function(e) {
        modalClose();
    }, false);

    //Prevent event bubbling if click occurred within modal content body
    modalInfoItem.children[0].addEventListener('click', function(e) {
        e.stopPropagation();
    }, false);

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
function showModalInfoItem(){
    document.querySelector( '#modal-InfoItem' ).classList.add('modal-box-show');
}
