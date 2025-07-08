package business;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class ApartmentViewModel {

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
    @JsonProperty("owner")
    private boolean owner;
    @JsonProperty("archived")
    private boolean isArchived;

    public ApartmentViewModel(UUID adId, String adTitle, String adDescription, float adPrice, String adSellerName, float adSellerScore, String adImagesPath, boolean isSaved, boolean isReported, boolean isSeller, boolean isArchived) {
        this.adId = adId;
        this.adTitle = adTitle;
        this.adDescription = adDescription;
        this.adPrice = adPrice;
        this.adSellerName = adSellerName;
        this.adSellerScore = adSellerScore;
        this.adImagesPath = adImagesPath;
        this.saved = isSaved;
        this.reported = isReported;
        this.owner = isSeller;
        this.isArchived = isArchived;
    }

}
