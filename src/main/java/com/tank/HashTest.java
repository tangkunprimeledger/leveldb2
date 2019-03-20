package com.tank;

import java.io.*;
import java.util.*;

/**
 * @author tangkun
 * @date 2018-11-09
 */
public class HashTest {

    public static void main(String[] args) {
        //createFile();
        //sortByValue(getSortTable());
        try {
            sortByTop(getSortTable());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void createFile() throws IOException {
        //生成1000万的字符串
        int size = 10000000;
        File file = new File("/data/file/query.txt");
        file.deleteOnExit();
        file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        for (int i = 0; i < size; i++) {
            //随机生成的字符串在1-300万之间，所以去重后字符串不超过300万
            fos.write((new Random().nextInt(3000000)+"\n").getBytes());
        }
        fos.flush();
        fos.close();
    }

    public static Hashtable<String,Integer> getSortTable() throws IOException {

        File file = new File("/data/file/query.txt");

        InputStreamReader reader = new InputStreamReader(new FileInputStream(file));

        BufferedReader br = new BufferedReader(reader);

        String line;
        Hashtable<String,Integer> hashTable = new Hashtable();
        while ((line = br.readLine()) != null){
           Integer count = hashTable.getOrDefault(line,0);
           hashTable.put(line,++count);
        }
        System.out.println(hashTable.size());
        return hashTable;
    }

    /**
     * 通过java 自带 Collections sort方法排序
     * @param table
     */
    public static void sortByCollections(Hashtable table){

        long time = System.currentTimeMillis();
        List<Map.Entry<String,Integer>> list = new ArrayList<>(table.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String,Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue() > o1.getValue() ? 1 : o2.getValue() == o1.getValue() ? 0 : -1;
            }
        });

        list.subList(0,10).forEach(System.out::println);

        System.out.println("times:"+(System.currentTimeMillis()-time));
    }

    /**
     * 先获取10个元素排序后（降序）放到top10集合中，
     * 然后将剩余待排序元素与top10集合中最后一个元素（最小元素）比较，如小于或等于则跳过进行下一个元素比较
     * 如大于，则替换top10集合中最后一个元素为当前元素，且依次检查后一个元素是否比前一个元素大，则交换位置
     * @param table
     */
    public static void sortByTop(Hashtable<String,Integer> table){
        long time = System.currentTimeMillis();
        List<Map.Entry<String,Integer>> top10 = new ArrayList<>(10);
        for (Map.Entry<String,Integer> en : table.entrySet()) {
            if(top10.size() == 10){
                break;
            }
            top10.add(en);
        }
        Collections.sort(top10, new Comparator<Map.Entry<String,Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue() > o1.getValue() ? 1 : o2.getValue().equals(o1.getValue()) ? 0 : -1;
            }
        });


        int index = 0;
        for (Map.Entry<String,Integer> en : table.entrySet()) {
           // System.out.println("xxxxxxxx"+en);

            //跳过包含在top10中的元素

            if(index < 10){
                index++;
                continue;
            }

            //如果小于或等于top10中最小的元素则直接跳过
            Integer min = top10.get(9).getValue();
            if(en.getValue() <= min){
                continue;
            }
            //如果大于top10最小元素则替换当前元素到top10位置
            top10.set(9,en);

            sortTop10(top10);

        }
       top10.forEach(System.out::println);
       System.out.println("time:"+(System.currentTimeMillis()-time));

    }

    /**
     * 排序top10中看是否需要交换位置
     * @param top10
     */
    public static void sortTop10(List<Map.Entry<String,Integer>> top10){

        int index = 9;
        while (top10.get(index).getValue() >  top10.get(index-1).getValue() && index > 0){
            //交换位置
            Map.Entry<String,Integer> tmp = top10.get(index);

            top10.set(index,top10.get(index-1));
            top10.set(index-1,tmp);
            if (--index == 0){
                break;
            }
        }
    }
}
