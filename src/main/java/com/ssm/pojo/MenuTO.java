package com.ssm.pojo;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MenuTO {
    private Integer id;//菜单
    private String text;//菜单名
    private Integer parentId;//父菜单
    private String url;//菜单的连接
    private Map<String, Object> attributes = new HashMap<>();
    private List<MenuTO> children = new LinkedList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public List<MenuTO> getChildren() {
        return children;
    }

    public void setChildren(List<MenuTO> children) {
        this.children = children;
    }
}
