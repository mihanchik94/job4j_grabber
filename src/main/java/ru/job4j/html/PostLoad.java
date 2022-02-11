package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import ru.job4j.grabber.utils.SqlRuDateTimeParser;
import ru.job4j.model.Post;

import java.io.IOException;

public class PostLoad {
    public static Post loadPost(String link) throws IOException {
        SqlRuDateTimeParser parser = new SqlRuDateTimeParser();
        Document doc = Jsoup.connect(link).get();
        Elements row = doc.select(".msgTable");
        String[] titleArray = row.get(0).child(0).child(0).text().split("\\[");
        String description = doc.select(".msgBody").get(1).text();
        String[] dateTime = row.get(0).child(0).child(2).child(0).text().split("\\[");
        return Post.getPost(0, titleArray[0], link, description, parser.parse(dateTime[0].substring(0, dateTime[0].length() - 1)));
    }


    public static void main(String[] args) throws Exception {
        System.out.println(loadPost("https://www.sql.ru/forum/1325330/lidy-be-fe-senior-cistemnye-analitiki-qa-i-devops-moskva-do-200t").toString());
    }
}

