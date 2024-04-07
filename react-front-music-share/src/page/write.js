import React, {useState} from "react";
import "../css/write.css";
import {useLocation, useNavigate, useSearchParams} from "react-router-dom";

function Write({keys}) {
    const [data, setData] = useState("");
    const location = useLocation();
    const [searchParams] = useSearchParams();
    const navigate = useNavigate();

    function submitSayeon() {
        fetch('http://localhost:8080/api/sayeonWrite?uri=' + location.pathname + '&key=' + searchParams.get("tab"), {
            method: 'POST',
            credentials: 'include',  // Required for cookies, authorization headers with HTTPS
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                comment: data,
            }),
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
                if (data.success) {
                    navigate('/sayeonWri');
                }
            })
            .catch(error => {
                console.error('There has been a problem with your fetch operation:', error);
            })
    }

    return (
        <div className={"jcCenter"}>
            <div className="writeBoard">
                {/*<div className="artBoard">*/}
                {/*    <div className="artScene"></div>*/}
                {/*    <div className={"artSceneBtm"}>*/}
                {/*        <label className="inputBtn" htmlFor="input-file">*/}
                {/*            파일 및 사진 업로드*/}
                {/*        </label>*/}
                {/*        <input type="file" id="input-file" style={{display: "none"}}/>*/}
                {/*    </div>*/}
                {/*</div>*/}
                <div className="textBoard">
                    <textarea className="textScene"
                              value={data}
                              onChange={e => setData(e.target.value)}></textarea>
                    <div className={"textSceneBtm"}>
                        <button onClick={submitSayeon}>등록</button>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Write;
