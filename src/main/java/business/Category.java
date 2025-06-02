package business;

import java.util.UUID;

public class Category {
    private UUID categoryId;
    private String categoryName;

    public Category(UUID categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public UUID getCategoryId() { return categoryId; }
    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }


}
