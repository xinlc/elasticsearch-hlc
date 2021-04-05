package com.github.xinlc.eshlc.core.factory;

import cn.hutool.core.date.SystemClock;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.github.xinlc.eshlc.core.domain.EsInfo;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ES 框架
 *
 * @author Richard
 * @since 2021-03-26
 */
public class EsFramework {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * ES 配置信息
     */
    private final EsInfo info;

    /**
     * ES external
     */
    private IEsExternal external;

    /**
     * 工厂容器
     */
    private final Map<String, IEsFactory> factory = new ConcurrentHashMap<>();

    /**
     * 默认工厂实例
     */
    private IEsFactory defaultFactory;

    public EsFramework(EsInfo info) {
        this.info = info;
    }

    /**
     * 获取默认工厂, 默认获取第一个配置
     *
     * @return IESFactory
     */
    public IEsFactory getFactory() {
        return defaultFactory;
    }

    /**
     * 获取指定工厂
     *
     * @param key 容器key，集群配置key
     * @return IESFactory
     */
    public IEsFactory getFactory(String key) {
        return this.factory.get(key);
    }

    /**
     * 设置外部实现
     *
     * @param external ES 外部实现
     * @return ES 框架
     */
    public EsFramework external(IEsExternal external) {
        this.external = external;
        return this;
    }

    /**
     * 启动 ES
     */
    public void start() {
        if (this.external == null) {
            this.external = new IEsExternal() {
            };
        }

        logger.info("正在启动 elasticsearch...");
        long start = SystemClock.now();
        info.getClusters().forEach((key, cluster) -> {
            // 初始化ES配置
            if (cluster.isEnable()) {
                logger.info("正在装载 elasticsearch 配置：{}", cluster);

                RestHighLevelClient client = loadProperties(cluster);
                IEsFactory esFactory = external.create(key);
                esFactory.setClient(client);
                esFactory.setName(key);
                factory.put(key, esFactory);

                // 设置默认工厂
                if (cluster.isDefaultClient()) {
                    defaultFactory = esFactory;
                }

                logger.info("{} elasticsearch 配置装载完成", key);
            }
        });
        long end = SystemClock.now();

        logger.info("elasticsearch 启动成功 , 耗时 {} 毫秒", end - start);
    }

    /**
     * 装载配置
     *
     * @param cluster 集群配置
     * @return 客户端实例
     */
    private RestHighLevelClient loadProperties(EsInfo.EsCluster cluster) {
        HttpHost[] httpHosts = createHttpHost(cluster);

        RestClientBuilder builder = RestClient.builder(httpHosts);

        setClientConfig(builder, cluster);

        return new RestHighLevelClient(builder);
    }

    /**
     * 设置客户端配置
     *
     * @param builder 客户端构建器
     * @param cluster 集群配置
     */
    private void setClientConfig(RestClientBuilder builder, EsInfo.EsCluster cluster) {
        // 账号密码 (x-pack basic验证)
        CredentialsProvider credentialsProvider = null;
        if (StrUtil.isNotBlank(cluster.getUsername()) && StrUtil.isNotBlank(cluster.getPassword())) {
            credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(cluster.getUsername(), cluster.getPassword()));
            /// builder.setHttpClientConfigCallback(f -> f.setDefaultCredentialsProvider(credentialsProvider));
        }

        // 请求配置
        builder.setRequestConfigCallback(requestConfigBuilder -> {
            requestConfigBuilder.setConnectTimeout(cluster.getConnectTimeout());
            requestConfigBuilder.setSocketTimeout(cluster.getSocketTimeout());
            requestConfigBuilder.setConnectionRequestTimeout(cluster.getConnectionRequestTimeout());
            return requestConfigBuilder;
        });

        // 连接池配置
        CredentialsProvider finalCredentialsProvider = credentialsProvider;
        builder.setHttpClientConfigCallback(httpClientBuilder -> {
            httpClientBuilder.setMaxConnTotal(cluster.getMaxConnectTotal());
            httpClientBuilder.setMaxConnPerRoute(cluster.getMaxConnectPerRoute());
            if (null != finalCredentialsProvider) {
                httpClientBuilder.setDefaultCredentialsProvider(finalCredentialsProvider);
            }
            return httpClientBuilder;
        });
    }

    /**
     * 配置集群链接地址
     *
     * @param cluster 集群配置
     * @return 链接配置
     */
    private HttpHost[] createHttpHost(EsInfo.EsCluster cluster) {
        Assert.notEmpty(cluster.getHosts(), "ElasticSearch cluster ip address cannot empty.");

        String[] hosts = cluster.getHosts().split(",");
        String[] ports = cluster.getPorts().split(",");
        HttpHost[] httpHosts = new HttpHost[hosts.length];

        for (int i = 0; i < hosts.length; i++) {
            httpHosts[i] = new HttpHost(hosts[i].trim(), Integer.parseInt(ports[i].trim()), cluster.getScheme());
        }

        return httpHosts;
    }

}
