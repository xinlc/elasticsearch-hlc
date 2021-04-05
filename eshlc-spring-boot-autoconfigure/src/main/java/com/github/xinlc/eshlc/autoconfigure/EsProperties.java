package com.github.xinlc.eshlc.autoconfigure;

import com.github.xinlc.eshlc.core.constant.EsConstant;
import com.github.xinlc.eshlc.core.domain.EsInfo;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * ES 属性配置
 *
 * @author Richard
 * @since 2021-03-26
 */
@ConfigurationProperties(prefix = EsConstant.ELASTICSEARCH)
public class EsProperties extends EsInfo {

    /**
     * 是否启用自动配置, 默认 true
     */
    private boolean enable = true;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
