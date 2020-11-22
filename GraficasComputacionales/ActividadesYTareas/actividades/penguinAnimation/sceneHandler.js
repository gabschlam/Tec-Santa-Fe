function initControls()
{
    $("#durationSlider").slider({min: 2, max: 10, value: duration, step: 0.1, animate: false});
    $("#durationSlider").on("slide", (e, u) =>{
            duration = u.value;
            $("#durationValue").html(duration + "s");				
        });
    $("#durationSlider").on("slidechange", (e, u) => {
            duration = u.value;
            playAnimations();
    });		
}