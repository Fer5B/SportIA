package unaj.ajsw.sportia.service;

// SDK Mercado Pago
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.common.AddressRequest;
import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.common.PhoneRequest;
import com.mercadopago.client.preference.*;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import unaj.ajsw.sportia.dto.PreferenceDTO;
import unaj.ajsw.sportia.model.Location;

import java.util.ArrayList;
import java.util.stream.Collectors;


@Service("mpService")
public class CheckoutProMPService implements MPService {
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public CheckoutProMPService(){
    }

    /**
     * <h1>Sandbox de Pago: Creación de preferencia</h1>
     * Método encargado de crear la preferencia de la API de Mercado Pago (Checkout Pro) a partir de la preferencia
     * definida especificamente por el sistema para la venta de sus servicios y según la elección
     * de estos para la compra por parte del usuario.
     * <p>En la preferencia creada por Checkout Pro se encuentra el link por medio del cuál se redirigirá
     * al usuario hacia Mercado Pago para que continue allí el proceso de compra.</p>
     * @param preferenceDTO Objeto de Preferencia definido por el sistema para contener la elección de compra del usuario.
     * */
    public ResponseEntity createPreference(PreferenceDTO preferenceDTO) throws MPException, MPApiException {
        if (preferenceDTO.getItems().isEmpty()) {
            return ResponseEntity.badRequest().body("No hay items");
        }

        addAccessToken();
        PreferenceClient preferenceClient = new PreferenceClient();
        String notifyBaseUrl = "localhost:9191/classes/" + preferenceDTO.getCategory();
        String sussessUrl = notifyBaseUrl + "/successful-payment";
        String pendingUrl = notifyBaseUrl + "/pending-payment";
        String failureUrl = notifyBaseUrl + "/failed-payment";

        PreferenceRequest request = PreferenceRequest.builder()
//                Add payer
                .payer(PreferencePayerRequest.builder()
                        .name(preferenceDTO.getPayer().getName())
                        .identification(IdentificationRequest.builder()
                                .type("DNI")
                                .number(String.valueOf(preferenceDTO.getPayer().getDni()))
                                .build())
                        .surname(preferenceDTO.getPayer().getLastName())
                        .email(preferenceDTO.getPayer().getEmail())
                        .phone(PhoneRequest.builder().number(preferenceDTO.getPayer().getPhone()).build())
                        .address(getPayerAddress(preferenceDTO.getPayer().getLocation()))
                        .build())
//                Add items
                .items(preferenceDTO.getItems().stream().map(i -> {
                    PreferenceItemRequest item = PreferenceItemRequest.builder()
                    .title(i.getName())
                    .description(i.getDescription())
                    .quantity(i.getQuantity())
                    .currencyId(i.getCurrencyId())
                    .unitPrice(i.getPrice())
                    .build();
                    return item;
                }).collect(Collectors.toCollection(ArrayList::new)))
//                Redirigir al final del proceso de pago a nuestro sitio
                .backUrls(PreferenceBackUrlsRequest.builder()
                        .success(sussessUrl)
                        .pending(pendingUrl)
                        .failure(failureUrl)
                        .build())
                .autoReturn("approved")
                .build();

        if(request == null){
            return ResponseEntity.badRequest().body("Preference could not be created.");
        }

        String preference = gson.toJson(preferenceClient.create(request));

        return ResponseEntity.ok(preference);
    }

    private AddressRequest getPayerAddress(Location location) {
        if(location != null)
            return AddressRequest.builder()
                        .streetName(location.getStreet())
                        .streetNumber(String.valueOf(location.getNumber()))
                        .zipCode(location.getZipCode())
                        .build();

        return null;
    }


    private void addAccessToken(){
        MercadoPagoConfig.setAccessToken("ACCESS_TOKEN");
    }

}
