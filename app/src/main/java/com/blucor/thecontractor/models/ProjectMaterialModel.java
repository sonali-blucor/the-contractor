package com.blucor.thecontractor.models;

public class ProjectMaterialModel {
    private String no;
    private String materialName;
    private String materialDate;
    private String materialType;
    private String materialTransport;
    private String materialQuantity;
    private String materialUnit;
    private boolean editFlag;

    public ProjectMaterialModel() {
    }

    /**
     *
     * @param editFlag
     */
    public ProjectMaterialModel(boolean editFlag) {
        this.editFlag = editFlag;
    }

    /**
     *
     * @param no
     * @param materialName
     * @param materialDate
     * @param materialType
     * @param materialTransport
     * @param materialQuantity
     * @param materialUnit
     * @param editFlag
     */
    public ProjectMaterialModel(String no, String materialName, String materialDate, String materialType, String materialTransport, String materialQuantity, String materialUnit, boolean editFlag) {
        this.no = no;
        this.materialName = materialName;
        this.materialDate = materialDate;
        this.materialType = materialType;
        this.materialTransport = materialTransport;
        this.materialQuantity = materialQuantity;
        this.materialUnit = materialUnit;
        this.editFlag = editFlag;
    }

    /**
     *
     * @param no
     * @param materialName
     * @param materialDate
     * @param materialType
     * @param materialQuantity
     * @param editFlag
     */
    public ProjectMaterialModel(String no, String materialName, String materialDate, String materialType, String materialQuantity, boolean editFlag) {
        this.no = no;
        this.materialName = materialName;
        this.materialDate = materialDate;
        this.materialType = materialType;
        this.materialQuantity = materialQuantity;
        this.editFlag = editFlag;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getMaterialDate() {
        return materialDate;
    }

    public void setMaterialDate(String materialDate) {
        this.materialDate = materialDate;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public String getMaterialTransport() {
        return materialTransport;
    }

    public void setMaterialTransport(String materialTransport) {
        this.materialTransport = materialTransport;
    }

    public String getMaterialQuantity() {
        return materialQuantity;
    }

    public void setMaterialQuantity(String materialQuantity) {
        this.materialQuantity = materialQuantity;
    }

    public String getMaterialUnit() {
        return materialUnit;
    }

    public void setMaterialUnit(String materialUnit) {
        this.materialUnit = materialUnit;
    }

    public boolean isEditFlag() {
        return editFlag;
    }

    public void setEditFlag(boolean editFlag) {
        this.editFlag = editFlag;
    }
}
