package org.apache.tool;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;

/**
 * 采用mongodb的GridFS操作大文件
 * Created by fuli.shen on 2016/11/11.
 */
public class MongodbObjectGridFS {
    public static void main(String[] args) throws Exception {
        Person person = new Person("fuli.shen", 10);
        String key = "fuli";
        String fileName = "fuli.shen information";
        byte[] serialize = JSerializeTool.serialize(person);
        MongoClient mongoClient = new MongoClient("10.1.1.8", 27017);

        MongodbObjectGridFS mongodbGridFS = new MongodbObjectGridFS("gridfs", mongoClient);
        //把文件保存到gridfs中，并以文件的md5值为id
        mongodbGridFS.save(serialize, key, fileName);
        //据文件名从gridfs中读取到文件
        GridFSDBFile gridFSDBFile = mongodbGridFS.getById(key);
        if (gridFSDBFile != null) {
            System.out.println("filename:" + gridFSDBFile.getFilename());
            System.out.println("md5:" + gridFSDBFile.getMD5());
            System.out.println("length:" + gridFSDBFile.getLength());
            System.out.println("uploadDate:" + gridFSDBFile.getUploadDate());
            System.out.println("--------------------------------------");
            //gridFSDBFile.writeTo(System.out);
            Object unserialize = JSerializeTool.unserialize(IOUtils.toByteArray(gridFSDBFile.getInputStream()));
            System.out.println(unserialize);
        }

    }


    private MongoClient mongoClient;
    private DB db;
    private GridFS gridFS;
    private String dbName;
    private String host;
    private int port;

    /**
     * 用给出的id，保存文件，透明处理已存在的情况
     * <p>
     * id 可以是string，long，int，org.bson.types.ObjectId 类型
     *
     * @param in
     * @param id
     */
    public void save(InputStream in, Object id, String fileName) {
        DBObject query = new BasicDBObject("_id", id);
        GridFSDBFile gridFSDBFile = gridFS.findOne(query);
        if (gridFSDBFile != null) {
            return;
        }
        GridFSInputFile gridFSInputFile = gridFS.createFile(in);
        gridFSInputFile.setId(id);
        gridFSInputFile.setFilename(fileName);
        gridFSInputFile.save();
    }

    public void save(byte[] data, Object id, String fileName) {
        DBObject query = new BasicDBObject("_id", id);
        GridFSDBFile gridFSDBFile = gridFS.findOne(query);
        if (gridFSDBFile != null) {
            return;
        }
        GridFSInputFile gridFSInputFile = gridFS.createFile(data);
        gridFSInputFile.setId(id);
        gridFSInputFile.setFilename(fileName);
        gridFSInputFile.save();
    }

    /**
     * 据id返回文件
     *
     * @param id
     * @return
     */
    public GridFSDBFile getById(Object id) {
        DBObject query = new BasicDBObject("_id", id);
        GridFSDBFile gridFSDBFile = gridFS.findOne(query);
        return gridFSDBFile;
    }


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public MongodbObjectGridFS(String dbName, MongoClient mongoClient) {
        this.mongoClient = mongoClient;
        this.db = mongoClient.getDB(dbName);
        this.gridFS = new GridFS(db);
        this.dbName = dbName;
    }
}
