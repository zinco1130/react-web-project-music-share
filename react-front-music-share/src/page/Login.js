import React, {useState} from 'react';
import {Button, TextField, Paper, Typography, Alert} from '@mui/material';
import '../css/Login.css';
import {Link, useNavigate} from "react-router-dom";

function Login() {
    const [id, setId] = useState('');
    const [password, setPassword] = useState('');
    const [errorMessage, setErrorMessage] = useState('');

    const spacingStyle = {marginBottom: '10px'};

    const navigate = useNavigate();
    const handleLogin = () => {
        fetch('http://localhost:8080/user/login', {
            method: 'POST',
            credentials: 'include',  // Required for cookies, authorization headers with HTTPS
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
            },
            body: JSON.stringify({
                userId: id,
                password: password,
            }),
        })
            .then(response => {
                if (response.status === 401) {
                    setErrorMessage('ID or password is incorrect');
                }
                console.log(response)
                return response.json();
            })
            .then((response) => {
                const data = response;

                if (response.status === 200 || data.message === 'Login successful') {
                    navigate('/musicReco');
                    // Redirect to another page, or update the state to indicate a successful login
                } else {
                    setErrorMessage(data.message);
                }
            })
            .catch((error) => {
                console.error('Error:', error);
            });
    };

    return (
        <div className="container">
            <Paper elevation={3} className="paper" style={spacingStyle}>
                <Typography variant="h4" className="title" style={spacingStyle}>Login</Typography>
                <TextField
                    label="ID"
                    variant="outlined"
                    className="text-field"
                    value={id}
                    style={spacingStyle}
                    onChange={(e) => setId(e.target.value)}
                />
                <TextField
                    label="Password"
                    type="password"
                    variant="outlined"
                    className="text-field"
                    value={password}
                    style={spacingStyle}
                    onChange={(e) => setPassword(e.target.value)}
                />
                <Button
                    variant="contained"
                    color="primary"
                    className="button"
                    style={spacingStyle}
                    onClick={handleLogin}
                >
                    Login
                </Button>
                {errorMessage && (
                    <Alert severity="error" sx={{mt: 2}}>
                        {errorMessage}
                    </Alert>
                )}
                <Button
                    variant="text"
                    color="primary"
                    component={Link}
                    to="/register"
                    className="button"
                >
                    Don't have an account? Register
                </Button>
            </Paper>
        </div>
    );
}

export default Login;
