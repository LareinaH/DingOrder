package com.cotton.abmallback.model.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * ConfigObject
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/6/27
 */
public class ConfigObject {

    private String namespace;

    private List<ConfigItem> dataList;

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public List<ConfigItem> getDataList() {
        return dataList;
    }

    public void setDataList(List<ConfigItem> dataList) {
        this.dataList = dataList;
    }

    public static ConfigItem createConfigItem(){
        return new ConfigItem();
    }

    public static class ConfigItem{
        private Long id;
        private String item;
        private String value;
        private String defaultVaule;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getItem() {
            return item;
        }

        public void setItem(String item) {
            this.item = item;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getDefaultVaule() {
            return defaultVaule;
        }

        public void setDefaultVaule(String defaultVaule) {
            this.defaultVaule = defaultVaule;
        }
    }
}
