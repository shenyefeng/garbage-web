package me.anchora.garbage.entry;

import java.io.Serializable;
import java.util.Date;

import me.anchora.garbage.entry.base.SuperGarbages;

public class Garbages extends SuperGarbages implements Serializable {
    private Long id;

    private Long garbageStationId;

    private Long garbageCanId;

    private Long qrCodeId;

    private String qrCode;

    private Long userId;

    private Long garbageTypeId;

    private Float weight;

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

    public Long getGarbageStationId() {
        return garbageStationId;
    }

    public void setGarbageStationId(Long garbageStationId) {
        this.garbageStationId = garbageStationId;
    }

    public Long getGarbageCanId() {
        return garbageCanId;
    }

    public void setGarbageCanId(Long garbageCanId) {
        this.garbageCanId = garbageCanId;
    }

    public Long getQrCodeId() {
        return qrCodeId;
    }

    public void setQrCodeId(Long qrCodeId) {
        this.qrCodeId = qrCodeId;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode == null ? null : qrCode.trim();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getGarbageTypeId() {
        return garbageTypeId;
    }

    public void setGarbageTypeId(Long garbageTypeId) {
        this.garbageTypeId = garbageTypeId;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
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
        sb.append(", garbageStationId=").append(garbageStationId);
        sb.append(", garbageCanId=").append(garbageCanId);
        sb.append(", qrCodeId=").append(qrCodeId);
        sb.append(", qrCode=").append(qrCode);
        sb.append(", userId=").append(userId);
        sb.append(", garbageTypeId=").append(garbageTypeId);
        sb.append(", weight=").append(weight);
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