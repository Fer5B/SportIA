package unaj.ajsw.sportia.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import unaj.ajsw.sportia.model.User;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PreferenceDTO implements Serializable {
    private String lessonId;
    private User payer;
    private List<PreferenceItem> items;
    private String category;
}
