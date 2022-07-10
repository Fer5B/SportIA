// Add SDK credentials
// REPLACE WITH PUBLIC KEY AVAILABLE IN: https://developers.mercadopago.com/panel
const mp = new MercadoPago('PUBLIC_KEY', {
    locale: 'es-AR'
});

const btn_container_class = "mp-pay";

$(() => {
// Handle call to backend and generate preference.
    $(".btn-go").on("click", function() {
        $(this).attr("disabled", true);

        var $curso_item = $(this).parents(".curso-item");

        let desc = "";
        $curso_item.find(".item-description").each(function () { desc += $(this).text()+" "});
        desc.replace(/^\s+|\s+$/g, ''); // \s -> caracter especial que coincide con cualquier espacio, tabulación o nueva línea.

        const orderData = {
            lessonId: $curso_item.data("id"),
            payer: {email: $("#username").text()},
            category: $(".type-lessons").data('type'),
            items: [
                {
                    name: $curso_item.find(".item-name").text(),
                    description: desc,
                    quantity: 1,
                    currencyId: "ARS",
                    price: $curso_item.find(".item-price").text().replace(/\./g, "").replace(",", ".")
                }
            ]
        };

        fetch("/classes/create-preference", {
            credentials: 'include',
            // mode: "cors",
            method: "POST",
            headers: {
                // "Accept": "text/html",
                "Content-Type": "application/json",
                'X-Requested-With': 'XMLHttpRequest'
            },
            body: JSON.stringify(orderData),
        })
            .then(response => {

                if(response.status == 401) {
                    return response;
                }
                else
                    return response.json();
            })
            .then(preference => {
                // mp.checkout({preference: {id: preference.id}});

                window.location.replace(preference.sandboxInitPoint);

                // var $btn_container = $curso_item.find(".btn-container");
                // $btn_container.addClass(btn_container_class);
                //
                // $(this).fadeOut(500);
                // setTimeout(() => {
                //     createCheckoutButton(preference.id, "."+btn_container_class);
                //     $(".mercadopago-button").addClass("btn-go").removeClass("mercadopago-button").hide().fadeIn();
                //     $btn_container.removeClass(btn_container_class);
                // }, 500);
            })
            .catch(e => {
                alert("Unexpected error");
                $(this).attr("disabled", false);
            });
    });
})


// Create preference when click on checkout button
function createCheckoutButton(preferenceId, btn_container_class) {
    // Initialize the checkout
    mp.checkout({
        preference: {
            id: preferenceId
        },
        render: {
            container: btn_container_class, // Indica el nombre de la clase donde se mostrará el botón de pago
            label: 'Pagar', // Modifica el texto del botón de pago (opcional)
        }
    });
}