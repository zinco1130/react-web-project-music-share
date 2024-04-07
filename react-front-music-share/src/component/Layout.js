import "../App.css";
import React from "react";
import Side from "../component/side";
import {Link, useLocation, useNavigate} from "react-router-dom";

export default function Layout({children}) {
    const location = useLocation();
    const movePage = useNavigate();

    const path = ['/']

    function gowrite() {
        movePage("/writepage");
    }

    return (
        <div className="back">
            <Side/>
            <div className="main">
                <Link to={"/"}>
                    <div className="upperBar">사연과 관련된 음악 추천 라디오</div>
                </Link>
                {path.includes(location.pathname) &&
                    <div className="sayeon">
                        <button className={"custom-btn btn-8"} onClick={gowrite}><span>사연 작성하기</span></button>
                    </div>
                }
                {children}
            </div>
        </div>
    )
}