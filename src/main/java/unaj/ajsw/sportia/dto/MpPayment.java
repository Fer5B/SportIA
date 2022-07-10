package unaj.ajsw.sportia.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MpPayment {
    private String payment_id; // ID del pago de Mercado Pago.
    private String status; // Estado de pago. Ej.: approved -> pago aprobado o pending -> pago pendiente.
    private String external_reference; // Valor enviado al crear la preferencia de pago.
    private String payment_type; // Método de pago
    private String merchant_order_id; // ID de la orden de pago generada en Mercado Pago.
    private String preference_id;
}
