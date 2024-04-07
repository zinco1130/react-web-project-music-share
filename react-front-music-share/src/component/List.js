import React from 'react';
import {useLocation} from "react-router-dom";

function List({items}) {
    const location = useLocation();
    return (
        <>
            {
                location.pathname !== '/sayeonWri' &&
                items.map((item, index) => (
                    <li key={index}>{item.title} - {item.singer}</li>
                ))
            }
            {
                location.pathname === '/sayeonWri' &&
                items.map((item, index) => (
                    <li key={index}>{
                        (item.comment.length > 20) ?
                            (((item.comment).substring(0, 20 - 3)) + '...') :
                            item.comment
                    }</li>
                ))
            }
        </>
    );
}

export default List;
