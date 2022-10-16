$(function () {
    let colors = ["#f2fbf2", "#def6ff", "#f8f3fe", "#fff7f0", "#fff2f3"]
    $(".lw-tag-cloud a,.lw-tag-list a").each((index, element) => {
        $(element).css("background-color", randomArr(colors));
    })


    $("#lw-search-box .lw-search-close").on('click', function () {
        $("#lw-search-box").hide()
    })

    $(".lw-search-btn").on('click', function () {
        $("#lw-search-box").show()
    })

    function randomArr(arr) {
        return arr[parseInt(Math.random() * arr.length, 10)]
    }
})