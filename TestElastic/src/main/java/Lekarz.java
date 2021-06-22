import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Lekarz implements Serializable {
    private String id;
    private String imie;
    private String nazwisko;
    private Integer pensja;
}
