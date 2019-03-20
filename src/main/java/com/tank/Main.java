package com.tank;

import com.google.common.annotations.VisibleForTesting;
import org.iq80.leveldb.DB;
import org.iq80.leveldb.DBFactory;
import org.iq80.leveldb.Options;
import org.iq80.leveldb.WriteOptions;
import org.iq80.leveldb.impl.Iq80DBFactory;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author tangkun
 * @date 2018-11-07
 */
public class Main {

    DB db = null;

    String preKey = "key:";

    @Before
    public void initDB() throws IOException {
        Charset charset = Charset.forName("utf-8");
        String path = "/data/leveldb";
        //init
        DBFactory factory = Iq80DBFactory.factory;
        File dir = new File(path);

        Options options = new Options().createIfMissing(true);
        db = factory.open(dir,options);
    }


    @Test
    public  void bigPut() throws IOException {

        //write后立即进行磁盘同步写
        WriteOptions writeOptions = new WriteOptions().sync(true);//线程安全
        String str = "abcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcd";
        long time = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            db.put(("key:"+i).getBytes(),str.getBytes(),writeOptions);
        }
        System.out.println("耗时"+(System.currentTimeMillis()-time));
        System.exit(1);
    }

    @Test
    public void get(){

        System.out.println(new String(db.get((preKey+"99999").getBytes())));
    }

    @Test
    public void testSet() throws InterruptedException {
        int wordSize = 1000000;
        String keyPre = "value:value:value:value:value:value:value:value:value:value:value:";
        List set = new ArrayList<>() ;
        long time = System.currentTimeMillis();
        for (int i = 0; i < wordSize; i++) {
            set.add(keyPre+i);
        }
        System.out.println("添加时间："+(System.currentTimeMillis()-time));
        time=System.currentTimeMillis();
        System.out.println(set.contains(keyPre+(wordSize+2)));
        System.out.println("查找时间："+(System.currentTimeMillis()-time));
        TimeUnit.SECONDS.sleep(30);
    }
}
