import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Pacjent implements Serializable {
    private String id;
    private String imie;
    private String nazwisko;
    private Integer wiek;
}
