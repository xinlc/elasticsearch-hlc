package com.github.xinlc.eshlc;

import com.github.xinlc.eshlc.core.factory.EsFactory;
import com.github.xinlc.eshlc.core.factory.IEsExternal;
import com.github.xinlc.eshlc.core.factory.IEsFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ElasticsearchApplicationTest {
    /**
     * 自定义ES创建类
     *
     * @return
     */
    static class CustomESExternal implements IEsExternal {
        @Override
        public IEsFactory create(String key) {
            System.out.println("自定义ES生产key" + key);
            // 自定义工厂实现
            return new EsFactory();
        }
    }

    /**
     * 自定义ES
     *
     * @return
     */
    @Bean
    public IEsExternal CustomESExternal() {
        return new CustomESExternal();
    }

    public static void main(String[] args) {
        SpringApplication.run(ElasticsearchApplicationTest.class, args);
    }
}
