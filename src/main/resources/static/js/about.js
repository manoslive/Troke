/**
 * Created by Ysteinthal on 2015-10-16.
 */
$(document).ready(function() {

    var fontSize = 12;//$(window).width()/120;
    $('#InfoH').css('font-size', fontSize);


    $(window).resize(function() {
        var size = $('#InfoH').css('font-size').split('px')[0];

        //alert(size);

        if((size > 8 )&&(size <= 12))
        {

            var fontSize = $(window).width() / 120;
            if(fontSize <= 12 && fontSize >= 8) {
                $('#InfoH').css('font-size', fontSize);
            }

        }
    });

});

function ImageClick(lettre)
{
    var Shaun_image  = document.getElementById("ImgShaun");
    var Shaun_label = document.getElementById("InfoShaun");
    var Manu_image  = document.getElementById("ImgManu");
    var Manu_label = document.getElementById("InfoManu");
    var Alex_image  = document.getElementById("ImgAlex");
    var Alex_label = document.getElementById("InfoAlex");
    var Yvon_image  = document.getElementById("ImgYvon");
    var Yvon_label = document.getElementById("InfoYvon");

    if(lettre === "A")
    {
        Alex_image.className= Alex_image.className + " circularimageclick";
        Alex_label.style.visibility = "visible";

        Alex_label.className = "animated fadeInDown";


        Yvon_image.className = "circularimage";
        Manu_image.className = "circularimage";
        Shaun_image.className = "circularimage";


        Manu_label.className = "animated fadeOut";
        Shaun_label.className = "animated fadeOut";
        Yvon_label.className = "animated fadeOut";
    }
    else if(lettre ==="Y")
    {
        Yvon_image.className= Yvon_image.className + " circularimageclick";
        Yvon_label.style.visibility = "visible";

        Yvon_label.className ="animated fadeInDown";


        Alex_image.className = "circularimage";
        Manu_image.className = "circularimage";
        Shaun_image.className = "circularimage";


        Manu_label.className ="animated fadeOut";
        Shaun_label.className ="animated fadeOut";
        Alex_label.className ="animated fadeOut";
    }
    else if (lettre === "S")
    {
        Shaun_image.className= Shaun_image.className + " circularimageclick";
        Shaun_label.style.visibility = "visible";

        Shaun_label.className = "animated fadeInDown";

        Yvon_image.className = "circularimage";
        Alex_image.className = "circularimage";
        Manu_image.className = "circularimage";

        Alex_label.className ="animated fadeOut";
        Yvon_label.className ="animated fadeOut";
        Manu_label.className ="animated fadeOut";



    }
    else if (lettre === "M")
    {
        Manu_image.className= Manu_image.className + " circularimageclick";
        Manu_label.style.visibility = "visible";

        Manu_label.className = "animated fadeInDown";

        Yvon_image.className = "circularimage";
        Alex_image.className = "circularimage";
        Shaun_image.className = "circularimage";

        Alex_label.className ="animated fadeOut";
        Yvon_label.className ="animated fadeOut";
        Shaun_label.className ="animated fadeOut";

    }


}