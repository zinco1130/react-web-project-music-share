import React from "react";
import { Link } from "react-router-dom";
import "../css/side.css";

const Side = () => {
  return (
    <div className="side">
      <Link to="/musicReco">음악 추천</Link>
      <Link to="/sayeonWri">사연 작성</Link>
      <Link to="/musicLink">음악 공유</Link>
      <Link to="/login">로그인</Link>
    </div>
  );
};
export default Side;
