package unaj.ajsw.sportia.service;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import org.springframework.http.ResponseEntity;
import unaj.ajsw.sportia.dto.PreferenceDTO;
import unaj.ajsw.sportia.model.Inscription;
import unaj.ajsw.sportia.model.User;

public interface MPService {
    ResponseEntity createPreference(PreferenceDTO preferenceDTO) throws MPException, MPApiException;
}
