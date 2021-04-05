package com.github.xinlc.eshlc;

import com.github.xinlc.eshlc.core.ElasticsearchTemplate;
import com.github.xinlc.eshlc.core.annotation.EsID;
import com.github.xinlc.eshlc.core.annotation.EsTable;
import com.github.xinlc.eshlc.core.domain.*;
import com.github.xinlc.eshlc.core.enums.OrderType;
import com.github.xinlc.eshlc.core.factory.EsFramework;
import com.github.xinlc.eshlc.core.factory.IEsFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertTrue;


/**
 * 测试
 *
 * @author Richard
 * @date 2021-03-29
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ElasticsearchApplicationTest.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ElasticsearchTest {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private EsFramework esFramework;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    /**
     * 测试ES工厂
     */
    @Test
    public void testEsFactory() {
//        IEsFactory esFactory = esFramework.getFactory();
        IEsFactory esFactory = esFramework.getFactory("es1");
        String indexName = "test_index";
//
////        ES新增
//        IndexResponse response = esFactory.index(indexName, "{\"age\":25}", null);
//        logger.info("新增1:{}", response);
//        logger.info("新增1:{}", response);
//
//        String id = response.getId();
//
////        ES修改
//        UpdateResponse response2 = esFactory.update(indexName, "{\"age\":26}", id);
//        logger.info("修改:{}", response2);
//
////        ES删除
//        DeleteResponse response3 = esFactory.delete(indexName, id);
//        logger.info("删除:{}", response3);
//
//        // ES清空索引所有数据
//        AcknowledgedResponse response4 = esFactory.delete(indexName);
//        logger.info("删除索引:{}", response4);
//

//		logger.info("list size: {}", list.size());
//		assertTrue("模糊查询未返回数据", list.size() > 0);
    }

    /**
     * 测试文档服务
     */
    @Test
    public void testDoc() {
        List<DocFoo> docAS = new ArrayList<>();
        docAS.add(new DocFoo(null, "Richard1"));
        docAS.add(new DocFoo(null, "Richard2"));
        docAS.add(new DocFoo("3", "Richard3"));

        // 批量
        List<EsBatchResponse> batchResponses = elasticsearchTemplate.saveBatch(docAS);
        batchResponses.forEach(i -> logger.info("{}", i));
    }

    /**
     * 测试搜索服务
     */
    @Test
    public void testSearch() {
        EsPageSortHighLight psh = new EsPageSortHighLight(1, 10);
        EsSort.Order order = new EsSort.Order("id", OrderType.DESC);
        EsSort.Order order1 = new EsSort.Order("time", OrderType.ASC);
        psh.setSort(new EsSort(order, order1));
        psh.setHighLight(new EsHighLight().field("name"));

        // 首页从1开始
        psh.setOneAsFirstPageNo();

        EsPageImpl<Object> objects = new EsPageImpl<>(100, Collections.singletonList("1"), psh);
        objects.getPageable();

//        elasticsearchTemplate.search();
    }


    @EsTable(index = "test_index")
    static class DocFoo {
        @EsID
        private String id;
        private String name;

        public DocFoo(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "DocFoo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
        }
    }
}
