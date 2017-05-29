package com.tma.sparking.services.http;

/**
 * Define field information, include name, id and description
 */
class Field {
    private int mFieldId;
    private String mFieldName;
    private String mFieldDescription;

    int getFieldId() {
        return mFieldId;
    }

    void setFieldId(int fieldId) {
        mFieldId = fieldId;
    }

    String getFieldName() {
        return mFieldName;
    }

    void setFieldName(String fieldName) {
        mFieldName = fieldName;
    }

    String getFieldDescription() {
        return mFieldDescription;
    }

    void setFieldDescription(String fieldDescription) {
        mFieldDescription = fieldDescription;
    }
}
