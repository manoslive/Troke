$(document).ready(function () {
    $('.phone_ca').mask('(000) 000-0000');
    $('.zipcode').mask('S0S-0S0');
});
function Refresh() {
    location.reload();
}
function Closing() {
    $('#avatar').click(function () {
        $('#fileExplorer').click();
    });
    function modalClose() {
        if (location.hash == '#openModalConnexion') {
            location.hash = '';
        }
        else if (location.hash == '#openModalInscription') {
            location.hash = '';
        }
    }

    // Handle ESC key (key code 27)
    document.addEventListener('keyup', function (e) {
        if (e.keyCode == 27) {
            modalClose();
        }
    });

    //var modal = document.querySelector('#openModalConnexion');

    var modalConnexion = document.getElementById("openModalConnexion");
    var modalInscription = document.getElementById("openModalInscription");

    modalConnexion.addEventListener('click', function (e) {
        modalClose();
    }, false);
    modalInscription.addEventListener('click', function (e) {
        modalClose();
    }, false);

    //Prevent event bubbling if click occurred within modal content body
    modalConnexion.children[0].addEventListener('click', function (e) {
        e.stopPropagation();
    }, false);
    modalInscription.children[0].addEventListener('click', function (e) {
        e.stopPropagation();
    }, false);
    modalInscription.children[1].addEventListener('click', function (e) {
        e.stopPropagation();
    }, false);
}
function generateCaptcha() {
    var sym = Math.floor((Math.random() * 2) + 1);
    if (sym === 1) {
        sym = "+";
    } else {
        sym = "-";
    }
    document.getElementById("num1").value = Math.floor((Math.random() * 50) + 10);
    document.getElementById("num2").value = Math.floor((Math.random() * 10) + 1);
    document.getElementById("sym").value = sym;
}
function checkCaptcha() {
    var num1 = document.getElementById("num1").value;
    var num2 = document.getElementById("num2").value;
    var sym = document.getElementById("sym").value;
    var resultat;
    if (sym === "+") {
        resultat = parseInt(num1) + parseInt(num2);
    } else {
        resultat = parseInt(num1) - parseInt(num2);
    }
    if (parseInt(document.getElementById("captcha").value) === resultat) {
        document.forms["inscriptionForm"].submit();
    } else {
        alert("Captcha est pas bon!");
        document.getElementById("captcha").value = "";
        generateCaptcha();
    }
}
function Flip() {
    var email = document.getElementById('email').value;  //////////////////////////////////////////icitteee
    var email_confirm = document.getElementById('email_confirm').value;
    var pass = document.getElementById('pass').value;
    var pass_confirm = document.getElementById('pass_confirm').value;

    if (String(email) != '') {
        if (String(pass) != '') {
            if (String(email) == String(email_confirm)) {
                if (String(pass) == String(pass_confirm)) {
                    document.querySelector('#openModalInscription').classList.toggle('flip');
                    ResetHighlight();
                    generateCaptcha();
                }
                else {
                    document.querySelector('#pass_confirm').classList.add('highlightTextBox');
                }
            }
            else {
                document.querySelector('#email_confirm').classList.add('highlightTextBox');
            }
        }
        else {
            document.querySelector('#pass').classList.add('highlightTextBox');
        }
    }
    else {
        document.querySelector('#email').classList.add('highlightTextBox');
    }
}
function ResetHighlight() {
    document.querySelector('#email_confirm').classList.remove('highlightTextBox');
    document.querySelector('#email').classList.remove('highlightTextBox');
    document.querySelector('#pass').classList.remove('highlightTextBox');
    document.querySelector('#pass_confirm').classList.remove('highlightTextBox');
}
function HighlightEmail() {
    document.querySelector('#email_confirm').classList.add('highlightTextBox');
    document.querySelector('#email').classList.add('highlightTextBox');
}
function ConfirmEmail() {
    var emailConfirm = document.getElementById('email').value;
    document.getElementById('confirmEmail').value = emailConfirm;
}
function checkEmail() {
    document.querySelector('#email').classList.remove('highlightTextBox');   ////////////////////icittee
}
function checkConfirmEmail() {
    document.querySelector('#email_confirm').classList.remove('highlightTextBox');  ////////////////////icittteee
}
function PreviewImage(value) {
    var imgValue = value.slice(value.lastIndexOf('\\'), value.length);
    document.getElementById("avatar").style.background = "url(uploaded-images/" + imgValue + ")no-repeat center";
}
function SubmitImage(){
    $('#imageform').submit();
}
function openMesEchanges() {
    document.getElementById("content").innerHTML = '<object class="loadContent" type="text/html" data="myTrades.html"></object>';
}
function forgottenPasswordForm(){
    var data = $( "input[name=iduser]" ).val();
    $.post( "/resetpassword", {iduser:data});
}
function logoutButtonMouseenter() {
    $("#signup").text("Deconnexion");
}
function logoutButtonMouseLeave(oldValue){
    $("#signup").text('Bienvenue ' + oldValue);
}


