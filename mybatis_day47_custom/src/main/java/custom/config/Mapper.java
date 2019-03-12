package custom.config;

/**
 * 封装映射文件信息
 <mapper namespace="com.itheima.dao.IUserDao">
     <select id="findAll" resultType="com.itheima.model.User">
        SELECT * FROM USER;
     </select>
 </mapper>
 */
public class Mapper {
    private String namespace;
    private String id;
    private String resultType;
    private String queryString;

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }
7
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }
}
