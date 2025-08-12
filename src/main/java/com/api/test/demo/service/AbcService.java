package com.api.test.demo.service;

import com.api.test.demo.NoticiaDTO;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Selector;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AbcService {

    @Value("${app.abc.base-url:https://www.abc.com.py/buscador/?query=}")
    private String baseUrl;

    @Value("${app.http.timeout.ms:12000}")
    private int timeoutMs;

    @Value("${app.sel.card:#resultdata > div > a}")
    private String selCard;

    @Value("${app.sel.title:.queryly_item_title}")
    private String selTitle;

    @Value("${app.sel.summary:.queryly_item_description}")
    private String selSummary;

    @Value("${app.sel.img:.queryly_advanced_item_imagecontainer}")
    private String selImg;

    public List<NoticiaDTO> buscar(String q) {
        List<NoticiaDTO> out = new ArrayList<>();

        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new", "--no-sandbox", "--disable-gpu",
                "--window-size=1366,768", "--lang=es-ES",
                "--user-agent=Mozilla/5.0");
        WebDriver driver = new ChromeDriver(options);

        String url = baseUrl + q;
        try {
            driver.get(url);

            new WebDriverWait(driver, Duration.ofMillis(Math.max(6000, timeoutMs)))
                    .until(ExpectedConditions.or(
                            ExpectedConditions.presenceOfElementLocated(By.cssSelector("#resultdata")),
                            ExpectedConditions.presenceOfElementLocated(By.cssSelector(selCard))
                    ));

            Document doc = Jsoup.parse(driver.getPageSource(), url);

            System.out.println("selCard=" + selCard);
            System.out.println("selTitle=" + selTitle);
            System.out.println("selSummary=" + selSummary);
            System.out.println("selImg=" + selImg);

            for (Element a : doc.select(safeCss(selCard, "#resultdata > div > a"))) {
                String enlace  = a.absUrl("href");
                String titulo  = text(safeSelectFirst(a, selTitle));
                String resumen = text(safeSelectFirst(a, selSummary));

                String enlaceFoto = "";
                Element imgBox = safeSelectFirst(a, selImg);
                if (imgBox != null) {
                    Element img = imgBox.selectFirst("img");
                    if (img != null) enlaceFoto = img.absUrl("src");
                    if (enlaceFoto.isBlank()) enlaceFoto = firstFromSrcset(imgBox);
                    if (enlaceFoto.isBlank()) enlaceFoto = urlFromStyle(imgBox.attr("style"), url);
                }

                String fecha = Optional.ofNullable(safeSelectFirst(a, "time[datetime]"))
                        .map(t -> t.attr("datetime").trim())
                        .filter(s -> !s.isBlank())
                        .orElse(OffsetDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));

                if (!enlace.isBlank() || !titulo.isBlank()) {
                    out.add(new NoticiaDTO(fecha, enlace, enlaceFoto, titulo, resumen));
                }
            }
        } catch (TimeoutException te) {
            System.out.println("Timeout esperando resultados en: " + url);
        } finally {
            driver.quit();
        }
        return out;
    }

    private static String text(Element el) {
        return el == null ? "" : el.text().trim();
    }

    private static Element safeSelectFirst(Element scope, String css) {
        if (scope == null || css == null || css.isBlank()) return null;
        try {
            return scope.selectFirst(css);
        } catch (Selector.SelectorParseException e) {
            return null; 
        }
    }

    private static String safeCss(String css, String fallback) {
        return (css == null || css.isBlank()) ? fallback : css;
    }

    private static String firstFromSrcset(Element scope) {
        Element source = scope.selectFirst("source[srcset]");
        if (source == null) return "";
        String srcset = source.attr("srcset");
        int comma = srcset.indexOf(',');
        return (comma > 0 ? srcset.substring(0, comma) : srcset).trim();
    }

    private static String urlFromStyle(String style, String baseUrl) {
        if (style == null) return "";
        Matcher m = Pattern.compile("url\\(['\"]?(.*?)['\"]?\\)").matcher(style);
        if (!m.find()) return "";
        String raw = m.group(1);
        try { return new URL(new URL(baseUrl), raw).toString(); }
        catch (Exception e) { return raw; }
    }
}
