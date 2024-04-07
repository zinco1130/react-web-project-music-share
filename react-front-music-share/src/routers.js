import React from "react";
import {Route, Routes} from "react-router-dom";
import SayeonWri from "./page/SayeonWri";
import Login from "./page/Login";
import MusicLink from "./page/MusicLink";
import MusicReco from "./page/MusicReco";
import App from "./App";
import Write from "./page/write";
import Layout from "./component/Layout";
import Register from "./page/Register";

const Router = () => {
    return (
        <Layout>
            <Routes>
                <Route exact path="/login" element={<Login/>}/>
                <Route exact path="/register" element={<Register/>}/>
                <Route path="/" element={<App/>}/>
                <Route path="/musicReco" element={<MusicReco/>}/>
                <Route path="/sayeonWri" element={<SayeonWri/>}/>
                <Route path="/musicLink" element={<MusicLink/>}/>
                <Route path="/writepage" element={<Write/>}/>
            </Routes>
        </Layout>
    );
};
export default Router;
