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