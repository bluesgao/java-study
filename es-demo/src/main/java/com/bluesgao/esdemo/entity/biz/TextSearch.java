package com.bluesgao.esdemo.entity.biz;

/**
 * @ClassName：TextSearch
 * @Description：
 * @Author：bluesgao
 * @Date：2021/1/26 10:14
 **/
public class TextSearch {
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TextSearch{");
        sb.append("title='").append(title).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
