/**
 * Created by Alex on 2015-11-04.
 */
$(document).ready(function(){
    $('#RectangleName').width($('#ProfilBanner').width()+30);
    $('#RectangleName2').width($('#InventoryBanner').width()+30);
});

$(document).ready(function(){
    $('[data-toggle="tooltip"]').tooltip();

});

$(window).resize(function()
{
    $('#RectangleName').width($('#ProfilBanner').width()+30);
    $('#RectangleName2').width($('#InventoryBanner').width()+30);
    //$('#UserName').css('margin-left','40%');
    //$('#ProfilButtons').css('margin-left','40%');

    if($(window).width() < 993)
    {
        $('#MiddleInfo').height(1100);
        $('#ProfilInfo').height(400);
        $('#PhotoProfil').css('margin-top', '100px');
        $('#ProfilEmail').css('margin-top', '60px');
    }

    if($(window).width() > 993)
    {
        $('#MiddleInfo').height(750);
        $('#ProfilInfo').height(35+'%');
        $('#PhotoProfil').css('margin-top', '5px');
        $('#ProfilEmail').css('margin-top', '0px');
    }

    //var tah = document.getElementById("DescTextArea").clientHeight;
    //var mdin = document.getElementById("MiddleInfo").clientHeight;
    //document.getElementById("MiddleInfo").style.height = (tah+mdin)+"px";
})
;
//$(document).ready(function(){
//
//    //var area, textareas = document.getElementsByClassName('.DescTextArea');
//    //
//    //for(area in textareas)
//    //{
//    //    textareas[area].height = textareas[area].scrollHeight;
//    //}
//    //$('.DescTextArea').height( $('.DescTextArea')[0].scrollHeight );
//    //var tah = document.getElementById("DescTextArea").clientHeight;
//
//
//
//});

//$(document).ready(function(){
//   // $("#Innerdiv").height( 800 );
//   // var doh = document.getElementById("Innerdiv").height;
//
//    //alert(doh);
//
//
//
//});

//$(init);
//function init()
//{
//    //var area, textareas = document.getElementsByClassName('.DescTextArea');
//    //
//    //for(area in textareas)
//    //{
//    //    textareas[area].height = textareas[area].scrollHeight;
//    //    alert(textareas[area].height);
//    //}
//
//
//
//
//}

//function initializeTextArea()
//{
//    var $hgt = $('.active').find('.DescTextArea');
//    alert($hgt.prop('scrollHeight'));
//    $hgt.height($hgt.prop('scrollHeight'));
//    //alert($hgt.scrollHeight);
//    //$('.active').find('.DescTextArea').height($('.DescTextArea')[0].scrollHeight );
//    //$('.DescTextArea').height( $('.DescTextArea')[0].scrollHeight );
//}

function readURL(idFileExplorer, idAvatarProfil) {
    var file = document.getElementById(idFileExplorer).files[0];
    var reader = new FileReader();
    reader.onloadend = function () {
        document.getElementById(idAvatarProfil).style.backgroundImage = "url(" + reader.result + ")";
    }
    if (file) {
        reader.readAsDataURL(file);
    } else {
    }
}
function checkImage(idFileExplorer, idAvatarProfil) {
    var x = document.getElementById(idFileExplorer); // get the file input element in your form
    var f = x.files.item(0); // get only the first file from the list of files
    var filesize = f.size;
    // Si l'image est plus petite que 10mb
    if (filesize < 10485760) {
        document.getElementById('errorDiv').innerHTML = '';
        readURL(idFileExplorer, idAvatarProfil);
    }
    else {
        document.getElementById('errorDiv').innerHTML = '<span style="color:red" id="image-error">Choisissez une image de moins de 10mb</span>';
    }
}
function TestFileType(idFileExplorer, idAvatarProfil) {
    var fileName = document.getElementById(idFileExplorer).value;
    var fileTypes = /(.jpg|.jpeg|.gif|.png)$/i;
    var extensionNames = ".jpg / .jpeg / .gif / .png";
    if (fileName != '') {
        if (fileTypes.test(fileName)) {
            document.getElementById('errorDiv').innerHTML = '';
            checkImage(idFileExplorer, idAvatarProfil); // Vérification de la taille de l'image

        }
        else {
            //document.getElementById('avatar').style.backgroundImage = "/uploaded-images/no_avatar.jpg";
            document.getElementById('errorDiv').innerHTML = '<span style="color:red" id="image-error">Doit être une image du type ' + extensionNames + ' </span>';
        }
    }
    else {
        //document.getElementById('avatar').style.backgroundImage = "/uploaded-images/no_avatar.jpg";
        document.getElementById('errorDiv').innerHTML = '<span style="color:red" id="image-error">Le nom d\'image est vide</span>';
    }
}

function formsubmit()
{
    $('#formUser').submit();
}
function deleteSubmit()
{
    $('#formDelete').submit();
}

function modalSubmitDelete(id)
{
    document.getElementById('idObjectDelete').value = id;
    $('#deleter').submit();

}

function modalSubmitModifier(id)
{
    document.getElementById('idObjectModifier').value = id;
    $('#modifier').submit();
}

function modalSubmitAjouter()
{

    $('#ajouter').submit();
}
$(document).ready(function () {
    $('.phone_ca').mask('(000) 000-0000');
    $('.zipcode').mask('S0S-0S0');
});

