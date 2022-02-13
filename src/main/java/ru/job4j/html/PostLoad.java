package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import ru.job4j.grabber.utils.SqlRuDateTimeParser;
import ru.job4j.model.Post;

import java.io.IOException;

public class PostLoad {
    public static Post loadPost(String link) throws IOException {
        SqlRuDateTimeParser parser = new SqlRuDateTimeParser();
        Document doc = Jsoup.connect(link).get();
        String titleArray = doc.select(".messageHeader").get(0).ownText();
        String description = doc.select(".msgBody").get(1).text();
        String dateTime = doc.select(".msgFooter").get(0).text().substring(0, 16);
        return Post.getPost(0, titleArray, link, description, parser.parse(dateTime));
    }


    public static void main(String[] args) throws Exception {
        System.out.println(loadPost("https://www.sql.ru/forum/1325330/lidy-be-fe-senior-cistemnye-analitiki-qa-i-devops-moskva-do-200t").toString());
    }
}

