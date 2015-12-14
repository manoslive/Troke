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



//GEOLOCALISATION
//$(window).scroll(function(){
//    if ($(window).scrollTop() > 140) {
//        var scroll = $(this).scrollTop()-140;
//        $('#MapSnippet').css('top', scroll);
//        console.log($(this).scrollTop());
//    }
//
//
//});


function closegoogle()
{
    document.getElementById('MapSnippet').setAttribute("style", "display:none");
}

function SendCoor(event,add)
{

    var offset = $('#searchResults').offset();
    var bobby = event.pageY - offset.top;
    var bobby2 = event.pageX - offset.left;

    var div = document.getElementById('MapSnippet');

    div.style.top=bobby;
    div.style.left=bobby2 + 10;
    div.style.display = "";

    document.getElementById('address').value=add;
    document.getElementById('submit').click();
    google.maps.event.trigger(map, 'resize');
    map.setZoom( map.getZoom() );

}


function initMap() {
    var map = new google.maps.Map(document.getElementById('map'), {
        zoom: 12,
        center: {lat: -34.397, lng: 150.644}
    });
    var geocoder = new google.maps.Geocoder();

    document.getElementById('submit').addEventListener('click', function() {
        geocodeAddress(geocoder, map);
    });

    google.maps.event.trigger(map, 'resize');
    map.setZoom( map.getZoom() );

}

function geocodeAddress(geocoder, resultsMap) {
    var address = document.getElementById('address').value;
    geocoder.geocode({'address': address}, function(results, status) {
        if (status === google.maps.GeocoderStatus.OK) {
            resultsMap.setCenter(results[0].geometry.location);
            var marker = new google.maps.Marker({
                map: resultsMap,
                position: results[0].geometry.location

            });

        } else {
            alert('Geocode was not successful for the following reason: ' + status);
        }

        google.maps.event.trigger(map, 'resize');
        map.setZoom( map.getZoom() );
    });
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

/*    var modal, modalInfoItem = document.getElementsByName("modal-Item-Info");
    for (modal in modalInfoItem) {
        modalInfoItem[modal].addEventListener('click', function (e) {
            modalClose();
        }, false);
        //Prevent event bubbling if click occurred within modal content body
        modalInfoItem[modal].children[0].addEventListener('click', function (e) {
         e.stopPropagation();
         }, false);
    }
*/
    /* Delegate Scroll images InfoItem */
    $('#divSubImages').delegate('img', 'click', function () {
        $('#mainImage').attr('src', $(this).attr('src').replace('sub', 'main'));
    });
    $('#divSubImages').delegate('img', 'hover', function () {
        $('#mainImage').attr('src', $(this).attr('src').replace('sub', 'main'));
    });
}