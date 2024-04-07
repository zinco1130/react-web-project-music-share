import React, {useEffect, useState} from "react";
import "../css/SayeonWri.css";
import SayeonRect from "./SayeonRect";

function SayeonWri() {
    const [data, setData] = useState([]);
    useEffect(() => {
        fetch('http://localhost:8080/api/sayeon', {
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
                console.log(data)
                data = data.slice(4, 8);
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
                        <SayeonRect key={item.id} keys={index} text={item.story} music={item.music} items={item.commentList} />
                    );
                })
            }
        </div>
    );
}

export default SayeonWri;
