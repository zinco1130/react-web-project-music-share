package com.zinco.react_back.utils;

import com.zinco.react_back.entity.Music;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class MusicDataFetcher {

    private static final String API_URL = "http://www.maniadb.com/api/search/metallica/?sr=song&display=10&key=example&v=0.5";
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistence-unit");
    private static final EntityManager em = emf.createEntityManager();

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(API_URL, String.class);

        // Assume parseXMLToMusic is a method you create to parse the XML and return a Music entity
        List<Music> musicList = parseXMLToMusic(response);

        em.getTransaction().begin();
        for (Music music : musicList) {
            em.persist(music);  // Persist each Music entity individually
        }
        em.getTransaction().commit();
    }

    // ... other methods like parseXMLToMusic ...

    public static List<Music> parseXMLToMusic(String xmlResponse) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        List<Music> musicList = new ArrayList<>();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(xmlResponse)));
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("item");  // Each song is represented within an <item> tag

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    Music music = new Music();
                    music.setTitle(element.getElementsByTagName("title").item(0).getTextContent());  // Getting the title of the song
                    NodeList artistNodeList = element.getElementsByTagName("maniadb:artist");
                    if (artistNodeList.getLength() > 0) {
                        Element artistElement = (Element) artistNodeList.item(0);
                        music.setSinger(artistElement.getElementsByTagName("name").item(0).getTextContent());  // Getting the name of the artist
                    }
                    musicList.add(music);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return musicList;  // Return a list of Music entities
    }
}
