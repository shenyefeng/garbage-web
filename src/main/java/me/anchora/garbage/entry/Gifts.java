package me.anchora.garbage.entry;

import java.io.Serializable;
import java.util.Date;

import me.anchora.garbage.entry.base.PageEntry;

public class Gifts extends PageEntry implements Serializable {
    private Long id;

    private String giftName;

    private Float giftNum;

    private Float giftGrantNum;

    private Float score;

    private String remark;

    private Date createdAt;

    private Long createdBy;

    private Date updatedAt;

    private Long updatedBy;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName == null ? null : giftName.trim();
    }

    public Float getGiftNum() {
        return giftNum;
    }

    public void setGiftNum(Float giftNum) {
        this.giftNum = giftNum;
    }

    public Float getGiftGrantNum() {
        return giftGrantNum;
    }

    public void setGiftGrantNum(Float giftGrantNum) {
        this.giftGrantNum = giftGrantNum;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", giftName=").append(giftName);
        sb.append(", giftNum=").append(giftNum);
        sb.append(", giftGrantNum=").append(giftGrantNum);
        sb.append(", score=").append(score);
        sb.append(", remark=").append(remark);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", createdBy=").append(createdBy);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", updatedBy=").append(updatedBy);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}