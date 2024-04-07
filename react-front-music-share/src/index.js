import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import reportWebVitals from "./reportWebVitals";
import {BrowserRouter} from "react-router-dom";
import Router from "./routers";
import CacheBuster from "react-cache-buster";

const root = ReactDOM.createRoot(document.getElementById("root"));

const isProduction = process.env.NODE_ENV === 'production';

root.render(
    <CacheBuster
        currentVersion={'1.0.0'} //An arbitrary string that gets appended to your source files to indicate a version.
        isEnabled={isProduction} //If false, the library is disabled.
        isVerboseMode={false} //If true, the library writes verbose logs to console.
        metaFileDirectory={'.'} //If public assets are hosted somewhere other than root on your server.
    >
        <BrowserRouter>
            <Router />
        </BrowserRouter>
    </CacheBuster>
);

reportWebVitals();
