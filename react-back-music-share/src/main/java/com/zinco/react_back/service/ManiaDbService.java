package com.zinco.react_back.service;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.zinco.react_back.entity.Music;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.zinco.react_back.utils.utils.decodeHtmlEntities;

@Service
public class ManiaDbService {
    private final RestTemplate restTemplate;

    public ManiaDbService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public static ManiaDbApiResponse parseXml(String xmlData) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        return xmlMapper.readValue(xmlData, ManiaDbApiResponse.class);
    }

    public List<Music> getMusicInfo(String musicTitle) {
        String url = "http://www.maniadb.com/api/search/{musicTitle}/?apikey={apiKey}&sr=song&display=20&v=0.5";
        String apiKey = "powipod695@newnime.com";
        Map<String, String> params = Map.of("musicTitle", musicTitle, "apiKey", apiKey);

        try {
            // Get the XML response as a String
            String responseXml = restTemplate.getForObject(url, String.class, params);

            // Create an XmlMapper object
            XmlMapper xmlMapper = new XmlMapper();

            // Parse the XML response into your ManiaDbApiResponse class
            ManiaDbApiResponse maniaDbApiResponse = xmlMapper.readValue(responseXml, ManiaDbApiResponse.class);

            // Convert the ManiaDbApiResponse object to a List of Music objects
            List<Music> musicList = convertToMusicList(maniaDbApiResponse);

            return musicList;
        } catch (Exception e) {
            // Handle any exceptions that occur
//            return null;
            throw new RuntimeException("Failed to parse XML response: " + e.getMessage(), e);
        }
    }

    private List<Music> convertToMusicList(ManiaDbApiResponse maniaDbApiResponse) {
        List<Music> musicList = new ArrayList<>();
        for (MusicItem musicItem : maniaDbApiResponse.getChannel().getItem()) {
            Music music = new Music();
            music.setTitle(decodeHtmlEntities(musicItem.getTitle()));
            if (musicItem.getTrackArtists() != null && musicItem.getTrackArtists().getArtist() != null) {
                music.setSinger(musicItem.getTrackArtists().getArtist().getName());
            }
            // Other fields as necessary...
            musicList.add(music);
        }
        return musicList;
    }


}
