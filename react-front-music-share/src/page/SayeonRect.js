import {React, useState} from "react";
import "../css/SayeonRect.css";
import CustomModal from "../component/Modal";
import {Link, useLocation} from "react-router-dom";
import List from "../component/List";
import SearchComponent from "../component/SearchComponent";

function SayeonRect({picture, text, children, items, keys, music}) {
    const location = useLocation();
    const [isModalOpen, setModalOpen] = useState(false);

    const openModal = () => setModalOpen(true);
    const closeModal = () => setModalOpen(false);

    const classNamesConfig = [
        {
            paths: ['/', '/musicReco'],
            classNames: {
                piText: ['piText'],
                syRectBtm: ['syRectBtm'],
            },
        },
        {
            paths: ['/sayeonWri', '/musicLink'],
            classNames: {
                piText: ['piTextWrite', 'piText'],
                syRectBtm: ['', 'syRectBtm'],
            },
        },
    ];

    const sharePath = ['/', '/musicReco', '/musicLink'];

    const getClassNames = (divKey) => {
        for (const config of classNamesConfig) {
            if (config.paths.includes(location.pathname)) {
                return config.classNames[divKey]?.join(' ') || '';
            }
        }
        return '';
    };

    return (
        <div className={"syRect"}>
            <div className={getClassNames('piText')}>
                {
                    location.pathname === '/musicLink' &&
                    <img className="picture" src={(picture != null ? picture : "https://placehold.co/400")} alt={"pic"}></img>
                }
                <div>
                    {location.pathname === '/sayeonWri' && music &&
                        <div className={"swTitle"}>
                            <span>{music.title} <br /> {music.singer}</span>
                        </div>
                    }
                    {text}
                </div>
            </div>
            <div className={getClassNames("syRectBtm")}>
                {children}
                {
                    <>
                        {items && items.length > 0 && items[0] &&
                            <List items={items}/>
                        }
                        {(sharePath.includes(location.pathname)) &&
                            <button onClick={openModal} data-tab={keys}>음악 공유하기</button>
                        }
                        <CustomModal isOpen={isModalOpen} closeModal={closeModal}>
                            <SearchComponent closeModal={closeModal} keys={keys}/>
                        </CustomModal>
                    </>
                }
                {
                    location.pathname === '/sayeonWri' &&
                    <>
                        <div className={"swContainer"}>
                            <Link to={"/writepage?tab=" + keys} className={"swBtn"}>
                                <button className={"custom-btn btn-8"}><span>사연 작성하기</span></button>
                            </Link>
                        </div>
                    </>
                }
            </div>
        </div>
    );
}

export default SayeonRect;
