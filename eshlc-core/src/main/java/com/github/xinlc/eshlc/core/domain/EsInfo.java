package com.github.xinlc.eshlc.core.domain;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ES 实例类
 *
 * @author Richard
 * @since 2021-03-26
 */
public class EsInfo {

    /**
     * 可以配置多集群，指定多个key,value
     */
    private Map<String, EsCluster> clusters = new ConcurrentHashMap<>();

    public Map<String, EsCluster> getClusters() {
        return clusters;
    }

    public void setClusters(Map<String, EsCluster> clusters) {
        this.clusters = clusters;
    }

    public static class EsCluster {

        /**
         * 是否启用集群配置，默认启用
         */
        private boolean enable = true;

        /**
         * 是否将集群配置用于默认客户端
         */
        private boolean defaultClient;

        /**
         * 集群协议，默认 http
         */
        private String scheme = "http";

        /**
         * 集群host 逗号分隔，默认 本地
         */
        private String hosts = "127.0.0.1";

        /**
         * 集群port 逗号分隔，默认9200
         */
        private String ports = "9200";

        /**
         * 连接超时时间(毫秒)，默认 10秒
         */
        private Integer connectTimeout = 10000;

        /**
         * 连接请求超时时间(毫秒），默认 3秒
         */
        private Integer connectionRequestTimeout = 3000;

        /**
         * socket 超时时间(毫秒） 默认30秒
         */
        private Integer socketTimeout = 30000;

        /**
         * 客户端连接数，默认 20
         */
        private Integer maxConnectTotal = 20;

        /**
         * 并发数，默认 10
         */
        private Integer maxConnectPerRoute = 10;

        /**
         * Keep Alive 单位秒，默认 org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy
         */
        private Integer keepAliveDuration = null;

        /**
         * 认证用户
         */
        private String username;

        /**
         * 认证密码
         */
        private String password;

        public boolean isEnable() {
            return enable;
        }

        public void setEnable(boolean enable) {
            this.enable = enable;
        }

        public boolean isDefaultClient() {
            return defaultClient;
        }

        public void setDefaultClient(boolean defaultClient) {
            this.defaultClient = defaultClient;
        }

        public String getScheme() {
            return scheme;
        }

        public void setScheme(String scheme) {
            this.scheme = scheme;
        }

        public String getHosts() {
            return hosts;
        }

        public void setHosts(String hosts) {
            this.hosts = hosts;
        }

        public String getPorts() {
            return ports;
        }

        public void setPorts(String ports) {
            this.ports = ports;
        }

        public Integer getConnectTimeout() {
            return connectTimeout;
        }

        public void setConnectTimeout(Integer connectTimeout) {
            this.connectTimeout = connectTimeout;
        }

        public Integer getConnectionRequestTimeout() {
            return connectionRequestTimeout;
        }

        public void setConnectionRequestTimeout(Integer connectionRequestTimeout) {
            this.connectionRequestTimeout = connectionRequestTimeout;
        }

        public Integer getSocketTimeout() {
            return socketTimeout;
        }

        public void setSocketTimeout(Integer socketTimeout) {
            this.socketTimeout = socketTimeout;
        }

        public Integer getMaxConnectTotal() {
            return maxConnectTotal;
        }

        public void setMaxConnectTotal(Integer maxConnectTotal) {
            this.maxConnectTotal = maxConnectTotal;
        }

        public Integer getMaxConnectPerRoute() {
            return maxConnectPerRoute;
        }

        public void setMaxConnectPerRoute(Integer maxConnectPerRoute) {
            this.maxConnectPerRoute = maxConnectPerRoute;
        }

        public Integer getKeepAliveDuration() {
            return keepAliveDuration;
        }

        public void setKeepAliveDuration(Integer keepAliveDuration) {
            this.keepAliveDuration = keepAliveDuration;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        @Override
        public String toString() {
            return "EsCluster{" +
                "enable=" + enable +
                ", defaultClient=" + defaultClient +
                ", scheme='" + scheme + '\'' +
                ", hosts='" + hosts + '\'' +
                ", ports='" + ports + '\'' +
                ", connectTimeout=" + connectTimeout +
                ", connectionRequestTimeout=" + connectionRequestTimeout +
                ", socketTimeout=" + socketTimeout +
                ", maxConnectTotal=" + maxConnectTotal +
                ", maxConnectPerRoute=" + maxConnectPerRoute +
                ", keepAliveDuration=" + keepAliveDuration +
                ", username='" + username + '\'' +
                ", password='" + "******" + '\'' +
                '}';
        }
    }
}
