package com.bluesgao.algorithm;

import java.util.*;

/**
 * 统计高考名次
 * 假设高考总分750，2019年参加高考人数100w，从中找出小明（555分，201912222222）的排名。
 */
public class FindRank {
    //分数：学号
    private static HashMap<Integer, Student> allStudentScoreMap = new LinkedHashMap<Integer, Student>(750);

    private static Integer fingRankByScore(Student s) {
        long start = System.currentTimeMillis();
         allStudentScoreMap.values().toArray();
        for (int i=0; i<((Collection) collections).size(); i++){
            /*if(((Collection) collections).contains(s)){
                return i;
            }*/

        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        return null;
    }

    private static void initData(long num) {
        for (int i = 0; i < num; i++) {
            int score = new Random().nextInt(750);
            String no = "2019" + i;
            Student s = new Student(no, score);
            allStudentScoreMap.put(s.score, s);
        }
    }

    public static void main(String[] args) {
        initData(100000);
        System.out.println(FindRank.fingRankByScore(new Student("2019122222", 555)));
    }


    static class Student {
        public Student(String no, Integer score) {
            this.no = no;
            this.score = score;
        }

        String no;
        Integer score;
    }
}
