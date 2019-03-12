package custom.config;




import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.io.Resources;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 解析配置文件的工具类
 */
public class Configuration {
    /**
     * 第一：定义map集合, 封装解析结果
     * 1. 定义map集合，封装解析映射文件的结果：IUserDao.xml等。
     * 2. map集合的key就是namespace+id, 即：com.itheima.dao.IUserDao.findAll
     * 3. map集合的value就是解析映射文件封装为Mapper对象的结果
     */
    private Map<String ,Mapper> mappers = new HashMap<>();
    public Map<String , Mapper> getMappers(){
        return mappers;
    }
    //第二：通过构造函数加载配置文件
    private static Configuration config;
    private Configuration(){
        //加载SqlMapConfig.xml文件
        loadSqlMapConfig();
    }
    private String driver;
    private String url;
    private String username;
    private String password;
    public void loadSqlMapConfig(){
        try{
            //使用dom4j加载xml配置文件
            SAXReader reader = new SAXReader();
            //获取文件配置文件的流
            InputStream fis = this.getClass().getClassLoader().getResourceAsStream("SqlMpaConfig.xml");
            //获取根节点
            Document document = reader.read(fis);
            Element rootElement = document.getRootElement();
            //获取所有的属性的名称
            List<Element> propList = rootElement.selectNodes("//property");
            //遍历属性名称，并将对应的属性值设置到对应的成员变量
            for (Element prop : propList){
                String name = prop.attributeValue("name");
                String value = prop.attributeValue("value");
                if ("driver".equalsIgnoreCase(name)) driver = value;
                else if ("url".equalsIgnoreCase(name)) driver = url;
                else if ("username".equalsIgnoreCase(name)) driver = username;
                else if ("password".equalsIgnoreCase(name)) driver = password;
            }

            //获取映射文件
            List<Element> mapperList = rootElement.selectNodes("//mapper");
            for(Element mapper : mapperList){
                String resource = mapper.attributeValue("resource");
                loadMppper(resource);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //加载映射文件
    private void loadMppper(String resource) throws Exception{
        SAXReader saxReader = new SAXReader();
        InputStream inputStream = Resources.getResourceAsStream(resource);
        Document document = saxReader.read(inputStream);
        Element rootElement = document.getRootElement();

        //获取指定接口的类全名
        String namespace = rootElement.attributeValue("namespace");
        //解析所有的select标签
        List<Element> selectElements = rootElement.selectNodes("//select");
        for(Element selectElement : selectElements){
            //获取方法名
            String id = selectElement.attributeValue("id");
            //获取返回类型
            String resultType = selectElement.attributeValue("resultType");
            //获取标签内容
            String sql = selectElement.getText();
            // 封装对象
            Mapper mapper = new Mapper();
            mapper.setNamespace(namespace);
            mapper.setId(id);
            mapper.setResultType(resultType);
            mapper.setQueryString(sql);
            // 对象添加到集合
            mappers.put(namespace+"."+id,mapper);
        }
    }



    //创建连接池并返回
    private DruidDataSource ds;
    public DataSource getDataSource(){
        //没有创建，创建连接
        if (ds == null){
            ds = new DruidDataSource();
            ds.setDriverClassName(this.username);
            ds.setPassword(this.password);
            ds.setUrl(this.url);
        }
        return ds;

    }
}
