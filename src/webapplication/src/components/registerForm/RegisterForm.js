import { Button, Container, Grid, Paper, TextField, Checkbox, FormControlLabel, Alert, Snackbar } from '@mui/material';
import { Box } from '@mui/system';
import axios from 'axios';
import React, { useState } from 'react';

export const RegisterForm = () => {
    const [userRegistration, setUserRegistration] = useState({
        username: "",
        password: "",
        confirmPassword: ""
    });

    const URL = "http://localhost:8080";

    const [usernameError, setUsernameError] = useState(false);
    const [usernameErrorMessage, setUsernameMessageError] = useState("");

    const [passwordError, setPasswordError] = useState(false);
    const [passwordErrorMessage, setPasswordErrorMessage] = useState("");

    const [passwordConfirmError, setPasswordConfirmError] = useState(false);
    const [passwordConfirmErrorMessage, setPasswordConfirmErrorMessage] = useState("");

    const [isPasswordShowed, setIsPasswordShowed] = useState(false);
    const paperStyle = { padding: '20px 20px', width: 300, margin: "20px auto" }

    const handleUsername = (e) => {
        const value = e.target.value;
        const regex = /^[a-zA-Z0-9]+$/;
        const isRegexValid = regex.test(value);

        if (value.length < 5) {
            setUsernameError(true)
            setUsernameMessageError("Should be more than 5 characters");
        } else if (!isRegexValid) {
            setUsernameError(true);
            setUsernameMessageError("Should contains only letters and numbers");
        } else {
            setUsernameError(false)
            setUsernameMessageError("")
        }

        setUserRegistration({ ...userRegistration, username: value });
    }

    const handlePassword = (e) => {
        const value = e.target.value;
        const regex = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{7,}$/;
        const isRegexValid = regex.test(value);

        if (value.length < 8) {
            setPasswordError(true)
            setPasswordErrorMessage("Should be more than 8 characters");
        } else if (!isRegexValid) {
            setPasswordError(true);
            setPasswordErrorMessage("Should contains at least uppercase, lowercase, digit and symbol");
        } else {
            setPasswordError(false)
            setPasswordErrorMessage("")
        }

        if (userRegistration.confirmPassword.length > 0 && value !== userRegistration.confirmPassword) {
            setPasswordConfirmError(true);
        } else {
            setPasswordConfirmError(false);
        }

        setUserRegistration({ ...userRegistration, password: value });
    }

    const handleConfirmPassword = (e) => {
        const value = e.target.value;

        if (value !== userRegistration.password) {
            setPasswordConfirmError(true);
            setPasswordConfirmErrorMessage("Those passwords didn't match");
        } else {
            setPasswordConfirmError(false);
            setPasswordConfirmErrorMessage("");
        }

        setUserRegistration({ ...userRegistration, confirmPassword: value });
    }

    const handleSubmit = (e) => {
        e.preventDefault();

        if (userRegistration.username.length === 0) {
            setUsernameError(true);
            setUsernameMessageError("Username cannot be empty");
        }

        if (userRegistration.password.length === 0) {
            setPasswordError(true);
            setPasswordErrorMessage("Password cannot be empty");
        }

        if (userRegistration.confirmPassword.length === 0) {
            setPasswordConfirmError(true);
            setPasswordConfirmErrorMessage("Confirmed password cannot be empty")
        }

        if (!usernameError && !passwordError && !passwordConfirmError &&
            userRegistration.username.length !== 0 && userRegistration.password.length !== 0 && userRegistration.confirmPassword.length !== 0
        ) {
            console.log(userRegistration)
            axios.post(URL + '/api/user', {
                username: userRegistration.username,
                password: userRegistration.password
            })
                .then((response) => {
                    setOpenSuccess(true);
                    console.log(response);
                })
                .catch((error) => {
                    if (!error.response) {
                        console.log("Network connection error")
                        setOpenFailed(true);
                    }

                    if (error.response.status === 409) {
                        setUsernameError(true);
                        setUsernameMessageError("Username already taken. Pick another");
                    }

                    if (error.response.status === 400) {
                        const currentUsername = error.response.data.username;
                        const currentPassword = error.response.data.password;

                        if (currentUsername) {
                            setUsernameError(true);
                            setUsernameMessageError(error.response.data.username);
                        }

                        if (currentPassword !== undefined) {
                            setPasswordError(true)
                            setPasswordErrorMessage(error.response.data.password);
                        }
                    }
                    setOpenFailed(true);
                })
        } else {
            setOpenFailed(true)
        }
    }



    const cleanData = () => {
        setUserRegistration({ ...userRegistration, confirmPassword: "" });
        setUserRegistration({ ...userRegistration, password: "" });
        setUserRegistration({ ...userRegistration, username: "" });
    }


    const [openSuccess, setOpenSuccess] = React.useState(false);
    const [openFailed, setOpenFailed] = React.useState(false);

    const handleCloseSuccess = (event, reason) => {
        if (reason === 'clickaway') {
            return;
        }
        setOpenSuccess(false);
    };

    const handleCloseFailed = (event, reason) => {
        if (reason === 'clickaway') {
            return;
        }
        setOpenFailed(false);
    };

    return (
        <Container maxWidth="sm">
            <Grid>
                <Paper elevation={5} style={paperStyle} >
                    <h2>Create your account</h2>

                    <form onSubmit={handleSubmit}>
                        <Box mb={2}>
                            <TextField variant="outlined" label="Username" autoComplete='off' onChange={handleUsername} error={usernameError} helperText={usernameErrorMessage} fullWidth />
                        </Box>

                        <Box mb={2}>
                            <TextField variant="outlined" type={isPasswordShowed ? "text" : "password"} label="Password" autoComplete='off' onChange={handlePassword} error={passwordError} helperText={passwordErrorMessage}
                                fullWidth
                            />
                        </Box>

                        <Box mb={2}>
                            <TextField variant="outlined" type={isPasswordShowed ? "text" : "password"} label="Confirm password" autoComplete='off' onChange={handleConfirmPassword}
                                fullWidth error={passwordConfirmError} helperText={passwordConfirmErrorMessage}
                            />
                        </Box>

                        <FormControlLabel control={<Checkbox checked={isPasswordShowed} onChange={() => { setIsPasswordShowed(prevCheck => !prevCheck) }} />} label="Show password" />

                        <Button type='submit' variant='contained' color='primary' fullWidth> Sign up </Button>
                    </form>
                </Paper>
            </Grid>

            <Snackbar open={openSuccess} autoHideDuration={5000} onClose={handleCloseSuccess} anchorOrigin={{vertical: 'bottom', horizontal: 'center'}}>
                <Alert onClose={handleCloseSuccess} severity="success" variant="filled">
                    Account has been created
                </Alert>
            </Snackbar>

            <Snackbar open={openFailed} autoHideDuration={5000} onClose={handleCloseFailed} anchorOrigin={{vertical: 'bottom', horizontal: 'center'}}>
                <Alert onClose={handleCloseFailed} severity="error" variant="filled">
                    Account has not been created.
                </Alert>
            </Snackbar>

        </Container>


    );
};

