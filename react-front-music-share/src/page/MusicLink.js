import React, {useEffect, useState} from "react";
import "../css/MusicLink.css";
import SayeonRect from "./SayeonRect";
import Pic1 from "../pic1.jpeg";
import Pic2 from "../pic2.jpeg";
import Pic3 from "../pic3.jpeg";

function MusicLink() {
    const [data, setData] = useState([]);

    useEffect(() => {
        fetch('http://localhost:8080/api/musicReco', {
            method: 'GET',
            credentials: 'include',  // Required for cookies, authorization headers with HTTPS
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok ' + response.statusText);
                }
                console.log(response)
                return response.json();
            })
            .then(data => {
                data = data.slice(8, 12);
                setData(data);
            })
            .catch(error => {
                console.error('There has been a problem with your fetch operation:', error);
            });
    }, []);

    return (
        <div className={"flex"}>
            {
                data.map((item, index) => {
                    return (
                        <SayeonRect key={item.id} keys={index} text={item.content} items={item.musicList} />
                    );
                })
            }
        </div>
    );
}

export default MusicLink;
