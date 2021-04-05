package com.github.xinlc.eshlc.autoconfigure;

import cn.hutool.extra.spring.SpringUtil;
import com.github.xinlc.eshlc.core.ElasticsearchTemplate;
import com.github.xinlc.eshlc.core.constant.EsConstant;
import com.github.xinlc.eshlc.core.factory.EsFramework;
import com.github.xinlc.eshlc.core.factory.IEsExternal;
import com.github.xinlc.eshlc.core.factory.IEsFactory;
import com.github.xinlc.eshlc.core.server.DefaultDocumentOperations;
import com.github.xinlc.eshlc.core.server.DefaultSearchOperations;
import com.github.xinlc.eshlc.core.server.IDocumentOperations;
import com.github.xinlc.eshlc.core.server.ISearchOperations;
import com.github.xinlc.eshlc.core.util.AssertUtil;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

/**
 * ES 自动配置
 *
 * @author Richard
 * @since 2021-03-26
 */
@Configuration
@ConditionalOnClass(EsFramework.class)
@EnableConfigurationProperties(EsProperties.class)
@ConditionalOnProperty(prefix = EsConstant.ELASTICSEARCH, name = EsConstant.ENABLE, havingValue = EsConstant.TRUE, matchIfMissing = true)
public class EsAutoConfiguration {

    private final EsProperties properties;

    public EsAutoConfiguration(EsProperties properties) {
        this.properties = properties;
    }

    /**
     * 初始化 SpringUtil
     *
     * @return SpringUtil
     */
    @Bean
    @ConditionalOnMissingBean
    public SpringUtil springUtil() {
        return new SpringUtil();
    }

    /**
     * 初始化 ES Spring 实现
     *
     * @return ES 实现
     */
    @Bean
    @DependsOn("springUtil")
    @ConditionalOnMissingBean
    public IEsExternal esExternal() {
        return new SpringEsExternal();
    }

    /**
     * 初始化 ES 框架
     *
     * @param esExternal ES 外部实现
     * @return ES 框架
     */
    @Bean
    @ConditionalOnMissingBean
    public EsFramework esFramework(IEsExternal esExternal) {
        AssertUtil.check(this.properties, "es properties invalid...");

        // 构建ES框架
        EsFramework framework = new EsFramework(this.properties);
        framework.external(esExternal);
        framework.start();

        return framework;
    }

    /**
     * 获取默认ES客户端
     *
     * @param esFramework ES框架
     * @return ES 默认客户端
     */
    @Bean(destroyMethod = "close")
    @ConditionalOnMissingBean
    public RestHighLevelClient restHighLevelClient(EsFramework esFramework) {
        IEsFactory factory = esFramework.getFactory();

        AssertUtil.check(factory, "es default client not found...");

        return factory.getClient();
    }

    /**
     * 获取默认ES客户端
     *
     * @param esFramework ES框架
     * @return ES 默认客户端
     */
    @Bean
    @ConditionalOnMissingBean(ElasticsearchTemplate.class)
    public ElasticsearchTemplate elasticsearchTemplate(EsFramework esFramework) {
        return new ElasticsearchTemplate(documentOperations(esFramework), searchOperations(esFramework));
    }

    /**
     * 文档服务
     *
     * @param esFramework ES框架
     * @return 文档服务实现
     */
    private IDocumentOperations documentOperations(EsFramework esFramework) {
        return new DefaultDocumentOperations(esFramework);
    }

    /**
     * 搜索服务
     *
     * @param esFramework ES框架
     * @return 搜索服务实现
     */
    private ISearchOperations searchOperations(EsFramework esFramework) {
        return new DefaultSearchOperations(esFramework);
    }
}
