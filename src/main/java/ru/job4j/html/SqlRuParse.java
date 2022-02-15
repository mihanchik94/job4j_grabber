package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.grabber.Parse;
import ru.job4j.grabber.utils.DateTimeParser;
import ru.job4j.model.Post;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class SqlRuParse implements Parse {
    private final DateTimeParser dateTimeParser;

    public SqlRuParse(DateTimeParser dateTimeParser) {
        this.dateTimeParser = dateTimeParser;
    }

    @Override
    public List<Post> list(String link) {
        List<Post> posts = new ArrayList<>();
        for (int page = 1; page <= 5; page++) {
            Document doc = null;
            try {
                doc = Jsoup.connect(link + "/" + page).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert doc != null;
            Elements row = doc.select(".postslisttopic");
            for (Element td : row) {
                Element href = td.child(0);
                if (href.text().toLowerCase().contains("java")
                        && !href.text().toLowerCase().contains("javascript")) {
                    posts.add(detail(href.getAllElements().attr("href")));
                }
            }
        }
        return posts;
    }

    @Override
    public Post detail(String link) {
        Document doc = null;
        try {
            doc = Jsoup.connect(link).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert doc != null;
        String title = doc.select(".messageHeader").get(0).ownText();
        String description = doc.select(".msgBody").get(1).text();
        String dateTime = doc.select(".msgFooter").get(0).text().replaceFirst("\\[.*".trim(), "");
        return Post.getPost(0, title, link, description, dateTimeParser.parse(dateTime));
    }
}