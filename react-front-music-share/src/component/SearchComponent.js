import React, { useState } from 'react';
import SearchIcon from "@mui/icons-material/Search";
import {alpha, Button, InputBase, styled} from "@mui/material";
import {useLocation, useNavigate} from "react-router-dom";

const Search = styled('div')(({theme}) => ({
    position: 'relative',
    borderRadius: theme.shape.borderRadius,
    backgroundColor: alpha(theme.palette.common.black, 0.15),
    '&:hover': {
        backgroundColor: alpha(theme.palette.common.black, 0.25),
    },
    marginLeft: 0,
    width: '85%',
}));


const SearchIconWrapper = styled('div')(({theme}) => ({
    padding: theme.spacing(0, 2),
    height: '100%',
    position: 'absolute',
    pointerEvents: 'none',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
}));

const StyledInputBase = styled(InputBase)(({theme}) => ({
    color: 'inherit',
    width: '100%',
    '& .MuiInputBase-input': {
        padding: theme.spacing(1, 1, 1, 0),
        // vertical padding + font size from searchIcon
        paddingLeft: `calc(1em + ${theme.spacing(4)})`,
        transition: theme.transitions.create('width'),
        width: '100%',
    },
}));


function SearchComponent({closeModal, keys}) {
    const [query, setQuery] = useState('');
    const [results, setResults] = useState([]);

    const location = useLocation();
    const navigate = useNavigate();

    const handleSearch = async () => {
        if (query) {
            fetch(`http://localhost:8080/api/search?title=${query}`, {
                method: 'GET',
                credentials: 'include',  // Required for cookies, authorization headers with HTTPS
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                },
            })
                .then(response => response.json())
                .then(data => {
                    setResults(data);
                })
                .catch(error => {
                    console.error('There has been a problem with your fetch operation:', error);
                })
        }
    };

    function addMusic(music) {
        fetch(`http://localhost:8080/api/addMusic?uri=` + location.pathname + `&key=` + keys, {
            method: 'POST',
            credentials: 'include',  // Required for cookies, authorization headers with HTTPS
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(music),
        })
            .then(response => {
                if (response.status === 401 || response.status === 403) {
                    navigate('/login');
                }
                if (!response.ok) {
                    throw new Error('Network response was not ok ' + response.statusText);
                }
                return response;
            })
            .then(data => {
                closeModal();
                console.log(data)
            })
            .catch(error => {
                console.error('There has been a problem with your fetch operation:', error);
            });
    }

    return (
        <div>
            <div className={"modalSearch"} data-tab={keys}>
                <Search>
                    <SearchIconWrapper>
                        <SearchIcon/>
                    </SearchIconWrapper>
                    <StyledInputBase
                        placeholder="Search…"
                        inputProps={{'aria-label': 'search'}}
                        value={query}
                        onChange={(e) => setQuery(e.target.value)}
                    />
                </Search>
                <Button variant="contained" onClick={handleSearch}>검색</Button>
            </div>
            <div className={"modalResult"}>
                {results.map((result, index) => (
                    <div key={index} className={"songResult"} onClick={() => addMusic(result)}>
                        <div>Title: {result.title}</div>
                        <div>Singer: {result.singer}</div>
                    </div>
                ))}
            </div>
        </div>
    );
}

export default SearchComponent;
