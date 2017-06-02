package com.tma.sparking.services.parkingfieldservice;

/**
 * Define field information, include name, id and description
 */
class Field {
    private int mFieldId;
    private String mFieldName;
    private String mFieldDescription;

    public int getFieldId() {
        return mFieldId;
    }

    public void setFieldId(int fieldId) {
        mFieldId = fieldId;
    }

    public String getFieldName() {
        return mFieldName;
    }

    public void setFieldName(String fieldName) {
        mFieldName = fieldName;
    }

    public String getFieldDescription() {
        return mFieldDescription;
    }

    public void setFieldDescription(String fieldDescription) {
        mFieldDescription = fieldDescription;
    }
}
