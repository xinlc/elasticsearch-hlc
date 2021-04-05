package com.github.xinlc.eshlc.core.domain;

import com.github.xinlc.eshlc.core.constant.EsConstant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ES 高亮实体
 *
 * @author Richard
 * @since 2021-04-01
 */
public class EsHighLight {

    private String preTag = EsConstant.HIGHLIGHT_PRE_TAGS;
    private String postTag = EsConstant.HIGHLIGHT_POST_TAGS;
    private List<String> highLightList;

    public EsHighLight() {
        highLightList = new ArrayList<>();
    }

    public EsHighLight(String... fieldValue) {
        highLightList = new ArrayList<>();
        highLightList.addAll(Arrays.asList(fieldValue));
    }

    public EsHighLight field(String fieldValue) {
        highLightList.add(fieldValue);
        return this;
    }

    public String getPreTag() {
        return preTag;
    }

    public void setPreTag(String preTag) {
        this.preTag = preTag;
    }

    public String getPostTag() {
        return postTag;
    }

    public void setPostTag(String postTag) {
        this.postTag = postTag;
    }

    public List<String> getHighLightList() {
        return highLightList;
    }

    public void setHighLightList(List<String> highLightList) {
        this.highLightList = highLightList;
    }

    @Override
    public String toString() {
        return "EsHighLight{" +
            "preTag='" + preTag + '\'' +
            ", postTag='" + postTag + '\'' +
            ", highLightList=" + highLightList +
            '}';
    }
}
