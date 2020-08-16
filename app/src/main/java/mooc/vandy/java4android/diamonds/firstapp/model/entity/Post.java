package mooc.vandy.java4android.diamonds.firstapp.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "posts")
public class Post {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    private String id;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "accountId")
    private String accountID;

    @ColumnInfo(name = "comments")
    private Boolean activatedComments;

    public Post(@NonNull String id, String description, String accountID, Boolean activatedComments) {
        this.id = id;
        this.description = description;
        this.accountID = accountID;
        this.activatedComments = activatedComments;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getAccountID() {
        return accountID;
    }

    public Boolean getActivatedComments() {
        return activatedComments;
    }
}

