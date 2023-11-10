package datum.app.clinic.model;

import datum.app.admin.model.Phone;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Jacksonized
@Schema
public class Address implements Serializable {
    @Schema
    private List<Phone> phone = new ArrayList<>();
    @Schema(example = "Кыргызстан")
    private String country;
    @Schema(example = "Бишкек")
    private String city;
    private String region;
    @Schema(example = "пр. Чүй")
    private String street;
    @Schema(example = "205")
    private int house;
    private int apartment;
    @Schema(example = "42.877317")
    private String latitude;
    @Schema(example = "74.600215")
    private String longitude;
    @Schema(example = """
            <iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d2923
            .8231570293283!2d74.59588357567849!3d42.87657660231409!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13
            .1!3m3!1m2!1s0x389eb7ddc401db31%3A0xd868494a462beaa9!2z0JHQtdC70YvQuSDQtNC-0LwsINC_0YDQvtGB0
            L8uINCn0YPQuSwg0JHQuNGI0LrQtdC6!5e0!3m2!1sru!2skg!4v1699201561246!5m2!1sru!2skg"
            width="600"
            height="450"
            style="border:0;"
            allowfullscreen=""
            loading="lazy"
            referrerpolicy="no-referrer-when-downgrade">
            </iframe>
            """)
    private String iframe;
    @Schema(example = "https://go.2gis.com/gh71q")
    private String link;

}
