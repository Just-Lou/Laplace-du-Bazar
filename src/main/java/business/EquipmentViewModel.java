package business;

import java.util.UUID;

public class EquipmentViewModel {

    private UUID adId;
    private String adTitle;
    private String adDescription;
    private float adPrice;
    private String adSellerName;
    private float adSellerScore;
    private String adImagesPath;
    private boolean saved;
    private boolean reported;
    private boolean isArchived;

    EquipmentViewModel(UUID adId, String adTitle, String adDescription, float adPrice, String adSellerName, float adSellerScore, String adImagesPath, boolean isSaved, boolean isReported, boolean isArchived) {
        this.adId = adId;
        this.adTitle = adTitle;
        this.adDescription = adDescription;
        this.adPrice = adPrice;
        this.adSellerName = adSellerName;
        this.adSellerScore = adSellerScore;
        this.adImagesPath = adImagesPath;
        this.saved = isSaved;
        this.reported = isReported;
        this.isArchived = isArchived;
    }
}
