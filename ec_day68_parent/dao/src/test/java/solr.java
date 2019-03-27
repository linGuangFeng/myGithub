import com.ec.pojo.item.Item;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@ContextConfiguration("spring/applicationContext-redis.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class solr {

    @Resource
    SolrTemplate solrTemplate;

    @Test
    public void solrFindById(){
//        solrTemplate.getById(1,"c");
    }



    @Test
    public void solrSave(){

       Item item = new Item();




    }


}
