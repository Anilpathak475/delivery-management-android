package co.parsl.android.boilerplate.remote.model;

import com.google.gson.annotations.SerializedName;

/*
* {
    "uniqueId": "2V4QK97b8pOd",
    "assignedUrl": null,
    "status": "TAG_ACTIVE",
    "internalId": 8,
    "manufacturerId": "nfc_factory",
    "manufacturerGeneratedUuid": "b419a7ba-8052-434e-854c-3451492e1936",
    "responsibleUser": "test1",
    "orderId": "Gkd97X18B5pb"
}
* */
public class TagInfoPojo {

    @SerializedName("uniqueId")
    String uniqueId;

    @SerializedName("assignedUrl")
    String assignedUrl;

    @SerializedName("status")
    String status;

    @SerializedName("internalId")
    String internalId;

    @SerializedName("manufacturerId")
    String manufacturerId;

    @SerializedName("manufacturerGeneratedUuid")
    String manufacturerGeneratedUuid;

    @SerializedName("responsibleUser")
    String responsibleUser;

    @SerializedName("orderId")
    String orderId;

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getAssignedUrl() {
        return assignedUrl;
    }

    public void setAssignedUrl(String assignedUrl) {
        this.assignedUrl = assignedUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInternalId() {
        return internalId;
    }

    public void setInternalId(String internalId) {
        this.internalId = internalId;
    }

    public String getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(String manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public String getManufacturerGeneratedUuid() {
        return manufacturerGeneratedUuid;
    }

    public void setManufacturerGeneratedUuid(String manufacturerGeneratedUuid) {
        this.manufacturerGeneratedUuid = manufacturerGeneratedUuid;
    }

    public String getResponsibleUser() {
        return responsibleUser;
    }

    public void setResponsibleUser(String responsibleUser) {
        this.responsibleUser = responsibleUser;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "TagInfoPojo{" +
                "uniqueId='" + uniqueId + '\'' +
                ", assignedUrl='" + assignedUrl + '\'' +
                ", status='" + status + '\'' +
                ", internalId=" + internalId +
                ", manufacturerId='" + manufacturerId + '\'' +
                ", manufacturerGeneratedUuid='" + manufacturerGeneratedUuid + '\'' +
                ", responsibleUser='" + responsibleUser + '\'' +
                ", orderId='" + orderId + '\'' +
                '}';
    }
}
