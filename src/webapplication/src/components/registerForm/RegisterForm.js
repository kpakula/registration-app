import { Button, Container, Grid, Paper, TextField } from '@mui/material';
import { Box } from '@mui/system';
import React, { useState } from 'react';

export const RegisterForm = () => {
    const [userRegistration, setUserRegistration] = useState({
        username: "",
        password: "",
        confirmPassword: ""
    });

    const [usernameError, setUsernameError] = useState(false);
    const [usernameErrorMessage, setUsernameMessageError] = useState("");

    const [passwordError, setPasswordError] = useState(false);
    const [passwordErrorMessage, setPasswordErrorMessage] = useState("");

    const paperStyle = { padding: '20px 20px', width: 300, margin: "20px auto" }

    const handleUsername = (e) => {
        const name = e.target.name;
        const value = e.target.value;
        const regex = /^[a-zA-Z0-9]+$/;
        const isRegexValid = regex.test(value);

        if (value.length < 5) {
            setUsernameError(true)
            setUsernameMessageError("Should be more than 5 characters");
        } else if (!isRegexValid) {
            setUsernameError(true);
            setUsernameMessageError("Should contains only letters and numbers");
        }   else {
            setUsernameError(false)
            setUsernameMessageError("")
        }

        setUserRegistration({ ...userRegistration, [name]: value });
    }

    const handlePassword = (e) => {
        const name = e.target.name;
        const value = e.target.value;

        console.log(name, value);
        setUserRegistration({ ...userRegistration, [name]: value });
    }

    const handleConfirmPassword = (e) => {
        const name = e.target.name;
        const value = e.target.value;


        console.log(name, value);
        setUserRegistration({ ...userRegistration, [name]: value });
    }

    const handleSubmit = (e) => {
        e.preventDefault();
        console.log(userRegistration)
    }


    return (

        <Container maxWidth="sm">
            <Grid>
                <Paper elevation={5} style={paperStyle} >
                    <h2>Create your account</h2>

                    <form onSubmit={handleSubmit}>
                        <Box mb={2}>
                            <TextField variant="outlined" label="Username" autoComplete='username' onChange={handleUsername} error={usernameError} helperText={usernameErrorMessage} fullWidth/>
                        </Box>

                        <Box mb={2}>
                            <TextField variant="outlined" label="Password" autoComplete='password' onChange={handlePassword} error={passwordError} helperText={passwordErrorMessage} 
                                fullWidth
                            />
                        </Box>

                        <Box mb={2}>
                            <TextField variant="outlined" label="Confirm password" autoComplete='confirmPassword' onChange={handleConfirmPassword}
                                fullWidth
                            />
                        </Box>

                        <Button type='submit' variant='contained' color='primary' fullWidth> Sign up </Button>
                    </form>
                </Paper>
            </Grid>

            {/* <div className='container'>
                <form onSubmit={handleSubmit}>
                    <div className='username-input'>
                        <label htmlFor='username'>username</label>
                        <input type='text' value={userRegistration.username} onChange={handleUsername} name='username' id='username' />
                    </div>
                    <div className='password-input'>
                        <label htmlFor='password'>password</label>
                        <input type='password' value={userRegistration.password} onChange={handlePassword} name='password' id='password' />
                    </div>
                    <button type='submit'>Sign up</button>
                </form> */}

            {/* <div className='output-info'>
                <p>{userRegistration.username}</p>
                <p>{userRegistration.password}</p>
            </div> */}


            {/* </div> */}
        </Container>


    );
};

