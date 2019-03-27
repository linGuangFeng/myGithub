import com.sun.deploy.panel.ITreeNode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.result.ScoredPage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@ContextConfiguration("/solr.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class Solr {

    @Resource
    SolrTemplate solrTemplate;


    @Test
    public void findField(){
        Item item = solrTemplate.getById(2, Item.class);
        System.out.println(item);

    }

    @Test
    public void saveField(){
        Item item = new Item();
        item.setId(4L);
        item.setTitle("苹果手机");
        solrTemplate.saveBean(item);
        solrTemplate.commit();
//        solrTemplate.saveBean(item,10000);//延时自动提交
    }

    @Test
    public void findFieldByCriteria(){


        Criteria criteria = new Criteria("item_title");
//        criteria.and("item_title");
        criteria.is("华为");
        Query query = new SimpleQuery(criteria);
        ScoredPage<Item> scoredPage = solrTemplate.queryForPage(query, Item.class);
        List<Item> items = scoredPage.getContent();
        System.out.println(scoredPage);
//        System.out.println(items);
        for(Item item : items){
            System.out.println(item);

        }

    }

    @Test
    public void findBySimp(){
        Query query = new SimpleQuery("item_title:手机");
        ScoredPage<Item> scoredPage = solrTemplate.queryForPage(query, Item.class);
        List<Item> items = scoredPage.getContent();
        for(Item item : items){
            System.out.println(item);
        }


    }

}
