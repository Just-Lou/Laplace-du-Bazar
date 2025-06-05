package business;

import java.util.UUID;

public class UserType {

    private UUID typeId;
    private String typeName;

    UserType(String typeName) {
        this.typeName = typeName;
    }

    public UUID getTypeId() {
        return typeId;
    }
    public void setTypeId(UUID typeId) {
        this.typeId = typeId;
    }
    public String getTypeName() {
        return typeName;
    }
}
