package business;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.UUID;

public class ApartmentDetailsViewModel {
    @JsonProperty("adId")
    private UUID adId;
    @JsonProperty("adTitle")
    private String adTitle;
    @JsonProperty("adDescription")
    private String adDescription;
    @JsonProperty("adPrice")
    private float adPrice;
    @JsonProperty("adSellerName")
    private String adSellerName;
    @JsonProperty("adSellerScore")
    private float adSellerScore;
    @JsonProperty("adImagesPath")
    private String adImagesPath;
    @JsonProperty("saved")
    private boolean saved;
    @JsonProperty("reported")
    private boolean reported;
    @JsonProperty("adSellerEmail")
    private String adSellerEmail;
    @JsonProperty("adSellerPhone")
    private String adSellerPhone;
    @JsonProperty("disponibility")
    private LocalDate disponibility;
    @JsonProperty("adSellerScoreCount")
    private int adSellerScoreCount;

    public ApartmentDetailsViewModel(UUID adId, String adTitle, String adDescription, float adPrice, String adSellerName, float adSellerScore, String adImagesPath, boolean isSaved, boolean isReported, String adSellerEmail, String adSellerPhone, LocalDate disponibility, int adSellerScoreCount) {
        this.adId = adId;
        this.adTitle = adTitle;
        this.adDescription = adDescription;
        this.adPrice = adPrice;
        this.adSellerName = adSellerName;
        this.adSellerScore = adSellerScore;
        this.adImagesPath = adImagesPath;
        this.saved = isSaved;
        this.reported = isReported;
        this.adSellerEmail = adSellerEmail;
        this.adSellerPhone = adSellerPhone;
        this.disponibility = disponibility;
        this.adSellerScoreCount = adSellerScoreCount;
    }

    public String getFolderPath() { return adImagesPath; }
}
