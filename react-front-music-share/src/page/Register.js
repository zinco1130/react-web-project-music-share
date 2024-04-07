import React, {useState} from 'react';
import {Button, TextField, Paper, Typography, Alert} from '@mui/material';
import '../css/Login.css';
import {useNavigate} from "react-router-dom";

function Register() {
    const [id, setId] = useState('');
    const [nickname, setNickname] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [errorMessage, setErrorMessage] = useState('');

    const spacingStyle = {marginBottom: '10px'};

    const navigate = useNavigate();

    const handleRegister = async () => {
        if (password !== confirmPassword) {
            setErrorMessage('Passwords do not match');
            return;
        }

        fetch('http://localhost:8080/user/register', {
            method: 'POST',
            credentials: 'include',  // Required for cookies, authorization headers with HTTPS
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                userId: id,
                password: password,
                nickname: nickname,
            }),
        })
            .then(response => {
                if (response.status === 400) {
                    setErrorMessage('ID already exists');
                }
                return response.json();
            })
            .then((response) => {
                const data = response;

                console.log(data);
                if (response.status === 200 || data.message === 'Registration successful') {
                    navigate('/musicReco');
                    // Redirect to another page, or update the state to indicate a successful registration
                } else {
                    setErrorMessage(data.message);
                }
            });
    };

    return (
        <div className="container">
            <Paper elevation={3} className="paper" style={spacingStyle}>
                <Typography variant="h4" className="title" style={spacingStyle}>Register</Typography>
                <TextField
                    label="ID"
                    variant="outlined"
                    className="text-field"
                    required
                    value={id}
                    style={spacingStyle}
                    onChange={(e) => setId(e.target.value)}
                />
                <TextField
                    variant="outlined"
                    required
                    fullWidth
                    id="nickname"
                    label="Nickname"
                    name="nickname"
                    value={nickname}
                    style={spacingStyle}
                    onChange={e => setNickname(e.target.value)}
                />
                <TextField
                    label="Password"
                    type="password"
                    variant="outlined"
                    className="text-field"
                    required
                    value={password}
                    style={spacingStyle}
                    onChange={(e) => setPassword(e.target.value)}
                />
                <TextField
                    label="Confirm Password"
                    type="password"
                    variant="outlined"
                    className="text-field"
                    required
                    value={confirmPassword}
                    style={spacingStyle}
                    onChange={(e) => setConfirmPassword(e.target.value)}
                />
                <Button
                    variant="contained"
                    color="primary"
                    className="button"
                    onClick={handleRegister}
                >
                    Register
                </Button>
                {errorMessage && (
                    <Alert severity="error" sx={{mt: 2}}>
                        {errorMessage}
                    </Alert>
                )}
            </Paper>
        </div>
    );
}

export default Register;
