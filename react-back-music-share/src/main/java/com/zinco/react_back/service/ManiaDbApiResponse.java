package com.zinco.react_back.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "rss")
public class ManiaDbApiResponse {

    @JacksonXmlProperty(localName = "channel")
    private Channel channel;
}

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
class Channel {

    @JacksonXmlProperty(localName = "title")
    private String title;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "item")
    private List<MusicItem> item;
}


@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
class MusicItem {

    @JacksonXmlProperty(localName = "title", isAttribute = false)
    private String title;

    @JacksonXmlProperty(localName = "runningtime", isAttribute = false)
    private String runningTime;

    @JacksonXmlProperty(localName = "link", isAttribute = false)
    private String link;

    @JacksonXmlProperty(localName = "pubDate", isAttribute = false)
    private String pubDate;

    @JacksonXmlProperty(namespace = "maniadb", localName = "trackartists")
    private TrackArtists trackArtists;
}

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
class TrackArtists {

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(namespace = "maniadb", localName = "artist")
    private TrackArtist artist;
}


@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
class TrackArtist {

    @JacksonXmlProperty(localName = "name", isAttribute = false)
    private String name;
}


