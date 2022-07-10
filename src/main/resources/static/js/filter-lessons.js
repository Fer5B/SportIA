$(() => {
    filterLessons();
})

function filterLessons(){
    let $filterStartTime = $("#filter-start-time");
    let $filterEndTime = $("#filter-end-time");
    let $filterZone = $("#select-zone");

    $filterStartTime.data("pre", $filterStartTime.val());
    $filterEndTime.data("pre", $filterEndTime.val());

    $filterStartTime.on("change", function (e) {
        let $this = $(this);
        let endTimeValue = $filterEndTime.val();
        let flag = $filterEndTime.hasClass("border-danger");

        if($this.val() >= endTimeValue){
            $this.val($this.data("pre"));
            if(! flag) {
                $this.addClass("border-danger");
                $("#rt-error").text("La hora de inicio debe ser menor que la hora de fin").addClass("visible");
            }
        }
        else {
            if(! flag)
                $("#rt-error").removeClass("visible");
            $this.removeClass("border-danger");
            $this.data("pre", $this.val());

            let rangeTime = $this.val() + "-" +endTimeValue;

            //    Punch endpoint filter range time
            hitEndpointFilterLessons(rangeTime, $filterZone.val());
        }
    });

    $filterEndTime.on("change", function (e) {
        let $this = $(this);
        let startTimeValue = $filterStartTime.val();
        let flag = $filterStartTime.hasClass("border-danger");

        if($this.val() <= startTimeValue){
            $this.val($this.data("pre"));
            if(! flag) {
                $this.addClass("border-danger");
                $("#rt-error").text("La hora de fin debe ser mayor que la hora de inicio").addClass("visible");
            }
        }
        else {
            if(! flag)
                $("#rt-error").removeClass("visible");
            $this.removeClass("border-danger");
            $this.data("pre", $this.val());

            let rangeTime = startTimeValue + "-" + $this.val();

            hitEndpointFilterLessons(rangeTime, $filterZone.val());
        }
    });

    $filterZone.on("change", function (){
        hitEndpointFilterLessons($filterStartTime.val()+"-"+$filterEndTime.val(), $filterZone.val());
    })

    function hitEndpointFilterLessons(rangeTime, zone){

        let uri = window.location.pathname+"?range-time="+rangeTime+"&zone="+zone;
        let port = 9191;
        let url = window.location.protocol + "//" + window.location.hostname + ':' + port + uri;
        $(location).attr('href',url);
    }

}