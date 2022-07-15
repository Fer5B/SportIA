
$(() => {
    $(".field-error").parent(".form-field").find("input").css("border-color", "red");

    $(".toggle-password").on("click", function () {
        let input = $(this).parent().find("input");
        input.attr("type", input.attr("type") === 'password' ? 'text' : 'password');
        this.classList.toggle("bi-eye");
    })
})