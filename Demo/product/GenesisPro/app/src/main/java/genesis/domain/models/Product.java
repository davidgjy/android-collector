package genesis.domain.models;

import java.util.Date;
import java.util.UUID;

/**
 * Created by KG on 16/3/11.
 */
public class Product {
    private UUID id;
    private String name;
    private String description;
    private int channelId;
    private String cover;
    private String attachment;
    private int statusId;
    private Date createTime;
    private Date lastUpda;

    public Product() {
        this(UUID.randomUUID());
    }

    public Product(UUID id) {
        this.id = id;
        createTime = new Date();
        lastUpda = new Date();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getChannelId() {
        return channelId;
    }

    public String getCover() {
        return cover;
    }

    public String getAttachment() {
        return attachment;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public int getStatusId() {
        return statusId;
    }

    public Date getLastUpda() {
        return lastUpda;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setLastUpda(Date lastUpda) {
        this.lastUpda = lastUpda;
    }

    public String getPhotoFilename() {
        return "IMG_" + getId().toString() + ".jpg";
    }
}
